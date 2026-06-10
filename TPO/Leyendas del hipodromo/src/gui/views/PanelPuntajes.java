package gui.views;

import dto.CaballoDTO;
import dto.JugadorDTO;
import controllers.UiController;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
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

        Map<String, String> mapaCaballos = new HashMap<>();
        for (CaballoDTO caballo : UiController.getCaballosDisponiblesParaUI()) {
            mapaCaballos.put(caballo.getId(), caballo.getNombre());
        }

        UiController.getJugadoresParaUI().stream()
                .sorted(Comparator.comparingInt(JugadorDTO::getPuntaje).reversed())
                .forEach(jugador -> {
                    String nombreCaballo = mapaCaballos.getOrDefault(jugador.getCaballoId(), "Desconocido");

                    modeloTabla.addRow(new Object[]{
                            jugador.getNombre(),
                            nombreCaballo,
                            jugador.getPuntaje()
                    });
                });
    }
}