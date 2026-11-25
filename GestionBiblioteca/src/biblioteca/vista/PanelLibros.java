package biblioteca.vista;

import biblioteca.modelo.Autor;
import biblioteca.modelo.Genero;
import biblioteca.modelo.Libro;
import biblioteca.servicio.Biblioteca;
import biblioteca.servicio.BibliotecaListener;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

/**
 * Pestaña para crear, editar, eliminar, buscar y filtrar libros.
 * Incluye radio buttons (género), text area (descripción) y una tabla.
 */
public class PanelLibros extends JPanel implements BibliotecaListener {
    private static final long serialVersionUID = 1L;
    private static final String TODOS_AUTORES = "Todos los autores";
    private static final String TODOS_GENEROS = "Todos los géneros";

    private final Biblioteca biblioteca;
    private final LibroTableModel modelo = new LibroTableModel();
    private final JTable tabla = new JTable(modelo);

    // Filtros
    private final JTextField txtFiltroTitulo = new JTextField(18);
    private final JComboBox<Object> cmbFiltroAutor = new JComboBox<>();
    private final JComboBox<Object> cmbFiltroGenero = new JComboBox<>();
    private final JLabel lblResultados = new JLabel(" ");

    // Formulario
    private final JTextField txtTitulo = new JTextField(25);
    private final JComboBox<Autor> cmbAutor = new JComboBox<>();
    private final JTextField txtAnio = new JTextField(8);
    private final JTextField txtIsbn = new JTextField(16);
    private final Map<Genero, JRadioButton> radios = new EnumMap<>(Genero.class);
    private final ButtonGroup grupoGenero = new ButtonGroup();
    private final JTextArea txtDescripcion = new JTextArea(4, 25);

    private final JButton btnAgregar = new JButton("Agregar");
    private final JButton btnActualizar = new JButton("Actualizar");
    private final JButton btnEliminar = new JButton("Eliminar");
    private final JButton btnLimpiar = new JButton("Limpiar");

    private Libro seleccionado;
    private boolean cargando;

    public PanelLibros(Biblioteca biblioteca) {
        this.biblioteca = biblioteca;

        setLayout(new BorderLayout(8, 8));
        setBorder(new EmptyBorder(10, 10, 10, 10));
        add(crearPanelFiltros(), BorderLayout.NORTH);
        add(new JScrollPane(tabla), BorderLayout.CENTER);
        add(crearFormulario(), BorderLayout.SOUTH);

        configurarTabla();
        conectarEventos();

        biblioteca.agregarOyente(this);
        limpiar();
        datosCambiaron();
    }

    // ------------------------------------------------------ Construcción

