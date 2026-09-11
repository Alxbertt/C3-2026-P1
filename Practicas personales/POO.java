//Ejemplo de POO basico usando la creacion de clase tarea y 

class tarea{
   String Titulo;
   String mandato;
   int numerotarea;
}

public class POO{
   public static void main(String [] args) {
      tarea t1 = new tarea();
      tarea t2 = new tarea();

      t1.Titulo = "Realizar las derivadas";
      t1.mandato = "Realice la siguientes derivadas con ayuda de su cuaderno a modo de estudio";
      t1.numerotarea = 1;

      t2.Titulo = "Buscar la historia de juan pablo duarte";
      t2.mandato = "Con ayuda de su telefono busque la historia del que dice ahi arriba";
      t2.numerotarea = 2;

      System.out.println("Tu tarea 1 es lleva por titulo: " + t1.Titulo);
      System.out.println("El mandato de esta tarea es: " + t1.mandato);
      System.out.println("numero de tarea: " + t1.numerotarea);


   }

}