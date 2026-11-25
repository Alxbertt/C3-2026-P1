package biblioteca.servicio;

import biblioteca.modelo.Autor;
import biblioteca.modelo.Libro;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * Exporta libros y autores a archivos CSV (se abren directamente en Excel).
 */
public final class ExportadorCSV {
    private ExportadorCSV() {
    }

    public static void exportarLibros(List<Libro> libros, File archivo) throws IOException {
        try (Writer w = abrir(archivo)) {
            linea(w, "Título", "Autor", "Género", "Año", "ISBN", "Descripción");
            for (Libro l : libros) {
                linea(w, l.getTitulo(), l.getAutor().getNombre(), l.getGenero().getEtiqueta(),
                        l.getAnio() == 0 ? "" : String.valueOf(l.getAnio()),
                        l.getIsbn(), l.getDescripcion());
            }
        }
    }

    public static void exportarAutores(List<Autor> autores, File archivo) throws IOException {
        try (Writer w = abrir(archivo)) {
            linea(w, "Nombre", "Nacionalidad", "Biografía");
            for (Autor a : autores) {
                linea(w, a.getNombre(), a.getNacionalidad(), a.getBiografia());
            }
        }
    }

    private static Writer abrir(File archivo) throws IOException {
        Writer w = new OutputStreamWriter(new FileOutputStream(archivo), StandardCharsets.UTF_8);
        w.write('\uFEFF'); // BOM para que Excel reconozca las tildes
        return w;
    }

    private static void linea(Writer w, String... campos) throws IOException {
        for (int i = 0; i < campos.length; i++) {
            if (i > 0) {
                w.write(',');
            }
            w.write(escapar(campos[i]));
        }
        w.write("\r\n");
    }

    private static String escapar(String s) {
        if (s == null) {
            return "";
        }
        if (s.contains(",") || s.contains("\"") || s.contains("\n") || s.contains("\r")) {
            return "\"" + s.replace("\"", "\"\"") + "\"";
        }
        return s;
    }
}
