package Tarea1_basicos;
// import java.time.LocalDateTime;
// import java.util.Scanner;

public class Tarea1 {

    //1.Declarar variable de los diferentes tipos, asignarles valor e imprimir el valor. 
    // public static void main(String[] args) {
    //     //numeros enteros
    //     byte b1 = 125;
    //     short s1 = -32768;
    //     int i1 = 13;
    //     long l1 = 10000000;

    //     //numeros decimales 
    //     float f1 = 20.3f;
    //     double d1 = 223.31;

    //     //otros 
    //     char c1 = 'A';
    //     boolean bool = true;

    //     //impresiones
    //     System.out.println(b1);
    //     System.out.println(s1);
    //     System.out.println(i1);
    //     System.out.println(l1);
    //     System.out.println(f1);
    //     System.out.println(d1);
    //     System.out.println(c1);
    //     System.out.println(bool);

    // }

//======================================================================================= 

//2.	Buscar cómo se declara una constante en java e imprimir el valor. Probar de cambiar su valor luego y ver que es lo que pasa.

//     public static void main(String[] args) {
//         final double PI = 3.1416;
        
//         System.out.println(PI);
//         PI = 3.5;
//         System.out.println(PI);
//     }
//=======================================================================================

//3.	Declara un entero, incrementarlo, decrementarlo, hacer operaciones con el. 
    // public static void main(String[] args) {
    //     int n1 = 3;
    //     System.out.println("Numero: " + n1);
    //     n1++;
    //     System.out.println("Incrementado: " + n1);
    //     n1--;   
    //     System.out.println("Decrementado: " + n1);
    //     n1 += 5 ;   
    //     System.out.println("Suma + 5: " + n1);
    //     n1 -= 2;   
    //     System.out.println("Resta - 2: " + n1);
    //     n1 *= 10;   
    //     System.out.println("Multiplicado por 10: " + n1);
    //     n1 /= 2;   
    //     System.out.println("Dividido entre 2: " + n1);
    //     n1 %= 2;   
    //     System.out.println("Residuo de 2: " + n1);
    // }
//=======================================================================================
// 4.	Declarar un float con valor=10152466.25. Declara un byte que es igual a 5 + el float. 

    // public static void main(String[] args) {
    //     float f1 = 10152466.25f;
    //     System.out.println("float = " + f1);

    //     byte b1 = (byte)(5 + f1);
    //     System.out.println("byte "+b1);

    // }
//=======================================================================================
//5.	Adjuntar comentario de una y de varias líneas un su código.  Imprimir la fecha y hora del sistema. 

    //Este es un comentario para la clase de p1 con freidy 
    /*Este es el ejercicio 5 comentando
    comentando en java con varias lineas  */

    // public static void main(String[] args) {
    //     LocalDateTime now = LocalDateTime.now();        
    //     System.out.println(now);
    // }
//=======================================================================================
//6.Declarar un entero y asignarle un valor. Si el valor < 5 imprimir "Pequeño", si no imprimir "Grande".Declarar une entero y asignarle un valor. Si el valor = 1 imprimir "no", = 2 imprimir "Dos", =3 imprimir "3", si no imprimir "Ni uno, ni dos, ni tres". 
    
    // public static void main(String[] args) {
    //     int numero = 10;

    //     if (numero < 5 ){
    //         System.out.println("Pequeño");
    //     } else {
    //         System.out.println("Grande");

    //     }
    // }

    //pt2 

    // public static void main(String[] args) {
    //     int entero = 3;

    //     if (entero == 1){
    //         System.out.println("uno");
    //     }else if (entero == 2) {
    //         System.out.println("Dos");
    //     }else if (entero == 3) {
    //         System.out.println("Tres");
    //     }else{
    //         System.out.println("Ni uno, ni dos, ni tres");
    //     }
    // }
//=======================================================================================
//7.	Declarar un entero = 0. Mientras el entero <5, imprimir su valor y incrementarlo de 1. 

    // public static void main(String[] args) {
    //     int zero = 0; 

    //     while (zero < 5 ){
    //         System.out.println("Valor actual: " + zero);
    //         zero++;
    //     }   
    // }
//=======================================================================================
//8.	Declarar un entero = 10. Mientras el entero >5, imprimir su valor y decrementarlo de 1, pero imprime por lo menos una vez su valor. 
    

