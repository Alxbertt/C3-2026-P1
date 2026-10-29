package usuarios;

/**
 * Clase abstracta que hereda de Usuario y agrega el nivel de acceso.
 * Su constructor se comunica con el constructor padre usando "super".
 */
public abstract class Administrador extends Usuario {

    private int nivelAcceso;

    public Administrador(String nombre, String correo, String contrasena, int nivelAcceso) {
        super(nombre, correo, contrasena);   // llamada al constructor de Usuario
        this.nivelAcceso = nivelAcceso;
    }

    // Método abstracto: cada tipo de administrador define sus permisos.
    public abstract String getPermisos();

    public int getNivelAcceso() { return nivelAcceso; }
    public void setNivelAcceso(int nivelAcceso) { this.nivelAcceso = nivelAcceso; }

    @Override
    public String getTipo() {
        return "Administrador";
    }

    @Override
    public String getDescripcion() {
        // "super" también sirve para reutilizar un método del padre
        return super.getDescripcion() + "\n"
             + "Nivel de acceso: " + nivelAcceso + "\n"
             + "Permisos: " + getPermisos();
    }
}
