package Herencia;

import java.util.ArrayList;

public class AgenciaAlquiler {
        private ArrayList<Vehiculo> vehiculos = new ArrayList<>();

    // Agregar (evita matrículas repetidas)
    public boolean agregarVehiculo(Vehiculo v) {
        if (buscarVehiculo(v.getMatricula()) != null) {
            System.out.println("Ya existe un vehículo con esa matrícula.");
            return false;
        }
        vehiculos.add(v);
        System.out.println("Vehículo agregado correctamente.");
        return true;
    }

    // Buscar por matrícula: devuelve el vehículo o null
    public Vehiculo buscarVehiculo(String matricula) {
        for (Vehiculo v : vehiculos) {
            if (v.getMatricula().equalsIgnoreCase(matricula)) {
                return v;
            }
        }
        return null;
    }

    // Eliminar por matrícula
    public boolean eliminarVehiculo(String matricula) {
        Vehiculo v = buscarVehiculo(matricula);
        if (v != null) {
            vehiculos.remove(v);
            return true;
        }
        return false;
    }

    // Mostrar todos
    public void mostrarVehiculos() {
        if (vehiculos.isEmpty()) {
            System.out.println("No hay vehículos registrados.");
            return;
        }
        for (Vehiculo v : vehiculos) {
            v.mostrarInformacion();
            System.out.println("------------------------------");
        }
    }
}
