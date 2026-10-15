package Herencia;


public final class Auto extends Vehiculo implements IAlquiler {

    private int numeroPuertas;

    // Constructor completo
    public Auto(String matricula, String marca, String modelo, int anio,
                String estado, int numeroPuertas) {
        super(matricula, marca, modelo, anio, estado);
        this.numeroPuertas = numeroPuertas;
    }

    // Constructor sobrecargado: sin estado (queda "disponible")
    public Auto(String matricula, String marca, String modelo, int anio,
                int numeroPuertas) {
        super(matricula, marca, modelo, anio);
        this.numeroPuertas = numeroPuertas;
    }

    public int getNumeroPuertas() {
        return numeroPuertas;
    }

    public void setNumeroPuertas(int numeroPuertas) {
        this.numeroPuertas = numeroPuertas;
    }

    @Override
    public void alquilar() {
        if (getEstado().equals("disponible")) {
            setEstado("alquilado");
            System.out.println("Auto alquilado correctamente.");
        } else {
            System.out.println("El auto no está disponible.");
        }
    }

    @Override
    public void devolver() {
        if (getEstado().equals("alquilado")) {
            setEstado("disponible");
            System.out.println("Auto devuelto correctamente.");
        } else {
            System.out.println("El auto no necesita devolución.");
        }
    }

    @Override
    public void mostrarInformacion() {
        super.mostrarInformacion();
        System.out.println("Tipo: Auto | Puertas: " + numeroPuertas);
    }
}