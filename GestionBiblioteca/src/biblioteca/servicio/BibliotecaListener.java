package biblioteca.servicio;

/**
 * Interfaz para los componentes que quieren enterarse cuando
 * cambian los datos de la biblioteca (patrón Observador).
 */
public interface BibliotecaListener {
    void datosCambiaron();
}
