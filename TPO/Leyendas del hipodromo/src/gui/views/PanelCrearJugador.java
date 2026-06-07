package gui.views;

import gui.components.PanelTarjetaCaballo;
import data.dao.CaballoDAO;
import controllers.UiController;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class PanelCrearJugador extends JPanel {
    private final JTextField campoNombre;
    private final JPanel cuadriculaCaballos;
    private final DefaultListModel<String> modeloListaJugadores;
    private final List<PanelTarjetaCaballo> tarjetasCaballos = new ArrayList<>();
    private final Runnable alCambiarJugadores;
    private CaballoDAO caballoSeleccionado;

    public PanelCrearJugador(Runnable alCambiarJugadores) {
        this.alCambiarJugadores = alCambiarJugadores;
        setLayout(new BorderLayout(12, 12));
        setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));

        JLabel titulo = new JLabel("Crear jugadores");
        titulo.setFont(titulo.getFont().deriveFont(Font.BOLD, 20f));
        add(titulo, BorderLayout.NORTH);

        JPanel panelFormulario = new JPanel(new BorderLayout(12, 12));

        JPanel panelIzquierdo = new JPanel(new GridBagLayout());
        panelIzquierdo.setBackground(Color.WHITE);
        GridBagConstraints cs = new GridBagConstraints();
        cs.gridx = 0;
        cs.gridy = 0;
        cs.anchor = GridBagConstraints.WEST;
        cs.insets = new Insets(6, 6, 6, 6);

        panelIzquierdo.add(new JLabel("Nombre del jugador:"), cs);
        cs.gridy++;
        campoNombre = new JTextField(20);
        panelIzquierdo.add(campoNombre, cs);

        cs.gridy++;
        JButton botonCrear = new JButton("Crear jugador");
        botonCrear.setBackground(new Color(37, 99, 235));
        botonCrear.setForeground(Color.WHITE);
        botonCrear.addActionListener(e -> crearJugador());
        panelIzquierdo.add(botonCrear, cs);

        panelFormulario.add(panelIzquierdo, BorderLayout.WEST);

        cuadriculaCaballos = new JPanel(new GridLayout(0, 2, 10, 10));
        cuadriculaCaballos.setBackground(new Color(248, 250, 252));
        cargarTarjetasCaballos();

        JPanel panelCaballos = new JPanel(new BorderLayout(8, 8));
        panelCaballos.setBackground(new Color(248, 250, 252));
        panelCaballos.setBorder(BorderFactory.createTitledBorder("Seleccionar caballo"));
        panelCaballos.add(cuadriculaCaballos, BorderLayout.CENTER);
        panelFormulario.add(panelCaballos, BorderLayout.CENTER);

        add(panelFormulario, BorderLayout.CENTER);

        modeloListaJugadores = new DefaultListModel<>();
        JList<String> listaJugadores = new JList<>(modeloListaJugadores);
        listaJugadores.setBorder(BorderFactory.createTitledBorder("Jugadores creados"));
        add(new JScrollPane(listaJugadores), BorderLayout.SOUTH);

        actualizarListaJugadores();
    }

    private void cargarTarjetasCaballos() {
        for (CaballoDAO caballo : UiController.getCaballosDisponibles()) {
            PanelTarjetaCaballo tarjeta = new PanelTarjetaCaballo(caballo);
            tarjeta.setListenerSeleccion(() -> seleccionarCaballo(tarjeta));
            tarjetasCaballos.add(tarjeta);
            cuadriculaCaballos.add(tarjeta);
        }
    }

    private void seleccionarCaballo(PanelTarjetaCaballo tarjeta) {
        for (PanelTarjetaCaballo panelTarjeta : tarjetasCaballos) {
            panelTarjeta.setSeleccionado(panelTarjeta == tarjeta);
        }
        caballoSeleccionado = tarjeta.getCaballo();
    }

    private void crearJugador() {
        String nombre = campoNombre.getText();
        if (caballoSeleccionado == null) {
            JOptionPane.showMessageDialog(this, "Selecciona un caballo antes de crear el jugador.", "Falta información", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (!UiController.agregarJugador(nombre, caballoSeleccionado)) {
            JOptionPane.showMessageDialog(this, "Ingresa un nombre válido para el jugador.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        campoNombre.setText("");
        actualizarListaJugadores();
        if (alCambiarJugadores != null) {
            alCambiarJugadores.run();
        }
        JOptionPane.showMessageDialog(this, "Jugador creado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
    }

private void actualizarListaJugadores() {
        modeloListaJugadores.clear();
        List<CaballoDAO> caballos = UiController.getCaballosDisponibles();
        
        java.util.Map<Long, String> mapaCaballos = new java.util.HashMap<>();
        for (CaballoDAO caballo : caballos) {
            mapaCaballos.put(caballo.getId(), caballo.getNombre()); 
        }
        
        // 2. Iteramos sobre los jugadores usando el mapa para buscar el nombre
        UiController.getJugadores().forEach(jugador -> {
            // Buscamos el nombre del caballo por su ID. Si no lo encuentra, pone un texto por defecto.
            String nombreCaballo = mapaCaballos.getOrDefault(jugador.getCaballoId(), "Desconocido");
            
            modeloListaJugadores.addElement(jugador.getNombre() + " — " + nombreCaballo);
        });
    }
}