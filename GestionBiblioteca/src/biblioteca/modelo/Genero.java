package biblioteca.modelo;

/**
 * Géneros disponibles para clasificar un libro.
 */
public enum Genero {
    NOVELA("Novela"),
    CIENCIA_FICCION("Ciencia ficción"),
    FANTASIA("Fantasía"),
    MISTERIO("Misterio"),
    HISTORIA("Historia"),
    CUENTOS("Cuentos"),
    OTRO("Otro");

    private final String etiqueta;

    Genero(String etiqueta) {
        this.etiqueta = etiqueta;
    }

    public String getEtiqueta() {
        return etiqueta;
    }

    @Override
    public String toString() {
        return etiqueta;
    }
}
