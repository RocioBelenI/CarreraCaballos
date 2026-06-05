package gui.views;

import gui.components.RaceLanePanel;

import controllers.ControllerCarrera;
import models.MomentoCarrera;
import models.ProgresoCarrera;
import controllers.UiController;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Timer;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class RaceAnimationPanel extends JPanel {
    private final JPanel lanesContainer;
    private final JLabel statusLabel;
    private final JButton againButton;
    private final Runnable onRaceCompleted;
    private final Runnable onStartAnotherRace;
    private Timer timer;
    private ProgresoCarrera raceProgress;
    private int currentIndex;
    private final Map<String, RaceLanePanel> laneMap = new java.util.LinkedHashMap<>();

    public RaceAnimationPanel(Runnable onRaceCompleted, Runnable onStartAnotherRace) {
        this.onRaceCompleted = onRaceCompleted;
        this.onStartAnotherRace = onStartAnotherRace;

        setLayout(new BorderLayout(12, 12));
        setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));
        setBackground(new Color(248, 250, 252));

        JLabel title = new JLabel("Animación de la carrera");
        title.setFont(title.getFont().deriveFont(Font.BOLD, 20f));
        add(title, BorderLayout.NORTH);

        lanesContainer = new JPanel();
        lanesContainer.setLayout(new BoxLayout(lanesContainer, BoxLayout.Y_AXIS));
        lanesContainer.setBackground(new Color(248, 250, 252));
        add(new JScrollPane(lanesContainer), BorderLayout.CENTER);

        JPanel footer = new JPanel(new BorderLayout(10, 10));
        footer.setBackground(new Color(248, 250, 252));

        statusLabel = new JLabel("Presiona iniciar carrera en el panel anterior.");
        footer.add(statusLabel, BorderLayout.CENTER);

        againButton = new JButton("Iniciar otra carrera");
        againButton.setBackground(new Color(37, 99, 235));
        againButton.setForeground(Color.WHITE);
        againButton.setEnabled(false);
        againButton.addActionListener(e -> onStartAnotherRace.run());
        footer.add(againButton, BorderLayout.EAST);

        add(footer, BorderLayout.SOUTH);
    }

    public void startAnimation(ProgresoCarrera progress) {
        this.raceProgress = progress;
        lanesContainer.removeAll();
        laneMap.clear();

        List<String> players = new ArrayList<>();
        if (!progress.getMoments().isEmpty()) {
            players.addAll(progress.getMoments().get(0).getProgressByPlayer().keySet());
        }

        if (players.isEmpty()) {
            statusLabel.setText("No hay jugadores seleccionados.");
            repaint();
            return;
        }

        players.forEach(name -> {
            String horseName = UiController.getJugadores().stream()
                    .filter(player -> player.getName().equals(name))
                    .map(player -> player.getHorse().getName())
                    .findFirst()
                    .orElse("Caballo");
            RaceLanePanel lane = new RaceLanePanel(name, horseName);
            laneMap.put(name, lane);
            lanesContainer.add(lane);
            lanesContainer.add(Box.createVerticalStrut(10));
        });

        currentIndex = 0;
        statusLabel.setText("Corriendo...");
        againButton.setEnabled(false);
        revalidate();
        repaint();

        if (timer != null && timer.isRunning()) {
            timer.stop();
        }

        timer = new Timer(900, e -> stepAnimation());
        timer.start();
    }

    private void stepAnimation() {
        if (raceProgress == null || currentIndex >= raceProgress.getMoments().size()) {
            if (timer != null) {
                timer.stop();
            }
            statusLabel.setText("Carrera finalizada. Revisa los puntajes.");
            againButton.setEnabled(true);
            return;
        }

        MomentoCarrera moment = raceProgress.getMoments().get(currentIndex);
        moment.getProgressByPlayer().forEach((player, percent) -> {
            RaceLanePanel lane = laneMap.get(player);
            if (lane != null) {
                lane.updateProgress(percent);
            }
        });

        if (currentIndex == raceProgress.getMoments().size() - 1) {
            UiController.asignarPuntaje(moment.getProgressByPlayer());
            onRaceCompleted.run();
        }
        currentIndex++;
    }
}
