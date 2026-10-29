import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
@SuppressWarnings("unused")


/**
 * Ejercicio 3 - Mini encuesta grafica.
 * JRadioButton (sistema operativo), JCheckBox (especialidades) y JSlider (horas 0-10).
 * El JLabel junto al slider se actualiza con el evento stateChanged.
 */
public class MiniEncuesta extends JFrame {

    private JRadioButton rbWindows, rbLinux, rbMac;
    private JCheckBox cbProgramacion, cbDiseno, cbAdministracion;
    private JSlider slHoras;
    private JLabel lblValor;
    private JButton btnGenerar;

    public MiniEncuesta() {
        super("Mini Encuesta");
        setSize(260, 520);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(null);

        // --- Sistema operativo ---
        JLabel lblSO = new JLabel("Elige un sistema operativo");
        lblSO.setBounds(20, 20, 200, 20);
        add(lblSO);

        rbWindows = new JRadioButton("Windows");
        rbLinux   = new JRadioButton("Linux");
        rbMac     = new JRadioButton("Mac");
        rbWindows.setBounds(25, 50, 120, 25);
        rbLinux.setBounds(25, 80, 120, 25);
        rbMac.setBounds(25, 110, 120, 25);
        ButtonGroup grupo = new ButtonGroup();   // solo una opcion
        grupo.add(rbWindows); grupo.add(rbLinux); grupo.add(rbMac);
        add(rbWindows); add(rbLinux); add(rbMac);

        JSeparator sep1 = new JSeparator();
        sep1.setBounds(10, 150, 225, 2);
        add(sep1);

        // --- Especialidad ---
        JLabel lblEsp = new JLabel("Elige tu especialidad");
        lblEsp.setBounds(20, 160, 200, 20);
        add(lblEsp);

        cbProgramacion   = new JCheckBox("Programación");
        cbDiseno         = new JCheckBox("Diseño gráfico");
        cbAdministracion = new JCheckBox("Administración");
        cbProgramacion.setBounds(25, 190, 150, 25);
        cbDiseno.setBounds(25, 220, 150, 25);
        cbAdministracion.setBounds(25, 250, 150, 25);
        add(cbProgramacion); add(cbDiseno); add(cbAdministracion);

        JSeparator sep2 = new JSeparator();
        sep2.setBounds(10, 290, 225, 2);
        add(sep2);

        // --- Horas ---
        JLabel lblHoras = new JLabel("Horas que dedicas en el ordenador");
        lblHoras.setBounds(20, 300, 230, 20);
        add(lblHoras);

        slHoras = new JSlider(0, 10, 0);
        slHoras.setBounds(40, 335, 190, 25);
        add(slHoras);

        lblValor = new JLabel("0");
        lblValor.setBounds(20, 335, 20, 25);
        add(lblValor);

        // Evento stateChanged: actualiza la etiqueta con el valor actual
        slHoras.addChangeListener(e -> lblValor.setText(String.valueOf(slHoras.getValue())));

        btnGenerar = new JButton("Generar");
        btnGenerar.setBounds(80, 385, 90, 28);
        add(btnGenerar);

        btnGenerar.addActionListener(e -> mostrarResultado());
    }

    private void mostrarResultado() {
        String so;
        if (rbWindows.isSelected())      so = "Windows";
        else if (rbLinux.isSelected())   so = "Linux";
        else if (rbMac.isSelected())     so = "Mac";
        else                             so = "ninguno";

        ArrayList<String> esp = new ArrayList<>();
        if (cbProgramacion.isSelected())   esp.add("Programación");
        if (cbDiseno.isSelected())         esp.add("Diseño gráfico");
        if (cbAdministracion.isSelected()) esp.add("Administración");
        String especialidades = esp.isEmpty() ? "ninguna" : String.join(" ", esp);

        String mensaje = "Tu sistema operativo preferido es " + so + ",\n"
                + "tus especialidades son " + especialidades + "\n"
                + "y el numero de horas dedicadas al ordenador son " + slHoras.getValue();

        JOptionPane.showMessageDialog(this, mensaje, "Muestra de datos",
                JOptionPane.INFORMATION_MESSAGE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MiniEncuesta().setVisible(true));
    }
}
