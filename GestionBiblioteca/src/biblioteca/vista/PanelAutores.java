package biblioteca.vista;

import biblioteca.modelo.Autor;
import biblioteca.servicio.Biblioteca;
import biblioteca.servicio.BibliotecaListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;

/**
 * Pestaña para crear, editar, eliminar y buscar autores.
 */
public class PanelAutores extends JPanel implements BibliotecaListener {
    private static final long serialVersionUID = 1L;

    private final Biblioteca biblioteca;
    private final AutorTableModel modelo;
    private final JTable tabla;

    private final JTextField txtBuscar = new JTextField(22);
    private final JLabel lblResultados = new JLabel(" ");

    private final JTextField txtNombre = new JTextField(25);
    private final JTextField txtNacionalidad = new JTextField(25);
    private final JTextArea txtBiografia = new JTextArea(4, 25);

    private final JButton btnAgregar = new JButton("Agregar");
    private final JButton btnActualizar = new JButton("Actualizar");
    private final JButton btnEliminar = new JButton("Eliminar");
    private final JButton btnLimpiar = new JButton("Limpiar");

    private Autor seleccionado;
    private boolean cargando;

    public PanelAutores(Biblioteca biblioteca) {
        this.biblioteca = biblioteca;
        this.modelo = new AutorTableModel(biblioteca);
        this.tabla = new JTable(modelo);

        setLayout(new BorderLayout(8, 8));
        setBorder(new EmptyBorder(10, 10, 10, 10));
        add(crearPanelBusqueda(), BorderLayout.NORTH);
        add(new JScrollPane(tabla), BorderLayout.CENTER);
        add(crearFormulario(), BorderLayout.SOUTH);

        configurarTabla();
        conectarEventos();

        biblioteca.agregarOyente(this);
        limpiar();
        datosCambiaron();
    }

    // ------------------------------------------------------ Construcción

    private JPanel crearPanelBusqueda() {
        JPanel p = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 4));
        p.setBorder(BorderFactory.createTitledBorder("Buscar autores"));
        p.add(new JLabel("Nombre o nacionalidad:"));
        p.add(txtBuscar);
        p.add(lblResultados);
        return p;
    }

    private JPanel crearFormulario() {
        JPanel campos = new JPanel(new GridBagLayout());
        txtBiografia.setLineWrap(true);
        txtBiografia.setWrapStyleWord(true);
        Ui.fila(campos, 0, "Nombre:", txtNombre, false);
        Ui.fila(campos, 1, "Nacionalidad:", txtNacionalidad, false);
        Ui.fila(campos, 2, "Biografía:", new JScrollPane(txtBiografia), true);

        JPanel botones = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 4));
        botones.add(btnLimpiar);
        botones.add(btnEliminar);
        botones.add(btnActualizar);
        botones.add(btnAgregar);

        JPanel form = new JPanel(new BorderLayout());
        form.setBorder(BorderFactory.createTitledBorder("Datos del autor"));
        form.add(campos, BorderLayout.CENTER);
        form.add(botones, BorderLayout.SOUTH);
        return form;
    }

    private void configurarTabla() {
        tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabla.setAutoCreateRowSorter(true);
        tabla.setFillsViewportHeight(true);
        tabla.setRowHeight(24);
        tabla.getColumnModel().getColumn(0).setPreferredWidth(300);
        tabla.getColumnModel().getColumn(1).setPreferredWidth(200);
        tabla.getColumnModel().getColumn(2).setPreferredWidth(60);
    }

    private void conectarEventos() {
        txtBuscar.getDocument().addDocumentListener(new CambioTexto(this::aplicarBusqueda));

        tabla.getSelectionModel().addListSelectionListener(e -> {
            if (e.getValueIsAdjusting() || cargando) {
                return;
            }
            int fila = tabla.getSelectedRow();
            if (fila >= 0) {
                seleccionado = modelo.getAutor(tabla.convertRowIndexToModel(fila));
                mostrar(seleccionado);
                actualizarBotones();
            }
        });

        btnAgregar.addActionListener(e -> agregar());
        btnActualizar.addActionListener(e -> actualizar());
        btnEliminar.addActionListener(e -> eliminar());
        btnLimpiar.addActionListener(e -> limpiar());
    }

    // ----------------------------------------------------------- Acciones

    private void agregar() {
        try {
            biblioteca.agregarAutor(txtNombre.getText(), txtNacionalidad.getText(), txtBiografia.getText());
            limpiar();
        } catch (IllegalArgumentException ex) {
            Ui.error(this, ex.getMessage());
        }
    }

    private void actualizar() {
        if (seleccionado == null) {
            return;
        }
        try {
            biblioteca.editarAutor(seleccionado, txtNombre.getText(),
                    txtNacionalidad.getText(), txtBiografia.getText());
        } catch (IllegalArgumentException ex) {
            Ui.error(this, ex.getMessage());
        }
    }

    private void eliminar() {
        if (seleccionado == null) {
            return;
        }
        if (!Ui.confirmar(this, "¿Eliminar al autor \"" + seleccionado.getNombre() + "\"?", "Confirmar")) {
            return;
        }
        try {
            biblioteca.eliminarAutor(seleccionado);
            limpiar();
        } catch (IllegalStateException | IllegalArgumentException ex) {
            Ui.error(this, ex.getMessage());
        }
    }

    private void limpiar() {
        seleccionado = null;
        tabla.clearSelection();
        txtNombre.setText("");
        txtNacionalidad.setText("");
        txtBiografia.setText("");
        actualizarBotones();
        txtNombre.requestFocusInWindow();
    }

    private void mostrar(Autor a) {
        txtNombre.setText(a.getNombre());
        txtNacionalidad.setText(a.getNacionalidad());
        txtBiografia.setText(a.getBiografia());
        txtBiografia.setCaretPosition(0);
    }

    private void actualizarBotones() {
        boolean haySeleccion = seleccionado != null;
        btnActualizar.setEnabled(haySeleccion);
        btnEliminar.setEnabled(haySeleccion);
    }

    // ------------------------------------------------------------ Datos

    @Override
    public void datosCambiaron() {
        aplicarBusqueda();
        if (seleccionado != null && modelo.indiceDe(seleccionado) < 0
                && !biblioteca.getAutores().contains(seleccionado)) {
            limpiar();
        }
    }

    private void aplicarBusqueda() {
        java.util.List<Autor> resultado = biblioteca.buscarAutores(txtBuscar.getText());
        cargando = true;
        modelo.setAutores(resultado);
        int idx = seleccionado == null ? -1 : modelo.indiceDe(seleccionado);
        if (idx >= 0) {
            int vista = tabla.convertRowIndexToView(idx);
            tabla.getSelectionModel().setSelectionInterval(vista, vista);
        }
        cargando = false;
        lblResultados.setText(resultado.size() + " de " + biblioteca.cantidadAutores() + " autores");
    }
}
