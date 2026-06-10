package gui;

import gui.views.PanelCrearJugador;
import gui.views.PanelEditarJugador;
import gui.views.PanelInicio;
import gui.views.PanelNavegacion;
import gui.views.PanelAnimacionCarrera;
import gui.views.PanelConfiguracionCarrera;
import gui.views.PanelPuntajes;

import controllers.ControllerCarrera;
import controllers.UiController;
import models.ProgresoCarrera;
import models.Jugador;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.stream.Collectors;

public class AppFrame extends JFrame implements PanelNavegacion.ListenerNavegacion, PanelInicio.ListenerInicio {
    public static final String INICIO = "inicio";
    public static final String CREAR_JUGADOR = "crearJugador";
    public static final String EDITAR_JUGADOR = "editarJugador";
    public static final String CONFIGURACION_CARRERA = "configuracionCarrera";
    public static final String ANIMACION_CARRERA = "animacionCarrera";
    public static final String PUNTAJES = "puntajes";

    private final JPanel panelContenedor;
    private final CardLayout cardLayout;
    private final PanelNavegacion panelNavegacion;
    private final PanelCrearJugador panelCrearJugador;
    private final PanelEditarJugador panelEditarJugador;
    private final PanelConfiguracionCarrera panelConfiguracionCarrera;
    private final PanelAnimacionCarrera panelAnimacionCarrera;
    private final PanelPuntajes panelPuntajes;

    public AppFrame() {
        super("Leyendas del Hipódromo");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1024, 720);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        add(crearBanner(), BorderLayout.NORTH);

        panelNavegacion = new PanelNavegacion(this);
        panelNavegacion.setVisible(false);
        add(panelNavegacion, BorderLayout.WEST);

        cardLayout = new CardLayout();
        panelContenedor = new JPanel(cardLayout);

        panelContenedor.add(new PanelInicio(this), INICIO);
        panelCrearJugador = new PanelCrearJugador(this::alCambiarJugadores);
        panelContenedor.add(panelCrearJugador, CREAR_JUGADOR);
        panelEditarJugador = new PanelEditarJugador(this::alCambiarJugadores);
        panelContenedor.add(panelEditarJugador, EDITAR_JUGADOR);
        panelConfiguracionCarrera = new PanelConfiguracionCarrera(this);
        panelContenedor.add(panelConfiguracionCarrera, CONFIGURACION_CARRERA);
        
        panelAnimacionCarrera = new PanelAnimacionCarrera(this::alCompletarCarrera, () -> mostrarPanel(CONFIGURACION_CARRERA));
        panelContenedor.add(panelAnimacionCarrera, ANIMACION_CARRERA);
        
        panelPuntajes = new PanelPuntajes();
        panelContenedor.add(panelPuntajes, PUNTAJES);

        add(panelContenedor, BorderLayout.CENTER);
        mostrarPanel(INICIO);
    }

    private JComponent crearBanner() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(37, 99, 235));
        panel.setBorder(BorderFactory.createEmptyBorder(12, 16, 12, 16));

        JLabel titulo = new JLabel("Leyendas del Hipódromo - Carrera de Caballos");
        titulo.setForeground(Color.WHITE);
        titulo.setFont(titulo.getFont().deriveFont(Font.BOLD, 24f));
        panel.add(titulo, BorderLayout.WEST);

        JLabel subtitulo = new JLabel("Juego de carreras con jugadores y caballos");
        subtitulo.setForeground(Color.WHITE);
        panel.add(subtitulo, BorderLayout.EAST);

        return panel;
    }

    public void mostrarPanel(String nombre) {
        if (INICIO.equals(nombre)) {
            panelNavegacion.setVisible(false);
        } else {
            panelNavegacion.setVisible(true);
            panelNavegacion.setBotonActivo(nombre);
        }
        cardLayout.show(panelContenedor, nombre);
    }

    public void iniciarCarreraConJugadores(List<String> nombresSeleccionados, int distanciaMeta) {
        List<Jugador> jugadoresParticipantes = UiController.getJugadoresModelo().stream()
                .filter(j -> nombresSeleccionados.contains(j.getNombre()))
                .collect(Collectors.toList());

        if (jugadoresParticipantes.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "No se encontraron jugadores para iniciar la carrera.",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        ProgresoCarrera progreso = ControllerCarrera.simularCarrera(jugadoresParticipantes, distanciaMeta);

        panelAnimacionCarrera.iniciarAnimacion(progreso);
        mostrarPanel(ANIMACION_CARRERA);
    }

    private void alCambiarJugadores() {
        panelConfiguracionCarrera.actualizarListaJugadores();
        panelEditarJugador.actualizarListaJugadores();
        panelPuntajes.actualizarPuntajes();
    }

    private void alCompletarCarrera() {
        panelPuntajes.actualizarPuntajes();
    }

    @Override
    public void alNavegar(String destino) {
        mostrarPanel(destino);
    }

    @Override
    public void alPresionarEntrar() {
        panelNavegacion.setVisible(true);
        mostrarPanel(CREAR_JUGADOR);
    }
}