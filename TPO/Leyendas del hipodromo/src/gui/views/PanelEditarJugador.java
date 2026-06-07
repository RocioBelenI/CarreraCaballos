package gui.views;

import gui.components.PanelTarjetaCaballo;
import data.dao.CaballoDAO;
import data.dao.JugadorDAO;
import javax.swing.*;

import controllers.UiController;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class PanelEditarJugador extends JPanel {
    private final DefaultListModel<String> modeloLista;
    private final JList<String> listaJugadores;
    private final JTextField campoNombre;
    private final JPanel cuadriculaCaballos;
    private final List<PanelTarjetaCaballo> tarjetasCaballos = new ArrayList<>();
    private final Runnable alCambiarJugadores;
    private CaballoDAO caballoSeleccionado;
    private int indiceSeleccionado = -1;

    public PanelEditarJugador(Runnable alCambiarJugadores) {
        this.alCambiarJugadores = alCambiarJugadores;
        setLayout(new BorderLayout(12, 12));
        setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));

        JLabel titulo = new JLabel("Editar jugadores");
        titulo.setFont(titulo.getFont().deriveFont(Font.BOLD, 20f));
        add(titulo, BorderLayout.NORTH);

        JPanel contenido = new JPanel(new BorderLayout(12, 12));

        modeloLista = new DefaultListModel<>();
        listaJugadores = new JList<>(modeloLista);
        listaJugadores.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listaJugadores.addListSelectionListener(e -> cargarJugadorSeleccionado());
        contenido.add(new JScrollPane(listaJugadores), BorderLayout.WEST);

        JPanel formularioEdicion = new JPanel(new GridBagLayout());
        formularioEdicion.setBackground(Color.WHITE);
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(8, 8, 8, 8);
        c.gridx = 0;
        c.gridy = 0;
        c.anchor = GridBagConstraints.WEST;

        formularioEdicion.add(new JLabel("Nombre del jugador:"), c);
        c.gridx = 1;
        campoNombre = new JTextField(18);
        formularioEdicion.add(campoNombre, c);

        c.gridx = 0;
        c.gridy++;
        c.gridwidth = 2;
        cuadriculaCaballos = new JPanel(new GridLayout(0, 2, 10, 10));
        cuadriculaCaballos.setBackground(new Color(248, 250, 252));
        formularioEdicion.add(new JScrollPane(cuadriculaCaballos), c);

        c.gridy++;
        JButton botonGuardar = new JButton("Guardar cambios");
        botonGuardar.setBackground(new Color(37, 99, 235));
        botonGuardar.setForeground(Color.WHITE);
        botonGuardar.addActionListener(e -> guardarCambios());
        formularioEdicion.add(botonGuardar, c);

        contenido.add(formularioEdicion, BorderLayout.CENTER);
        add(contenido, BorderLayout.CENTER);

        cargarTarjetasCaballos();
        actualizarListaJugadores();
    }

    private void cargarTarjetasCaballos() {
        cuadriculaCaballos.removeAll();
        tarjetasCaballos.clear();

        for (CaballoDAO caballo : UiController.getCaballosDisponibles()) {
            PanelTarjetaCaballo tarjeta = new PanelTarjetaCaballo(caballo);
            tarjeta.setListenerSeleccion(() -> seleccionarCaballo(tarjeta));
            tarjetasCaballos.add(tarjeta);
            cuadriculaCaballos.add(tarjeta);
        }
    }

    private void seleccionarCaballo(PanelTarjetaCaballo tarjeta) {
        for (PanelTarjetaCaballo tarjetaCaballo : tarjetasCaballos) {
            tarjetaCaballo.setSeleccionado(tarjetaCaballo == tarjeta);
        }
        caballoSeleccionado = tarjeta.getCaballo();
    }

    public void actualizarListaJugadores() {
        modeloLista.clear();
        List<CaballoDAO> caballos = UiController.getCaballosDisponibles();
        java.util.Map<Long, String> mapaCaballos = new java.util.HashMap<>();
        for (CaballoDAO caballo : caballos) {
            mapaCaballos.put(caballo.getId(), caballo.getNombre()); 
        }
        UiController.getJugadores().forEach(jugador -> modeloLista.addElement(jugador.getNombre() + " — " + mapaCaballos.getOrDefault(jugador.getCaballoId(), "Desconocido")));
    }

private void cargarJugadorSeleccionado() {
        indiceSeleccionado = listaJugadores.getSelectedIndex();
        if (indiceSeleccionado < 0) {
            campoNombre.setText("");
            caballoSeleccionado = null;
            tarjetasCaballos.forEach(tarjeta -> tarjeta.setSeleccionado(false));
            return;
        }

        JugadorDAO jugador = UiController.getJugadores().get(indiceSeleccionado);
        campoNombre.setText(jugador.getNombre());
        
        caballoSeleccionado = UiController.getCaballosDisponibles().stream()
                .filter(caballo -> caballo.getId().equals(jugador.getCaballoId()))
                .findFirst()
                .orElse(null); // Si por algún motivo no lo encuentra, queda en null

        tarjetasCaballos.forEach(tarjeta -> {
            boolean esElCaballoSeleccionado = (caballoSeleccionado != null) 
                    && tarjeta.getCaballo().getId().equals(caballoSeleccionado.getId());
                    
            tarjeta.setSeleccionado(esElCaballoSeleccionado);
        });
    }

    private void guardarCambios() {
        if (indiceSeleccionado < 0) {
            JOptionPane.showMessageDialog(this, "Selecciona un jugador para editar.", "Atención", JOptionPane.WARNING_MESSAGE);
            return;
        }
        String nombre = campoNombre.getText();
        if (nombre.isBlank() || caballoSeleccionado == null) {
            JOptionPane.showMessageDialog(this, "Completa el nombre y selecciona un caballo.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        UiController.actualizarJugador(indiceSeleccionado, nombre, caballoSeleccionado);
        actualizarListaJugadores();
        if (alCambiarJugadores != null) {
            alCambiarJugadores.run();
        }
        JOptionPane.showMessageDialog(this, "Jugador actualizado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
    }
}