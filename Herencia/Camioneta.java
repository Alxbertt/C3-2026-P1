package Herencia;

public non-sealed class Camioneta extends Vehiculo implements IAlquiler {

    private double capacidadCarga; // en kilogramos

    // Constructor completo
    public Camioneta(String matricula, String marca, String modelo, int anio,
        String estado, double capacidadCarga) {
        super(matricula, marca, modelo, anio, estado);
        this.capacidadCarga = capacidadCarga;
    }

    // Constructor sobrecargado: sin estado (queda "disponible")
    public Camioneta(String matricula, String marca, String modelo, int anio,
        double capacidadCarga) {
        super(matricula, marca, modelo, anio);
        this.capacidadCarga = capacidadCarga;
    }

    public double getCapacidadCarga() {
        return capacidadCarga;
    }

    public void setCapacidadCarga(double capacidadCarga) {
        this.capacidadCarga = capacidadCarga;
    }

    @Override
    public void alquilar() {
        if (getEstado().equals("disponible")) {
            setEstado("alquilado");
            System.out.println("Camioneta alquilada correctamente.");
        } else {
            System.out.println("La camioneta no está disponible.");
        }
    }

    // Sobrecarga: alquilar con capacidad mínima requerida
    public void alquilar(double capacidadMinima) {
        if (!getEstado().equals("disponible")) {
            System.out.println("La camioneta no está disponible.");
        } else if (capacidadCarga < 800) {
            System.out.println("No se puede alquilar: la capacidad es menor a 800 kg.");
        } else if (capacidadCarga < capacidadMinima) {
            System.out.println("No se puede alquilar: la capacidad (" + capacidadCarga
                    + " kg) es menor a la requerida (" + capacidadMinima + " kg).");
        } else {
            setEstado("alquilado");
            System.out.println("Camioneta alquilada correctamente.");
        }
    }

    @Override
    public void devolver() {
        if (getEstado().equals("alquilado")) {
            setEstado("disponible");
            System.out.println("Camioneta devuelta correctamente.");
        } else {
            System.out.println("La camioneta no necesita devolución.");
        }
    }

    @Override
    public void mostrarInformacion() {
        super.mostrarInformacion();
        System.out.println("Tipo: Camioneta | Capacidad de carga: " + capacidadCarga + " kg");
    }
}