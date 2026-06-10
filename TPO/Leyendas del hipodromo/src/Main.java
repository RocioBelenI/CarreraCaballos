import gui.AppFrame;
import dao.CaballoRepository;

import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {

        try {
            CaballoRepository.getInstance().cargarDatosCaballo();
            System.out.println("Base de datos inicializada correctamente.");
        } catch (Exception e) {
            System.err.println("Error al conectar con la base de datos: " + e.getMessage());
        }

        SwingUtilities.invokeLater(() -> {
            AppFrame frame = new AppFrame();
            frame.setVisible(true);
        });
    }
}