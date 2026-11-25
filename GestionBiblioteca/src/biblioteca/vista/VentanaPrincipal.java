package biblioteca.vista;

import biblioteca.servicio.Biblioteca;
import biblioteca.servicio.BibliotecaListener;
import biblioteca.servicio.ExportadorCSV;

import javax.swing.BorderFactory;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.KeyStroke;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.BorderLayout;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;

/**
 * Ventana principal: barra de menú (Archivo / Ayuda), pestañas de libros
 * y autores, y barra de estado.
 */
public class VentanaPrincipal extends JFrame implements BibliotecaListener {
    private static final long serialVersionUID = 1L;
    private static final String EXTENSION = "bib";

    private final Biblioteca biblioteca;
    private final JLabel lblEstado = new JLabel(" ");
    private final JTabbedPane pestanas = new JTabbedPane();

    public VentanaPrincipal(Biblioteca biblioteca) {
        super("Gestión de Biblioteca");
        this.biblioteca = biblioteca;

        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                salir();
            }
        });

        setJMenuBar(crearMenu());

        pestanas.addTab("Libros", new PanelLibros(biblioteca));
        pestanas.addTab("Autores", new PanelAutores(biblioteca));
        add(pestanas, BorderLayout.CENTER);

        JPanel barraEstado = new JPanel(new BorderLayout());
        barraEstado.setBorder(BorderFactory.createEmptyBorder(4, 10, 4, 10));
        barraEstado.add(lblEstado, BorderLayout.WEST);
        add(barraEstado, BorderLayout.SOUTH);

        biblioteca.agregarOyente(this);
        datosCambiaron();

        setSize(1000, 760);
        setMinimumSize(new java.awt.Dimension(800, 600));
        setLocationRelativeTo(null);
    }

    // --------------------------------------------------------------- Menú

    private JMenuBar crearMenu() {
        int tecla = Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx();

        JMenu archivo = new JMenu("Archivo");
        archivo.setMnemonic(KeyEvent.VK_A);
        archivo.add(item("Nuevo", KeyEvent.VK_N, tecla, e -> nuevo()));
        archivo.add(item("Abrir...", KeyEvent.VK_O, tecla, e -> abrir()));
        archivo.add(item("Guardar", KeyEvent.VK_S, tecla, e -> guardar()));
        archivo.add(item("Guardar como...", 0, 0, e -> guardarComo()));
        archivo.addSeparator();

        JMenu exportar = new JMenu("Exportar a CSV");
        exportar.add(item("Libros...", 0, 0, e -> exportarLibros()));
        exportar.add(item("Autores...", 0, 0, e -> exportarAutores()));
        archivo.add(exportar);

        archivo.add(item("Cargar datos de ejemplo", 0, 0, e -> cargarEjemplo()));
        archivo.addSeparator();
        archivo.add(item("Salir", KeyEvent.VK_Q, tecla, e -> salir()));

        JMenu ayuda = new JMenu("Ayuda");
        ayuda.setMnemonic(KeyEvent.VK_Y);
        ayuda.add(item("Instrucciones de uso", KeyEvent.VK_F1, 0, e -> mostrarInstrucciones()));
        ayuda.addSeparator();
        ayuda.add(item("Acerca de...", 0, 0, e -> mostrarAcercaDe()));

        JMenuBar barra = new JMenuBar();
        barra.add(archivo);
        barra.add(ayuda);
        return barra;
    }

    private JMenuItem item(String texto, int teclaCodigo, int modificador,
                           java.awt.event.ActionListener accion) {
        JMenuItem it = new JMenuItem(texto);
        if (teclaCodigo != 0) {
            it.setAccelerator(KeyStroke.getKeyStroke(teclaCodigo, modificador));
        }
        it.addActionListener(accion);
        return it;
    }

    // ---------------------------------------------------------- Acciones

    private void nuevo() {
        if (confirmarDescartar()) {
            biblioteca.nuevo();
        }
    }

    private void abrir() {
        if (!confirmarDescartar()) {
            return;
        }
        JFileChooser fc = selector("Abrir biblioteca", "Biblioteca (*.bib)", EXTENSION);
        if (fc.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            try {
                biblioteca.cargar(fc.getSelectedFile());
            } catch (IOException ex) {
                Ui.error(this, "No se pudo abrir el archivo:\n" + ex.getMessage());
            }
        }
    }

    private void guardar() {
        File actual = biblioteca.getArchivoActual();
        if (actual == null) {
            guardarComo();
            return;
        }
        guardarEn(actual);
    }

    private void guardarComo() {
        JFileChooser fc = selector("Guardar biblioteca", "Biblioteca (*.bib)", EXTENSION);
        if (fc.showSaveDialog(this) != JFileChooser.APPROVE_OPTION) {
            return;
        }
        File f = conExtension(fc.getSelectedFile(), EXTENSION);
        if (f.exists() && !Ui.confirmar(this, "El archivo ya existe. ¿Desea reemplazarlo?", "Confirmar")) {
            return;
        }
        guardarEn(f);
    }

    private void guardarEn(File f) {
        try {
            biblioteca.guardar(f);
        } catch (IOException ex) {
            Ui.error(this, "No se pudo guardar el archivo:\n" + ex.getMessage());
        }
    }

    private void exportarLibros() {
        File f = pedirArchivoCsv("libros.csv");
        if (f == null) {
            return;
        }
        try {
            ExportadorCSV.exportarLibros(biblioteca.getLibros(), f);
            Ui.info(this, "Se exportaron " + biblioteca.cantidadLibros() + " libros a:\n" + f, "Exportación");
        } catch (IOException ex) {
            Ui.error(this, "No se pudo exportar:\n" + ex.getMessage());
        }
    }

    private void exportarAutores() {
        File f = pedirArchivoCsv("autores.csv");
        if (f == null) {
            return;
        }
        try {
            ExportadorCSV.exportarAutores(biblioteca.getAutores(), f);
            Ui.info(this, "Se exportaron " + biblioteca.cantidadAutores() + " autores a:\n" + f, "Exportación");
        } catch (IOException ex) {
            Ui.error(this, "No se pudo exportar:\n" + ex.getMessage());
        }
    }

    private File pedirArchivoCsv(String sugerido) {
        JFileChooser fc = selector("Exportar a CSV", "Archivo CSV (*.csv)", "csv");
        fc.setSelectedFile(new File(sugerido));
        if (fc.showSaveDialog(this) != JFileChooser.APPROVE_OPTION) {
            return null;
        }
        File f = conExtension(fc.getSelectedFile(), "csv");
        if (f.exists() && !Ui.confirmar(this, "El archivo ya existe. ¿Desea reemplazarlo?", "Confirmar")) {
            return null;
        }
        return f;
    }

    private void cargarEjemplo() {
        if (confirmarDescartar()) {
            biblioteca.cargarEjemplo();
        }
    }

    private void salir() {
        if (confirmarDescartar()) {
            dispose();
            System.exit(0);
        }
    }

    /** Pregunta qué hacer con los cambios sin guardar. Devuelve false si se cancela. */
    private boolean confirmarDescartar() {
        if (!biblioteca.isModificado()) {
            return true;
        }
        Object[] opciones = {"Guardar", "Descartar", "Cancelar"};
        int r = JOptionPane.showOptionDialog(this,
                "Hay cambios sin guardar. ¿Qué desea hacer?", "Cambios sin guardar",
                JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE,
                null, opciones, opciones[0]);
        if (r == 0) {
            guardar();
            return !biblioteca.isModificado();
        }
        return r == 1;
    }

    private void mostrarInstrucciones() {
        Ui.info(this,
                "LIBROS\n"
                        + " • Complete el formulario y presione «Agregar».\n"
                        + " • Seleccione una fila de la tabla para editarla o eliminarla.\n"
                        + " • Use los filtros de título, autor y género para buscar.\n"
                        + " • Haga clic en el encabezado de una columna para ordenar.\n\n"
                        + "AUTORES\n"
                        + " • Registre primero los autores para poder asignarlos a los libros.\n"
                        + " • Un autor con libros no se puede eliminar.\n\n"
                        + "ARCHIVO\n"
                        + " • «Guardar» y «Abrir» usan archivos .bib.\n"
                        + " • «Exportar a CSV» genera archivos para Excel.",
                "Instrucciones de uso");
    }

    private void mostrarAcercaDe() {
        Ui.info(this,
                "Gestión de Biblioteca 1.0\n\n"
                        + "Aplicación de escritorio en Java Swing\n"
                        + "para la tarea de Programación Orientada a Objetos.",
                "Acerca de");
    }

    // ----------------------------------------------------------- Estado

    @Override
    public void datosCambiaron() {
        File f = biblioteca.getArchivoActual();
        String nombre = f == null ? "Sin título" : f.getName();
        String marca = biblioteca.isModificado() ? " *" : "";
        setTitle("Gestión de Biblioteca — " + nombre + marca);
        lblEstado.setText("Archivo: " + nombre + marca + "   |   "
                + biblioteca.cantidadLibros() + " libros   |   "
                + biblioteca.cantidadAutores() + " autores");
    }

    // ----------------------------------------------------------- Ayudas

    private static JFileChooser selector(String titulo, String descripcion, String extension) {
        JFileChooser fc = new JFileChooser();
        fc.setDialogTitle(titulo);
        fc.setFileFilter(new FileNameExtensionFilter(descripcion, extension));
        return fc;
    }

    private static File conExtension(File f, String extension) {
        if (f.getName().toLowerCase().endsWith("." + extension)) {
            return f;
        }
        return new File(f.getParentFile(), f.getName() + "." + extension);
    }
}