    // public static void main(String[] args) {
    //     int entero = 10;

    //     do {
    //         System.out.println(entero);
    //         entero--;
    //     }while (entero > 5);
    // }
//=======================================================================================
// 9.	Crear un bucle que se ejecuta 10 veces utilizando un entero que va de 10 a 19 incrementándose en 1. Imprimir en cada bucle el valor del entero, menos cuando es igual a 15.
   
    // public static void main(String[] args) {
    //     for(int i = 10; (i <= 19); i++){
    //         if (i == 15){
    //             continue;
    //         }
    //         System.out.println(i);
    //     }
    // }
//=======================================================================================
    // public static void main(String[] args) {
    //     Scanner sc = new Scanner(System.in);
    //     System.out.println("Ingrese un primer numero:" );
    //     int numero1 = sc.nextInt();
    //     System.out.println("Ingrese un segundo numero:" );

    //     int numero2 = sc.nextInt();
        
    //     if (numero1 == numero2){
    //         System.out.println("Son iguales");
    //     }else{
    //         System.out.println("Son diferentes");
    //     }
    //     sc.close();
    // }
//=======================================================================================  
//11.	Pedir una nota de 0 a 10 y mostrarla de la forma: Insuficiente, Suficiente, Bien usando un switch

    // public static void main(String[] args) {
    //     Scanner sc = new Scanner(System.in);
    //     System.out.println("Ingrese su nota del 0 al 10: ");
    //     int nota = sc.nextInt();
        
    //     switch (nota) {
    //         case 1:
    //         case 2:
    //         case 3:
    //         case 4:
    //             System.out.println("Insuficiente");
    //             break;
    //         case 5:
    //         case 6:
    //             System.out.println("Suficiente");
    //             break;
    //         case 7:
    //         case 8:
    //         case 9:
    //         case 10:
    //             System.out.println("Bien");
    //             break;

    //         default:
    //             System.out.println("Nota invalida");
    //             break;
    //     }
    //     sc.close();
    // }
//=======================================================================================
//12.Leer un número y mostrar su cuadrado, repetir el proceso hasta que se introduzca un número negativo
    // public static void main(String[] args) {
    //     Scanner sc = new Scanner(System.in);
    //     int numero;

    //     do {

    //         System.out.println("Ingrese un numero: ");
    //         numero = sc.nextInt();

    //         if (numero >=0){
    //             System.out.println("El cuadrado de este numero es: " + numero*numero);
    //         }else{
    //             System.out.println("El numero ingresado es negativo");
    //         }

    //     }while (numero >= 0);
         
    //     sc.close();
    // }
//=======================================================================================
//13.	Pedir números hasta que se teclee un 0, mostrar la suma de todos los números introducidos. Usando solo uno de los bucles mostrados en clase

    // public static void main(String[] args) {

    //     Scanner sc = new Scanner(System.in);
    //     int numero;
    //     int suma = 0;

    //     do {
    //         System.out.println("Ingrese un numero (0 para terminar): ");
    //         numero = sc.nextInt();

    //         if (numero != 0) {
    //             suma += numero;
    //         }

    //     } while (numero != 0);

    //     System.out.println("La suma total es: " + suma);

    //     sc.close();
    // }
//=======================================================================================
//14.	Pedir 15 números y escribir la suma total.  Usando solo uno de los bucles mostrados en clase

    // public static void main(String[] args) {
    //     Scanner sc = new Scanner(System.in);
    //     int suma = 0;

    //     for (int i = 1; i <= 15; i++) {
    //         System.out.println("Ingrese el número " + i + ": ");
    //         int numero = sc.nextInt();
    //         suma += numero;
    //     }

    //     System.out.println("La suma total es: " + suma);
    //     sc.close();
    // }

//=======================================================================================
//15.	Pide un número (que debe estar entre 0 y 10) y mostrar la tabla de multiplicar de dicho número

    // public static void main(String[] args) {
    //     Scanner sc = new Scanner(System.in);
    //     System.out.println("Ingrese un número entre 0 y 10: ");
    //     int numero = sc.nextInt();

    //     for (int i = 1; i <= 10; i++) {
    //         System.out.println(numero + " x " + i + " = " + (numero * i));
    //     }

