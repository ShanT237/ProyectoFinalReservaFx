package co.edu.uniquindio.proyectofinalhotelfx.Repo;

import co.edu.uniquindio.proyectofinalhotelfx.Modelo.Entidades.Alojamiento;

import java.util.ArrayList;
import java.util.List;

public class AlojamientoRepository {

    private final List<Alojamiento> alojamientos = new ArrayList<>();

    public List<Alojamiento> obtenerTodos() {
        return new ArrayList<>(alojamientos); // Retorna una copia para evitar modificación directa
    }

    // Puedes agregar métodos para guardar alojamientos si necesitas
    public void guardar(Alojamiento alojamiento) {
        alojamientos.add(alojamiento);
    }

    public void eliminar(Alojamiento alojamiento) {
        alojamientos.remove(alojamiento);
    }

    // También puedes agregar métodos para buscar por ID si lo necesitas
}
