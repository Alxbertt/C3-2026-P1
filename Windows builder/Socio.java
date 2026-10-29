/**
 * Ejercicio 7 - Clase Socio.
 * a) Variables de instancia: nombre y numero. Variable de clase: proximoNumero.
 * c) proximoNumero se inicializa en 1.
 */
public class Socio {

    // Variable de clase (compartida por todos los socios)
    private static int proximoNumero = 1;

    // Variables de instancia
    private String nombre;
    private int numero;

    /** Al crear un socio se le asigna el numero actual y se incrementa el proximo. */
    public Socio(String nombre) {
        this.nombre = nombre;
        this.numero = proximoNumero;
        proximoNumero++;
    }

    // b) Metodos de acceso
    public String getNombre()  { return nombre; }
    public int getNumero()     { return numero; }
    public static int getProximoNumero() { return proximoNumero; }

    // b) Metodos de modificacion
    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setNumero(int numero)    { this.numero = numero; }

    public void mostrarDatos() {
        System.out.println("Socio Nº " + numero + " - Nombre: " + nombre);
    }
}
