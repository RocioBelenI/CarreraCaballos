package gui.components;

import javax.swing.*;
import java.awt.*;

public class RaceLanePanel extends JPanel {
    private final JLabel playerLabel;
    private final JProgressBar progressBar;

    public RaceLanePanel(String playerName, String horseName) {
        setLayout(new BorderLayout(10, 8));
        setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(148, 163, 184), 1, true),
                BorderFactory.createEmptyBorder(8, 8, 8, 8)));
        setBackground(Color.WHITE);

        playerLabel = new JLabel(playerName + " — " + horseName);
        playerLabel.setFont(playerLabel.getFont().deriveFont(Font.BOLD, 14f));
        add(playerLabel, BorderLayout.NORTH);

        progressBar = new JProgressBar(0, 100);
        progressBar.setStringPainted(true);
        progressBar.setPreferredSize(new Dimension(100, 24));
        add(progressBar, BorderLayout.CENTER);
    }

    public void updateProgress(int percent) {
        progressBar.setValue(percent);
    }
}
