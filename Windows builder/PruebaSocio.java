/** Ejercicio 7 d) y e) - Crear socios y mostrar sus datos. */
public class PruebaSocio {
    public static void main(String[] args) {
        Socio socio1 = new Socio("Juan Pérez");
        socio1.mostrarDatos();

        Socio socio2 = new Socio("María Gómez");
        socio2.mostrarDatos();

        System.out.println("Próximo número disponible: " + Socio.getProximoNumero());
    }
}
