package co.edu.uniquindio.proyectofinalhotelfx.Repo;

import co.edu.uniquindio.proyectofinalhotelfx.Modelo.Entidades.Alojamiento;

import java.util.ArrayList;
import java.util.List;

public class AlojamientoRepository {

    private final List<Alojamiento> alojamientos = new ArrayList<>();

    public List<Alojamiento> obtenerTodos() {
        return new ArrayList<>(alojamientos);
    }

    public void guardar(Alojamiento alojamiento) {
        alojamientos.add(alojamiento);
    }

    public void eliminar(Alojamiento alojamiento) {
        alojamientos.remove(alojamiento);
    }
    public void actualizar(Alojamiento alojamientoActualizado){
        alojamientos.set(alojamientos.indexOf(alojamientoActualizado), alojamientoActualizado);
    }

    public Alojamiento buscarPorId(String id){
        for(Alojamiento a: alojamientos){
            if(a.getId().equals(id)){
                return a;
            }
        }
        return null;
    }

}
