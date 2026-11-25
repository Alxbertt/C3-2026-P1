/**
 * Ejercicio 6 - Clase Triangulo.
 * Se define por la longitud de sus tres lados.
 */
public class Triangulo {

    private static final double EPSILON = 1e-9;

    private double lado1, lado2, lado3;

    public Triangulo(double lado1, double lado2, double lado3) {
        if (lado1 <= 0 || lado2 <= 0 || lado3 <= 0) {
            throw new IllegalArgumentException("Los lados deben ser mayores que cero.");
        }
        // Desigualdad triangular: cada lado debe ser menor que la suma de los otros dos
        if (lado1 + lado2 <= lado3 || lado1 + lado3 <= lado2 || lado2 + lado3 <= lado1) {
            throw new IllegalArgumentException("Los lados no forman un triángulo válido.");
        }
        this.lado1 = lado1;
        this.lado2 = lado2;
        this.lado3 = lado3;
    }

    private boolean iguales(double a, double b) {
        return Math.abs(a - b) < EPSILON;
    }

    /** Escaleno: los tres lados son distintos. */
    public boolean esEscaleno() {
        return !iguales(lado1, lado2) && !iguales(lado1, lado3) && !iguales(lado2, lado3);
    }

    /** Isosceles: exactamente dos lados iguales. */
    public boolean esIsosceles() {
        int pares = 0;
        if (iguales(lado1, lado2)) pares++;
        if (iguales(lado1, lado3)) pares++;
        if (iguales(lado2, lado3)) pares++;
        return pares == 1;
    }

    /** Equilatero: los tres lados son iguales. */
    public boolean esEquilatero() {
        return iguales(lado1, lado2) && iguales(lado2, lado3);
    }

    /** Angulo recto: se cumple el teorema de Pitagoras con el lado mayor como hipotenusa. */
    public boolean tieneAnguloRecto() {
        double max = Math.max(lado1, Math.max(lado2, lado3));
        double suma = lado1 * lado1 + lado2 * lado2 + lado3 * lado3 - max * max;
        return Math.abs(suma - max * max) < EPSILON;
    }

    @Override
    public String toString() {
        return "Triángulo (" + lado1 + ", " + lado2 + ", " + lado3 + ")";
    }
}
