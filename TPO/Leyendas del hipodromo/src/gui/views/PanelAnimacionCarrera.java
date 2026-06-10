package gui.views;

import gui.components.PanelCarrilCarrera;
import controllers.UiController;
import dto.CaballoDTO;
import dto.JugadorDTO; // Asumimos la existencia de un DTO similar para Jugador
import models.MomentoCarrera;
import models.ProgresoCarrera;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PanelAnimacionCarrera extends JPanel {
    private final JPanel contenedorCarriles;
    private final JLabel etiquetaEstado;
    private final JButton botonOtraVez;
    private final Runnable alCompletarCarrera;
    private final Runnable alIniciarOtraCarrera;
    private Timer temporizador;
    private ProgresoCarrera progresoCarrera;
    private int indiceActual;
    private final Map<String, PanelCarrilCarrera> mapaCarriles = new java.util.LinkedHashMap<>();

    public PanelAnimacionCarrera(Runnable alCompletarCarrera, Runnable alIniciarOtraCarrera) {
        this.alCompletarCarrera = alCompletarCarrera;
        this.alIniciarOtraCarrera = alIniciarOtraCarrera;

        setLayout(new BorderLayout(12, 12));
        setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));
        setBackground(new Color(248, 250, 252));

        JLabel titulo = new JLabel("Animación de la carrera");
        titulo.setFont(titulo.getFont().deriveFont(Font.BOLD, 20f));
        add(titulo, BorderLayout.NORTH);

        contenedorCarriles = new JPanel();
        contenedorCarriles.setLayout(new BoxLayout(contenedorCarriles, BoxLayout.Y_AXIS));
        contenedorCarriles.setBackground(new Color(248, 250, 252));
        add(new JScrollPane(contenedorCarriles), BorderLayout.CENTER);

        JPanel pieDePagina = new JPanel(new BorderLayout(10, 10));
        pieDePagina.setBackground(new Color(248, 250, 252));

        etiquetaEstado = new JLabel("Presiona iniciar carrera en el panel anterior.");
        pieDePagina.add(etiquetaEstado, BorderLayout.CENTER);

        botonOtraVez = new JButton("Iniciar otra carrera");
        botonOtraVez.setBackground(new Color(37, 99, 235));
        botonOtraVez.setForeground(Color.WHITE);
        botonOtraVez.setEnabled(false);
        botonOtraVez.addActionListener(e -> alIniciarOtraCarrera.run());
        pieDePagina.add(botonOtraVez, BorderLayout.EAST);

        add(pieDePagina, BorderLayout.SOUTH);
    }

    public void iniciarAnimacion(ProgresoCarrera progreso) {
        this.progresoCarrera = progreso;
        contenedorCarriles.removeAll();
        mapaCarriles.clear();

        List<String> jugadores = new ArrayList<>();
        if (!progreso.getMomentos().isEmpty()) {
            jugadores.addAll(progreso.getMomentos().get(0).getProgresoPorJugador().keySet());
        }

        if (jugadores.isEmpty()) {
            etiquetaEstado.setText("No hay jugadores seleccionados.");
            repaint();
            return;
        }

        java.util.Map<String, String> mapaCaballos = new java.util.HashMap<>();
        for (CaballoDTO caballo : UiController.getCaballosDisponiblesParaUI()) {
            mapaCaballos.put(caballo.getId(), caballo.getNombre());
        }

        jugadores.forEach(nombreJugador -> {
            String caballoId = UiController.getJugadoresParaUI().stream()
                    .filter(jugador -> jugador.getNombre().equals(nombreJugador))
                    .map(JugadorDTO::getCaballoId)
                    .findFirst()
                    .orElse(null);

            String nombreCaballo = mapaCaballos.getOrDefault(caballoId, "Desconocido");

            PanelCarrilCarrera carril = new PanelCarrilCarrera(nombreJugador, nombreCaballo);
            mapaCarriles.put(nombreJugador, carril);
            contenedorCarriles.add(carril);
            contenedorCarriles.add(Box.createVerticalStrut(10));
        });

        indiceActual = 0;
        etiquetaEstado.setText("Corriendo...");
        botonOtraVez.setEnabled(false);
        revalidate();
        repaint();

        if (temporizador != null && temporizador.isRunning()) {
            temporizador.stop();
        }

        // El temporizador llama a avanzarAnimacion cada 900 milisegundos
        temporizador = new Timer(900, e -> avanzarAnimacion());
        temporizador.start();
    }

    private void avanzarAnimacion() {
        if (progresoCarrera == null || indiceActual >= progresoCarrera.getMomentos().size()) {
            if (temporizador != null) {
                temporizador.stop();
            }
            botonOtraVez.setEnabled(true);
            return;
        }

        MomentoCarrera momento = progresoCarrera.getMomentos().get(indiceActual);
        momento.getProgresoPorJugador().forEach((jugador, porcentaje) -> {
            PanelCarrilCarrera carril = mapaCarriles.get(jugador);
            if (carril != null) {
                carril.actualizarProgreso(porcentaje);
            }
        });

        if (indiceActual == progresoCarrera.getMomentos().size() - 1) {
            UiController.asignarPuntaje(momento.getProgresoPorJugador());

            List<Map.Entry<String, Integer>> resultados = new ArrayList<>(momento.getProgresoPorJugador().entrySet());
            resultados.sort((e1, e2) -> e2.getValue().compareTo(e1.getValue()));
            
            if (!resultados.isEmpty()) {
                int maxPorcentaje = resultados.get(0).getValue();
                List<String> ganadores = new ArrayList<>();
                for (Map.Entry<String, Integer> entrada : resultados) {
                    if (entrada.getValue() == maxPorcentaje) {
                        ganadores.add(entrada.getKey());
                    }
                }
                
                if (ganadores.size() == 1) {
                    etiquetaEstado.setText("🏆 ¡Ganó " + ganadores.get(0) + "!");
                } else {
                    etiquetaEstado.setText("🏆 ¡Empate! Ganaron: " + String.join(", ", ganadores));
                }
            } else {
                etiquetaEstado.setText("Carrera finalizada. Revisa los puntajes.");
            }
            
            botonOtraVez.setEnabled(true);
            alCompletarCarrera.run();
        }
        indiceActual++;
    }
}