package gui.views;

import gui.AppFrame;
import dto.CaballoDTO;
import dto.JugadorDTO;
import controllers.UiController;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PanelConfiguracionCarrera extends JPanel {
    private final AppFrame ventanaPrincipal;
    private final JPanel panelCheckboxesJugadores;
    private final List<JCheckBox> checkboxesJugadores = new ArrayList<>();
    private final JTextField campoNombreCarrera;
    private final JTextField campoDistancia;

    public PanelConfiguracionCarrera(AppFrame ventanaPrincipal) {
        this.ventanaPrincipal = ventanaPrincipal;
        setLayout(new BorderLayout(12, 12));
        setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));

        JLabel titulo = new JLabel("Iniciar nueva carrera");
        titulo.setFont(titulo.getFont().deriveFont(Font.BOLD, 20f));
        add(titulo, BorderLayout.NORTH);

        JPanel formulario = new JPanel(new GridBagLayout());
        formulario.setBackground(Color.WHITE);
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(8, 8, 8, 8);
        c.anchor = GridBagConstraints.WEST;
        c.gridx = 0;
        c.gridy = 0;

        formulario.add(new JLabel("Nombre de la carrera:"), c);
        c.gridx = 1;
        campoNombreCarrera = new JTextField("Gran Derby", 18);
        formulario.add(campoNombreCarrera, c);

        c.gridx = 0;
        c.gridy++;
        formulario.add(new JLabel("Distancia (metros):"), c);
        c.gridx = 1;
        campoDistancia = new JTextField("1200", 18);
        formulario.add(campoDistancia, c);

        c.gridx = 0;
        c.gridy++;
        c.gridwidth = 2;
        formulario.add(new JLabel("Selecciona los jugadores para la carrera:"), c);

        panelCheckboxesJugadores = new JPanel(new GridLayout(0, 1, 4, 4));
        panelCheckboxesJugadores.setBorder(BorderFactory.createLineBorder(new Color(148, 163, 184)));
        panelCheckboxesJugadores.setBackground(new Color(248, 250, 252));
        c.gridy++;
        formulario.add(panelCheckboxesJugadores, c);

        JButton botonIniciar = new JButton("Iniciar carrera");
        botonIniciar.setBackground(new Color(37, 99, 235));
        botonIniciar.setForeground(Color.WHITE);
        botonIniciar.addActionListener(e -> iniciarCarrera());
        c.gridy++;
        formulario.add(botonIniciar, c);

        add(formulario, BorderLayout.CENTER);
        actualizarListaJugadores();
    }

    public void actualizarListaJugadores() {
        panelCheckboxesJugadores.removeAll();
        checkboxesJugadores.clear();

        // Creamos el diccionario relacionando el ID (String) del caballo con su Nombre usando los DTOs
        Map<String, String> mapaCaballos = new HashMap<>();
        for (CaballoDTO caballo : UiController.getCaballosDisponiblesParaUI()) {
            mapaCaballos.put(caballo.getId(), caballo.getNombre());
        }

        // Iteramos sobre los jugadores usando los DTOs para armar los checkboxes
        for (JugadorDTO jugador : UiController.getJugadoresParaUI()) {
            String nombreCaballo = mapaCaballos.getOrDefault(jugador.getCaballoId(), "Desconocido");
            
            JCheckBox checkbox = new JCheckBox(jugador.getNombre() + " — " + nombreCaballo);
            checkbox.setBackground(new Color(248, 250, 252));
            
            panelCheckboxesJugadores.add(checkbox);
            checkboxesJugadores.add(checkbox);
        }

        revalidate();
        repaint();
    }

    private void iniciarCarrera() {
        List<String> jugadoresSeleccionados = new ArrayList<>();
        List<JugadorDTO> todosLosJugadores = UiController.getJugadoresParaUI();

        for (int i = 0; i < checkboxesJugadores.size(); i++) {
            if (checkboxesJugadores.get(i).isSelected()) {
                jugadoresSeleccionados.add(todosLosJugadores.get(i).getNombre());
            }
        }

        if (jugadoresSeleccionados.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Selecciona al menos un jugador para iniciar la carrera.", "Atención", JOptionPane.WARNING_MESSAGE);
            return;
        }

        ventanaPrincipal.iniciarCarreraConJugadores(jugadoresSeleccionados); 
    }
}