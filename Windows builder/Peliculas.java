import javax.swing.*;

/**
 * Ejercicio 4 - Lista de peliculas.
 * Se escribe el titulo en un JTextField y, al pulsar "Añadir",
 * se agrega al JComboBox.
 */
public class Peliculas extends JFrame {

    private JTextField txtTitulo;
    private JButton btnAnadir;
    private JComboBox<String> cmbPeliculas;

    public Peliculas() {
        super("Películas");
        setSize(440, 205);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(null);

        JLabel lblTitulo = new JLabel("Escribe el título de una película");
        lblTitulo.setBounds(30, 25, 200, 25);
        add(lblTitulo);

        JLabel lblLista = new JLabel("Películas");
        lblLista.setBounds(275, 25, 80, 25);
        add(lblLista);

        txtTitulo = new JTextField();
        txtTitulo.setBounds(55, 75, 145, 25);
        add(txtTitulo);

        cmbPeliculas = new JComboBox<>();
        cmbPeliculas.setBounds(235, 75, 150, 25);
        add(cmbPeliculas);

        btnAnadir = new JButton("Añadir");
        btnAnadir.setBounds(65, 120, 80, 25);
        add(btnAnadir);

        btnAnadir.addActionListener(e -> anadirPelicula());
        txtTitulo.addActionListener(e -> anadirPelicula());   // Enter tambien añade
    }

    private void anadirPelicula() {
        String titulo = txtTitulo.getText().trim();
        if (titulo.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Escribe el título de una película.",
                    "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        cmbPeliculas.addItem(titulo);
        txtTitulo.setText("");
        txtTitulo.requestFocus();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Peliculas().setVisible(true));
    }
}
