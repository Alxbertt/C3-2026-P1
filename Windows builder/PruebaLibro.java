/**
 * Ejercicio 5 f) - Clase de prueba con metodo main.
 * Crea 2 libros: uno original que NO se presta y una fotocopia que SI se presta.
 */
public class PruebaLibro {
    public static void main(String[] args) {
        // Se crean con valores iniciales y luego se usan los metodos de instancia
        Libro libro1 = new Libro("", false, false);
        libro1.setTitulo("Cien años de soledad");
        libro1.setOriginal(true);       // es original
        libro1.setPrestable(false);     // no se presta

        Libro libro2 = new Libro("", false, false);
        libro2.setTitulo("El Quijote");
        libro2.setOriginal(false);      // es fotocopia
        libro2.setPrestable(true);      // se presta

        System.out.println("=== Libros creados ===");
        mostrar(libro1);
        mostrar(libro2);
    }

    private static void mostrar(Libro l) {
        System.out.println("Título    : " + l.getTitulo());
        System.out.println("Original  : " + l.getOriginal() + " (esOriginal: " + l.esOriginal() + ")");
        System.out.println("Prestable : " + l.getPrestable() + " (sePresta: " + l.sePresta() + ")");
        System.out.println(l);
        System.out.println();
    }
}
