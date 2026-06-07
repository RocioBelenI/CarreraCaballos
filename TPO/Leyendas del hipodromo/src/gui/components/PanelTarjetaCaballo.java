package gui.components;

import javax.swing.*;
import javax.swing.border.LineBorder;

import data.dao.CaballoDAO;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class PanelTarjetaCaballo extends JPanel {
    private final CaballoDAO caballo;
    private boolean seleccionado;

    public PanelTarjetaCaballo(CaballoDAO caballo) {
        this.caballo = caballo;
        setLayout(new BorderLayout(6, 6));
        setBorder(new LineBorder(new Color(148, 163, 184), 1, true));
        setBackground(Color.WHITE);
        setPreferredSize(new Dimension(160, 140));

        JLabel etiquetaNombre = new JLabel(caballo.getEmoji() + " " + caballo.getNombre());
        etiquetaNombre.setFont(etiquetaNombre.getFont().deriveFont(Font.BOLD, 14f));
        add(etiquetaNombre, BorderLayout.NORTH);

        JPanel estadisticas = new JPanel(new GridLayout(2, 1, 4, 4));
        estadisticas.setOpaque(false);
        estadisticas.add(crearBarra("Velocidad", caballo.getVelocidad(), new Color(37, 99, 235)));
        estadisticas.add(crearBarra("Resistencia", caballo.getResistencia(), new Color(244, 114, 33)));
        add(estadisticas, BorderLayout.CENTER);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                alternarSeleccion();
            }
        });
    }

    private JPanel crearBarra(String etiqueta, int valor, Color color) {
        JPanel panel = new JPanel(new BorderLayout(4, 4));
        panel.setOpaque(false);

        JLabel texto = new JLabel(etiqueta + " " + valor);
        texto.setFont(texto.getFont().deriveFont(12f));
        panel.add(texto, BorderLayout.NORTH);

        JProgressBar barra = new JProgressBar(0, 100);
        barra.setValue(valor);
        barra.setForeground(color);
        barra.setStringPainted(false);
        panel.add(barra, BorderLayout.CENTER);

        return panel;
    }

    public CaballoDAO getCaballo() {
        return caballo;
    }

    public void setSeleccionado(boolean seleccionado) {
        this.seleccionado = seleccionado;
        setBorder(new LineBorder(seleccionado ? new Color(37, 99, 235) : new Color(148, 163, 184), 2, true));
        setBackground(seleccionado ? new Color(224, 242, 254) : Color.WHITE);
        repaint();
    }

    public boolean isSeleccionado() {
        return seleccionado;
    }

    public void setListenerSeleccion(Runnable listener) {
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                listener.run();
            }
        });
    }

    private void alternarSeleccion() {
        setSeleccionado(!seleccionado);
    }
}