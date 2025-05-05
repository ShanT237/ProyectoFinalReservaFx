package co.edu.uniquindio.proyectofinalhotelfx.Servicios;

import co.edu.uniquindio.proyectofinalhotelfx.Modelo.Entidades.Reserva;
import co.edu.uniquindio.proyectofinalhotelfx.Repo.ReservaRepository;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
public class ServicioReserva {

    private final ReservaRepository reservaRepository;

    // CREAR RESERVA
    public void crearReserva(Reserva reserva) throws Exception {
        if (reservaRepository.buscarPorId(reserva.getId()) != null) {
            throw new Exception("Ya existe una reserva con ese ID");
        }
        reserva.setEstado("Activa");
        reservaRepository.guardar(reserva);
    }

    // CANCELAR RESERVA
    public void cancelarReserva(String reservaId) throws Exception {
        Reserva reserva = reservaRepository.buscarPorId(reservaId);
        if (reserva == null) {
            throw new Exception("Reserva no encontrada");
        }
        if (!reserva.getEstado().equals("Activa")) {
            throw new Exception("La reserva ya fue cancelada o finalizada");
        }
        reserva.setEstado("Cancelada");
        reservaRepository.actualizar(reserva);
    }

    // OBTENER RESERVAS POR CLIENTE
    public List<Reserva> obtenerReservasPorCliente(String clienteId) {
        return reservaRepository.obtenerTodos()
                .stream()
                .filter(reserva -> reserva.getCliente().getId().equals(clienteId))
                .collect(Collectors.toList());
    }

    // AGREGAR REVIEW
    public void agregarReview(String reservaId, String comentario, int valoracion) throws Exception {
        Reserva reserva = reservaRepository.buscarPorId(reservaId);
        if (reserva == null) {
            throw new Exception("Reserva no encontrada");
        }
        if (reserva.getComentario() != null || reserva.getValoracion() > 0) {
            throw new Exception("Ya se ha dejado una reseña para esta reserva");
        }
        reserva.setComentario(comentario);
        reserva.setValoracion(valoracion);
        reservaRepository.actualizar(reserva);
    }
}
