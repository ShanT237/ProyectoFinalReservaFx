package co.edu.uniquindio.proyectofinalhotelfx.Repo;

import co.edu.uniquindio.proyectofinalhotelfx.Modelo.Entidades.Cliente;
import co.edu.uniquindio.proyectofinalhotelfx.Modelo.Entidades.Reserva;
import co.edu.uniquindio.proyectofinalhotelfx.Persistencia.Persistencia;
import co.edu.uniquindio.proyectofinalhotelfx.Persistencia.Ruta;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Repositorio para la gestión de reservas.
 * Proporciona operaciones básicas CRUD para entidades de tipo Reserva,
 * utilizando UUID como identificador único.
 */
public class ReservaRepository {

    // Lista que almacena todas las reservas registradas
    private ArrayList<Reserva> reservas;

    public ReservaRepository() {
        this.reservas = leerDatos();
    }

    public ArrayList<Reserva> listarReservas() {
        return reservas;
    }

    /**
     * Busca una reserva por su identificador único (UUID).
     * @param id UUID de la reserva a buscar
     * @return La reserva encontrada o null si no existe
     */
    public Reserva buscarPorId(UUID id) throws Exception {
        for (Reserva r : reservas) {
            if (r.getCodigo().equals(id)) {
                return r;
            }
        }
        throw new Exception("Reserva no encontrada");
    }
    private void guardarDatos() {
        try {
            Persistencia.serializarObjeto(Ruta.RUTA_RESERVA, reservas);
        } catch (IOException e) {
            System.err.println("Error guardando clientes: " + e.getMessage());
        }
    }

    private ArrayList<Reserva> leerDatos() {
        try {
            Object datos = Persistencia.deserializarObjeto(Ruta.RUTA_RESERVA);
            if (datos != null) {
                return (ArrayList<Reserva>) datos;
            }
        } catch (Exception e) {
            System.err.println("Error cargando clientes: " + e.getMessage());
        }
        return new ArrayList<>();
    }

    /**
     * Busca una reserva por su identificador único (UUID) sin lanzar excepción.
     * @param id UUID de la reserva a buscar
     * @return La reserva encontrada o null si no existe
     */
    private Reserva buscarPorIdSinExcepcion(UUID id) {
        for (Reserva r : reservas) {
            if (r.getCodigo().equals(id)) {
                return r;
            }
        }
        return null;
    }

    /**
     * Verifica si existe una reserva con el ID dado.
     * @param id UUID de la reserva a verificar
     * @return true si existe, false si no existe
     */
    public boolean existePorId(UUID id) {
        return buscarPorIdSinExcepcion(id) != null;
    }

    /**
     * Registra una nueva reserva en el repositorio.
     * @param reserva Objeto Reserva a almacenar
     * @throws IllegalArgumentException Si la reserva ya existe (mismo UUID)
     */
    public void guardar(Reserva reserva) throws Exception {
        if (existePorId(reserva.getCodigo())) {
            throw new IllegalArgumentException("Ya existe una reserva con el código: " + reserva.getCodigo());
        }
        reservas.add(reserva);
        guardarDatos();
    }

    /**
     * Actualiza los datos de una reserva existente.
     * @param reserva Reserva con los datos actualizados (debe tener el mismo UUID)
     * @throws IllegalArgumentException Si no existe una reserva con el UUID proporcionado
     */
    public void actualizar(Reserva reserva) {
        for (int i = 0; i < reservas.size(); i++) {
            if (reservas.get(i).getCodigo().equals(reserva.getCodigo())) {
                reservas.set(i, reserva);
                guardarDatos();
                return;
            }
        }
        throw new IllegalArgumentException("No existe una reserva con el código: " + reserva.getCodigo());
    }

    /**
     * Obtiene una copia segura de todas las reservas registradas.
     * @return Nueva lista que contiene todas las reservas
     */
    public List<Reserva> obtenerTodos() {
        return new ArrayList<>(reservas); // Devuelve una copia para evitar modificaciones externas
    }

    public void eliminarResena(UUID idResena) throws Exception {
        boolean encontrada = false;

        for (Reserva reserva : listarReservas()) {
            if (reserva.getReview() != null && reserva.getReview().getCodigo().equals(idResena)) {
                // Eliminar la reseña del alojamiento también
                if (reserva.getAlojamiento() != null && reserva.getAlojamiento().getReviews() != null) {
                    reserva.getAlojamiento().getReviews().removeIf(review -> review.getCodigo().equals(idResena));
                }

                // Eliminar la reseña de la reserva
                reserva.setReview(null);
                actualizar(reserva);
                guardarDatos();
                encontrada = true;
                break;
            }
        }

        if (!encontrada) {
            throw new Exception("Reseña no encontrada");
        }
    }
}