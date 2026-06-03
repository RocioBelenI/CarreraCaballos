package gui.views;

import gui.components.HorseCardPanel;
import gui.models.HorseInfo;
import gui.dto.HorseInfo;
import javax.swing.*;

import controllers.UiController;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class CreatePlayerPanel extends JPanel {
    private final JTextField nameField;
    private final JPanel horseGrid;
    private final DefaultListModel<String> playerListModel;
    private final List<HorseCardPanel> horseCards = new ArrayList<>();
    private final Runnable onPlayersChanged;
    private HorseInfo selectedHorse;

    public CreatePlayerPanel(Runnable onPlayersChanged) {
        this.onPlayersChanged = onPlayersChanged;
        setLayout(new BorderLayout(12, 12));
        setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));

        JLabel title = new JLabel("Crear jugadores");
        title.setFont(title.getFont().deriveFont(Font.BOLD, 20f));
        add(title, BorderLayout.NORTH);

        JPanel formPanel = new JPanel(new BorderLayout(12, 12));

        JPanel leftPanel = new JPanel(new GridBagLayout());
        leftPanel.setBackground(Color.WHITE);
        GridBagConstraints cs = new GridBagConstraints();
        cs.gridx = 0;
        cs.gridy = 0;
        cs.anchor = GridBagConstraints.WEST;
        cs.insets = new Insets(6, 6, 6, 6);

        leftPanel.add(new JLabel("Nombre del jugador:"), cs);
        cs.gridy++;
        nameField = new JTextField(20);
        leftPanel.add(nameField, cs);

        cs.gridy++;
        JButton createButton = new JButton("Crear jugador");
        createButton.setBackground(new Color(37, 99, 235));
        createButton.setForeground(Color.WHITE);
        createButton.addActionListener(e -> createPlayer());
        leftPanel.add(createButton, cs);

        formPanel.add(leftPanel, BorderLayout.WEST);

        horseGrid = new JPanel(new GridLayout(0, 2, 10, 10));
        horseGrid.setBackground(new Color(248, 250, 252));
        populateHorseCards();

        JPanel horsePanel = new JPanel(new BorderLayout(8, 8));
        horsePanel.setBackground(new Color(248, 250, 252));
        horsePanel.setBorder(BorderFactory.createTitledBorder("Seleccionar caballo"));
        horsePanel.add(horseGrid, BorderLayout.CENTER);
        formPanel.add(horsePanel, BorderLayout.CENTER);

        add(formPanel, BorderLayout.CENTER);

        playerListModel = new DefaultListModel<>();
        JList<String> playerList = new JList<>(playerListModel);
        playerList.setBorder(BorderFactory.createTitledBorder("Jugadores creados"));
        add(new JScrollPane(playerList), BorderLayout.SOUTH);

        refreshPlayerList();
    }

    private void populateHorseCards() {
        for (HorseInfo horse : UiController.getCaballosDisponibles()) {
            HorseCardPanel card = new HorseCardPanel(horse);
            card.setSelectionListener(() -> selectHorse(card));
            horseCards.add(card);
            horseGrid.add(card);
        }
    }

    private void selectHorse(HorseCardPanel card) {
        for (HorseCardPanel cardPanel : horseCards) {
            cardPanel.setSelected(cardPanel == card);
        }
        selectedHorse = card.getHorse();
    }

    private void createPlayer() {
        String name = nameField.getText();
        if (selectedHorse == null) {
            JOptionPane.showMessageDialog(this, "Selecciona un caballo antes de crear el jugador.", "Falta información", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (!UiController.agregarJugador(name, selectedHorse)) {
            JOptionPane.showMessageDialog(this, "Ingresa un nombre válido para el jugador.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        nameField.setText("");
        refreshPlayerList();
        if (onPlayersChanged != null) {
            onPlayersChanged.run();
        }
        JOptionPane.showMessageDialog(this, "Jugador creado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
    }

    private void refreshPlayerList() {
        playerListModel.clear();
        UiController.getJugadores().forEach(player -> playerListModel.addElement(player.getName() + " — " + player.getHorse().getName()));
    }
}
