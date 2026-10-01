package Tarea2_POO;

public class Ejercicio4_Pelicula {
    public static void main(String[] args) {
        Pelicula titanic = new Pelicula("Titanic", "james cameron", true);
    
        System.out.println(titanic);

        titanic.alquilar();
        System.out.println(titanic);
 
        titanic.devolver();
        System.out.println(titanic);
    }

}

class Pelicula {
    private String titulo;
    private String director;
    private boolean disponible;

    public Pelicula(String titulo, String director, boolean disponible){
        this.titulo = titulo;
        this.director = director;
        this.disponible = disponible;
    }

    public void alquilar(){
        if (disponible){
            this.disponible = false;
            System.out.println("Pelicula alquilada con exito");
        }else{
            System.out.println("La pelicula ya ha sido alquilada");
        }
    }

    public void devolver(){
        if (!disponible){
            this.disponible = true;
            System.out.println("Devolucion exitosa!");
        }else{
            System.out.println("No se puede devolver algo que no estaba alquilado");
        }
    }
    
    @Override
    public String toString(){
        if (disponible){
            return "Titulo: " + titulo + ", Director: " + director + ", Estado: " + "Disponible";
        }else{
            return "Titulo: " + titulo + ", Director: " + director + ", Estado: " + "Alquilada";
        }
    }


    public String getTitulo() {
        return this.titulo;
    }
 
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
 
    public String getDirector() {
        return this.director;
    }
 
    public void setDirector(String director) {
        this.director = director;
    }
 
    public boolean isDisponible() {
        return this.disponible;
    }
 
    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }
}
