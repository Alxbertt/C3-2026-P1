package formularios;

import modelo.Cliente;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.ArrayList;

public class FrmClientes extends JFrame {

    // ---------- Componentes ----------
    private JTextField txtId, txtDescripcion, txtDireccion, txtTelefono, txtRuc;
    private JButton btnNuevo, btnGuardar, btnEditar, btnEliminar, btnInforme, btnCancelar, btnVolver;
    private JTable tabla;
    private DefaultTableModel modeloTabla;

    // ---------- Referencia al menú principal ----------
    private FrmInicio inicio;

    // ---------- Datos en memoria ----------
    private ArrayList<Cliente> listaClientes = new ArrayList<>();
    private int siguienteId = 1;
    private int indiceEditando = -1;   // -1 significa "estoy creando uno nuevo"
    private boolean enEdicion = false;

    public FrmClientes(FrmInicio inicio) {
        this.inicio = inicio;

        setTitle("Formulario de Clientes");
        setSize(720, 620);
        setDefaultCloseOperation(HIDE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Si se cierra con la X, se vuelve al menú en lugar de dejar el programa sin ventanas
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                FrmClientes.this.inicio.setVisible(true);
            }
        });

        construirInterfaz();
        modoEdicion(false);
    }

    // =====================================================
    //                 CONSTRUCCIÓN DE LA VISTA
    // =====================================================

    private void construirInterfaz() {
        setLayout(new BorderLayout(0, 8));
        add(crearPanelTitulo(), BorderLayout.NORTH);

        JPanel centro = new JPanel(new BorderLayout(0, 8));
        centro.setBorder(BorderFactory.createEmptyBorder(0, 12, 12, 12));

        JPanel formulario = new JPanel();
        formulario.setLayout(new BoxLayout(formulario, BoxLayout.Y_AXIS));
        formulario.add(crearPanelDatos());
        formulario.add(Box.createVerticalStrut(8));
        formulario.add(crearPanelBotones());

        centro.add(formulario, BorderLayout.NORTH);
        centro.add(crearPanelTabla(), BorderLayout.CENTER);
        add(centro, BorderLayout.CENTER);
    }

    private JPanel crearPanelTitulo() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 20, 0, 20));

        JLabel titulo = new JLabel("FORMULARIO DE CLIENTES");
        titulo.setFont(new Font("Tahoma", Font.BOLD, 24));

        ImageIcon icono = cargarIcono("clientes.png", 48);
        if (icono != null) {
            titulo.setIcon(icono);
            titulo.setIconTextGap(15);
        }
        panel.add(titulo, BorderLayout.WEST);

        btnVolver = new JButton("← Volver al inicio");
        btnVolver.setFocusPainted(false);
        btnVolver.addActionListener(e -> volverAlInicio());

        // El contenedor evita que el botón se estire a lo alto del panel
        JPanel contenedorBoton = new JPanel(new GridBagLayout());
        contenedorBoton.add(btnVolver);
        panel.add(contenedorBoton, BorderLayout.EAST);

        return panel;
    }

    private JPanel crearPanelDatos() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Datos de Cliente"));

        txtId = new JTextField();
        txtId.setEditable(false);          // el ID se genera solo
        txtDescripcion = new JTextField();
        txtDireccion = new JTextField();
        txtTelefono = new JTextField();
        txtRuc = new JTextField();

        // Fila 0: etiquetas
        colocar(panel, new JLabel("ID"), 0, 0, 1);
        colocar(panel, new JLabel("Descripción"), 0, 1, 1);
        colocar(panel, new JLabel("Dirección"), 0, 2, 1);
        // Fila 1: campos
        colocar(panel, txtId, 1, 0, 1);
        colocar(panel, txtDescripcion, 1, 1, 1);
        colocar(panel, txtDireccion, 1, 2, 1);
        // Fila 2: etiquetas
        colocar(panel, new JLabel("Teléfono"), 2, 0, 2);
        colocar(panel, new JLabel("RUC"), 2, 2, 1);
        // Fila 3: campos
        colocar(panel, txtTelefono, 3, 0, 2);
        colocar(panel, txtRuc, 3, 2, 1);

        return panel;
    }

    // Método auxiliar: coloca un componente en la cuadrícula del GridBagLayout
    private void colocar(JPanel panel, JComponent comp, int fila, int columna, int ancho) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = columna;
        gbc.gridy = fila;
        gbc.gridwidth = ancho;
        gbc.insets = new Insets(4, 6, 4, 6);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = (columna == 0 && ancho == 1) ? 0.1 : 1.0;
        panel.add(comp, gbc);
    }

    private JPanel crearPanelBotones() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 8));
        panel.setBorder(BorderFactory.createEtchedBorder());

        btnNuevo = crearBoton("Nuevo", "nuevo.png");
        btnGuardar = crearBoton("Guardar", "guardar.png");
        btnEditar = crearBoton("Editar", "editar.png");
        btnEliminar = crearBoton("Eliminar", "eliminar.png");
        btnInforme = crearBoton("Informe", "informe.png");
        btnCancelar = crearBoton("Cancelar", "cancelar.png");

        btnNuevo.addActionListener(e -> accionNuevo());
        btnGuardar.addActionListener(e -> accionGuardar());
        btnEditar.addActionListener(e -> accionEditar());
        btnEliminar.addActionListener(e -> accionEliminar());
        btnInforme.addActionListener(e -> accionInforme());
        btnCancelar.addActionListener(e -> accionCancelar());

        panel.add(btnNuevo);
        panel.add(btnGuardar);
        panel.add(btnEditar);
        panel.add(btnEliminar);
        panel.add(btnInforme);
        panel.add(btnCancelar);
        return panel;
    }

    private JButton crearBoton(String texto, String archivoIcono) {
        JButton boton = new JButton(texto);
        ImageIcon icono = cargarIcono(archivoIcono, 32);
        if (icono != null) {
            boton.setIcon(icono);
            boton.setVerticalTextPosition(SwingConstants.BOTTOM);
            boton.setHorizontalTextPosition(SwingConstants.CENTER);
        }
        boton.setPreferredSize(new Dimension(95, 62));
        boton.setFocusPainted(false);
        return boton;
    }

    private JScrollPane crearPanelTabla() {
        String[] columnas = {"ID", "Descripción", "Dirección", "Teléfono", "RUC"};

        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int fila, int columna) {
                return false;   // la tabla es solo de lectura
            }
        };

        tabla = new JTable(modeloTabla);
        tabla.setRowHeight(22);
        tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabla.getColumnModel().getColumn(0).setMaxWidth(60);

        // Al hacer clic en una fila, se muestran sus datos en los campos
        tabla.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                mostrarSeleccion();
            }
        });

        return new JScrollPane(tabla);
    }

    // Busca el ícono en varias carpetas posibles. Si no lo encuentra, devuelve null
    // y el botón queda solo con texto.
    private ImageIcon cargarIcono(String nombre, int tamano) {
        String[] carpetas = {
            "Recursos",
            "Tarea java Swing/Recursos",
            "Swing/Tarea java Swing/Recursos",
            "C3-2026 P1/Swing/Tarea java Swing/Recursos"
        };

        for (String carpeta : carpetas) {
            File archivo = new File(carpeta + File.separator + nombre);
            if (archivo.exists()) {
                Image imagen = new ImageIcon(archivo.getPath()).getImage()
                        .getScaledInstance(tamano, tamano, Image.SCALE_SMOOTH);
                return new ImageIcon(imagen);
            }
        }
        return null;
    }

    // =====================================================
    //                 ACCIONES DE LOS BOTONES
    // =====================================================

    private void accionNuevo() {
        limpiarCampos();
        indiceEditando = -1;
        txtId.setText(String.valueOf(siguienteId));
        modoEdicion(true);
        txtDescripcion.requestFocus();
    }

    private void accionGuardar() {
        String descripcion = txtDescripcion.getText().trim();
        String direccion = txtDireccion.getText().trim();
        String telefono = txtTelefono.getText().trim();
        String ruc = txtRuc.getText().trim();

        if (!datosValidos(descripcion, direccion, telefono, ruc)) {
            return;
        }

        if (indiceEditando == -1) {
            listaClientes.add(new Cliente(siguienteId, descripcion, direccion, telefono, ruc));
            siguienteId++;
            JOptionPane.showMessageDialog(this, "Cliente guardado correctamente.");
        } else {
            Cliente cliente = listaClientes.get(indiceEditando);
            cliente.setDescripcion(descripcion);
            cliente.setDireccion(direccion);
            cliente.setTelefono(telefono);
            cliente.setRuc(ruc);
            JOptionPane.showMessageDialog(this, "Cliente actualizado correctamente.");
        }

        refrescarTabla();
        limpiarCampos();
        indiceEditando = -1;
        modoEdicion(false);
    }

    private void accionEditar() {
        int fila = tabla.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un cliente de la tabla para editar.",
                    "Aviso", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        indiceEditando = fila;
        cargarEnCampos(listaClientes.get(fila));
        modoEdicion(true);
        txtDescripcion.requestFocus();
    }

    private void accionEliminar() {
        int fila = tabla.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un cliente de la tabla para eliminar.",
                    "Aviso", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        Cliente cliente = listaClientes.get(fila);
        int respuesta = JOptionPane.showConfirmDialog(this,
                "¿Eliminar al cliente \"" + cliente.getDescripcion() + "\"?",
                "Confirmar eliminación", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

        if (respuesta == JOptionPane.YES_OPTION) {
            listaClientes.remove(fila);
            refrescarTabla();
            limpiarCampos();
        }
    }

    private void accionInforme() {
        if (listaClientes.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No hay clientes registrados todavía.",
                    "Informe", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        StringBuilder texto = new StringBuilder();
        texto.append(String.format("%-5s %-20s %-22s %-14s %s%n",
                "ID", "Descripción", "Dirección", "Teléfono", "RUC"));
        texto.append("-".repeat(80)).append("\n");

        for (Cliente c : listaClientes) {
            texto.append(String.format("%-5d %-20s %-22s %-14s %s%n",
                    c.getId(), c.getDescripcion(), c.getDireccion(), c.getTelefono(), c.getRuc()));
        }
        texto.append("\nTotal de clientes: ").append(listaClientes.size());

        JTextArea area = new JTextArea(texto.toString());
        area.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        area.setEditable(false);

        JScrollPane scroll = new JScrollPane(area);
        scroll.setPreferredSize(new Dimension(580, 260));

        JOptionPane.showMessageDialog(this, scroll, "Informe de clientes", JOptionPane.PLAIN_MESSAGE);
    }

    private void accionCancelar() {
        limpiarCampos();
        indiceEditando = -1;
        modoEdicion(false);
        tabla.clearSelection();
    }

    private void volverAlInicio() {
        if (enEdicion) {
            int respuesta = JOptionPane.showConfirmDialog(this,
                    "Hay cambios sin guardar.\n¿Desea salir y descartarlos?",
                    "Volver al inicio", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
            if (respuesta != JOptionPane.YES_OPTION) {
                return;
            }
            accionCancelar();
        }
        setVisible(false);
        inicio.setVisible(true);
    }

    // =====================================================
    //                 MÉTODOS DE APOYO
    // =====================================================

    // Activa o desactiva los campos y los botones según el estado del formulario
    private void modoEdicion(boolean activo) {
        enEdicion = activo;

        txtDescripcion.setEditable(activo);
        txtDireccion.setEditable(activo);
        txtTelefono.setEditable(activo);
        txtRuc.setEditable(activo);

        btnGuardar.setEnabled(activo);
        btnCancelar.setEnabled(activo);

        btnNuevo.setEnabled(!activo);
        btnEditar.setEnabled(!activo);
        btnEliminar.setEnabled(!activo);
        btnInforme.setEnabled(!activo);
        tabla.setEnabled(!activo);
    }

    private void limpiarCampos() {
        txtId.setText("");
        txtDescripcion.setText("");
        txtDireccion.setText("");
        txtTelefono.setText("");
        txtRuc.setText("");
    }

    private void cargarEnCampos(Cliente c) {
        txtId.setText(String.valueOf(c.getId()));
        txtDescripcion.setText(c.getDescripcion());
        txtDireccion.setText(c.getDireccion());
        txtTelefono.setText(c.getTelefono());
        txtRuc.setText(c.getRuc());
    }

    private void mostrarSeleccion() {
        if (enEdicion) {
            return;
        }
        int fila = tabla.getSelectedRow();
        if (fila >= 0 && fila < listaClientes.size()) {
            cargarEnCampos(listaClientes.get(fila));
        }
    }

    private void refrescarTabla() {
        modeloTabla.setRowCount(0);
        for (Cliente c : listaClientes) {
            modeloTabla.addRow(c.aFila());
        }
    }

    private boolean datosValidos(String descripcion, String direccion, String telefono, String ruc) {
        if (descripcion.isEmpty()) {
            avisar("La descripción es obligatoria.", txtDescripcion);
            return false;
        }
        if (direccion.isEmpty()) {
            avisar("La dirección es obligatoria.", txtDireccion);
            return false;
        }
        if (!telefono.matches("[0-9\\- ]{7,15}")) {
            avisar("El teléfono debe tener entre 7 y 15 caracteres (solo números, espacios o guiones).", txtTelefono);
            return false;
        }
        if (!ruc.matches("[0-9\\-]{5,15}")) {
            avisar("El RUC debe tener entre 5 y 15 caracteres (solo números o guiones).", txtRuc);
            return false;
        }
        return true;
    }

    private void avisar(String mensaje, JTextField campo) {
        JOptionPane.showMessageDialog(this, mensaje, "Dato incorrecto", JOptionPane.WARNING_MESSAGE);
        campo.requestFocus();
    }
}