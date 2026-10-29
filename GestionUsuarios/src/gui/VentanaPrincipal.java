package gui;

import usuarios.Administrador;
import usuarios.AdministradorGeneral;
import usuarios.Usuario;
import usuarios.UsuarioRegular;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;


public class VentanaPrincipal extends JFrame {

    private static final String TIPO_REGULAR = "Usuario regular";
    private static final String TIPO_ADMIN = "Administrador";
    private static final String[] COLUMNAS =
            {"Nombre", "Correo electrónico", "Contraseña", "Tipo", "Nivel de acceso"};

    private final ArrayList<Usuario> usuarios = new ArrayList<>();

    private final JTextField txtNombre = new JTextField(18);
    private final JTextField txtCorreo = new JTextField(18);
    private final JPasswordField txtContrasena = new JPasswordField(18);
    private final JComboBox<String> cmbTipo = new JComboBox<>(new String[]{TIPO_REGULAR, TIPO_ADMIN});
    private final JSpinner spnNivel = new JSpinner(new SpinnerNumberModel(1, 1, 5, 1));

    private final ModeloUsuarios modelo = new ModeloUsuarios();
    private final JTable tabla = new JTable(modelo);
    private final JLabel lblEstado = new JLabel("Listo.");

    public VentanaPrincipal() {
        super("Gestión de usuarios");
        UIManager.put("OptionPane.okButtonText", "Aceptar");
        UIManager.put("OptionPane.yesButtonText", "Sí");
        UIManager.put("OptionPane.noButtonText", "No");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout(8, 8));

        add(crearFormulario(), BorderLayout.NORTH);
        add(new JScrollPane(tabla), BorderLayout.CENTER);
        add(crearBotones(), BorderLayout.SOUTH);

        tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabla.setRowHeight(24);
        tabla.setName("tabla");
        tabla.getColumnModel().getColumn(1).setPreferredWidth(260);
        tabla.getSelectionModel().addListSelectionListener(new SeleccionTabla());
        cmbTipo.addActionListener(new CambioTipo());
        spnNivel.setEnabled(false);

