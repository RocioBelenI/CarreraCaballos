package gui.views;

import data.dao.JugadorDAO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import controllers.UiController;

import java.awt.*;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class PanelPuntajes extends JPanel {
    private final DefaultTableModel modeloTabla;

    public PanelPuntajes() {
        setLayout(new BorderLayout(12, 12));
        setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));

        JLabel titulo = new JLabel("Puntajes de los jugadores");
        titulo.setFont(titulo.getFont().deriveFont(Font.BOLD, 20f));
        add(titulo, BorderLayout.NORTH);

        modeloTabla = new DefaultTableModel(new Object[]{"Jugador", "Caballo", "Puntaje"}, 0) {
            @Override
            public boolean isCellEditable(int fila, int columna) {
                return false; // Evita que el usuario edite la tabla haciéndole doble clic
            }
        };

        JTable tabla = new JTable(modeloTabla);
        tabla.setFillsViewportHeight(true);
        add(new JScrollPane(tabla), BorderLayout.CENTER);

        actualizarPuntajes();
    }

    public void actualizarPuntajes() {
        modeloTabla.setRowCount(0); // Limpia la tabla antes de volver a llenarla

        // 1. Creamos el diccionario (Map) relacionando ID -> Nombre del caballo
        Map<Long, String> mapaCaballos = new HashMap<>();
        for (var caballo : UiController.getCaballosDisponibles()) {
            mapaCaballos.put(caballo.getId(), caballo.getNombre());
        }

        // 2. Traemos a los jugadores, los ordenamos por puntaje de mayor a menor y los agregamos
        UiController.getJugadores().stream()
                .sorted(Comparator.comparingInt(JugadorDAO::getPuntaje).reversed())
                .forEach(jugador -> {
                    // Buscamos el nombre del caballo en el diccionario
                    String nombreCaballo = mapaCaballos.getOrDefault(jugador.getCaballoId(), "Desconocido");
                    
                    // Agregamos la fila a la tabla
                    modeloTabla.addRow(new Object[]{
                            jugador.getNombre(),
                            nombreCaballo,
                            jugador.getPuntaje()
                    });
                });
    }
}