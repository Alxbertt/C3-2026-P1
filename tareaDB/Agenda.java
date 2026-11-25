
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

public class Agenda extends JFrame {

    // ====== CAMBIA ESTOS DATOS POR LOS TUYOS ======
    static final String NOMBRE = "Albert Emmanuel Hichez Perez";
    static final String MATRICULA = "2026-0047";
    static final String CARRERA = "Desarrollo de software";
    static final String ASIGNATURA = "Programación 1";
    // ===============================================

    static final String ARCHIVO = "agenda.txt";

    private final DefaultTableModel modelo = new DefaultTableModel(
            new String[]{"ID", "Título", "Fecha", "Hora", "Descripción"}, 0) {
        @Override
        public boolean isCellEditable(int r, int c) {
            return false;
        }
    };
    private final JTable tabla = new JTable(modelo);
    private final JTextField txtTitulo = new JTextField(20);
    private final JTextField txtFecha = new JTextField(10);
    private final JTextField txtHora = new JTextField(6);
    private final JTextArea txtDescripcion = new JTextArea(3, 20);

    // Lista con todos los eventos (la tabla puede mostrar solo los filtrados)
    private final List<String[]> eventos = new ArrayList<>();
    private int siguienteId = 1;

    public Agenda() {
        super("Agenda Electrónica");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(760, 480);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(8, 8));

        crearMenu();
        add(crearFormulario(), BorderLayout.NORTH);

        tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabla.setRowHeight(22);
        tabla.getColumnModel().getColumn(0).setMaxWidth(50);
        tabla.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) cargarFilaSeleccionada();
        });
        add(new JScrollPane(tabla), BorderLayout.CENTER);

        cargarArchivo();
        mostrar(eventos);
    }

    // ---------------------------------------------------------------- Interfaz
    private void crearMenu() {
        JMenuBar barra = new JMenuBar();

        JMenu acciones = new JMenu("Acciones");
        acciones.add(item("Guardar", e -> guardar()));
        acciones.add(item("Actualizar", e -> actualizar()));
        acciones.add(item("Borrar", e -> borrar()));
        acciones.add(item("Buscar", e -> buscar()));

        JMenu info = new JMenu("Información");
        info.add(item("Perfil del estudiante", e -> perfil()));

        barra.add(acciones);
        barra.add(info);
        setJMenuBar(barra);
    }

    private JMenuItem item(String texto, java.awt.event.ActionListener al) {
        JMenuItem it = new JMenuItem(texto);
        it.addActionListener(al);
        return it;
    }

    private JPanel crearFormulario() {
        JPanel p = new JPanel(new GridBagLayout());
        p.setBorder(BorderFactory.createTitledBorder("Datos del evento"));
        GridBagConstraints g = new GridBagConstraints();
        g.insets = new Insets(4, 6, 4, 6);
        g.anchor = GridBagConstraints.WEST;

        g.gridx = 0; g.gridy = 0; p.add(new JLabel("Título:"), g);
        g.gridx = 1; g.gridwidth = 3; g.fill = GridBagConstraints.HORIZONTAL; p.add(txtTitulo, g);

        g.gridwidth = 1; g.fill = GridBagConstraints.NONE;
        g.gridx = 0; g.gridy = 1; p.add(new JLabel("Fecha (dd/mm/aaaa):"), g);
        g.gridx = 1; p.add(txtFecha, g);
        g.gridx = 2; p.add(new JLabel("Hora (hh:mm):"), g);
        g.gridx = 3; p.add(txtHora, g);

        g.gridx = 0; g.gridy = 2; p.add(new JLabel("Descripción:"), g);
        g.gridx = 1; g.gridwidth = 3; g.fill = GridBagConstraints.HORIZONTAL;
        txtDescripcion.setLineWrap(true);
        p.add(new JScrollPane(txtDescripcion), g);
        return p;
    }

    // ---------------------------------------------------------------- Acciones
    private void guardar() {
        if (!validar()) return;
        eventos.add(new String[]{String.valueOf(siguienteId++), txtTitulo.getText().trim(),
                txtFecha.getText().trim(), txtHora.getText().trim(), txtDescripcion.getText().trim()});
        guardarArchivo();
        mostrar(eventos);
        limpiar();
        JOptionPane.showMessageDialog(this, "Evento guardado correctamente.");
    }

    private void actualizar() {
        String[] ev = eventoSeleccionado();
        if (ev == null) {
            JOptionPane.showMessageDialog(this, "Selecciona un evento de la tabla para actualizar.");
            return;
        }
        if (!validar()) return;
        ev[1] = txtTitulo.getText().trim();
        ev[2] = txtFecha.getText().trim();
        ev[3] = txtHora.getText().trim();
        ev[4] = txtDescripcion.getText().trim();
        guardarArchivo();
        mostrar(eventos);
        limpiar();
        JOptionPane.showMessageDialog(this, "Evento actualizado correctamente.");
    }

    private void borrar() {
        String[] ev = eventoSeleccionado();
        if (ev == null) {
            JOptionPane.showMessageDialog(this, "Selecciona un evento de la tabla para borrar.");
            return;
        }
        int r = JOptionPane.showConfirmDialog(this, "¿Seguro que deseas borrar \"" + ev[1] + "\"?",
                "Confirmar", JOptionPane.YES_NO_OPTION);
        if (r == JOptionPane.YES_OPTION) {
            eventos.remove(ev);
            guardarArchivo();
            mostrar(eventos);
            limpiar();
        }
    }

    private void buscar() {
        String texto = JOptionPane.showInputDialog(this,
                "Escribe lo que quieres buscar (título, fecha, hora o descripción).\nDeja vacío para ver todo:",
                "Buscar", JOptionPane.QUESTION_MESSAGE);
        if (texto == null) return;
        texto = texto.trim().toLowerCase();
        List<String[]> res = new ArrayList<>();
        for (String[] ev : eventos) {
            for (int i = 1; i < ev.length; i++) {
                if (ev[i].toLowerCase().contains(texto)) {
                    res.add(ev);
                    break;
                }
            }
        }
        mostrar(res);
        if (res.isEmpty()) JOptionPane.showMessageDialog(this, "No se encontraron eventos.");
    }

    private void perfil() {
        String msg = "Nombre: " + NOMBRE + "\n"
                + "Matrícula: " + MATRICULA + "\n"
                + "Carrera: " + CARRERA + "\n"
                + "Asignatura: " + ASIGNATURA;
        JOptionPane.showMessageDialog(this, msg, "Perfil del estudiante", JOptionPane.INFORMATION_MESSAGE);
    }

    // ---------------------------------------------------------------- Auxiliares
    private boolean validar() {
        if (txtTitulo.getText().trim().isEmpty() || txtFecha.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "El título y la fecha son obligatorios.",
                    "Datos incompletos", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        return true;
    }

    private String[] eventoSeleccionado() {
        int fila = tabla.getSelectedRow();
        if (fila < 0) return null;
        String id = (String) modelo.getValueAt(fila, 0);
        for (String[] ev : eventos) if (ev[0].equals(id)) return ev;
        return null;
    }

    private void cargarFilaSeleccionada() {
        String[] ev = eventoSeleccionado();
        if (ev == null) return;
        txtTitulo.setText(ev[1]);
        txtFecha.setText(ev[2]);
        txtHora.setText(ev[3]);
        txtDescripcion.setText(ev[4]);
    }

    private void mostrar(List<String[]> lista) {
        modelo.setRowCount(0);
        for (String[] ev : lista) modelo.addRow(ev);
    }

    private void limpiar() {
        txtTitulo.setText("");
        txtFecha.setText("");
        txtHora.setText("");
        txtDescripcion.setText("");
        tabla.clearSelection();
    }

    // ---------------------------------------------------------------- Archivo
    private void guardarArchivo() {
        List<String> lineas = new ArrayList<>();
        for (String[] ev : eventos) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < ev.length; i++) {
                if (i > 0) sb.append('\t');
                sb.append(ev[i].replace('\t', ' ').replace('\n', ' ').replace('\r', ' '));
            }
            lineas.add(sb.toString());
        }
        try {
            Files.write(Paths.get(ARCHIVO), lineas, StandardCharsets.UTF_8);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "No se pudo guardar el archivo: " + e.getMessage());
        }
    }

    private void cargarArchivo() {
        Path p = Paths.get(ARCHIVO);
        if (!Files.exists(p)) return;
        try {
            for (String linea : Files.readAllLines(p, StandardCharsets.UTF_8)) {
                String[] partes = linea.split("\t", -1);
                if (partes.length == 5) {
                    eventos.add(partes);
                    siguienteId = Math.max(siguienteId, Integer.parseInt(partes[0]) + 1);
                }
            }
        } catch (IOException | NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "No se pudo leer el archivo: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Agenda().setVisible(true));
    }
}