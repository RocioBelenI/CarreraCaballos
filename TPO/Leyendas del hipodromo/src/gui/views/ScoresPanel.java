package gui.views;

import gui.models.PlayerInfo;
import gui.dto.PlayerInfo;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import controllers.UiController;

import java.awt.*;
import java.util.Comparator;

public class ScoresPanel extends JPanel {
    private final DefaultTableModel tableModel;

    public ScoresPanel() {
        setLayout(new BorderLayout(12, 12));
        setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));

        JLabel title = new JLabel("Puntajes de los jugadores");
        title.setFont(title.getFont().deriveFont(Font.BOLD, 20f));
        add(title, BorderLayout.NORTH);

        tableModel = new DefaultTableModel(new Object[]{"Jugador", "Caballo", "Puntaje"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        JTable table = new JTable(tableModel);
        table.setFillsViewportHeight(true);
        add(new JScrollPane(table), BorderLayout.CENTER);

        refreshScores();
    }

    public void refreshScores() {
        tableModel.setRowCount(0);
        UiController.getJugadores().stream()
                .sorted(Comparator.comparingInt(PlayerInfo::getScore).reversed())
                .forEach(player -> tableModel.addRow(new Object[]{
                        player.getName(),
                        player.getHorse().getName(),
                        player.getScore()
                }));
    }
}
