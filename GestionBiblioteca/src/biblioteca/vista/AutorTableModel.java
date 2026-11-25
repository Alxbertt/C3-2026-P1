package biblioteca.vista;

import biblioteca.modelo.Autor;
import biblioteca.servicio.Biblioteca;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

/**
 * Modelo de tabla para mostrar autores (hereda de AbstractTableModel).
 */
public class AutorTableModel extends AbstractTableModel {
    private static final long serialVersionUID = 1L;
    private static final String[] COLUMNAS = {"Nombre", "Nacionalidad", "Libros"};

    private final Biblioteca biblioteca;
    private List<Autor> autores = new ArrayList<>();

    public AutorTableModel(Biblioteca biblioteca) {
        this.biblioteca = biblioteca;
    }

    public void setAutores(List<Autor> nuevos) {
        autores = new ArrayList<>(nuevos);
        fireTableDataChanged();
    }

    public Autor getAutor(int fila) {
        return autores.get(fila);
    }

    public int indiceDe(Autor autor) {
        return autores.indexOf(autor);
    }

    @Override
    public int getRowCount() {
        return autores.size();
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
        return columna == 2 ? Integer.class : String.class;
    }

    @Override
    public Object getValueAt(int fila, int columna) {
        Autor a = autores.get(fila);
        switch (columna) {
            case 0:
                return a.getNombre();
            case 1:
                return a.getNacionalidad();
            default:
                return biblioteca.contarLibros(a);
        }
    }
}
