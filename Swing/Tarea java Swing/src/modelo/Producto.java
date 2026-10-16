package modelo;

public class Producto {

    private int id;
    private String descripcion;
    private String categoria;
    private double precio;
    private int stock;

    public Producto(int id, String descripcion, String categoria, double precio, int stock) {
        this.id = id;
        this.descripcion = descripcion;
        this.categoria = categoria;
        this.precio = precio;
        this.stock = stock;
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getCategoria() {
        return categoria;
    }

    public double getPrecio() {
        return precio;
    }

    public int getStock() {
        return stock;
    }

    // Setters
    public void setId(int id) {
        this.id = id;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    // Dinero total que representa este producto en el inventario (precio x cantidad)
    public double getValorInventario() {
        return precio * stock;
    }

    // Convierte el producto en una fila lista para agregar al JTable.
    // El orden debe coincidir con las columnas: ID, Descripción, Categoría, Precio, Stock
    public Object[] aFila() {
        return new Object[] { id, descripcion, categoria, String.format("%.2f", precio), stock };
    }
}