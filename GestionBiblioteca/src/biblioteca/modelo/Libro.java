package biblioteca.modelo;

import java.io.Serializable;

/**
 * Libro de la biblioteca. Está asociado a un {@link Autor} y a un {@link Genero}.
 */
public class Libro implements Identificable, Serializable {
    private static final long serialVersionUID = 1L;

    private final int id;
    private String titulo;
    private Autor autor;
    private Genero genero;
    private int anio; // 0 = no especificado
    private String isbn;
    private String descripcion;

    public Libro(int id, String titulo, Autor autor, Genero genero,
                 int anio, String isbn, String descripcion) {
        this.id = id;
        this.titulo = titulo;
        this.autor = autor;
        this.genero = genero;
        this.anio = anio;
        this.isbn = isbn;
        this.descripcion = descripcion;
    }

    @Override
    public int getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    public Genero getGenero() {
        return genero;
    }

    public void setGenero(Genero genero) {
        this.genero = genero;
    }

    public int getAnio() {
        return anio;
    }

    public void setAnio(int anio) {
        this.anio = anio;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public String toString() {
        return titulo;
    }
}
