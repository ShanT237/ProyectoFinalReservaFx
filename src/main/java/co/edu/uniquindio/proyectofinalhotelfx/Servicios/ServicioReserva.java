package co.edu.uniquindio.proyectofinalhotelfx.Servicios;

import co.edu.uniquindio.proyectofinalhotelfx.Modelo.Entidades.*;
import co.edu.uniquindio.proyectofinalhotelfx.Repo.ReservaRepository;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Getter
@Builder
public class ServicioReserva {

    private final ReservaRepository reservaRepository;

    // CREAR RESERVA SIMPLE
    public void crearReserva(Reserva reserva) throws Exception {
        if (reservaRepository.buscarPorId(reserva.getCodigo()) != null) {
            throw new Exception("Ya existe una reserva con ese ID");
        }
        reserva.setEstadoReserva(true);
        reserva.setFechaCreacion(LocalDateTime.now());
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

    public void agregarReview(UUID reservaId, String comentario, int valoracion) throws Exception {
        Reserva reserva = reservaRepository.buscarPorId(reservaId);
        if (reserva == null) {
            throw new Exception("Reserva no encontrada");
        }
        if (reserva.getReview() != null) {
            throw new Exception("Ya se ha dejado una reseña para esta reserva");
        }

        Review nuevaReview = new Review(
                UUID.randomUUID(),                             // ID único para la review
                reserva.getCliente(),                          // Cliente que hizo la reserva
                comentario,                                    // Comentario de la review
                valoracion,                                    // Valoración numérica
                reserva.getAlojamiento(),                      // Alojamiento relacionado
                LocalDateTime.now()                            // Fecha actual
        );

        reserva.setReview(nuevaReview);
        reserva.getAlojamiento().getReviews().add(nuevaReview);
        reservaRepository.actualizar(reserva);
    }


    // AGENDAR ALOJAMIENTO BÁSICO (Sin fechas)
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

    // VERIFICAR DISPONIBILIDAD
    public boolean alojamientoDisponible(Alojamiento alojamiento, LocalDateTime inicio, LocalDateTime fin) {
        return reservaRepository.obtenerTodos().stream()
                .filter(r -> r.getAlojamiento().equals(alojamiento) && r.isEstadoReserva())
                .noneMatch(r ->
                        !(fin.isBefore(r.getFechaInicio()) || inicio.isAfter(r.getFechaFin()))
                );
    }

    // CALCULAR TOTAL
    public double calcularTotal(Alojamiento alojamiento, LocalDateTime inicio, LocalDateTime fin) {
        long dias = ChronoUnit.DAYS.between(inicio, fin);
        if (dias <= 0) dias = 1;
        return dias * alojamiento.getPrecioNoche();
    }

    // VERIFICAR SALDO
    public boolean clienteTieneSaldoSuficiente(Cliente cliente, double total) {
        return cliente.getBilletera().getSaldo() >= total;
    }

    // CREAR Y GUARDAR RESERVA COMPLETA
    public Reserva crearYGuardarReserva(Cliente cliente, Alojamiento alojamiento, LocalDateTime inicio, LocalDateTime fin, int numHuespedes) throws Exception {
        if (!alojamientoDisponible(alojamiento, inicio, fin)) {
            throw new Exception("El alojamiento no está disponible en esas fechas");
        }

        double total = calcularTotal(alojamiento, inicio, fin);

        if (!clienteTieneSaldoSuficiente(cliente, total)) {
            throw new Exception("El cliente no tiene saldo suficiente");
        }

        Reserva reserva = new Reserva();
        reserva.setCodigo(UUID.randomUUID());
        reserva.setCliente(cliente);
        reserva.setAlojamiento(alojamiento);
        reserva.setFechaInicio(inicio);
        reserva.setFechaFin(fin);
        reserva.setNumeroHuespedes(numHuespedes);
        reserva.setTotal(total);
        reserva.setFechaCreacion(LocalDateTime.now());
        reserva.setEstadoReserva(true);

        Factura factura = generarFactura(reserva);
        reserva.setFactura(factura);
        reserva.setQrCode(factura.getCodigoQR());

        reservaRepository.guardar(reserva);

        // Registrar transacción y descontar saldo
        cliente.getBilletera().setSaldo(cliente.getBilletera().getSaldo() - (float) total);

        return reserva;
    }

    // GENERAR FACTURA
    public Factura generarFactura(Reserva reserva) {
        return Factura.builder()
                .id(UUID.randomUUID())
                .fecha(java.time.LocalDate.now())
                .subtotal(reserva.getTotal())
                .total(reserva.getTotal()) // Si tienes impuestos, puedes modificar esto
                .codigoQR("QR_" + reserva.getCodigo())
                .build();
    }
    /*
    Implementar
     */
    public int obtenerTotalNochesReservadas(String idAlojamiento) {
        return 0;
    }

    public int obtenerTotalNochesDisponibles(String idAlojamiento) {
        return 0;
    }

    public double obtenerGananciasPorAlojamiento(String idAlojamiento) {
        return 0;
    }

    public int contarReservasPorAlojamiento(String id) {
        return 0;
    }
}