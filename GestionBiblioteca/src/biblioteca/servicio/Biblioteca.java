package biblioteca.servicio;

import biblioteca.modelo.Autor;
import biblioteca.modelo.Genero;
import biblioteca.modelo.Libro;

import java.io.File;
import java.io.IOException;
import java.time.Year;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Lógica de negocio de la aplicación: altas, bajas, modificaciones,
 * búsquedas y guardado/carga. La interfaz gráfica solo habla con esta clase.
 * Las validaciones lanzan IllegalArgumentException / IllegalStateException
 * con mensajes listos para mostrarse al usuario.
 */
public class Biblioteca {
    private DatosBiblioteca datos = new DatosBiblioteca();
    private final Persistencia persistencia;
    private final List<BibliotecaListener> oyentes = new ArrayList<>();
    private File archivoActual;
    private boolean modificado;

    public Biblioteca(Persistencia persistencia) {
        this.persistencia = persistencia;
    }

    // ------------------------------------------------------------ Oyentes

    public void agregarOyente(BibliotecaListener oyente) {
        oyentes.add(oyente);
    }

    private void notificar() {
        for (BibliotecaListener o : new ArrayList<>(oyentes)) {
            o.datosCambiaron();
        }
    }

    private void cambio() {
        modificado = true;
        notificar();
    }

    // ------------------------------------------------------------ Autores

    public Autor agregarAutor(String nombre, String nacionalidad, String biografia) {
        nombre = requerido(nombre, "El nombre del autor es obligatorio.");
        verificarNombreUnico(nombre, null);
        Autor autor = new Autor(datos.nuevoId(), nombre, limpiar(nacionalidad), limpiar(biografia));
        datos.autores.add(autor);
        cambio();
        return autor;
    }

    public void editarAutor(Autor autor, String nombre, String nacionalidad, String biografia) {
        verificarExiste(datos.autores.contains(autor), "El autor ya no existe.");
        nombre = requerido(nombre, "El nombre del autor es obligatorio.");
        verificarNombreUnico(nombre, autor);
        autor.setNombre(nombre);
        autor.setNacionalidad(limpiar(nacionalidad));
        autor.setBiografia(limpiar(biografia));
        cambio();
    }

    public void eliminarAutor(Autor autor) {
        verificarExiste(datos.autores.contains(autor), "El autor ya no existe.");
        int cantidad = contarLibros(autor);
        if (cantidad > 0) {
            throw new IllegalStateException("No se puede eliminar a \"" + autor.getNombre()
                    + "\" porque tiene " + cantidad + " libro(s) registrado(s).\n"
                    + "Elimine esos libros o cámbieles el autor primero.");
        }
        datos.autores.remove(autor);
        cambio();
    }

    public int contarLibros(Autor autor) {
        int n = 0;
        for (Libro l : datos.libros) {
            if (l.getAutor() == autor) {
                n++;
            }
        }
        return n;
    }

    public List<Autor> getAutores() {
        List<Autor> copia = new ArrayList<>(datos.autores);
        copia.sort(Comparator.comparing(a -> a.getNombre().toLowerCase()));
        return copia;
    }

    /** Busca autores cuyo nombre o nacionalidad contenga el texto. */
    public List<Autor> buscarAutores(String texto) {
        String t = texto == null ? "" : texto.trim().toLowerCase();
        List<Autor> resultado = new ArrayList<>();
        for (Autor a : getAutores()) {
            String nac = a.getNacionalidad() == null ? "" : a.getNacionalidad().toLowerCase();
            if (t.isEmpty() || a.getNombre().toLowerCase().contains(t) || nac.contains(t)) {
                resultado.add(a);
            }
        }
        return resultado;
    }

    // ------------------------------------------------------------- Libros

    public Libro agregarLibro(String titulo, Autor autor, Genero genero,
                              int anio, String isbn, String descripcion) {
        validarLibro(titulo, autor, genero, anio);
        Libro libro = new Libro(datos.nuevoId(), titulo.trim(), autor, genero,
                anio, limpiar(isbn), limpiar(descripcion));
        datos.libros.add(libro);
        cambio();
        return libro;
    }

    public void editarLibro(Libro libro, String titulo, Autor autor, Genero genero,
                            int anio, String isbn, String descripcion) {
        verificarExiste(datos.libros.contains(libro), "El libro ya no existe.");
        validarLibro(titulo, autor, genero, anio);
        libro.setTitulo(titulo.trim());
        libro.setAutor(autor);
        libro.setGenero(genero);
        libro.setAnio(anio);
        libro.setIsbn(limpiar(isbn));
        libro.setDescripcion(limpiar(descripcion));
        cambio();
    }

    public void eliminarLibro(Libro libro) {
        verificarExiste(datos.libros.contains(libro), "El libro ya no existe.");
        datos.libros.remove(libro);
        cambio();
    }

    public List<Libro> getLibros() {
        return buscarLibros(null, null, null);
    }

    public int cantidadLibros() {
        return datos.libros.size();
    }

    public int cantidadAutores() {
        return datos.autores.size();
    }

    /**
     * Busca y filtra libros.
     *
     * @param titulo texto que debe contener el título (vacío = cualquiera)
     * @param autor  autor exacto (null = cualquiera)
     * @param genero género exacto (null = cualquiera)
     */
    public List<Libro> buscarLibros(String titulo, Autor autor, Genero genero) {
        String t = titulo == null ? "" : titulo.trim().toLowerCase();
        List<Libro> resultado = new ArrayList<>();
        for (Libro l : datos.libros) {
            if (!t.isEmpty() && !l.getTitulo().toLowerCase().contains(t)) {
                continue;
            }
            if (autor != null && l.getAutor() != autor) {
                continue;
            }
            if (genero != null && l.getGenero() != genero) {
                continue;
            }
            resultado.add(l);
        }
        resultado.sort(Comparator.comparing(l -> l.getTitulo().toLowerCase()));
        return resultado;
    }

