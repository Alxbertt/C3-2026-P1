import formularios.FrmInicio;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class Main {

    public static void main(String[] args) {

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            System.out.println("No se pudo aplicar el aspecto del sistema: " + e.getMessage());
        }

        SwingUtilities.invokeLater(() -> {
            FrmInicio inicio = new FrmInicio();
            inicio.setVisible(true);
        });
    }
}