        ((JComponent) getContentPane()).setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        setSize(880, 520);
        setLocationRelativeTo(null);
    }

    // ---------------------------------------------------------------- construcción de la interfaz

    private JPanel crearFormulario() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Datos del usuario"));
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(4, 6, 4, 6);
        c.anchor = GridBagConstraints.WEST;

        txtNombre.setName("txtNombre");
        txtCorreo.setName("txtCorreo");
        txtContrasena.setName("txtContrasena");
        cmbTipo.setName("cmbTipo");
        spnNivel.setName("spnNivel");

        agregarFila(panel, c, 0, "Nombre:", txtNombre, "Tipo:", cmbTipo);
        agregarFila(panel, c, 1, "Correo electrónico:", txtCorreo, "Nivel de acceso (1-5):", spnNivel);
        agregarFila(panel, c, 2, "Contraseña:", txtContrasena, null, null);
        return panel;
    }

    private void agregarFila(JPanel p, GridBagConstraints c, int fila,
                             String et1, JComponent comp1, String et2, JComponent comp2) {
        c.gridy = fila;
        c.gridx = 0; p.add(new JLabel(et1), c);
        c.gridx = 1; p.add(comp1, c);
        if (et2 != null) {
            c.gridx = 2; p.add(new JLabel(et2), c);
            c.gridx = 3; p.add(comp2, c);
        }
    }

    private JPanel crearBotones() {
        JButton btnAgregar = new JButton("Agregar");
        JButton btnEditar = new JButton("Editar");
        JButton btnEliminar = new JButton("Eliminar");
        JButton btnMostrar = new JButton("Mostrar detalles");
        JButton btnLimpiar = new JButton("Limpiar");

        btnAgregar.setName("btnAgregar");
        btnEditar.setName("btnEditar");
        btnEliminar.setName("btnEliminar");
        btnMostrar.setName("btnMostrar");
        btnLimpiar.setName("btnLimpiar");

        btnAgregar.addActionListener(new AccionAgregar());
        btnEditar.addActionListener(new AccionEditar());
        btnEliminar.addActionListener(new AccionEliminar());
        btnMostrar.addActionListener(new AccionMostrar());
        btnLimpiar.addActionListener(new AccionLimpiar());

        JPanel botones = new JPanel(new FlowLayout(FlowLayout.CENTER, 8, 4));
        botones.add(btnAgregar);
        botones.add(btnEditar);
        botones.add(btnEliminar);
        botones.add(btnMostrar);
        botones.add(btnLimpiar);

        JPanel sur = new JPanel(new BorderLayout());
        sur.add(botones, BorderLayout.CENTER);
        sur.add(lblEstado, BorderLayout.SOUTH);
        lblEstado.setName("lblEstado");
        return sur;
    }

    // ---------------------------------------------------------------- lógica auxiliar

    /**
     * Lee el formulario, valida los datos y construye el objeto Usuario correspondiente.
     * @param indiceActual fila que se está editando (o -1 si se está agregando)
     * @return el usuario creado, o null si hay algún error (ya se mostró el mensaje)
     */
    private Usuario leerFormulario(int indiceActual) {
        String nombre = txtNombre.getText().trim();
        String correo = txtCorreo.getText().trim();
        String contrasena = new String(txtContrasena.getPassword());

        if (nombre.isEmpty()) {
            mostrarError("El nombre es obligatorio.");
            return null;
        }
        if (!correo.matches("^[\\w.+-]+@[\\w-]+(\\.[\\w-]+)+$")) {
            mostrarError("El correo electrónico no es válido.");
            return null;
        }
        if (contrasena.length() < 4) {
            mostrarError("La contraseña debe tener al menos 4 caracteres.");
            return null;
        }
        for (int i = 0; i < usuarios.size(); i++) {
            if (i != indiceActual && usuarios.get(i).getCorreo().equalsIgnoreCase(correo)) {
                mostrarError("Ya existe un usuario con ese correo.");
                return null;
            }
        }

        if (TIPO_ADMIN.equals(cmbTipo.getSelectedItem())) {
            int nivel = (Integer) spnNivel.getValue();
            return new AdministradorGeneral(nombre, correo, contrasena, nivel);
        }
        return new UsuarioRegular(nombre, correo, contrasena);
    }

    private void limpiarFormulario() {
        txtNombre.setText("");
        txtCorreo.setText("");
        txtContrasena.setText("");
        cmbTipo.setSelectedIndex(0);
        spnNivel.setValue(1);
        tabla.clearSelection();
    }

    private void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Error de validación", JOptionPane.ERROR_MESSAGE);
        lblEstado.setText("Error: " + mensaje);
    }

    private int filaSeleccionada() {
        int fila = tabla.getSelectedRow();
        if (fila < 0) {
            mostrarError("Seleccione un usuario de la tabla primero.");
        }
        return fila;
    }

    // ---------------------------------------------------------------- clases internas

    /** Modelo de datos de la tabla: lee directamente la lista de usuarios. */
    private class ModeloUsuarios extends AbstractTableModel {
        @Override public int getRowCount() { return usuarios.size(); }
        @Override public int getColumnCount() { return COLUMNAS.length; }
        @Override public String getColumnName(int col) { return COLUMNAS[col]; }

        @Override
        public Object getValueAt(int fila, int col) {
            Usuario u = usuarios.get(fila);
            switch (col) {
                case 0: return u.getNombre();
                case 1: return u.getCorreo();
                case 2: return "*".repeat(u.getContrasena().length());
                case 3: return u.getTipo();
                default:
                    if (u instanceof Administrador) {
                        return String.valueOf(((Administrador) u).getNivelAcceso());
                    }
                    return "-";
            }
        }
    }

    private class AccionAgregar implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Usuario nuevo = leerFormulario(-1);
            if (nuevo == null) return;
            usuarios.add(nuevo);
            modelo.fireTableDataChanged();
            limpiarFormulario();
            lblEstado.setText("Usuario agregado: " + nuevo.getNombre());
        }
    }

    private class AccionEditar implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int fila = filaSeleccionada();
            if (fila < 0) return;
            Usuario editado = leerFormulario(fila);
            if (editado == null) return;
            usuarios.set(fila, editado);
            modelo.fireTableDataChanged();
            limpiarFormulario();
            lblEstado.setText("Usuario editado: " + editado.getNombre());
        }
    }

    private class AccionEliminar implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int fila = filaSeleccionada();
            if (fila < 0) return;
            Usuario u = usuarios.get(fila);
            int resp = JOptionPane.showConfirmDialog(VentanaPrincipal.this,
                    "¿Eliminar a " + u.getNombre() + "?", "Confirmar eliminación",
                    JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (resp == JOptionPane.YES_OPTION) {
                usuarios.remove(fila);
                modelo.fireTableDataChanged();
                limpiarFormulario();
                lblEstado.setText("Usuario eliminado: " + u.getNombre());
            }
        }
    }

    private class AccionMostrar implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int fila = filaSeleccionada();
            if (fila < 0) return;
            JOptionPane.showMessageDialog(VentanaPrincipal.this,
                    usuarios.get(fila).getDescripcion(), "Detalles del usuario",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private class AccionLimpiar implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            limpiarFormulario();
            lblEstado.setText("Formulario limpio.");
        }
    }

    /** Habilita el nivel de acceso solo cuando el tipo elegido es Administrador. */
    private class CambioTipo implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            spnNivel.setEnabled(TIPO_ADMIN.equals(cmbTipo.getSelectedItem()));
        }
    }

    /** Al seleccionar una fila, carga sus datos en el formulario para poder editarlos. */
    private class SeleccionTabla implements ListSelectionListener {
        @Override
        public void valueChanged(ListSelectionEvent e) {
            if (e.getValueIsAdjusting()) return;
            int fila = tabla.getSelectedRow();
            if (fila < 0) return;
            Usuario u = usuarios.get(fila);
            txtNombre.setText(u.getNombre());
            txtCorreo.setText(u.getCorreo());
            txtContrasena.setText(u.getContrasena());
            if (u instanceof Administrador) {
                cmbTipo.setSelectedItem(TIPO_ADMIN);
                spnNivel.setValue(((Administrador) u).getNivelAcceso());
            } else {
                cmbTipo.setSelectedItem(TIPO_REGULAR);
            }
        }
    }

    // ---------------------------------------------------------------- main

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception ignorada) {
            // si Nimbus no está disponible se usa el aspecto por defecto
        }
        SwingUtilities.invokeLater(() -> new VentanaPrincipal().setVisible(true));
    }
}
