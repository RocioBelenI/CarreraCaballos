package gui.views;

import javax.swing.*;
import java.awt.*;

public class PanelInicio extends JPanel {
    
    // Esta es la interfaz que ya implementaste en AppFrame
    public interface ListenerInicio {
        void alPresionarEntrar();
    }

    public PanelInicio(ListenerInicio listener) {
        setLayout(new GridBagLayout());
        setBackground(new Color(248, 250, 252));

        GridBagConstraints restricciones = new GridBagConstraints();
        restricciones.insets = new Insets(10, 10, 10, 10);
        restricciones.gridx = 0;
        restricciones.gridy = 0;
        restricciones.anchor = GridBagConstraints.CENTER;

        JLabel titulo = new JLabel("Bienvenido a la Carrera de Caballos");
        titulo.setFont(titulo.getFont().deriveFont(Font.BOLD, 28f));
        add(titulo, restricciones);

        restricciones.gridy = 1;
        JLabel texto = new JLabel("Presiona Entrar para comenzar a crear jugadores y correr la carrera.");
        texto.setFont(texto.getFont().deriveFont(16f));
        add(texto, restricciones);

        restricciones.gridy = 2;
        JButton botonEntrar = new JButton("Entrar al juego");
        botonEntrar.setBackground(new Color(37, 99, 235));
        botonEntrar.setForeground(Color.WHITE);
        botonEntrar.setPreferredSize(new Dimension(180, 44));
        botonEntrar.addActionListener(e -> listener.alPresionarEntrar());
        add(botonEntrar, restricciones);
    }
}