package usuarios;

/**
 * Clase abstracta base de todos los usuarios del sistema.
 * No se puede instanciar directamente: hay que usar una subclase concreta
 * (por ejemplo UsuarioRegular o AdministradorGeneral).
 */
public abstract class Usuario {

    private String nombre;
    private String correo;
    private String contrasena;

    public Usuario(String nombre, String correo, String contrasena) {
        this.nombre = nombre;
        this.correo = correo;
        this.contrasena = contrasena;
    }

    // Método abstracto: cada subclase define cómo se identifica su tipo.
    public abstract String getTipo();

    public String getDescripcion() {
        return "Nombre: " + nombre + "\n"
             + "Correo: " + correo + "\n"
             + "Tipo: " + getTipo();
    }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }

    public String getContrasena() { return contrasena; }
    public void setContrasena(String contrasena) { this.contrasena = contrasena; }

    @Override
    public String toString() {
        return getTipo() + " - " + nombre + " <" + correo + ">";
    }
}
