package modelo;

public class Cliente {
    
    private int id;
    private String descripcion;
    private String direccion;
    private String telefono;
    private String ruc;

    public Cliente(int id, String descripcion, String direccion, String telefono, String ruc) {
        this.id = id;
        this.descripcion = descripcion;
        this.direccion = direccion;
        this.telefono = telefono;
        this.ruc = ruc;
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getDireccion() {
        return direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getRuc() {
        return ruc;
    }

    // Setters
    public void setId(int id) {
        this.id = id;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public void setRuc(String ruc) {
        this.ruc = ruc;
    }


    public Object[] aFila() {
        return new Object[] { id, descripcion, direccion, telefono, ruc };
    }
}