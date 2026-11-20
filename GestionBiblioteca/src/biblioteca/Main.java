package biblioteca;

import biblioteca.servicio.Biblioteca;
import biblioteca.servicio.PersistenciaArchivo;
import biblioteca.vista.VentanaPrincipal;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

/**
 * Punto de entrada de la aplicación.
 */
public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            usarNimbus();
            Biblioteca biblioteca = new Biblioteca(new PersistenciaArchivo());
            new VentanaPrincipal(biblioteca).setVisible(true);
        });
    }

    private static void usarNimbus() {
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    return;
                }
            }
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignorada) {
            // Si falla, se usa el aspecto por defecto de Swing.
        }
    }
}
