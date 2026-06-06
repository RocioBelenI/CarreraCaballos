package gui.components;

import javax.swing.*;
import javax.swing.border.LineBorder;

import dto.CaballoDTO;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class HorseCardPanel extends JPanel {
    private final CaballoDTO horse;
    private boolean selected;

    public HorseCardPanel(CaballoDTO horse) {
        this.horse = horse;
        setLayout(new BorderLayout(6, 6));
        setBorder(new LineBorder(new Color(148, 163, 184), 1, true));
        setBackground(Color.WHITE);
        setPreferredSize(new Dimension(160, 140));

        JLabel nameLabel = new JLabel(horse.getEmoji() + " " + horse.getNombre());
        nameLabel.setFont(nameLabel.getFont().deriveFont(Font.BOLD, 14f));
        add(nameLabel, BorderLayout.NORTH);

        JPanel stats = new JPanel(new GridLayout(2, 1, 4, 4));
        stats.setOpaque(false);
        stats.add(createBar("Velocidad", horse.getVelocidad(), new Color(37, 99, 235)));
        stats.add(createBar("Resistencia", horse.getResistencia(), new Color(244, 114, 33)));
        add(stats, BorderLayout.CENTER);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                toggleSelection();
            }
        });
    }

    private JPanel createBar(String label, int value, Color color) {
        JPanel panel = new JPanel(new BorderLayout(4, 4));
        panel.setOpaque(false);

        JLabel text = new JLabel(label + " " + value);
        text.setFont(text.getFont().deriveFont(12f));
        panel.add(text, BorderLayout.NORTH);

        JProgressBar bar = new JProgressBar(0, 100);
        bar.setValue(value);
        bar.setForeground(color);
        bar.setStringPainted(false);
        panel.add(bar, BorderLayout.CENTER);

        return panel;
    }

    public CaballoDTO getHorse() {
        return horse;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
        setBorder(new LineBorder(selected ? new Color(37, 99, 235) : new Color(148, 163, 184), 2, true));
        setBackground(selected ? new Color(224, 242, 254) : Color.WHITE);
        repaint();
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelectionListener(Runnable listener) {
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                listener.run();
            }
        });
    }

    private void toggleSelection() {
        setSelected(!selected);
    }
}
