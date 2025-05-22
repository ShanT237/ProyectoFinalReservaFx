package co.edu.uniquindio.proyectofinalhotelfx.Servicios;

import co.edu.uniquindio.proyectofinalhotelfx.Modelo.Entidades.*;
import co.edu.uniquindio.proyectofinalhotelfx.Modelo.Enums.Ciudad;
import co.edu.uniquindio.proyectofinalhotelfx.Notificacion.Notificacion;
import co.edu.uniquindio.proyectofinalhotelfx.Repo.ReservaRepository;
import co.edu.uniquindio.proyectofinalhotelfx.utils.GeneradorQR;
import jakarta.activation.DataSource;
import jakarta.mail.util.ByteArrayDataSource;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

@Setter
@Getter
@Builder
public class ServicioReserva {

    private final ReservaRepository reservaRepository;
    private final ServicioCliente servicioCliente;
    private final ServicioAlojamiento servicioAlojamiento;
    private final ServicioOferta servicioOferta;

    public ArrayList<Reserva> listarReservas() {
        return reservaRepository.listarReservas();
    }

    public void agregarReserva(String idCliente, String idAlojamiento, LocalDateTime fechainicial, LocalDateTime fechafin,
                               int numeroHuespedes, double total, LocalDateTime fechaCreacion) throws Exception {

        validarDatos(idCliente, idAlojamiento, fechainicial, fechafin, numeroHuespedes, total, fechaCreacion);

        Cliente cliente = servicioCliente.buscarCliente(idCliente);
        Alojamiento alojamiento = servicioAlojamiento.getAlojamientoRepository().buscarPorId(idAlojamiento);

        double descuento = aplicarDescuento();

        double precioConDescuento = total - (total * descuento / 100);

        Factura factura = crearFactura(precioConDescuento, cliente, fechaCreacion);
        Reserva reserva = crearReserva(cliente, alojamiento, numeroHuespedes, fechainicial, fechafin, factura, precioConDescuento);

        reservaRepository.guardar(reserva);

        byte[] imagenQR = GeneradorQR.generarQRComoBytes(factura.getId().toString(), 300, 300);
        DataSource ds = new ByteArrayDataSource(imagenQR, "image/png");

        Notificacion.enviarNotificacionImagen(
                cliente.getCorreo(),
                "Aquí tiene su factura de la reserva realizada",
                "Reserva:\n" + reserva.toString() + "\nFactura:\n" + factura.toString(),
                ds
        );
    }

    public void validarDatos(String idAlojamiento, String cliente, LocalDateTime fechaInicio, LocalDateTime fechaFin,
                             int numeroHuespedes, double total, LocalDateTime fechaCreacion) throws Exception {
        if (idAlojamiento == null || idAlojamiento.isEmpty()) {
            throw new Exception("Debe seleccionar un alojamiento.");
        }
        if (cliente == null || cliente.isEmpty()) {
            throw new Exception("Debe seleccionar un cliente.");
        }
        if (fechaInicio == null) {
            throw new Exception("Debe seleccionar una fecha de inicio.");
        }
        if (fechaFin == null) {
            throw new Exception("Debe seleccionar una fecha de fin.");
        }
        if (numeroHuespedes <= 0) {
            throw new Exception("Debe seleccionar un número de huespedes mayor a 0.");
        }
        if (total <= 0) {
            throw new Exception("Debe seleccionar un total mayor a 0.");
        }
        if (fechaCreacion == null) {
            throw new Exception("Debe seleccionar una fecha de creación.");
        }
    }

    public Reserva crearReserva(Cliente cliente, Alojamiento alojamiento, int numeroHuespedes,
                                LocalDateTime fechainicial, LocalDateTime fechafin,
                                Factura factura, double precioFinal) throws Exception {
        return Reserva.builder()
                .codigo(UUID.randomUUID())
                .cliente(cliente)
                .alojamiento(alojamiento)
                .numeroHuespedes(numeroHuespedes)
                .fechaInicio(fechainicial)
                .fechaFin(fechafin)
                .factura(factura)
                .total(precioFinal)
                .fechaCreacion(LocalDateTime.now())
                .EstadoReserva(true)
                .fechaReserva(LocalDate.now())
                .build();
    }

