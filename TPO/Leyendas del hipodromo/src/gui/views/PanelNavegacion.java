package gui.views;

import gui.AppFrame;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

public class PanelNavegacion extends JPanel implements ActionListener {
    
    // Esta es la interfaz que ya implementamos en el AppFrame
    public interface ListenerNavegacion {
        void alNavegar(String destino);
    }

    private final ListenerNavegacion listener;
    private final Map<String, JButton> botones = new HashMap<>();

    public PanelNavegacion(ListenerNavegacion listener) {
        this.listener = listener;
        setLayout(new GridLayout(0, 1, 0, 10));
        setBorder(BorderFactory.createEmptyBorder(16, 12, 16, 12));
        setBackground(new Color(226, 232, 240));

        // Actualizamos a las constantes en español que definimos en AppFrame
        add(crearBotonNav("Crear jugadores", AppFrame.CREAR_JUGADOR));
        add(crearBotonNav("Editar jugadores", AppFrame.EDITAR_JUGADOR));
        add(crearBotonNav("Iniciar carrera", AppFrame.CONFIGURACION_CARRERA));
        add(crearBotonNav("Ver puntajes", AppFrame.PUNTAJES));
    }

    private JButton crearBotonNav(String texto, String destino) {
        JButton boton = new JButton(texto);
        boton.setActionCommand(destino);
        boton.setFocusPainted(false);
        boton.addActionListener(this);
        boton.setBackground(Color.WHITE);
        boton.setBorder(BorderFactory.createLineBorder(new Color(148, 163, 184), 1, true));
        botones.put(destino, boton);
        return boton;
    }

    public void setBotonActivo(String destino) {
        botones.forEach((clave, boton) -> {
            if (clave.equals(destino)) {
                boton.setBackground(new Color(37, 99, 235));
                boton.setForeground(Color.WHITE);
            } else {
                boton.setBackground(Color.WHITE);
                boton.setForeground(Color.BLACK);
            }
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        listener.alNavegar(e.getActionCommand());
    }
}