    //     sc.close();
    // }

//=======================================================================================
//16.	 Determinar a cuánto es igual la suma de los elementos de la serie de Fibonacci entre 0 y 100. 

    // public static void main(String[] args) {
    //     int a = 0, b = 1;
    //     int suma = 0;

    //     while (a <= 100) {
    //         suma += a;
    //         int siguiente = a + b;
    //         a = b;
    //         b = siguiente;
    //     }

    //     System.out.println("La suma de Fibonacci entre 0 y 100 es: " + suma);
    // }
//=======================================================================================   
//17.	Determinar a cuánto es igual el promedio entero de los elementos de la serie de Fibonacci entre 0 y 1000. 

    // public static void main(String[] args) {
    //     int a = 0, b = 1;
    //     int suma = 0;
    //     int contador = 0;

    //     while (a <= 1000) {
    //         suma += a;
    //         contador++;
    //         int siguiente = a + b;
    //         a = b;
    //         b = siguiente;
    //     }

    //     int promedio = suma / contador;
    //     System.out.println("El promedio entero de Fibonacci entre 0 y 1000 es: " + promedio);
    // }

//=======================================================================================
//18.	 Determinar cuántos elementos de la serie de Fibonacci se encuentran entre 1000 y 2000. 

//    public static void main(String[] args) {
//         int a = 0, b = 1;
//         int contador = 0;

//         while (a <= 2000) {
//             if (a >= 1000) {
//                 contador++;
//             }
//             int siguiente = a + b;
//             a = b;
//             b = siguiente;
//         }

//         System.out.println("Cantidad de elementos de Fibonacci entre 1000 y 2000: " + contador);
//     } 
//=======================================================================================
//19.	 Leer un número y calcularle su factorial. 

    //  public static void main(String[] args) {
    //     Scanner sc = new Scanner(System.in);
    //     System.out.println("Ingrese un número: ");
    //     int numero = sc.nextInt();

    //     long factorial = 1;
    //     for (int i = 1; i <= numero; i++) {
    //         factorial *= i;
    //     }

    //     System.out.println("El factorial de " + numero + " es: " + factorial);
    //     sc.close();
    // }
//=======================================================================================
// 20.	 Leer un número y calcularles la factorial a todos los enteros comprendidos entre 1 y el número leído.

    // public static void main(String[] args) {
    //     Scanner sc = new Scanner(System.in);
    //     System.out.println("Ingrese un número: ");
    //     int numero = sc.nextInt();

    //     for (int i = 1; i <= numero; i++) {
    //         long factorial = 1;
    //         for (int j = 1; j <= i; j++) {
    //             factorial *= j;
    //         }
    //         System.out.println("El factorial de " + i + " es: " + factorial);
    //     }

    //     sc.close();
    // }


//=======================================================================================
// 21.	 Leer un número entero y calcular el promedio entero de las factoriales de los enteros comprendidos entre 1 y el número leído. 

    // public static void main(String[] args) {
    //     Scanner sc = new Scanner(System.in);
    //     System.out.println("Ingrese un número: ");
    //     int numero = sc.nextInt();

    //     long sumaFactoriales = 0;

    //     for (int i = 1; i <= numero; i++) {
    //         long factorial = 1;
    //         for (int j = 1; j <= i; j++) {
    //             factorial *= j;
    //         }
    //         sumaFactoriales += factorial;
    //     }

    //     long promedio = sumaFactoriales / numero;
    //     System.out.println("El promedio entero de las factoriales es: " + promedio);

    //     sc.close();
    // }


//=======================================================================================
// 22.	 Leer un número entero y calcular a cuánto es igual la sumatoria de todas las factoriales de los números comprendidos entre 1 y el número leído

//   public static void main(String[] args) {
//         Scanner sc = new Scanner(System.in);
//         System.out.println("Ingrese un número: ");
//         int numero = sc.nextInt();

//         long sumaFactoriales = 0;

//         for (int i = 1; i <= numero; i++) {
//             long factorial = 1;
//             for (int j = 1; j <= i; j++) {
//                 factorial *= j;
//             }
//             sumaFactoriales += factorial;
//         }

//         System.out.println("La sumatoria de las factoriales es: " + sumaFactoriales);

//         sc.close();
//     }  


}


