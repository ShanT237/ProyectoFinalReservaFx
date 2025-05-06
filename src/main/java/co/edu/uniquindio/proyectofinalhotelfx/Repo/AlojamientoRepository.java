package co.edu.uniquindio.proyectofinalhotelfx.Repo;

import co.edu.uniquindio.proyectofinalhotelfx.Modelo.Entidades.Alojamiento;

import java.util.ArrayList;
import java.util.List;

/**
 * Repositorio para gestionar las operaciones CRUD de alojamientos
 */
public class AlojamientoRepository {

    private final List<Alojamiento> alojamientos = new ArrayList<>();

    /**
     * Obtiene una lista con todos los alojamientos registrados
     * @return Lista copia de los alojamientos almacenados
     */
    public List<Alojamiento> obtenerTodos() {
        return new ArrayList<>(alojamientos);
    }

    /**
     * Guarda un nuevo alojamiento en el repositorio
     * @param alojamiento El alojamiento a guardar
     */
    public void guardar(Alojamiento alojamiento) {
        alojamientos.add(alojamiento);
    }

    /**
     * Elimina un alojamiento del repositorio
     * @param idAlojamiento El alojamiento a eliminar
     */
    public void eliminar(String idAlojamiento) {
        for (Alojamiento alojamiento : alojamientos) {
            if (alojamiento.getId().equals(idAlojamiento)) {
                alojamientos.remove(alojamiento);
                return;
            }
        }
    }

    /**
     * Actualiza un alojamiento existente identificándolo por su ID único.
     * Busca el alojamiento con el mismo ID que el proporcionado y lo reemplaza
     * completamente con la nueva instancia.
     *
     * @param alojamientoActualizado La nueva versión del alojamiento con el mismo ID
     * @throws IllegalArgumentException si no existe un alojamiento con el ID especificado
     */
    public void actualizar(Alojamiento alojamientoActualizado) {
        String idBuscado = alojamientoActualizado.getId();

        for (int i = 0; i < alojamientos.size(); i++) {
            if (alojamientos.get(i).getId().equals(idBuscado)) {
                alojamientos.set(i, alojamientoActualizado);
                return;
            }
        }

        throw new IllegalArgumentException("No existe un alojamiento con ID: " + idBuscado);
    }

    /**
     * Busca un alojamiento por su identificador único
     * @param id El identificador del alojamiento a buscar
     * @return El alojamiento encontrado o null si no existe
     */
    public Alojamiento buscarPorId(String id) {
        for(Alojamiento a: alojamientos) {
            if(a.getId().equals(id)) {
                return a;
            }
        }
        return null;
    }
}