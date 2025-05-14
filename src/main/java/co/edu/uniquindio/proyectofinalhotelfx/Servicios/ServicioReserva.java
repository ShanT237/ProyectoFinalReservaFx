package co.edu.uniquindio.proyectofinalhotelfx.Servicios;

import co.edu.uniquindio.proyectofinalhotelfx.Modelo.Entidades.Alojamiento;
import co.edu.uniquindio.proyectofinalhotelfx.Modelo.Entidades.Cliente;
import co.edu.uniquindio.proyectofinalhotelfx.Modelo.Entidades.Reserva;
import co.edu.uniquindio.proyectofinalhotelfx.Repo.ReservaRepository;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Getter
@Builder
public class ServicioReserva {

    private final ReservaRepository reservaRepository;

    // CREAR RESERVA
    public void crearReserva(Reserva reserva) throws Exception {
        if (reservaRepository.buscarPorId(reserva.getCodigo()) != null) {
            throw new Exception("Ya existe una reserva con ese ID");
        }
        reserva.setEstadoReserva(true);
        reservaRepository.guardar(reserva);
    }

    // CANCELAR RESERVA
    public void cancelarReserva(UUID reservaId) throws Exception {
        Reserva reserva = reservaRepository.buscarPorId(reservaId);
        if (reserva == null) {
            throw new Exception("Reserva no encontrada");
        }
        if (!reserva.isEstadoReserva()) {
            throw new Exception("La reserva ya fue cancelada o finalizada");
        }
        reserva.setEstadoReserva(false);
        reservaRepository.actualizar(reserva);
    }

    // OBTENER RESERVAS POR CLIENTE
    public List<Reserva> obtenerReservasPorCliente(String clienteId) {
        return reservaRepository.obtenerTodos()
                .stream()
                .filter(reserva -> reserva.getCliente().getCedula().equals(clienteId))
                .collect(Collectors.toList());
    }

    // AGREGAR REVIEW
    public void agregarReview(UUID reservaId, String comentario, int valoracion) throws Exception {
        Reserva reserva = reservaRepository.buscarPorId(reservaId);
        if (reserva == null) {
            throw new Exception("Reserva no encontrada");
        }
        if (reserva.getReview() != null) {
            throw new Exception("Ya se ha dejado una reseña para esta reserva");
        }
        reserva.getReview().setComentario(comentario);
        reserva.getReview().setValoracion(valoracion);
        reserva.getAlojamiento().getReviews().add(reserva.getReview());
        reservaRepository.actualizar(reserva);
    }

    public void agendarAlojamiento(Cliente cliente, Alojamiento alojamiento) throws Exception {
        if (cliente == null || alojamiento == null) {
            throw new Exception("Cliente o alojamiento no válido");
        }

        Reserva reserva = new Reserva();
        reserva.setCodigo(UUID.randomUUID());
        reserva.setCliente(cliente);
        reserva.setAlojamiento(alojamiento);
        reserva.setEstadoReserva(true);
        reserva.setFechaReserva(java.time.LocalDate.now());

        reservaRepository.guardar(reserva);
    }


}
