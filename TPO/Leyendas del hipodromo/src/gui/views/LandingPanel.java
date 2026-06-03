package gui.views;

import gui.AppFrame;

import javax.swing.*;
import java.awt.*;

public class LandingPanel extends JPanel {
    public interface LandingListener {
        void onEnterPressed();
    }

    public LandingPanel(LandingListener listener) {
        setLayout(new GridBagLayout());
        setBackground(new Color(248, 250, 252));

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(10, 10, 10, 10);
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.anchor = GridBagConstraints.CENTER;

        JLabel title = new JLabel("Bienvenido a la Carrera de Caballos");
        title.setFont(title.getFont().deriveFont(Font.BOLD, 28f));
        add(title, constraints);

        constraints.gridy = 1;
        JLabel text = new JLabel("Presiona Entrar para comenzar a crear jugadores y correr la carrera.");
        text.setFont(text.getFont().deriveFont(16f));
        add(text, constraints);

        constraints.gridy = 2;
        JButton enterButton = new JButton("Entrar al juego");
        enterButton.setBackground(new Color(37, 99, 235));
        enterButton.setForeground(Color.WHITE);
        enterButton.setPreferredSize(new Dimension(180, 44));
        enterButton.addActionListener(e -> listener.onEnterPressed());
        add(enterButton, constraints);
    }
}
