package Tarea2_POO;

import java.util.HashMap;
import java.util.Map;

public class Ejercicio5_moneda {
    @SuppressWarnings("unused")
    public static void main(String[] args) {
        // 8 monedas con distintos valores y tipos, como pide el enunciado
        Moneda m1 = new Moneda(100.0, "USD");
        Moneda m2 = new Moneda(50.0, "USD");
        Moneda m3 = new Moneda(200.0, "EUR");
        Moneda m4 = new Moneda(5000.0, "DOP");
        Moneda m5 = new Moneda(75.0, "GBP");
        Moneda m6 = new Moneda(1000.0, "MXN");
        Moneda m7 = new Moneda(300000.0, "COP");
        Moneda m8 = new Moneda(15000.0, "JPY");
 
        System.out.println("\n--- Conversión ---");
        Moneda convertida = m1.convertir("DOP");
        System.out.println("Resultado: " + convertida.getValor() + " " + convertida.getTipoMoneda());
 
        System.out.println("\n--- Suma (misma divisa: m1 + m2) ---");
        Moneda suma = m1.agregar(m2);
        if (suma != null) {
            System.out.println("Resultado: " + suma.getValor() + " " + suma.getTipoMoneda());
        }
 
        System.out.println("\n--- Resta (misma divisa: m1 - m2) ---");
        Moneda resta = m1.restar(m2);
        if (resta != null) {
            System.out.println("Resultado: " + resta.getValor() + " " + resta.getTipoMoneda());
        }
 
        System.out.println("\n--- Intento de suma con divisas distintas (m1 USD + m3 EUR) ---");
        Moneda sumaInvalida = m1.agregar(m3);
        if (sumaInvalida == null) {
            System.out.println("(la operación fue rechazada correctamente, como se esperaba)");
        }
 
        System.out.println("\n--- Verificando que m1 no cambió (agregar/restar no deben modificar el original) ---");
        System.out.println("m1 sigue siendo: " + m1.getValor() + " " + m1.getTipoMoneda());
    }
}

class Moneda {
    private double valor;
    private String tipoMoneda;
 
    // tabla de tasas de cambio, todas relativas a USD. Es "static" porque
    // es una sola tabla compartida por todos los objetos Moneda, no una copia por cada uno.
    private static final Map<String, Double> TASAS_A_USD = new HashMap<>();
    static {
        TASAS_A_USD.put("USD", 1.0);
        TASAS_A_USD.put("EUR", 0.92);
        TASAS_A_USD.put("DOP", 58.5);
        TASAS_A_USD.put("GBP", 0.79);
        TASAS_A_USD.put("MXN", 17.0);
        TASAS_A_USD.put("COP", 3900.0);
        TASAS_A_USD.put("JPY", 149.0);
        TASAS_A_USD.put("CAD", 1.35);
    }
 
    public Moneda(double valor, String tipoMoneda) {
        this.valor = valor;
        this.tipoMoneda = tipoMoneda;
    }
 
    // convierte el valor de esta moneda a otro tipo, pasando primero por USD como referencia
    public Moneda convertir(String tipoMonedaDestino) {
        double valorEnUsd = this.valor / TASAS_A_USD.get(this.tipoMoneda);
        double valorConvertido = valorEnUsd * TASAS_A_USD.get(tipoMonedaDestino);
        System.out.println(this.valor + " " + this.tipoMoneda + " equivalen a " + valorConvertido + " " + tipoMonedaDestino);
        return new Moneda(valorConvertido, tipoMonedaDestino);
    }
 
    // suma dos montos de la MISMA divisa; devuelve una moneda nueva, no modifica esta
    public Moneda agregar(Moneda otraMoneda) {
        if (this.tipoMoneda.equals(otraMoneda.tipoMoneda)) {
            return new Moneda(this.valor + otraMoneda.valor, this.tipoMoneda);
        } else {
            System.out.println("No se pueden sumar montos de divisas distintas (" + this.tipoMoneda + " y " + otraMoneda.tipoMoneda + ")");
            return null;
        }
    }
 
    // resta dos montos de la MISMA divisa; devuelve una moneda nueva, no modifica esta
    public Moneda restar(Moneda otraMoneda) {
        if (this.tipoMoneda.equals(otraMoneda.tipoMoneda)) {
            return new Moneda(this.valor - otraMoneda.valor, this.tipoMoneda);
        } else {
            System.out.println("No se pueden restar montos de divisas distintas (" + this.tipoMoneda + " y " + otraMoneda.tipoMoneda + ")");
            return null;
        }
    }
 
    public double getValor() {
        return this.valor;
    }
 
    public String getTipoMoneda() {
        return this.tipoMoneda;
    }
}