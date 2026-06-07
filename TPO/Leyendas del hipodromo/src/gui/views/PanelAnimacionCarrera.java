package gui.views;

import gui.components.PanelCarrilCarrera;

import controllers.ControllerCarrera;
import models.MomentoCarrera;
import models.ProgresoCarrera;
import controllers.UiController;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.Timer; 
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

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

        // Creamos el mapa relacionando el ID del caballo con su Nombre
        java.util.Map<Long, String> mapaCaballos = new java.util.HashMap<>();
        for (var caballo : UiController.getCaballosDisponibles()) {
            mapaCaballos.put(caballo.getId(), caballo.getNombre());
        }

        // Iteramos sobre los jugadores que van a correr
        jugadores.forEach(nombreJugador -> {
            Long caballoId = UiController.getJugadores().stream()
                    .filter(jugador -> jugador.getNombre().equals(nombreJugador))
                    .map(jugador -> jugador.getCaballoId())
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

        // Acá es donde el temporizador llama a avanzarAnimacion (antes stepAnimation) cada 900 milisegundos
        temporizador = new Timer(900, e -> avanzarAnimacion());
        temporizador.start();
    }

    // Este es el método que te faltaba, traducido de stepAnimation()
    private void avanzarAnimacion() {
        if (progresoCarrera == null || indiceActual >= progresoCarrera.getMomentos().size()) {
            if (temporizador != null) {
                temporizador.stop();
            }
            etiquetaEstado.setText("Carrera finalizada. Revisa los puntajes.");
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
            alCompletarCarrera.run();
        }
        indiceActual++;
    }
}