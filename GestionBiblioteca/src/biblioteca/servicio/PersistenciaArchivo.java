package biblioteca.servicio;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Implementación de {@link Persistencia} usando serialización de Java.
 * Como se guarda todo el grafo de objetos junto, las referencias
 * Libro -> Autor se conservan al cargar.
 */
public class PersistenciaArchivo implements Persistencia {

    @Override
    public void guardar(DatosBiblioteca datos, File archivo) throws IOException {
        try (ObjectOutputStream out = new ObjectOutputStream(
                new BufferedOutputStream(new FileOutputStream(archivo)))) {
            out.writeObject(datos);
        }
    }

    @Override
    public DatosBiblioteca cargar(File archivo) throws IOException {
        try (ObjectInputStream in = new ObjectInputStream(
                new BufferedInputStream(new FileInputStream(archivo)))) {
            Object leido = in.readObject();
            if (!(leido instanceof DatosBiblioteca)) {
                throw new IOException("El archivo no contiene datos de la biblioteca.");
            }
            return (DatosBiblioteca) leido;
        } catch (ClassNotFoundException | java.io.InvalidClassException e) {
            throw new IOException("El archivo no es válido o es de otra versión.", e);
        }
    }
}
