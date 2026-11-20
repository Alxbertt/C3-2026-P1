package biblioteca.vista;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.Insets;

/**
 * Utilidades comunes de la interfaz (diálogos en español y filas de formulario).
 */
final class Ui {
    private Ui() {
    }

    /** Agrega una fila "etiqueta + campo" a un panel con GridBagLayout. */
    static void fila(JPanel panel, int y, String etiqueta, Component campo, boolean crecerVertical) {
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = y;
        c.anchor = crecerVertical ? GridBagConstraints.NORTHWEST : GridBagConstraints.WEST;
        c.insets = new Insets(4, 6, 4, 8);
        panel.add(new JLabel(etiqueta), c);

        GridBagConstraints f = new GridBagConstraints();
        f.gridx = 1;
        f.gridy = y;
        f.weightx = 1;
        f.weighty = crecerVertical ? 1 : 0;
        f.fill = crecerVertical ? GridBagConstraints.BOTH : GridBagConstraints.HORIZONTAL;
        f.insets = new Insets(4, 0, 4, 6);
        panel.add(campo, f);
    }

    static void error(Component padre, String mensaje) {
        JOptionPane.showMessageDialog(padre, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }

    static void info(Component padre, String mensaje, String titulo) {
        JOptionPane.showMessageDialog(padre, mensaje, titulo, JOptionPane.INFORMATION_MESSAGE);
    }

    static boolean confirmar(Component padre, String mensaje, String titulo) {
        Object[] opciones = {"Sí", "No"};
        int r = JOptionPane.showOptionDialog(padre, mensaje, titulo, JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE, null, opciones, opciones[1]);
        return r == 0;
    }
}
