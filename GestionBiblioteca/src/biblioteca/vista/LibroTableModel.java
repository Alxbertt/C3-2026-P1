package biblioteca.vista;

import biblioteca.modelo.Libro;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

/**
 * Modelo de tabla para mostrar libros (hereda de AbstractTableModel).
 */
public class LibroTableModel extends AbstractTableModel {
    private static final long serialVersionUID = 1L;
    private static final String[] COLUMNAS = {"Título", "Autor", "Género", "Año", "ISBN"};

    private List<Libro> libros = new ArrayList<>();

    public void setLibros(List<Libro> nuevos) {
        libros = new ArrayList<>(nuevos);
        fireTableDataChanged();
    }

    public Libro getLibro(int fila) {
        return libros.get(fila);
    }

    public int indiceDe(Libro libro) {
        return libros.indexOf(libro);
    }

    @Override
    public int getRowCount() {
        return libros.size();
    }

    @Override
    public int getColumnCount() {
        return COLUMNAS.length;
    }

    @Override
    public String getColumnName(int columna) {
        return COLUMNAS[columna];
    }

    @Override
    public Class<?> getColumnClass(int columna) {
        return columna == 3 ? Integer.class : String.class;
    }

    @Override
    public Object getValueAt(int fila, int columna) {
        Libro l = libros.get(fila);
        switch (columna) {
            case 0:
                return l.getTitulo();
            case 1:
                return l.getAutor().getNombre();
            case 2:
                return l.getGenero().getEtiqueta();
            case 3:
                return l.getAnio() == 0 ? null : l.getAnio();
            default:
                return l.getIsbn();
        }
    }
}
