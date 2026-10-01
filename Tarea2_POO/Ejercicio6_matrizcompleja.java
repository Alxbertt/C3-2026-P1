package Tarea2_POO;

public class Ejercicio6_matrizcompleja {
    public static void main(String[] args) {
        MatrizCompleja m1 = new MatrizCompleja(2, 2);
        m1.setValorReal(0, 0, 1);       m1.setValorImaginario(0, 0, 2);
        m1.setValorReal(0, 1, 3);       m1.setValorImaginario(0, 1, -1);
        m1.setValorReal(1, 0, 0);       m1.setValorImaginario(1, 0, 5);
        m1.setValorReal(1, 1, 2);       m1.setValorImaginario(1, 1, 2);
 
        MatrizCompleja m2 = new MatrizCompleja(2, 2);
        m2.setValorReal(0, 0, 4);       m2.setValorImaginario(0, 0, 0);
        m2.setValorReal(0, 1, 1);       m2.setValorImaginario(0, 1, 1);
        m2.setValorReal(1, 0, -2);      m2.setValorImaginario(1, 0, 3);
        m2.setValorReal(1, 1, 1);       m2.setValorImaginario(1, 1, 0);
 
        System.out.println("\n--- Matriz 1 ---");
        m1.imprimirMatriz();
 
        System.out.println("\n--- Matriz 2 ---");
        m2.imprimirMatriz();
 
        System.out.println("\n--- Suma (m1 + m2) ---");
        MatrizCompleja suma = m1.sumar(m2);
        suma.imprimirMatriz();
 
        System.out.println("\n--- Multiplicación (m1 x m2) ---");
        MatrizCompleja producto = m1.multiplicar(m2);
        producto.imprimirMatriz();
 
        System.out.println("\n--- m1 sigue intacta (las operaciones no la modifican) ---");
        m1.imprimirMatriz();
 
        System.out.println("\n--- Validación de integridad de m1 ---");
        System.out.println("¿m1 está bien formada? " + m1.validarIntegridadEstructura());
    }
}


class MatrizCompleja {
    private int filas;
    private int columnas;
    private double[][] real;
    private double[][] imaginario;
 
    public MatrizCompleja(int filas, int columnas) {
        this.filas = filas;
        this.columnas = columnas;
        this.real = new double[filas][columnas];
        this.imaginario = new double[filas][columnas];
    }
 
    // verifica que los arreglos internos tengan el tamaño esperado (filas x columnas)
    public boolean validarIntegridadEstructura() {
        if (this.real.length != this.filas || this.imaginario.length != this.filas) {
            return false;
        }
        for (int i = 0; i < this.filas; i++) {
            if (this.real[i].length != this.columnas || this.imaginario[i].length != this.columnas) {
                return false;
            }
        }
        return true;
    }
 
    // suma elemento por elemento; devuelve una matriz NUEVA, no toca esta ni la otra
    public MatrizCompleja sumar(MatrizCompleja otraMatriz) {
        if (this.filas != otraMatriz.filas || this.columnas != otraMatriz.columnas) {
            System.out.println("No se pueden sumar matrices de tamaños distintos.");
            return null;
        }
        MatrizCompleja resultado = new MatrizCompleja(this.filas, this.columnas);
        for (int i = 0; i < this.filas; i++) {
            for (int j = 0; j < this.columnas; j++) {
                resultado.real[i][j] = this.real[i][j] + otraMatriz.real[i][j];
                resultado.imaginario[i][j] = this.imaginario[i][j] + otraMatriz.imaginario[i][j];
            }
        }
        return resultado;
    }
 
    // multiplicación estándar de matrices, con aritmética de números complejos
    // (a+bi)(c+di) = (ac-bd) + (ad+bc)i
    public MatrizCompleja multiplicar(MatrizCompleja otraMatriz) {
        if (this.columnas != otraMatriz.filas) {
            System.out.println("No se pueden multiplicar: columnas de la primera deben coincidir con filas de la segunda.");
            return null;
        }
        MatrizCompleja resultado = new MatrizCompleja(this.filas, otraMatriz.columnas);
        for (int i = 0; i < this.filas; i++) {
            for (int j = 0; j < otraMatriz.columnas; j++) {
                double sumaReal = 0;
                double sumaImag = 0;
                for (int k = 0; k < this.columnas; k++) {
                    double ar = this.real[i][k];
                    double ai = this.imaginario[i][k];
                    double br = otraMatriz.real[k][j];
                    double bi = otraMatriz.imaginario[k][j];
                    sumaReal += (ar * br) - (ai * bi);
                    sumaImag += (ar * bi) + (ai * br);
                }
                resultado.real[i][j] = sumaReal;
                resultado.imaginario[i][j] = sumaImag;
            }
        }
        return resultado;
    }
 
    public void imprimirMatriz() {
        for (int i = 0; i < this.filas; i++) {
            StringBuilder fila = new StringBuilder();
            for (int j = 0; j < this.columnas; j++) {
                double parteReal = this.real[i][j];
                double parteImag = this.imaginario[i][j];
                String signo = parteImag >= 0 ? "+" : "-";
                fila.append(parteReal).append(" ").append(signo).append(" ").append(Math.abs(parteImag)).append("i");
                if (j < this.columnas - 1) {
                    fila.append("   ");
                }
            }
            System.out.println(fila.toString());
        }
    }
 
    // getters y setters
    public double getValorReal(int fila, int columna) {
        return this.real[fila][columna];
    }
 
    public void setValorReal(int fila, int columna, double valor) {
        this.real[fila][columna] = valor;
    }
 
    public double getValorImaginario(int fila, int columna) {
        return this.imaginario[fila][columna];
    }
 
    public void setValorImaginario(int fila, int columna, double valor) {
        this.imaginario[fila][columna] = valor;
    }
 
    public int getFilas() {
        return this.filas;
    }
 
    public int getColumnas() {
        return this.columnas;
    }
}