package gui.views;

import gui.components.HorseCardPanel;
import dto.CaballoDTO;
import dto.JugadorDTO;
import javax.swing.*;

import Controllers.UiController;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class EditPlayerPanel extends JPanel {
    private final DefaultListModel<String> listModel;
    private final JList<String> playerList;
    private final JTextField nameField;
    private final JPanel horseGrid;
    private final List<HorseCardPanel> horseCards = new ArrayList<>();
    private final Runnable onPlayersChanged;
    private CaballoDTO selectedHorse;
    private int selectedIndex = -1;

    public EditPlayerPanel(Runnable onPlayersChanged) {
        this.onPlayersChanged = onPlayersChanged;
        setLayout(new BorderLayout(12, 12));
        setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));

        JLabel title = new JLabel("Editar jugadores");
        title.setFont(title.getFont().deriveFont(Font.BOLD, 20f));
        add(title, BorderLayout.NORTH);

        JPanel content = new JPanel(new BorderLayout(12, 12));

        listModel = new DefaultListModel<>();
        playerList = new JList<>(listModel);
        playerList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        playerList.addListSelectionListener(e -> loadSelectedPlayer());
        content.add(new JScrollPane(playerList), BorderLayout.WEST);

        JPanel editForm = new JPanel(new GridBagLayout());
        editForm.setBackground(Color.WHITE);
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(8, 8, 8, 8);
        c.gridx = 0;
        c.gridy = 0;
        c.anchor = GridBagConstraints.WEST;

        editForm.add(new JLabel("Nombre del jugador:"), c);
        c.gridx = 1;
        nameField = new JTextField(18);
        editForm.add(nameField, c);

        c.gridx = 0;
        c.gridy++;
        c.gridwidth = 2;
        horseGrid = new JPanel(new GridLayout(0, 2, 10, 10));
        horseGrid.setBackground(new Color(248, 250, 252));
        editForm.add(new JScrollPane(horseGrid), c);

        c.gridy++;
        JButton saveButton = new JButton("Guardar cambios");
        saveButton.setBackground(new Color(37, 99, 235));
        saveButton.setForeground(Color.WHITE);
        saveButton.addActionListener(e -> saveChanges());
        editForm.add(saveButton, c);

        content.add(editForm, BorderLayout.CENTER);
        add(content, BorderLayout.CENTER);

        populateHorseCards();
        refreshPlayerList();
    }

    private void populateHorseCards() {
        horseGrid.removeAll();
        horseCards.clear();

        for (CaballoDTO horse : UiController.getCaballosDisponibles()) {
            HorseCardPanel card = new HorseCardPanel(horse);
            card.setSelectionListener(() -> selectHorse(card));
            horseCards.add(card);
            horseGrid.add(card);
        }
    }

    private void selectHorse(HorseCardPanel card) {
        for (HorseCardPanel horseCard : horseCards) {
            horseCard.setSelected(horseCard == card);
        }
        selectedHorse = card.getHorse();
    }

    public void refreshPlayerList() {
        listModel.clear();
        UiController.getJugadores().forEach(player -> listModel.addElement(player.getNombre() + " — " + player.getCaballo().getNombre()));
    }

    private void loadSelectedPlayer() {
        selectedIndex = playerList.getSelectedIndex();
        if (selectedIndex < 0) {
            nameField.setText("");
            selectedHorse = null;
            horseCards.forEach(card -> card.setSelected(false));
            return;
        }

        JugadorDTO player = UiController.getJugadores().get(selectedIndex);
        nameField.setText(player.getNombre());
        selectedHorse = player.getCaballo();
        horseCards.forEach(card -> card.setSelected(card.getHorse().getId().equals(selectedHorse.getId())));
    }

    private void saveChanges() {
        if (selectedIndex < 0) {
            JOptionPane.showMessageDialog(this, "Selecciona un jugador para editar.", "Atención", JOptionPane.WARNING_MESSAGE);
            return;
        }
        String name = nameField.getText();
        if (name.isBlank() || selectedHorse == null) {
            JOptionPane.showMessageDialog(this, "Completa el nombre y selecciona un caballo.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        UiController.actualizarJugador(selectedIndex, name, selectedHorse);
        refreshPlayerList();
        if (onPlayersChanged != null) {
            onPlayersChanged.run();
        }
        JOptionPane.showMessageDialog(this, "Jugador actualizado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
    }
}
