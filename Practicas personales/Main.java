import java.util.Scanner; // importamos la clase Scanner para poder leer datos que el usuario escriba por consola

public class Main { // declaramos la clase principal; el nombre de la clase debe coincidir con el nombre del archivo (Main.java)
    public static void main(String[] args){ // este es el método principal, el punto de entrada del programa; es "void" porque NO devuelve ningún valor, solo ejecuta instrucciones
        System.out.println("Hola mundo"); // imprime el texto "Hola mundo" y salta a una nueva línea automáticamente (eso lo hace println por sí solo, no hay ningún \n en este texto)
        nombre();  // llamamos al método nombre(), definido más abajo; en Java el orden en que escribes los métodos no importa, todos están disponibles dentro de la misma clase
        edad_colorfav_ciudad(); // llamamos al método que imprime edad, color favorito y ciudad
        probando_scanner(); // llamamos al método que prueba el uso de Scanner para leer datos del usuario
    }

    public static void nombre(){ // método sin retorno (void); no recibe parámetros y solo imprime un texto fijo
        System.out.println("Albert Emmanuel Hichez Perez"); // contenido del método: imprime el nombre completo
    }

    public static void edad_colorfav_ciudad(){ // método sin retorno que imprime varios datos juntos
        System.out.println("Mi edad es 19 años\nmi color fav es el verde\ny mi ciudad es santo domingo"); // aquí sí usamos \n dentro del texto para forzar saltos de línea, aunque sea un solo println
    }

    public static void probando_scanner(){ // método que demuestra cómo leer texto ingresado por el usuario
        Scanner scanner = new Scanner(System.in); // creamos un objeto Scanner que lee desde la entrada estándar (el teclado)
        System.out.println("Como te llamas ?"); // pedimos al usuario que escriba su nombre
        String nombre = scanner.nextLine(); // leemos la línea completa que escribió el usuario y la guardamos en una variable String
        System.out.println("Hola " + nombre); // concatenamos texto con la variable usando + e imprimimos el saludo
        scanner.close(); // cerramos el Scanner para liberar el recurso de lectura de consola
    }

}
