package Tarea2_POO;

public class Ejercicio2_TarjetaCredito {
    public static void main(String[] args) {
        TarjetaCredito tc1 = new TarjetaCredito("0001", "Albert Hichez", "10/3/2040", "202", "Visa", 20000.0);
        TarjetaCredito tc2 = new TarjetaCredito("0002", "Patricia Garcia", "2/5/2028", "145", "Master Card", 10000.30);
        TarjetaCredito tc3 = new TarjetaCredito("0003", "Eren Nuñez", "9/2/2031", "403", "Visa", 340000.0);
 
        System.out.println("\n" + "=".repeat(40));
        System.out.println("Tarjeta 0001 - Albert Hichez");
        System.out.println("=".repeat(40));
        tc1.realizarCompra(5000);
        tc1.pagarTarjeta(2000);
        System.out.println("Límite actual: " + tc1.verificarLimite());
 
        System.out.println("\n" + "=".repeat(40));
        System.out.println("Tarjeta 0002 - Patricia Garcia");
        System.out.println("=".repeat(40));
        tc2.realizarCompra(15000);
 
        System.out.println("\n" + "=".repeat(40));
        System.out.println("Tarjeta 0003 - Eren Nuñez");
        System.out.println("=".repeat(40));
        tc3.realizarCompra(100000);
        tc3.pagarTarjeta(50000);
        System.out.println("Límite actual: " + tc3.verificarLimite());
    }
}

@SuppressWarnings("unused") // para que deje VSC me deje de pedir que use las variables de la clase TarjetaCredito
class TarjetaCredito {
    private String nombreTitular;
    private String numeroTarjeta;
    private String fechaVencimiento;
    private String codigoCVV;
    private String franquicia;
    private double limiteCredito;
    private double creditoDisponible;
 
    public TarjetaCredito(String numeroTarjeta, String nombreTitular, String fechaVencimiento, String codigoCVV, String franquicia, double limiteCredito) {
        System.out.println("Bienvenido al sistema " + nombreTitular);
        System.out.println("Su número de tarjeta es " + numeroTarjeta + " y su límite de crédito es de " + limiteCredito);
        this.numeroTarjeta = numeroTarjeta;
        this.nombreTitular = nombreTitular;
        this.fechaVencimiento = fechaVencimiento;
        this.codigoCVV = codigoCVV;
        this.franquicia = franquicia;
        this.limiteCredito = limiteCredito;
        this.creditoDisponible = limiteCredito;
    }
 
    // aquí se maneja la compra
    public void realizarCompra(double monto) {
        if (monto <= this.creditoDisponible) {
            this.creditoDisponible = this.creditoDisponible - monto;
            System.out.println("Compra confirmada, su crédito disponible ahora es de: " + this.creditoDisponible);
        } else {
            System.out.println("La compra no pudo ser realizada, favor revisar su crédito.");
        }
    }
 
    // aqui  aumenta el crédito disponible de la tarjeta
    public void pagarTarjeta(double monto) {
        this.creditoDisponible = this.creditoDisponible + monto;
        if (this.creditoDisponible > this.limiteCredito) {
            this.creditoDisponible = this.limiteCredito;
        }
        System.out.println("Pago de tarjeta exitoso!!! Su crédito disponible ahora es de: " + this.creditoDisponible);
    }
 
    public double verificarLimite() {
        return this.creditoDisponible;
    }
}