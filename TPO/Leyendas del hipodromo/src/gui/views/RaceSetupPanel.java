package gui.views;

import gui.AppFrame;
import gui.models.PlayerInfo;
import gui.dto.PlayerInfo;
import javax.swing.*;

import controllers.UiController;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class RaceSetupPanel extends JPanel {
    private final AppFrame owner;
    private final JPanel playerCheckboxPanel;
    private final List<JCheckBox> playerCheckBoxes = new ArrayList<>();
    private final JTextField raceNameField;
    private final JTextField distanceField;

    public RaceSetupPanel(AppFrame owner) {
        this.owner = owner;
        setLayout(new BorderLayout(12, 12));
        setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));

        JLabel title = new JLabel("Iniciar nueva carrera");
        title.setFont(title.getFont().deriveFont(Font.BOLD, 20f));
        add(title, BorderLayout.NORTH);

        JPanel form = new JPanel(new GridBagLayout());
        form.setBackground(Color.WHITE);
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(8, 8, 8, 8);
        c.anchor = GridBagConstraints.WEST;
        c.gridx = 0;
        c.gridy = 0;

        form.add(new JLabel("Nombre de la carrera:"), c);
        c.gridx = 1;
        raceNameField = new JTextField("Gran Derby", 18);
        form.add(raceNameField, c);

        c.gridx = 0;
        c.gridy++;
        form.add(new JLabel("Distancia (metros):"), c);
        c.gridx = 1;
        distanceField = new JTextField("1200", 18);
        form.add(distanceField, c);

        c.gridx = 0;
        c.gridy++;
        c.gridwidth = 2;
        form.add(new JLabel("Selecciona los jugadores para la carrera:"), c);

        playerCheckboxPanel = new JPanel(new GridLayout(0, 1, 4, 4));
        playerCheckboxPanel.setBorder(BorderFactory.createLineBorder(new Color(148, 163, 184)));
        playerCheckboxPanel.setBackground(new Color(248, 250, 252));
        c.gridy++;
        form.add(playerCheckboxPanel, c);

        JButton startButton = new JButton("Iniciar carrera");
        startButton.setBackground(new Color(37, 99, 235));
        startButton.setForeground(Color.WHITE);
        startButton.addActionListener(e -> startRace());
        c.gridy++;
        form.add(startButton, c);

        add(form, BorderLayout.CENTER);
        refreshPlayerList();
    }

    public void refreshPlayerList() {
        playerCheckboxPanel.removeAll();
        playerCheckBoxes.clear();

        for (PlayerInfo player : UiController.getJugadores()) {
            JCheckBox checkbox = new JCheckBox(player.getName() + " — " + player.getHorse().getName());
            checkbox.setBackground(new Color(248, 250, 252));
            playerCheckboxPanel.add(checkbox);
            playerCheckBoxes.add(checkbox);
        }

        revalidate();
        repaint();
    }

    private void startRace() {
        List<String> selectedPlayers = new ArrayList<>();
        for (int i = 0; i < playerCheckBoxes.size(); i++) {
            if (playerCheckBoxes.get(i).isSelected()) {
                selectedPlayers.add(UiController.getJugadores().get(i).getName());
            }
        }

        if (selectedPlayers.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Selecciona al menos un jugador para iniciar la carrera.", "Atención", JOptionPane.WARNING_MESSAGE);
            return;
        }

        owner.startRaceWithPlayers(selectedPlayers);
    }
}