    private void validarLibro(String titulo, Autor autor, Genero genero, int anio) {
        requerido(titulo, "El título del libro es obligatorio.");
        if (autor == null || !datos.autores.contains(autor)) {
            throw new IllegalArgumentException("Debe seleccionar un autor.");
        }
        if (genero == null) {
            throw new IllegalArgumentException("Debe seleccionar un género.");
        }
        if (anio < 0 || anio > Year.now().getValue() + 1) {
            throw new IllegalArgumentException("El año de publicación no es válido.");
        }
    }

    // -------------------------------------------------- Archivo y estado

    public File getArchivoActual() {
        return archivoActual;
    }

    public boolean isModificado() {
        return modificado;
    }

    /** Vacía la biblioteca y empieza un documento nuevo. */
    public void nuevo() {
        datos = new DatosBiblioteca();
        archivoActual = null;
        modificado = false;
        notificar();
    }

    public void guardar(File archivo) throws IOException {
        persistencia.guardar(datos, archivo);
        archivoActual = archivo;
        modificado = false;
        notificar();
    }

    public void cargar(File archivo) throws IOException {
        datos = persistencia.cargar(archivo);
        archivoActual = archivo;
        modificado = false;
        notificar();
    }

    /** Reemplaza los datos actuales por un conjunto de ejemplo. */
    public void cargarEjemplo() {
        datos = new DatosBiblioteca();
        archivoActual = null;

        Autor garcia = nuevoAutor("Gabriel García Márquez", "Colombia",
                "Escritor colombiano, Premio Nobel de Literatura 1982.");
        Autor cervantes = nuevoAutor("Miguel de Cervantes", "España",
                "Autor del Quijote, figura central de la literatura en español.");
        Autor allende = nuevoAutor("Isabel Allende", "Chile",
                "Novelista chilena, una de las autoras más leídas en español.");
        Autor borges = nuevoAutor("Jorge Luis Borges", "Argentina",
                "Escritor argentino, maestro del cuento fantástico.");
        Autor tolkien = nuevoAutor("J. R. R. Tolkien", "Reino Unido",
                "Filólogo y escritor, creador de la Tierra Media.");
        Autor christie = nuevoAutor("Agatha Christie", "Reino Unido",
                "Reina de la novela policial clásica.");
        Autor asimov = nuevoAutor("Isaac Asimov", "Estados Unidos",
                "Escritor y bioquímico, referente de la ciencia ficción.");

        nuevoLibro("Cien años de soledad", garcia, Genero.NOVELA, 1967,
                "La saga de la familia Buendía en el pueblo imaginario de Macondo.");
        nuevoLibro("El amor en los tiempos del cólera", garcia, Genero.NOVELA, 1985,
                "Una historia de amor que espera más de cincuenta años.");
        nuevoLibro("Don Quijote de la Mancha", cervantes, Genero.NOVELA, 1605,
                "Las aventuras de un hidalgo que enloquece leyendo libros de caballerías.");
        nuevoLibro("La casa de los espíritus", allende, Genero.NOVELA, 1982,
                "Cuatro generaciones de la familia Trueba en un país latinoamericano.");
        nuevoLibro("Ficciones", borges, Genero.CUENTOS, 1944,
                "Colección de cuentos sobre laberintos, bibliotecas y universos infinitos.");
        nuevoLibro("El Aleph", borges, Genero.CUENTOS, 1949,
                "Relatos que giran en torno al infinito y al tiempo.");
        nuevoLibro("El hobbit", tolkien, Genero.FANTASIA, 1937,
                "Bilbo Bolsón se embarca en una aventura con trece enanos.");
        nuevoLibro("El Señor de los Anillos", tolkien, Genero.FANTASIA, 1954,
                "La lucha por destruir el Anillo Único.");
        nuevoLibro("Asesinato en el Orient Express", christie, Genero.MISTERIO, 1934,
                "Hércules Poirot investiga un crimen en un tren detenido por la nieve.");
        nuevoLibro("Fundación", asimov, Genero.CIENCIA_FICCION, 1951,
                "La caída de un imperio galáctico y el plan para salvar el conocimiento.");

        modificado = true;
        notificar();
    }

    private Autor nuevoAutor(String nombre, String nacionalidad, String biografia) {
        Autor a = new Autor(datos.nuevoId(), nombre, nacionalidad, biografia);
        datos.autores.add(a);
        return a;
    }

    private void nuevoLibro(String titulo, Autor autor, Genero genero, int anio, String desc) {
        datos.libros.add(new Libro(datos.nuevoId(), titulo, autor, genero, anio, "", desc));
    }

    // ----------------------------------------------------------- Ayudas

    private void verificarNombreUnico(String nombre, Autor excepto) {
        for (Autor a : datos.autores) {
            if (a != excepto && a.getNombre().equalsIgnoreCase(nombre)) {
                throw new IllegalArgumentException("Ya existe un autor con ese nombre.");
            }
        }
    }

    private static void verificarExiste(boolean existe, String mensaje) {
        if (!existe) {
            throw new IllegalArgumentException(mensaje);
        }
    }

    private static String requerido(String valor, String mensaje) {
        if (valor == null || valor.trim().isEmpty()) {
            throw new IllegalArgumentException(mensaje);
        }
        return valor.trim();
    }

    private static String limpiar(String valor) {
        return valor == null ? "" : valor.trim();
    }
}
