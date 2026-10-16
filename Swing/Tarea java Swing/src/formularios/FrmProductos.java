package formularios;

import modelo.Producto;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.ArrayList;

public class FrmProductos extends JFrame {

    // ---------- Componentes ----------
    private JTextField txtId, txtDescripcion, txtPrecio, txtStock;
    private JComboBox<String> cmbCategoria;
    private JButton btnNuevo, btnGuardar, btnEditar, btnEliminar, btnInforme, btnCancelar, btnVolver;
    private JTable tabla;
    private DefaultTableModel modeloTabla;
    private JLabel lblTotalInventario;

    // ---------- Referencia al menú principal ----------
    private FrmInicio inicio;

    // ---------- Datos en memoria ----------
    private ArrayList<Producto> listaProductos = new ArrayList<>();
    private int siguienteId = 1;
    private int indiceEditando = -1;   // -1 significa "estoy creando uno nuevo"
    private boolean enEdicion = false;

    private static final String[] CATEGORIAS = {
        "Alimentos", "Bebidas", "Limpieza", "Higiene personal", "Electrónica", "Otros"
    };

    public FrmProductos(FrmInicio inicio) {
        this.inicio = inicio;

        setTitle("Formulario de Productos");
        setSize(720, 650);
        setDefaultCloseOperation(HIDE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Si se cierra con la X, se vuelve al menú en lugar de dejar el programa sin ventanas
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                FrmProductos.this.inicio.setVisible(true);
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
        centro.add(crearPanelTotal(), BorderLayout.SOUTH);
        add(centro, BorderLayout.CENTER);
    }

    private JPanel crearPanelTitulo() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 20, 0, 20));

        JLabel titulo = new JLabel("FORMULARIO DE PRODUCTOS");
        titulo.setFont(new Font("Tahoma", Font.BOLD, 24));

        ImageIcon icono = cargarIcono("productos.png", 48);
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
        panel.setBorder(BorderFactory.createTitledBorder("Datos de Producto"));

        txtId = new JTextField();
        txtId.setEditable(false);          // el ID se genera solo
        txtDescripcion = new JTextField();
        cmbCategoria = new JComboBox<>(CATEGORIAS);
        txtPrecio = new JTextField();
        txtStock = new JTextField();

        // Fila 0: etiquetas
        colocar(panel, new JLabel("ID"), 0, 0, 1);
        colocar(panel, new JLabel("Descripción"), 0, 1, 1);
        colocar(panel, new JLabel("Categoría"), 0, 2, 1);
        // Fila 1: campos
        colocar(panel, txtId, 1, 0, 1);
        colocar(panel, txtDescripcion, 1, 1, 1);
        colocar(panel, cmbCategoria, 1, 2, 1);
        // Fila 2: etiquetas
        colocar(panel, new JLabel("Precio"), 2, 0, 2);
        colocar(panel, new JLabel("Stock"), 2, 2, 1);
        // Fila 3: campos
        colocar(panel, txtPrecio, 3, 0, 2);
        colocar(panel, txtStock, 3, 2, 1);

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
        String[] columnas = {"ID", "Descripción", "Categoría", "Precio", "Stock"};

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

        // Centra las columnas numéricas (ID, Precio y Stock)
        DefaultTableCellRenderer centrado = new DefaultTableCellRenderer();
        centrado.setHorizontalAlignment(SwingConstants.CENTER);
        tabla.getColumnModel().getColumn(0).setCellRenderer(centrado);
        tabla.getColumnModel().getColumn(3).setCellRenderer(centrado);
        tabla.getColumnModel().getColumn(4).setCellRenderer(centrado);

        // Al hacer clic en una fila, se muestran sus datos en los campos
        tabla.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                mostrarSeleccion();
            }
        });

        return new JScrollPane(tabla);
    }

    private JPanel crearPanelTotal() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        lblTotalInventario = new JLabel();
        lblTotalInventario.setFont(new Font("Tahoma", Font.BOLD, 13));
        panel.add(lblTotalInventario);
        actualizarTotal();
        return panel;
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
        String categoria = (String) cmbCategoria.getSelectedItem();

        if (descripcion.isEmpty()) {
            avisar("La descripción es obligatoria.", txtDescripcion);
            return;
        }

        // Precio: debe ser un número mayor que 0 (acepta coma o punto decimal)
        double precio;
        try {
            precio = Double.parseDouble(txtPrecio.getText().trim().replace(",", "."));
        } catch (NumberFormatException ex) {
            avisar("El precio debe ser un número válido (ejemplo: 25.50).", txtPrecio);
            return;
        }
        if (precio <= 0) {
            avisar("El precio debe ser mayor que 0.", txtPrecio);
            return;
        }

        // Stock: debe ser un entero mayor o igual a 0
        int stock;
        try {
            stock = Integer.parseInt(txtStock.getText().trim());
        } catch (NumberFormatException ex) {
            avisar("El stock debe ser un número entero (ejemplo: 40).", txtStock);
            return;
        }
        if (stock < 0) {
            avisar("El stock no puede ser negativo.", txtStock);
            return;
        }

        if (indiceEditando == -1) {
            listaProductos.add(new Producto(siguienteId, descripcion, categoria, precio, stock));
            siguienteId++;
            JOptionPane.showMessageDialog(this, "Producto guardado correctamente.");
        } else {
            Producto producto = listaProductos.get(indiceEditando);
            producto.setDescripcion(descripcion);
            producto.setCategoria(categoria);
            producto.setPrecio(precio);
            producto.setStock(stock);
            JOptionPane.showMessageDialog(this, "Producto actualizado correctamente.");
        }

        refrescarTabla();
        limpiarCampos();
        indiceEditando = -1;
        modoEdicion(false);
    }

    private void accionEditar() {
        int fila = tabla.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un producto de la tabla para editar.",
                    "Aviso", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        indiceEditando = fila;
        cargarEnCampos(listaProductos.get(fila));
        modoEdicion(true);
        txtDescripcion.requestFocus();
    }

    private void accionEliminar() {
        int fila = tabla.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un producto de la tabla para eliminar.",
                    "Aviso", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        Producto producto = listaProductos.get(fila);
        int respuesta = JOptionPane.showConfirmDialog(this,
                "¿Eliminar el producto \"" + producto.getDescripcion() + "\"?",
                "Confirmar eliminación", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

        if (respuesta == JOptionPane.YES_OPTION) {
            listaProductos.remove(fila);
            refrescarTabla();
            limpiarCampos();
        }
    }

    private void accionInforme() {
        if (listaProductos.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No hay productos registrados todavía.",
                    "Informe", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        StringBuilder texto = new StringBuilder();
        texto.append(String.format("%-5s %-22s %-18s %10s %7s %14s%n",
                "ID", "Descripción", "Categoría", "Precio", "Stock", "Valor"));
        texto.append("-".repeat(80)).append("\n");

        double totalInventario = 0;
        int unidades = 0;
        for (Producto p : listaProductos) {
            texto.append(String.format("%-5d %-22s %-18s %10.2f %7d %14.2f%n",
                    p.getId(), p.getDescripcion(), p.getCategoria(),
                    p.getPrecio(), p.getStock(), p.getValorInventario()));
            totalInventario += p.getValorInventario();
            unidades += p.getStock();
        }

        texto.append("-".repeat(80)).append("\n");
        texto.append("Total de productos: ").append(listaProductos.size()).append("\n");
        texto.append("Unidades en inventario: ").append(unidades).append("\n");
        texto.append(String.format("Valor total del inventario: %.2f", totalInventario));

        JTextArea area = new JTextArea(texto.toString());
        area.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        area.setEditable(false);

        JScrollPane scroll = new JScrollPane(area);
        scroll.setPreferredSize(new Dimension(640, 280));

        JOptionPane.showMessageDialog(this, scroll, "Informe de productos", JOptionPane.PLAIN_MESSAGE);
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
        txtPrecio.setEditable(activo);
        txtStock.setEditable(activo);
        cmbCategoria.setEnabled(activo);

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
        txtPrecio.setText("");
        txtStock.setText("");
        cmbCategoria.setSelectedIndex(0);
    }

    private void cargarEnCampos(Producto p) {
        txtId.setText(String.valueOf(p.getId()));
        txtDescripcion.setText(p.getDescripcion());
        cmbCategoria.setSelectedItem(p.getCategoria());
        txtPrecio.setText(String.valueOf(p.getPrecio()));
        txtStock.setText(String.valueOf(p.getStock()));
    }

    private void mostrarSeleccion() {
        if (enEdicion) {
            return;
        }
        int fila = tabla.getSelectedRow();
        if (fila >= 0 && fila < listaProductos.size()) {
            cargarEnCampos(listaProductos.get(fila));
        }
    }

    private void refrescarTabla() {
        modeloTabla.setRowCount(0);
        for (Producto p : listaProductos) {
            modeloTabla.addRow(p.aFila());
        }
        actualizarTotal();
    }

    // Recalcula el valor total del inventario que se muestra debajo de la tabla
    private void actualizarTotal() {
        double total = 0;
        for (Producto p : listaProductos) {
            total += p.getValorInventario();
        }
        lblTotalInventario.setText(String.format("Valor total del inventario: %.2f", total));
    }

    private void avisar(String mensaje, JTextField campo) {
        JOptionPane.showMessageDialog(this, mensaje, "Dato incorrecto", JOptionPane.WARNING_MESSAGE);
        campo.requestFocus();
    }
}