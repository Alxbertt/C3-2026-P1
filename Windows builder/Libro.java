/**
 * Ejercicio 5 - Clase Libro.
 * Una biblioteca maneja libros: algunos son originales y otros fotocopias;
 * no todos se prestan.
 */
public class Libro {

    // b) Atributos
    private String titulo;
    private boolean original;
    private boolean prestable;

    public Libro(String titulo, boolean original, boolean prestable) {
        this.titulo = titulo;
        this.original = original;
        this.prestable = prestable;
    }

    // c) Getters
    public String getTitulo()      { return titulo; }
    public boolean getOriginal()   { return original; }
    public boolean getPrestable()  { return prestable; }

    // d) Metodos booleanos
    public boolean esOriginal()    { return original; }
    public boolean sePresta()      { return prestable; }

    // e) Setters
    public void setTitulo(String titulo)        { this.titulo = titulo; }
    public void setOriginal(boolean original)   { this.original = original; }
    public void setPrestable(boolean prestable) { this.prestable = prestable; }

    @Override
    public String toString() {
        return "Libro: \"" + titulo + "\" | "
                + (esOriginal() ? "Original" : "Fotocopia") + " | "
                + (sePresta() ? "Se presta" : "No se presta");
    }
}
