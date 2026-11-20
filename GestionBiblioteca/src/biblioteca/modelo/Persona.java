package biblioteca.modelo;

import java.io.Serializable;

/**
 * Clase abstracta base para las personas del sistema (HERENCIA).
 * Actualmente solo la extiende {@link Autor}, pero permitiría agregar
 * otros tipos (Editor, Lector, etc.) sin cambiar el resto de la aplicación.
 */
public abstract class Persona implements Identificable, Serializable {
    private static final long serialVersionUID = 1L;

    private final int id;
    private String nombre;
    private String nacionalidad;

    protected Persona(int id, String nombre, String nacionalidad) {
        this.id = id;
        this.nombre = nombre;
        this.nacionalidad = nacionalidad;
    }

    @Override
    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNacionalidad() {
        return nacionalidad;
    }

    public void setNacionalidad(String nacionalidad) {
        this.nacionalidad = nacionalidad;
    }

    /** Cada tipo de persona define cómo se resume en una línea. */
    public abstract String resumen();

    @Override
    public String toString() {
        return nombre;
    }
}
