package Tarea3_continuacion_POO;

import java.util.ArrayList;
import java.util.Scanner;

public class Ejercicio1_sistema_gestion_productos {
    public static void main(String[] args) {
        ArrayList<Producto> productos = new ArrayList<>(); 
    Scanner scanner = new Scanner(System.in);
    int opcion = 0;

    do {
        System.out.println("""
            ===Bienvenido al menu principal===
            opciones: 
            1-Crear producto.
            2-Agregar cantidad de algun producto.
            3-Vender 
            4-mostrar todos los productos disponibles
            5-salir
        """);
        opcion = scanner.nextInt();
        scanner.nextLine();
        
        switch (opcion) {
                case 1:
                    System.out.println("Ingrese el nombre del producto:");
                    String nombre = scanner.nextLine();
                    System.out.println("Ingrese el precio del producto:");
                    double precio = scanner.nextDouble();
                    scanner.nextLine(); // limpia el buffer otra vez

                    Producto nuevoProducto = new Producto(nombre, precio);
                    productos.add(nuevoProducto);
                    System.out.println("Producto creado exitosamente.");
                    break;

                case 2:
                    if (productos.isEmpty()) {
                        System.out.println("No hay productos creados todavía.");
                        break;
                    }
                    for (int i = 0; i < productos.size(); i++) {
                        System.out.println(i + " - " + productos.get(i).getNombre());
                    }
                    System.out.println("Seleccione el número del producto:");
                    int indiceAgregar = scanner.nextInt();
                    System.out.println("Ingrese la cantidad a agregar:");
                    int cantidadAgregar = scanner.nextInt();

                    productos.get(indiceAgregar).agregarCantidad(cantidadAgregar);
                    System.out.println("Cantidad agregada exitosamente.");
                    break;

                case 3:
                    if (productos.isEmpty()) {
                        System.out.println("No hay productos creados todavía.");
                        break;
                    }
                    for (int i = 0; i < productos.size(); i++) {
                        System.out.println(i + " - " + productos.get(i).getNombre());
                    }
                    System.out.println("Seleccione el número del producto:");
                    int indiceVender = scanner.nextInt();
                    System.out.println("Ingrese la cantidad a vender:");
                    int cantidadVender = scanner.nextInt();

                    productos.get(indiceVender).vender(cantidadVender);
                    break;

                case 4:
                    if (productos.isEmpty()) {
                        System.out.println("No hay productos creados todavía.");
                        break;
                    }
                    for (Producto p : productos) {
                        p.mostrarInformacion();
                    }
                    break;

                case 5:
                    System.out.println("Saliendo del sistema...");
                    break;

                default:
                    System.out.println("Opción inválida.");
                    break;
        }
            
    }while (opcion != 5);

    scanner.close();
    }
}

class Producto{
    private String nombre; 
    private double precio;
    private int cantidad;
    private static int contador = 0;

     public Producto(String nombre, double precio){
        this.nombre = nombre;
        this.precio = precio;
        this.cantidad = 0;
        Producto.contador = contador ++;
     }

     //metodos
    public void agregarCantidad(int cantidad){
        this.cantidad = this.cantidad + cantidad;
     }

    public boolean vender(int cantidad){
        if  (this.cantidad < cantidad){
            System.out.println("Error, no hay tanta disponibilidad para vender...");
            return false;
        }
        this.cantidad -= cantidad;
        return true;
     }

    public void mostrarInformacion(){
        System.out.println("Nombre de producto: " + nombre + " Precio: " + precio + " cantidad actual: " + cantidad);
    }

    public String getNombre() {
        return nombre;
    }

    public static int getContador() {
        return contador;
    }
}