import gui.AppFrame;
import dao.CaballoRepository;

import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        
        // 1. Cargamos los caballos en la base de datos (si es que no existen)
        try {
            CaballoRepository.getInstance().cargarDatosCaballo();
            System.out.println("Base de datos inicializada correctamente.");
        } catch (Exception e) {
            System.err.println("Error al conectar con la base de datos: " + e.getMessage());
        }

        // 2. Iniciamos la interfaz gráfica de forma segura
        SwingUtilities.invokeLater(() -> {
            AppFrame frame = new AppFrame();
            frame.setVisible(true);
        });
    }
}