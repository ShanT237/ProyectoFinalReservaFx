package co.edu.uniquindio.proyectofinalhotelfx.Repo;

import co.edu.uniquindio.proyectofinalhotelfx.Modelo.Entidades.Alojamiento;
import co.edu.uniquindio.proyectofinalhotelfx.Modelo.Entidades.Habitacion;
import co.edu.uniquindio.proyectofinalhotelfx.Modelo.Entidades.Hotel;
import co.edu.uniquindio.proyectofinalhotelfx.Modelo.Entidades.Reserva;
import co.edu.uniquindio.proyectofinalhotelfx.Modelo.Enums.ServiciosIncluidos;
import co.edu.uniquindio.proyectofinalhotelfx.Modelo.Enums.TipoHabitacionHotel;
import co.edu.uniquindio.proyectofinalhotelfx.Persistencia.Ruta;
import co.edu.uniquindio.proyectofinalhotelfx.Persistencia.Persistencia;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Repositorio para gestionar las operaciones CRUD de alojamientos
 */
public class AlojamientoRepository {

    private final List<Alojamiento> alojamientos;

    public AlojamientoRepository() {
        this.alojamientos = leerDatos();
    }

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
        guardarDatos();
    }

    public void registrarHabitacionHotel(String idHotel, Habitacion habitacion) throws Exception {
        for (Alojamiento alojamiento : alojamientos) {
            if (alojamiento.getId().equals(idHotel)) {
                if (alojamiento instanceof Hotel) {
                    Hotel hotel = (Hotel) alojamiento;

                    for (Habitacion habExistente : hotel.getHabitaciones()) {
                        if (habExistente.getNumero() == habitacion.getNumero()) {
                            throw new Exception("Ya existe una habitación con el número " + habitacion.getNumero() + " en este hotel.");
                        }
                    }


                    hotel.getHabitaciones().add(habitacion);
                    guardarDatos();
                    return;
                } else {
                    throw new Exception("El alojamiento con ID " + idHotel + " no es un hotel.");
                }
            }
        }
        throw new Exception("No se encontró alojamiento con ID " + idHotel);
    }

    public void eliminarHabitacionHotel(String idHotel, int numeroHabitacion) throws Exception {
        for (Alojamiento alojamiento : alojamientos) {
            if (alojamiento.getId().equals(idHotel)) {
                if (alojamiento instanceof Hotel) {
                    Hotel hotel = (Hotel) alojamiento;
                    hotel.getHabitaciones().removeIf(h -> h.getNumero() == numeroHabitacion);
                    guardarDatos();
                    return;
                } else {
                    throw new Exception("El alojamiento con ID " + idHotel + " no es un hotel.");
                }
            }
        }
    }

    /**
     * Elimina un alojamiento del repositorio
     * @param idAlojamiento El alojamiento a eliminar
     */
    public void eliminar(String idAlojamiento) {
        for (Alojamiento alojamiento : alojamientos) {
            if (alojamiento.getId().equals(idAlojamiento)) {
                alojamientos.remove(alojamiento);
                guardarDatos();
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
                guardarDatos();
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

    public int obtenerCapacidadReservablePorAlojamiento(String id) throws Exception {
        for (Alojamiento alojamiento : alojamientos) {
            if (alojamiento.getId().equals(id)) {
                return alojamiento.getCapacidadHuespedes();
            }
        }

        throw new Exception("No se pudo obtener la capacidad reservable del alojamiento con id: " + id);
    }

    private void guardarDatos() {
        try {
            Persistencia.serializarObjeto(Ruta.RUTA_ALOJAMIENTO, alojamientos);
        } catch (IOException e) {
            System.err.println("Error guardando alojamientos: " + e.getMessage());
        }
    }

    private List<Alojamiento> leerDatos() {
        try {
            Object datos = Persistencia.deserializarObjeto(Ruta.RUTA_ALOJAMIENTO);
            if (datos != null) {
                return (List<Alojamiento>) datos;
            }
        } catch (Exception e) {
            System.err.println("Error cargando alojamientos: " + e.getMessage());
        }
        return new ArrayList<>();
    }
}