    private JPanel crearPanelFiltros() {
        DefaultComboBoxModel<Object> generos = new DefaultComboBoxModel<>();
        generos.addElement(TODOS_GENEROS);
        for (Genero g : Genero.values()) {
            generos.addElement(g);
        }
        cmbFiltroGenero.setModel(generos);

        JPanel p = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 4));
        p.setBorder(BorderFactory.createTitledBorder("Buscar y filtrar libros"));
        p.add(new JLabel("Título:"));
        p.add(txtFiltroTitulo);
        p.add(new JLabel("Autor:"));
        p.add(cmbFiltroAutor);
        p.add(new JLabel("Género:"));
        p.add(cmbFiltroGenero);
        p.add(lblResultados);
        return p;
    }

    private JPanel crearFormulario() {
        JPanel panelGenero = new JPanel(new FlowLayout(FlowLayout.LEFT, 6, 0));
        for (Genero g : Genero.values()) {
            JRadioButton rb = new JRadioButton(g.getEtiqueta());
            radios.put(g, rb);
            grupoGenero.add(rb);
            panelGenero.add(rb);
        }

        JPanel anioIsbn = new JPanel(new FlowLayout(FlowLayout.LEFT, 6, 0));
        anioIsbn.add(txtAnio);
        anioIsbn.add(new JLabel("ISBN:"));
        anioIsbn.add(txtIsbn);

        txtDescripcion.setLineWrap(true);
        txtDescripcion.setWrapStyleWord(true);

        JPanel campos = new JPanel(new GridBagLayout());
        Ui.fila(campos, 0, "Título:", txtTitulo, false);
        Ui.fila(campos, 1, "Autor:", cmbAutor, false);
        Ui.fila(campos, 2, "Año:", anioIsbn, false);
        Ui.fila(campos, 3, "Género:", panelGenero, false);
        Ui.fila(campos, 4, "Descripción:", new JScrollPane(txtDescripcion), true);

        JPanel botones = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 4));
        botones.add(btnLimpiar);
        botones.add(btnEliminar);
        botones.add(btnActualizar);
        botones.add(btnAgregar);

        JPanel form = new JPanel(new BorderLayout());
        form.setBorder(BorderFactory.createTitledBorder("Datos del libro"));
        form.add(campos, BorderLayout.CENTER);
        form.add(botones, BorderLayout.SOUTH);
        return form;
    }

    private void configurarTabla() {
        tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabla.setAutoCreateRowSorter(true);
        tabla.setFillsViewportHeight(true);
        tabla.setRowHeight(24);
        int[] anchos = {280, 200, 110, 60, 120};
        for (int i = 0; i < anchos.length; i++) {
            tabla.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);
        }
    }

    private void conectarEventos() {
        txtFiltroTitulo.getDocument().addDocumentListener(new CambioTexto(this::aplicarFiltros));
        cmbFiltroAutor.addActionListener(e -> {
            if (!cargando) {
                aplicarFiltros();
            }
        });
        cmbFiltroGenero.addActionListener(e -> {
            if (!cargando) {
                aplicarFiltros();
            }
        });

        tabla.getSelectionModel().addListSelectionListener(e -> {
            if (e.getValueIsAdjusting() || cargando) {
                return;
            }
            int fila = tabla.getSelectedRow();
            if (fila >= 0) {
                seleccionado = modelo.getLibro(tabla.convertRowIndexToModel(fila));
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
            biblioteca.agregarLibro(txtTitulo.getText(), (Autor) cmbAutor.getSelectedItem(),
                    leerGenero(), leerAnio(), txtIsbn.getText(), txtDescripcion.getText());
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
            biblioteca.editarLibro(seleccionado, txtTitulo.getText(), (Autor) cmbAutor.getSelectedItem(),
                    leerGenero(), leerAnio(), txtIsbn.getText(), txtDescripcion.getText());
        } catch (IllegalArgumentException ex) {
            Ui.error(this, ex.getMessage());
        }
    }

    private void eliminar() {
        if (seleccionado == null) {
            return;
        }
        if (!Ui.confirmar(this, "¿Eliminar el libro \"" + seleccionado.getTitulo() + "\"?", "Confirmar")) {
            return;
        }
        try {
            biblioteca.eliminarLibro(seleccionado);
            limpiar();
        } catch (IllegalArgumentException ex) {
            Ui.error(this, ex.getMessage());
        }
    }

    private void limpiar() {
        seleccionado = null;
        tabla.clearSelection();
        txtTitulo.setText("");
        txtAnio.setText("");
        txtIsbn.setText("");
        txtDescripcion.setText("");
        radios.get(Genero.NOVELA).setSelected(true);
        if (cmbAutor.getItemCount() > 0) {
            cmbAutor.setSelectedIndex(0);
        }
        actualizarBotones();
        txtTitulo.requestFocusInWindow();
    }

    private void mostrar(Libro l) {
        txtTitulo.setText(l.getTitulo());
        cmbAutor.setSelectedItem(l.getAutor());
        txtAnio.setText(l.getAnio() == 0 ? "" : String.valueOf(l.getAnio()));
        txtIsbn.setText(l.getIsbn());
        radios.get(l.getGenero()).setSelected(true);
        txtDescripcion.setText(l.getDescripcion());
        txtDescripcion.setCaretPosition(0);
    }

    private void actualizarBotones() {
        boolean haySeleccion = seleccionado != null;
        btnActualizar.setEnabled(haySeleccion);
        btnEliminar.setEnabled(haySeleccion);
    }

    private Genero leerGenero() {
        for (Map.Entry<Genero, JRadioButton> e : radios.entrySet()) {
            if (e.getValue().isSelected()) {
                return e.getKey();
            }
        }
        return null;
    }

    private int leerAnio() {
        String texto = txtAnio.getText().trim();
        if (texto.isEmpty()) {
            return 0;
        }
        try {
            return Integer.parseInt(texto);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("El año debe ser un número entero.");
        }
    }

    // ------------------------------------------------------------ Datos

    /** Se llama cuando cambian los datos: reconstruye los combos y vuelve a filtrar. */
    @Override
    public void datosCambiaron() {
        List<Autor> autores = biblioteca.getAutores();
        cargando = true;

        Object previoFiltro = cmbFiltroAutor.getSelectedItem();
        DefaultComboBoxModel<Object> filtro = new DefaultComboBoxModel<>();
        filtro.addElement(TODOS_AUTORES);
        for (Autor a : autores) {
            filtro.addElement(a);
        }
        cmbFiltroAutor.setModel(filtro);
        if (previoFiltro != null) {
            cmbFiltroAutor.setSelectedItem(previoFiltro);
        }

        Object previoForm = cmbAutor.getSelectedItem();
        cmbAutor.setModel(new DefaultComboBoxModel<>(autores.toArray(new Autor[0])));
        if (previoForm != null) {
            cmbAutor.setSelectedItem(previoForm);
        }

        cargando = false;
        aplicarFiltros();

        if (seleccionado != null && !biblioteca.getLibros().contains(seleccionado)) {
            limpiar();
        }
    }

    private void aplicarFiltros() {
        Object a = cmbFiltroAutor.getSelectedItem();
        Object g = cmbFiltroGenero.getSelectedItem();
        List<Libro> resultado = biblioteca.buscarLibros(txtFiltroTitulo.getText(),
                a instanceof Autor ? (Autor) a : null,
                g instanceof Genero ? (Genero) g : null);

        cargando = true;
        modelo.setLibros(resultado);
        int idx = seleccionado == null ? -1 : modelo.indiceDe(seleccionado);
        if (idx >= 0) {
            int vista = tabla.convertRowIndexToView(idx);
            tabla.getSelectionModel().setSelectionInterval(vista, vista);
        }
        cargando = false;
        lblResultados.setText(resultado.size() + " de " + biblioteca.cantidadLibros() + " libros");
    }
}
