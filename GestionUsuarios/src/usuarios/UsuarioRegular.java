package usuarios;

/**
 * Usuario regular (sin privilegios de administración).
 */
public class UsuarioRegular extends Usuario {

    public UsuarioRegular(String nombre, String correo, String contrasena) {
        super(nombre, correo, contrasena);   // constructor de Usuario
    }

    @Override
    public String getTipo() {
        return "Usuario regular";
    }
}
