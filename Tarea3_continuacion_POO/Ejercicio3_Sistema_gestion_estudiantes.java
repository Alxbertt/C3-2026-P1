package Tarea3_continuacion_POO;

import java.util.ArrayList;

public class Ejercicio3_Sistema_gestion_estudiantes {
    public static void main(String[] args) {
        ArrayList<Estudiante> estudiantes = new ArrayList<>();
        estudiantes.add(new Estudiante("Albert Hichez", "2026-0047", "Desarrollo de Software"));
        estudiantes.add(new Estudiante("Franchesca", "2025-1111", "Seguridad informatica"));

        //agregado de materias
        estudiantes.get(0).agregarMateria("Programacion 1");
        estudiantes.get(1).agregarMateria("Hacker etico 3");

        for (Estudiante e : estudiantes) {
            e.mostrarInformacion();
        }
 
        System.out.println("Cantidad de estudiantes creados: " + Estudiante.obtenerCantidadEstudiantes());
    }
}

class Estudiante{
    private String nombre;    
    private String matricula;
    private String carrera;
    private ArrayList<String> materias;
    private static int cantidadEstudiantes = 0;

    //constructor normal
    public Estudiante(String nombre, String matricula){
        this.nombre = nombre;
        this.matricula = matricula;
        this.materias = new ArrayList<String>();
        cantidadEstudiantes++;
    }

    //constructor sobrecargado 
    public Estudiante(String nombre, String matricula, String carrera){
        this.nombre = nombre;
        this.matricula = matricula;
        this.carrera = carrera;
        this.materias = new ArrayList<String>();
        cantidadEstudiantes++;
    }

    public void agregarMateria(String materia){
        this.materias.add(materia);
    }

    public void agregarMateria(ArrayList<String>materias){
        for (String materia : materias){
            this.materias.add(materia);
        }
    }

    public void mostrarInformacion(){
        System.out.println("===Datos estudiante===" );
        System.out.println("Nombre de estudiante: " + this.nombre);
        System.out.println("Matricula: " + this.matricula);
        System.out.println("Carrera: " + this.carrera);
        System.out.println("Materias: " + this.materias);
        System.out.println("\n");
    }

    public static int obtenerCantidadEstudiantes(){
        return cantidadEstudiantes;
    }

}