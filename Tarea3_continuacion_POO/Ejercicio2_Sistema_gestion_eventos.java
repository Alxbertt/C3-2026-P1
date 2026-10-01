package Tarea3_continuacion_POO;
import java.util.ArrayList;
import java.util.Scanner;

public class Ejercicio2_Sistema_gestion_eventos {
    public static void main(String[] args) {
        ArrayList<Evento> eventos = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);
 
        // Punto 9: crear varios eventos de ejemplo usando ambos constructores
        eventos.add(new Evento("Cumpleaños de Ana", "15/3/2026"));
        eventos.add(new Evento("Boda de Carlos", "20/6/2026", "Salon Real"));
        eventos.add(new Evento("Conferencia Java", "2/3/2023", "Centro de Convenciones"));
        eventos.add(new Evento("Reunion de trabajo", "10/4/2026", "Oficina Central"));
 
        eventos.get(0).agregarInvitado("Pedro");
        eventos.get(0).agregarInvitado("Maria");
 
        ArrayList<String> invitadosBoda = new ArrayList<>();
        invitadosBoda.add("Juan");
        invitadosBoda.add("Sofia");
        invitadosBoda.add("Luis");
        eventos.get(1).agregarInvitado(invitadosBoda);
 
        int opcion = 0;
        do {
            System.out.println("""
                    ===Sistema de gestion de eventos===
                    opciones:
                    1-Crear evento
                    2-Agregar invitado a un evento
                    3-Mostrar todos los eventos
                    4-Mostrar eventos por fecha
                    5-Mostrar eventos por lugar
                    6-Buscar eventos de un lugar especifico
                    7-Mostrar cantidad de eventos creados
                    8-Salir
                    """);
            opcion = scanner.nextInt();
            scanner.nextLine(); // limpia el Enter pendiente
 
            switch (opcion) {
                case 1:
                    System.out.println("Ingrese el nombre del evento:");
                    String nombre = scanner.nextLine();
                    System.out.println("Ingrese la fecha (formato d/m/aaaa):");
                    String fecha = scanner.nextLine();
                    System.out.println("¿Desea agregar un lugar? (s/n)");
                    String resp = scanner.nextLine();
 
                    if (resp.equalsIgnoreCase("s")) {
                        System.out.println("Ingrese el lugar:");
                        String lugar = scanner.nextLine();
                        eventos.add(new Evento(nombre, fecha, lugar));
                    } else {
                        eventos.add(new Evento(nombre, fecha));
                    }
                    System.out.println("Evento creado exitosamente.");
                    break;
 
                case 2:
                    if (eventos.isEmpty()) {
                        System.out.println("No hay eventos creados todavia.");
                        break;
                    }
                    for (int i = 0; i < eventos.size(); i++) {
                        System.out.println(i + " - " + eventos.get(i).getNombre());
                    }
                    System.out.println("Seleccione el numero del evento:");
                    int indice = scanner.nextInt();
                    scanner.nextLine();
                    System.out.println("Ingrese el nombre del invitado:");
                    String invitado = scanner.nextLine();
                    eventos.get(indice).agregarInvitado(invitado);
                    System.out.println("Invitado agregado.");
                    break;
 
                case 3:
                    if (eventos.isEmpty()) {
                        System.out.println("No hay eventos creados todavia.");
                        break;
                    }
                    for (Evento e : eventos) {
                        e.mostrarInformacion();
                    }
                    break;
 
                case 4:
                    System.out.println("Ingrese la fecha a buscar (formato d/m/aaaa):");
                    String fechaBuscar = scanner.nextLine();
                    for (Evento e : eventos) {
                        if (e.getFecha().equals(fechaBuscar)) {
                            e.mostrarInformacion();
                        }
                    }
                    break;
 
                case 5:
                    for (Evento e : eventos) {
                        System.out.println(e.getNombre() + " - " + e.getLugar());
                    }
                    break;
 
                case 6:
                    System.out.println("Ingrese el lugar a buscar:");
                    String lugarBuscar = scanner.nextLine();
                    for (Evento e : eventos) {
                        if (e.getLugar() != null && e.getLugar().equalsIgnoreCase(lugarBuscar)) {
                            e.mostrarInformacion();
                        }
                    }
                    break;
 
                case 7:
                    System.out.println("Cantidad de eventos creados: " + Evento.obtenerCantidadEventos());
                    break;
 
                case 8:
                    System.out.println("Saliendo del sistema...");
                    break;
 
                default:
                    System.out.println("Opcion invalida.");
                    break;
            }
 
        } while (opcion != 8);
 
        scanner.close();
    
    }
}

class Evento{
    private String nombre;
    private String fecha;
    private String lugar;
    private ArrayList<String> invitados;
    private static int cantidadEventos = 0;
 
    // constructor normal
    public Evento(String nombre, String fecha) {
        this.nombre = nombre;
        this.fecha = fecha;
        this.invitados = new ArrayList<>();
        cantidadEventos++;
    }
 
    // constructor sobrecargado
    public Evento(String nombre, String fecha, String lugar) {
        this.nombre = nombre;
        this.fecha = fecha;
        this.lugar = lugar;
        this.invitados = new ArrayList<>();
        cantidadEventos++;
    }
 
    // metodo agregarInvitado (lista completa)
    public void agregarInvitado(ArrayList<String> listaInvitados) {
        this.invitados.addAll(listaInvitados);
    }
 
    // metodo agregarInvitado sobrecargado (un solo invitado)
    public void agregarInvitado(String invitado) {
        this.invitados.add(invitado);
    }
 
    public void mostrarInformacion() {
        System.out.println("Evento: " + nombre);
        System.out.println("Fecha: " + fecha);
        System.out.println("Lugar: " + (lugar != null ? lugar : "No especificado"));
        System.out.println("Invitados: " + invitados);
        System.out.println("-------------------------------");
    }
 
    public static int obtenerCantidadEventos() {
        return cantidadEventos;
    }
 
    public String getNombre() {
        return nombre;
    }
 
    public String getFecha() {
        return fecha;
    }
 
    public String getLugar() {
        return lugar;
        
    }
}