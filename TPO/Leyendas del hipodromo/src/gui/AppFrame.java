package gui;

import gui.views.CreatePlayerPanel;
import gui.views.EditPlayerPanel;
import gui.views.LandingPanel;
import gui.views.NavigationPanel;
import gui.views.RaceAnimationPanel;
import gui.views.RaceSetupPanel;
import gui.views.ScoresPanel;

import javax.swing.*;

import Controllers.ControllerCarrera;
import Controllers.UiController;
import Models.Jugador;
import Models.ProgresoCarrera;

import java.awt.*;
import java.util.List;
import java.util.stream.Collectors;

public class AppFrame extends JFrame implements NavigationPanel.NavigationListener, LandingPanel.LandingListener {
    public static final String LANDING = "landing";
    public static final String CREATE_PLAYER = "createPlayer";
    public static final String EDIT_PLAYER = "editPlayer";
    public static final String RACE_SETUP = "raceSetup";
    public static final String RACE_ANIMATION = "raceAnimation";
    public static final String SCORES = "scores";

    private final JPanel contentCards;
    private final CardLayout cardLayout;
    private final NavigationPanel navigationPanel;
    private final CreatePlayerPanel createPlayerPanel;
    private final EditPlayerPanel editPlayerPanel;
    private final RaceSetupPanel raceSetupPanel;
    private final RaceAnimationPanel raceAnimationPanel;
    private final ScoresPanel scoresPanel;

    public AppFrame() {
        super("Leyendas del Hipódromo");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1024, 720);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        add(createBanner(), BorderLayout.NORTH);

        navigationPanel = new NavigationPanel(this);
        navigationPanel.setVisible(false);
        add(navigationPanel, BorderLayout.WEST);

        cardLayout = new CardLayout();
        contentCards = new JPanel(cardLayout);

        contentCards.add(new LandingPanel(this), LANDING);
        createPlayerPanel = new CreatePlayerPanel(this::onPlayersChanged);
        contentCards.add(createPlayerPanel, CREATE_PLAYER);
        editPlayerPanel = new EditPlayerPanel(this::onPlayersChanged);
        contentCards.add(editPlayerPanel, EDIT_PLAYER);
        raceSetupPanel = new RaceSetupPanel(this);
        contentCards.add(raceSetupPanel, RACE_SETUP);
        raceAnimationPanel = new RaceAnimationPanel(this::onRaceCompleted, () -> showPanel(RACE_SETUP));
        contentCards.add(raceAnimationPanel, RACE_ANIMATION);
        scoresPanel = new ScoresPanel();
        contentCards.add(scoresPanel, SCORES);

        add(contentCards, BorderLayout.CENTER);
        showPanel(LANDING);
    }

    private JComponent createBanner() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(37, 99, 235));
        panel.setBorder(BorderFactory.createEmptyBorder(12, 16, 12, 16));

        JLabel title = new JLabel("Leyendas del Hipódromo");
        title.setForeground(Color.WHITE);
        title.setFont(title.getFont().deriveFont(Font.BOLD, 24f));
        panel.add(title, BorderLayout.WEST);

        JLabel subtitle = new JLabel("Juego de carreras con jugadores y caballos");
        subtitle.setForeground(Color.WHITE);
        panel.add(subtitle, BorderLayout.EAST);

        return panel;
    }

    public void showPanel(String name) {
        if (LANDING.equals(name)) {
            navigationPanel.setVisible(false);
        } else {
            navigationPanel.setVisible(true);
            navigationPanel.setActiveButton(name);
        }
        cardLayout.show(contentCards, name);
    }

    /**
     * Inicia la animación de la carrera con un ProgresoCarrera ya calculado.
     * Punto de entrada genérico: el progreso puede venir del ControllerCarrera,
     * de un backend, o de cualquier otra fuente.
     */
    public void startRace(ProgresoCarrera progreso) {
        raceAnimationPanel.startAnimation(progreso);
        showPanel(RACE_ANIMATION);
    }

    /**
     * Orquesta la carrera a partir de los nombres de jugadores seleccionados:
     * busca los jugadores del modelo, simula la carrera con ControllerCarrera
     * y delega el inicio de la animación a startRace().
     * La distancia por defecto es 1200 metros.
     */
    public void startRaceWithPlayers(List<String> nombresSeleccionados) {
        List<Jugador> jugadoresParticipantes = UiController.getJugadoresModelo().stream()
                .filter(j -> nombresSeleccionados.contains(j.getNombre()))
                .collect(Collectors.toList());

        if (jugadoresParticipantes.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "No se encontraron jugadores para iniciar la carrera.",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        ProgresoCarrera progreso = ControllerCarrera.simularCarrera(jugadoresParticipantes, 1200);
        startRace(progreso);
    }

    private void onPlayersChanged() {
        raceSetupPanel.refreshPlayerList();
        editPlayerPanel.refreshPlayerList();
        scoresPanel.refreshScores();
    }

    private void onRaceCompleted() {
        scoresPanel.refreshScores();
    }

    @Override
    public void onNavigate(String destination) {
        showPanel(destination);
    }

    @Override
    public void onEnterPressed() {
        navigationPanel.setVisible(true);
        showPanel(CREATE_PLAYER);
    }
}
