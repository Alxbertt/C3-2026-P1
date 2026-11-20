package biblioteca.servicio;

import java.io.File;
import java.io.IOException;

/**
 * Contrato para guardar y cargar los datos. Permite cambiar la forma
 * de almacenamiento (archivo binario, JSON, base de datos...) sin tocar el resto.
 */
public interface Persistencia {
    void guardar(DatosBiblioteca datos, File archivo) throws IOException;

    DatosBiblioteca cargar(File archivo) throws IOException;
}
