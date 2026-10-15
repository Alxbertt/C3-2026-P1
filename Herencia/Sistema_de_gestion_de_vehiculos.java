package Herencia;

import java.util.Scanner;

public class Sistema_de_gestion_de_vehiculos {
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        AgenciaAlquiler agencia = new AgenciaAlquiler();
        int opcion;

        do {
            System.out.println("\n===== AGENCIA DE ALQUILER =====");
            System.out.println("1. Agregar vehículo");
            System.out.println("2. Eliminar vehículo");
            System.out.println("3. Alquilar vehículo");
            System.out.println("4. Devolver vehículo");
            System.out.println("5. Mostrar todos los vehículos");
            System.out.println("6. Salir");
            opcion = leerEntero("Elige una opción: ");

            switch (opcion) {
                case 1:
                    agregarVehiculo(agencia);
                    break;
                case 2:
                    String matEliminar = leerTexto("Matrícula a eliminar: ");
                    if (agencia.eliminarVehiculo(matEliminar)) {
                        System.out.println("Vehículo eliminado.");
                    } else {
                        System.out.println("No se encontró ese vehículo.");
                    }
                    break;
                case 3:
                    alquilarVehiculo(agencia);
                    break;
                case 4:
                    devolverVehiculo(agencia);
                    break;
                case 5:
                    agencia.mostrarVehiculos();
                    break;
                case 6:
                    System.out.println("Saliendo del sistema...");
                    break;
                default:
                    System.out.println("Opción no válida.");
            }
        } while (opcion != 6);
    }

    // ---------- Opciones del menú ----------

    static void agregarVehiculo(AgenciaAlquiler agencia) {
        System.out.println("1. Auto");
        System.out.println("2. Camioneta");
        int tipo = leerEntero("Tipo de vehículo: ");

        if (tipo != 1 && tipo != 2) {
            System.out.println("Tipo no válido.");
            return;
        }

        String matricula = leerTexto("Matrícula: ");
        String marca = leerTexto("Marca: ");
        String modelo = leerTexto("Modelo: ");
        int anio = leerEntero("Año: ");

        if (tipo == 1) {
            int puertas = leerEntero("Número de puertas: ");
            agencia.agregarVehiculo(new Auto(matricula, marca, modelo, anio, puertas));
        } else {
            double capacidad = leerDouble("Capacidad de carga (kg): ");
            agencia.agregarVehiculo(new Camioneta(matricula, marca, modelo, anio, capacidad));
        }
    }

    static void alquilarVehiculo(AgenciaAlquiler agencia) {
        String matricula = leerTexto("Matrícula a alquilar: ");
        Vehiculo v = agencia.buscarVehiculo(matricula);

        if (v == null) {
            System.out.println("No se encontró ese vehículo.");
        } else if (v instanceof Camioneta c) {
            String resp = leerTexto("¿Indicar capacidad mínima requerida? (s/n): ");
            if (resp.equalsIgnoreCase("s")) {
                double minima = leerDouble("Capacidad mínima (kg): ");
                c.alquilar(minima);   // versión sobrecargada
            } else {
                c.alquilar();         // versión normal
            }
        } else if (v instanceof IAlquiler a) {
            a.alquilar();
        }
    }

    static void devolverVehiculo(AgenciaAlquiler agencia) {
        String matricula = leerTexto("Matrícula a devolver: ");
        Vehiculo v = agencia.buscarVehiculo(matricula);

        if (v == null) {
            System.out.println("No se encontró ese vehículo.");
        } else if (v instanceof IAlquiler a) {
            a.devolver();
        }
    }

    // ---------- Métodos auxiliares de lectura ----------

    static String leerTexto(String mensaje) {
        System.out.print(mensaje);
        return sc.nextLine().trim();
    }

    static int leerEntero(String mensaje) {
        while (true) {
            try {
                System.out.print(mensaje);
                return Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Ingresa un número entero válido.");
            }
        }
    }

    static double leerDouble(String mensaje) {
        while (true) {
            try {
                System.out.print(mensaje);
                return Double.parseDouble(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Ingresa un número válido.");
            }
        }
    }
}

