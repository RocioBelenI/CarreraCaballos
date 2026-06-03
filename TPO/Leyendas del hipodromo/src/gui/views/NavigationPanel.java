package gui.views;

import gui.AppFrame;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

public class NavigationPanel extends JPanel implements ActionListener {
    public interface NavigationListener {
        void onNavigate(String destination);
    }

    private final NavigationListener listener;
    private final Map<String, JButton> buttons = new HashMap<>();

    public NavigationPanel(NavigationListener listener) {
        this.listener = listener;
        setLayout(new GridLayout(0, 1, 0, 10));
        setBorder(BorderFactory.createEmptyBorder(16, 12, 16, 12));
        setBackground(new Color(226, 232, 240));

        add(createNavButton("Crear jugadores", AppFrame.CREATE_PLAYER));
        add(createNavButton("Editar jugadores", AppFrame.EDIT_PLAYER));
        add(createNavButton("Iniciar carrera", AppFrame.RACE_SETUP));
        add(createNavButton("Ver puntajes", AppFrame.SCORES));
    }

    private JButton createNavButton(String text, String destination) {
        JButton button = new JButton(text);
        button.setActionCommand(destination);
        button.setFocusPainted(false);
        button.addActionListener(this);
        button.setBackground(Color.WHITE);
        button.setBorder(BorderFactory.createLineBorder(new Color(148, 163, 184), 1, true));
        buttons.put(destination, button);
        return button;
    }

    public void setActiveButton(String destination) {
        buttons.forEach((key, button) -> {
            if (key.equals(destination)) {
                button.setBackground(new Color(37, 99, 235));
                button.setForeground(Color.WHITE);
            } else {
                button.setBackground(Color.WHITE);
                button.setForeground(Color.BLACK);
            }
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        listener.onNavigate(e.getActionCommand());
    }
}
