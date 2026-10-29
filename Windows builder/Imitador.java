import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;

/**
 * Ejercicio 1 - Imitador (espejo).
 * Lo que se modifica en el panel "Original" se refleja en el panel "Espejo".
 * El espejo esta deshabilitado, por lo que NO es bidireccional.
 */
public class Imitador extends JFrame {

    // Componentes del lado ORIGINAL
    private JRadioButton rbOp1, rbOp2, rbOp3;
    private JCheckBox cbOp4, cbOp5, cbOp6;
    private JTextField txtOriginal;
    private JComboBox<String> cmbOriginal;
    private JSpinner spnOriginal;

    // Componentes del lado ESPEJO
    private JRadioButton rbEsp1, rbEsp2, rbEsp3;
    private JCheckBox cbEsp4, cbEsp5, cbEsp6;
    private JTextField txtEspejo;
    private JComboBox<String> cmbEspejo;
    private JSpinner spnEspejo;

    private static final String[] ITEMS = {"Item 1", "Item 2", "Item 3"};

    public Imitador() {
        super("Imitador");
        setSize(375, 395);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(null);

        construirOriginal();
        construirEspejo();

        JSeparator separador = new JSeparator();
        separador.setBounds(10, 182, 340, 2);
        add(separador);

        conectarEventos();
    }

    private void construirOriginal() {
        JLabel lbl = new JLabel("Original");
        lbl.setBounds(12, 8, 80, 20);
        add(lbl);

        rbOp1 = new JRadioButton("Opcion 1");
        rbOp2 = new JRadioButton("Opcion 2", true);
        rbOp3 = new JRadioButton("Opcion 3");
        rbOp1.setBounds(25, 35, 100, 25);
        rbOp2.setBounds(25, 65, 100, 25);
        rbOp3.setBounds(25, 95, 100, 25);
        ButtonGroup g = new ButtonGroup();
        g.add(rbOp1); g.add(rbOp2); g.add(rbOp3);
        add(rbOp1); add(rbOp2); add(rbOp3);

        cbOp4 = new JCheckBox("Opcion 4");
        cbOp5 = new JCheckBox("Opcion 5");
        cbOp6 = new JCheckBox("Opcion 6");
        cbOp4.setBounds(130, 35, 100, 25);
        cbOp5.setBounds(130, 65, 100, 25);
        cbOp6.setBounds(130, 95, 100, 25);
        add(cbOp4); add(cbOp5); add(cbOp6);

        txtOriginal = new JTextField();
        txtOriginal.setBounds(232, 35, 105, 25);
        add(txtOriginal);

        cmbOriginal = new JComboBox<>(ITEMS);
        cmbOriginal.setBounds(232, 70, 105, 25);
        add(cmbOriginal);

        spnOriginal = new JSpinner(new SpinnerNumberModel(20, 0, 100, 1));
        spnOriginal.setBounds(232, 105, 105, 25);
        add(spnOriginal);
    }

    private void construirEspejo() {
        JLabel lbl = new JLabel("Espejo");
        lbl.setBounds(12, 190, 80, 20);
        add(lbl);

        rbEsp1 = new JRadioButton("Opcion 1");
        rbEsp2 = new JRadioButton("Opcion 2", true);
        rbEsp3 = new JRadioButton("Opcion 3");
        rbEsp1.setBounds(25, 215, 100, 25);
        rbEsp2.setBounds(25, 255, 100, 25);
        rbEsp3.setBounds(25, 295, 100, 25);
        ButtonGroup g = new ButtonGroup();
        g.add(rbEsp1); g.add(rbEsp2); g.add(rbEsp3);
        add(rbEsp1); add(rbEsp2); add(rbEsp3);

        cbEsp4 = new JCheckBox("Opcion 4");
        cbEsp5 = new JCheckBox("Opcion 5");
        cbEsp6 = new JCheckBox("Opcion 6");
        cbEsp4.setBounds(130, 215, 100, 25);
        cbEsp5.setBounds(130, 255, 100, 25);
        cbEsp6.setBounds(130, 295, 100, 25);
        add(cbEsp4); add(cbEsp5); add(cbEsp6);

        txtEspejo = new JTextField();
        txtEspejo.setBounds(232, 215, 105, 25);
        add(txtEspejo);

        cmbEspejo = new JComboBox<>(ITEMS);
        cmbEspejo.setBounds(232, 255, 105, 25);
        add(cmbEspejo);

        spnEspejo = new JSpinner(new SpinnerNumberModel(20, 0, 100, 1));
        spnEspejo.setBounds(232, 295, 105, 25);
        add(spnEspejo);

        // Todo el lado espejo queda deshabilitado: solo se puede modificar el original
        for (Component c : new Component[]{rbEsp1, rbEsp2, rbEsp3, cbEsp4, cbEsp5, cbEsp6,
                txtEspejo, cmbEspejo, spnEspejo}) {
            c.setEnabled(false);
        }
    }

    private void conectarEventos() {
        // Radio buttons: al elegir uno en el original se elige el mismo en el espejo
        rbOp1.addActionListener(e -> rbEsp1.setSelected(true));
        rbOp2.addActionListener(e -> rbEsp2.setSelected(true));
        rbOp3.addActionListener(e -> rbEsp3.setSelected(true));

        // Check boxes
        cbOp4.addActionListener(e -> cbEsp4.setSelected(cbOp4.isSelected()));
        cbOp5.addActionListener(e -> cbEsp5.setSelected(cbOp5.isSelected()));
        cbOp6.addActionListener(e -> cbEsp6.setSelected(cbOp6.isSelected()));

        // Campo de texto: se copia el contenido cada vez que cambia
        txtOriginal.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e)  { txtEspejo.setText(txtOriginal.getText()); }
            public void removeUpdate(DocumentEvent e)  { txtEspejo.setText(txtOriginal.getText()); }
            public void changedUpdate(DocumentEvent e) { txtEspejo.setText(txtOriginal.getText()); }
        });

        // ComboBox
        cmbOriginal.addActionListener(e -> cmbEspejo.setSelectedIndex(cmbOriginal.getSelectedIndex()));

        // Spinner
        spnOriginal.addChangeListener(e -> spnEspejo.setValue(spnOriginal.getValue()));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Imitador().setVisible(true));
    }
}
