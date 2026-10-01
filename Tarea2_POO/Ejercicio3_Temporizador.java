package Tarea2_POO;

public class Ejercicio3_Temporizador {
    public static void main(String[] args) throws InterruptedException {
        Temporizador t1 = new Temporizador(0);
        Temporizador t2 = new Temporizador(10);
 
        System.out.println("\n--- Temporizador 1 ---");
        t1.iniciar();
        Thread.sleep(3000); 
        t1.detener();
        System.out.println("Tiempo actual: " + t1.getTiempoActual() + " segundos");
 
        t1.reiniciar();
        System.out.println("Tiempo actual tras reiniciar: " + t1.getTiempoActual() + " segundos");
 
        System.out.println("\n--- Temporizador 2 ---");
        t2.iniciar();
        Thread.sleep(2000);
        t2.detener();
        System.out.println("Tiempo actual: " + t2.getTiempoActual() + " segundos");
    }
}
 
class Temporizador {
    private long tiempoAcumulado;
    private long marcaInicio;
    private boolean activo;
 
    public Temporizador(long tiempoInicial) {
        this.tiempoAcumulado = tiempoInicial;
        this.activo = false;
    }
 
    public void iniciar() {
        if (!this.activo) {
            this.marcaInicio = System.currentTimeMillis();
            this.activo = true;
            System.out.println("Temporizador iniciado.");
        } else {
            System.out.println("El temporizador ya está en marcha.");
        }
    }
 
    public void detener() {
        if (this.activo) {
            long transcurrido = (System.currentTimeMillis() - this.marcaInicio) / 1000;
            this.tiempoAcumulado += transcurrido;
            this.activo = false;
            System.out.println("Temporizador detenido. Tiempo acumulado: " + this.tiempoAcumulado + " segundos.");
        } else {
            System.out.println("El temporizador no está en marcha, no se puede detener.");
        }
    }
 
    public void reiniciar() {
        this.tiempoAcumulado = 0;
        this.activo = false;
        System.out.println("Temporizador reiniciado a cero.");
    }
 
    // getters y setters para el tiempo actual
    public long getTiempoActual() {
        return this.tiempoAcumulado;
    }
 
    public void setTiempoActual(long tiempoActual) {
        this.tiempoAcumulado = tiempoActual;
    }
}