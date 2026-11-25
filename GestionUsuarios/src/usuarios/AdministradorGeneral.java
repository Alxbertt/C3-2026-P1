package usuarios;

/**
 * Administrador concreto. Implementa el método abstracto getPermisos().
 */
public class AdministradorGeneral extends Administrador {

    public AdministradorGeneral(String nombre, String correo, String contrasena, int nivelAcceso) {
        super(nombre, correo, contrasena, nivelAcceso);   // constructor de Administrador
    }

    @Override
    public String getPermisos() {
        return "Gestionar usuarios, editar configuración y ver reportes";
    }
}
