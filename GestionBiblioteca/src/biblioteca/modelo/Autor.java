package biblioteca.modelo;

/**
 * Autor de uno o varios libros. Hereda de {@link Persona}.
 */
public class Autor extends Persona {
    private static final long serialVersionUID = 1L;

    private String biografia;

    public Autor(int id, String nombre, String nacionalidad, String biografia) {
        super(id, nombre, nacionalidad);
        this.biografia = biografia;
    }

    public String getBiografia() {
        return biografia;
    }

    public void setBiografia(String biografia) {
        this.biografia = biografia;
    }

    @Override
    public String resumen() {
        String nac = getNacionalidad();
        return (nac == null || nac.isEmpty()) ? getNombre() : getNombre() + " (" + nac + ")";
    }
}
