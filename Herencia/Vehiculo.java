package Herencia;

public abstract sealed class Vehiculo permits Auto, Camioneta{
    private String matricula;
    private String marca;
    private String modelo;
    private int anio;
    private String estado;

    public Vehiculo(String matricula, String marca, String modelo, int anio, String estado){
        this.matricula = matricula;
        this.marca = marca;
        this.modelo = modelo;
        this.anio = anio;
        this.estado = estado;
    }

    public Vehiculo(String matricula, String marca, String modelo, int anio){
        this(matricula, marca, modelo, anio, "disponible");
    }

    public String getMatricula() {return matricula; }
    public void setMatricula(String matricula) {this.matricula = matricula; }
    
    public String getMarca() {return marca; }
    public void setMarca(String marca) {this.marca = marca; }
    
    public String getModelo() {return modelo; }
    public void setModelo(String modelo) {this.modelo = modelo; }

    public int getAnio() {return anio; }
    public void setAnio(int anio) {this.anio = anio; }

    public String getEstado() {return estado; }
    public void setEstado(String estado) {this.estado = estado; }

    public void mostrarInformacion() {
        System.out.println("Vehículo: " + matricula
                + " | Marca: " + marca
                + " | Modelo: " + modelo
                + " | Año: " + anio
                + " | Estado: " + estado);
        }
}