import javax.swing.*;
import java.awt.*;
import java.util.Random;
@SuppressWarnings("unused")


/**
 * Ejercicio 2 - Generador de numeros grafico.
 * Se eligen dos numeros en JSpinner y, al pulsar "Generar", se muestra en un
 * JTextField (no editable) un numero aleatorio comprendido entre ambos.
 */
public class GeneradorNumeros extends JFrame {

    private JSpinner spnNumero1, spnNumero2;
    private JTextField txtGenerado;
    private JButton btnGenerar;
    private final Random random = new Random();

    public GeneradorNumeros() {
        super("Generador de números");
        setSize(320, 335);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(null);

        JLabel lbl1 = new JLabel("Número 1");
        lbl1.setBounds(35, 55, 100, 25);
        add(lbl1);

        spnNumero1 = new JSpinner(new SpinnerNumberModel(5, Integer.MIN_VALUE, Integer.MAX_VALUE, 1));
        spnNumero1.setBounds(155, 55, 75, 25);
        add(spnNumero1);

        JLabel lbl2 = new JLabel("Número 2");
        lbl2.setBounds(35, 100, 100, 25);
        add(lbl2);

        spnNumero2 = new JSpinner(new SpinnerNumberModel(10, Integer.MIN_VALUE, Integer.MAX_VALUE, 1));
        spnNumero2.setBounds(155, 100, 75, 25);
        add(spnNumero2);

        JLabel lblGen = new JLabel("Número generado");
        lblGen.setBounds(35, 170, 120, 25);
        add(lblGen);

        txtGenerado = new JTextField();
        txtGenerado.setEditable(false);          // requisito: no editable
        txtGenerado.setBounds(155, 170, 75, 25);
        add(txtGenerado);

        btnGenerar = new JButton("Generar");
        btnGenerar.setBounds(155, 220, 80, 25);
        add(btnGenerar);

        btnGenerar.addActionListener(e -> generar());
    }

    private void generar() {
        int a = (Integer) spnNumero1.getValue();
        int b = (Integer) spnNumero2.getValue();

        // Si el usuario puso el mayor primero, se intercambian
        int min = Math.min(a, b);
        int max = Math.max(a, b);

        // nextInt(max - min + 1) + min  -> incluye ambos extremos
        long rango = (long) max - min + 1;
        int generado = (int) (min + (long) (random.nextDouble() * rango));
        txtGenerado.setText(String.valueOf(generado));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new GeneradorNumeros().setVisible(true));
    }
}
