/** Ejercicio 6 - Codigo de prueba de la clase Triangulo. */
public class PruebaTriangulo {

    public static void main(String[] args) {
        probar(new Triangulo(3, 4, 5));   // escaleno y rectangulo
        probar(new Triangulo(5, 5, 8));   // isosceles
        probar(new Triangulo(6, 6, 6));   // equilatero
        probar(new Triangulo(7, 8, 10));  // escaleno
        probar(new Triangulo(5, 5, Math.sqrt(50))); // isosceles rectangulo

        // Caso invalido
        try {
            new Triangulo(1, 2, 10);
        } catch (IllegalArgumentException e) {
            System.out.println("Error esperado: " + e.getMessage());
        }
    }

    private static void probar(Triangulo t) {
        System.out.println(t);
        System.out.println("  esEscaleno()       : " + t.esEscaleno());
        System.out.println("  esIsosceles()      : " + t.esIsosceles());
        System.out.println("  esEquilatero()     : " + t.esEquilatero());
        System.out.println("  tieneAnguloRecto() : " + t.tieneAnguloRecto());
        System.out.println();
    }
}