    public void agregarReview(UUID reservaId, String comentario, int valoracion) throws Exception {
        Reserva reserva = reservaRepository.buscarPorId(reservaId);
        if (reserva == null) {
            throw new Exception("Reserva no encontrada");
        }
        if (reserva.getReview() != null) {
            throw new Exception("Ya se ha dejado una reseña para esta reserva");
        }

        Review nuevaReview = Review.builder()
                .codigo(UUID.randomUUID())
                .cliente(reserva.getCliente())
                .comentario(comentario)
                .valoracion(valoracion)
                .alojamiento(reserva.getAlojamiento())
                .fecha(LocalDateTime.now())
                .build();

        reserva.setReview(nuevaReview);
        reserva.getAlojamiento().getReviews().add(nuevaReview);
        reservaRepository.actualizar(reserva);
    }

    public double aplicarDescuento() {
        double descuentoTotal = 0;

        List<Oferta> ofertasActivas = servicioOferta.listarOfertasActivas();

        for (Oferta oferta : ofertasActivas) {
            if (oferta.getDescuento() > descuentoTotal) {
                descuentoTotal = oferta.getDescuento();
            }
        }
        return descuentoTotal;
    }

    private Factura crearFactura(double total, Cliente cliente, LocalDateTime fechaCreacion) {
        return Factura.builder()
                .id(UUID.randomUUID())
                .cliente(cliente)
                .total(total)
                .fechaCreacion(fechaCreacion)
                .build();
    }

    public int obtenerTotalNochesReservadas(String idAlojamiento) {
        List<Reserva> reservas = reservaRepository.listarReservas().stream()
                .filter(r -> r.getAlojamiento().getId().equals(idAlojamiento))
                .collect(Collectors.toList());

        int totalNoches = 0;
        for (Reserva reserva : reservas) {
            long noches = ChronoUnit.DAYS.between(reserva.getFechaInicio().toLocalDate(), reserva.getFechaFin().toLocalDate());
            totalNoches += noches;
        }
        return totalNoches;
    }

    public int obtenerTotalNochesDisponibles(String idAlojamiento) {
        // Si tienes un método para obtener total de noches disponibles por alojamiento, impleméntalo aquí.
        // Ejemplo simple, debe ser modificado según reglas de negocio.
        Alojamiento alojamiento = servicioAlojamiento.getAlojamientoRepository().buscarPorId(idAlojamiento);
        if (alojamiento == null) return 0;

        // Supongamos que el alojamiento tiene un atributo "capacidadDiasDisponibles"
        return alojamiento.getCapacidadDiasDisponibles();
    }

    public double obtenerGananciasPorAlojamiento(String idAlojamiento) {
        List<Reserva> reservas = reservaRepository.listarReservas().stream()
                .filter(r -> r.getAlojamiento().getId().equals(idAlojamiento))
                .collect(Collectors.toList());

        return reservas.stream()
                .mapToDouble(Reserva::getTotal)
                .sum();
    }

    public int contarReservasPorAlojamiento(String id) {
        return (int) reservaRepository.listarReservas().stream()
                .filter(r -> r.getAlojamiento().getId().equals(id))
                .count();
    }

    public void reservarAlojamiento(String cedula, Alojamiento alojamiento) throws Exception {
        Cliente cliente = servicioCliente.buscarCliente(cedula);

        LocalDateTime ahora = LocalDateTime.now();

        Reserva reserva = crearReserva(cliente, alojamiento, 1, ahora, ahora.plusDays(1), null, alojamiento.getPrecioNoche());
        reservaRepository.guardar(reserva);
    }

}






