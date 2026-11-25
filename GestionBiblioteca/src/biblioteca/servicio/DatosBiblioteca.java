package biblioteca.servicio;

import biblioteca.modelo.Autor;
import biblioteca.modelo.Libro;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Contenedor serializable con todo lo que se guarda en el archivo:
 * autores, libros y el contador de identificadores.
 */
public class DatosBiblioteca implements Serializable {
    private static final long serialVersionUID = 1L;

    final ArrayList<Autor> autores = new ArrayList<>();
    final ArrayList<Libro> libros = new ArrayList<>();
    private int siguienteId = 1;

    int nuevoId() {
        return siguienteId++;
    }
}
