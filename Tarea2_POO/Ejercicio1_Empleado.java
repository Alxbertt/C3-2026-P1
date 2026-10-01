package Tarea2_POO;
public class Ejercicio1_Empleado {
    public static void main(String[] args) {
        Empleado ep1 = new Empleado("EMP001", "Albert", "Hichez Perez", 19, 50000, "19/04/2022", "Gerente");
        Empleado ep2 = new Empleado("EMP002", "Leslie", "Rosario Perez", 31, 90000, "3/08/2016", "Recursos Humanos");
        Empleado ep3 = new Empleado("EMP003", "Rodri", "Valencio hernandez", 22, 111304, "20/01/2025", "Programador");
        Empleado ep4 = new Empleado("EMP004", "Paula", "zuckerberg lirio", 36, 87000, "30/12/2018", "Servicio al cliente");
        Empleado ep5 = new Empleado("EMP005", "Nelson", "pujols soto", 28, 12304, "21/05/2021", "Ventas");


        System.out.println("\n--- " + ep1.getNombre() + " (" + ep1.getDepartamento() + ") ---");
        ep1.gestionar();
        ep1.darOrdenes();
        ep1.llamarAtencion();
 
        System.out.println("\n--- " + ep2.getNombre() + " (" + ep2.getDepartamento() + ") ---");
        ep2.contratarPersonal();
        ep2.organizarCapacitaciones();
        ep2.gestionarNomina();
 
        System.out.println("\n--- " + ep3.getNombre() + " (" + ep3.getDepartamento() + ") ---");
        ep3.corregirErrores();
        ep3.revisarCodigo();
        ep3.desarrollar();
 
        System.out.println("\n--- " + ep4.getNombre() + " (" + ep4.getDepartamento() + ") ---");
        ep4.asistirClientes();
        ep4.resolverQuejas();
        ep4.darSeguimiento();
 
        System.out.println("\n--- " + ep5.getNombre() + " (" + ep5.getDepartamento() + ") ---");
        ep5.cerrarVenta();
        ep5.ofrecerProducto();
        ep5.analizarMercado();
    }
}

class Empleado {
    private String nombre;
    private String apellidos;
    private int edad;
    private double sueldo;
    private String fechaingresomx;
    private String departamento;
 
    public Empleado(String empx, String nombre, String apellidos, int edad, double sueldo, String fechaingresomx, String departamento) {
        System.out.println("Bienvenido " + empx);
        System.out.println("Entrando al sistema...");
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.edad = edad;
        this.sueldo = sueldo;
        this.fechaingresomx = fechaingresomx;
        this.departamento = departamento;
    }

    //getters y setters 
    public String getNombre() {
    // aquí no va más código
    return this.nombre; 
    }

    public void setNombre(String nombre) {
    this.nombre = nombre;
    // aquí no va más código
    }

    public String getApellidos() {
        // aquí no va más código
        return this.apellidos;
    }

    public void setApellidos(String apellidos) {
    this.apellidos = apellidos;
    // aquí no va más código
    }

    public int getEdad() {
        // aquí no va más código
        return this.edad;
    }

    public void setedad(int edad) {
    this.edad = edad;
    // aquí no va más código
    }

    public double getSueldo() {
        // aquí no va más código
        return this.sueldo;
    }

    public void setSueldo(double sueldo) {
    this.sueldo = sueldo;
    // aquí no va más código
    }

    public String getFechaingresomx() {
        // aquí no va más código
        return this.fechaingresomx;
    }

    public void setFechaingresomx(String fechaingresomx) {
    this.fechaingresomx = fechaingresomx;
    // aquí no va más código
    }

    public String getDepartamento() {
        // aquí no va más código
        return this.departamento;
    }

    public void setDepartamento(String departamento) {
    this.departamento = departamento;
    // aquí no va más código
    }


    //metodos de empleados
    // --- Rol: Gerente ---
    public void gestionar() {
        System.out.println(this.nombre + " está gestionando al equipo del departamento de " + this.departamento);
    }
 
    public void darOrdenes() {
        System.out.println(this.nombre + " está dando órdenes al equipo.");
    }
 
    public void llamarAtencion() {
        System.out.println(this.nombre + " está llamando la atención a un empleado.");
    }

    // --- Rol: programador ---
    public void corregirErrores(){
        System.out.println(this.nombre + " esta corrigiendo errores en el codigo ");
    }

    public void revisarCodigo(){
        System.out.println(this.nombre + " esta revisando codigo ");
    }
    
    public void desarrollar(){
        System.out.println(this.nombre + " esta desarrollando algo nuevo ");
    }

    // --- Rol: servicio al cliente ---
    public void asistirClientes(){
        System.out.println(this.nombre + " esta asistiendo algunos clientes ");
    }

    public void resolverQuejas(){
        System.out.println(this.nombre + " esta resolviendo las quejas de los clientes ");
    }

    public void darSeguimiento(){
        System.out.println(this.nombre + " Esta dando seguimiento a lo nuevo ");
    }

    // --- Rol: vendedor ---
    public void cerrarVenta(){
        System.out.println(this.nombre + " esta cerrando una venta ");
    }    
    public void ofrecerProducto(){
        System.out.println(this.nombre + " esta ofreciendo algunos productos ");
    }
    public void analizarMercado(){
        System.out.println(this.nombre + " esta analizando el mercado ");
    }

    // --- Rol: recursos humanos ---
    public void contratarPersonal(){
        System.out.println(this.nombre + " esta contratando nuevo personal ");
    }
    public void organizarCapacitaciones(){
        System.out.println(this.nombre + " esta organizando capacitaciones nuevas ");
    }
    public void gestionarNomina(){
        System.out.println(this.nombre + " esta trabajando con la nomina ");
    }
}

