package gui.components;

import javax.swing.*;
import java.awt.*;

public class PanelCarrilCarrera extends JPanel {
    private final JLabel etiquetaJugador;
    private final JProgressBar barraProgreso;

    public PanelCarrilCarrera(String nombreJugador, String nombreCaballo) {
        setLayout(new BorderLayout(10, 8));
        setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(148, 163, 184), 1, true),
                BorderFactory.createEmptyBorder(8, 8, 8, 8)));
        setBackground(Color.WHITE);

        etiquetaJugador = new JLabel(nombreJugador + " — " + nombreCaballo);
        etiquetaJugador.setFont(etiquetaJugador.getFont().deriveFont(Font.BOLD, 14f));
        add(etiquetaJugador, BorderLayout.NORTH);

        barraProgreso = new JProgressBar(0, 100);
        barraProgreso.setStringPainted(true);
        barraProgreso.setPreferredSize(new Dimension(100, 24));
        add(barraProgreso, BorderLayout.CENTER);
    }

    public void actualizarProgreso(int porcentaje) {
        barraProgreso.setValue(porcentaje);
    }
}