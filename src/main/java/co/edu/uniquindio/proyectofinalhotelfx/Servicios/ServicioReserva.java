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
public class ServicioReserva{

    private final ReservaRepository reservaRepository;
    private final ServicioCliente servicioCliente;
    private final ServicioAlojamiento servicioAlojamiento;
    private final ServicioOferta servicioOferta;


    public ArrayList<Reserva> listarReservas() {
        return reservaRepository.listarReservas();
    }

    public void agregarReserva(String idCliente, String idAlojamiento, LocalDateTime fechaInicial, LocalDateTime fechaFinal, int numeroHuespedes, double subtotal, LocalDateTime fechaCreacion) throws Exception {
        validarDatos(idCliente, idAlojamiento, fechaInicial, fechaFinal, numeroHuespedes, subtotal, fechaCreacion);

        Cliente cliente = servicioCliente.buscarCliente(idCliente);
        Alojamiento alojamiento = servicioAlojamiento.getAlojamientoRepository().buscarPorId(idAlojamiento);

        Reserva reservaTemp = Reserva.builder()
                .cliente(cliente)
                .alojamiento(alojamiento)
                .numeroHuespedes(numeroHuespedes)
                .fechaInicio(fechaInicial)
                .fechaFin(fechaFinal)
                .total(subtotal)
                .build();

        double totalConDescuento = aplicarDescuento(subtotal, reservaTemp);


        UUID idFactura = UUID.randomUUID();
        byte[] imagenQR = GeneradorQR.generarQRComoBytes(idFactura.toString(), 300, 300);
        DataSource ds = new ByteArrayDataSource(imagenQR, "image/png");

        String codigoQRBase64 = Base64.getEncoder().encodeToString(imagenQR);
        Factura factura = crearFactura(subtotal, totalConDescuento, codigoQRBase64);
        factura.setId(idFactura);


        Reserva reservaFinal = crearReserva(cliente, alojamiento, numeroHuespedes, fechaInicial, fechaFinal, factura, totalConDescuento);
        agregarReservaAlSistema(reservaFinal);

        Notificacion.enviarNotificacionImagen(
                cliente.getCorreo(),
                "Aquí tiene su factura de la reserva realizada",
                "Reserva:\n" + reservaFinal.toString() + "\nFactura:\n" + factura.toString(),
                ds
        );
    }


    public void agregarReservaAlSistema(Reserva reserva) throws Exception {
        reservaRepository.guardar(reserva);
    }

    public void validarDatos(String idAlojamiento, String cliente, LocalDateTime fechaInicio, LocalDateTime fechaFin, int numeroHuespedes, double total, LocalDateTime fechaCreacion) throws Exception {
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

    // CREAR RESERVA SIMPLE
    public Reserva crearReserva(Cliente cliente, Alojamiento alojamiento, int numeroHuespedes, LocalDateTime fechaInicial, LocalDateTime fechaFinal, Factura factura, double precioFinal) throws Exception {
        // Crear la reserva
        return Reserva.builder()
                .codigo(UUID.randomUUID())
                .cliente(cliente)
                .alojamiento(alojamiento)
                .numeroHuespedes(numeroHuespedes)
                .fechaInicio(fechaInicial)
                .fechaFin(fechaFinal)
                .factura(factura)
                .total(precioFinal)
                .fechaCreacion(LocalDateTime.now())
                .EstadoReserva(true)
                .fechaReserva(LocalDate.now())
                .build();
    }

    public Factura crearFactura(double subtotal, double totalConDescuento, String codigoQR) {
        return Factura.builder()
                .id(UUID.randomUUID())
                .subtotal(subtotal)
                .total(totalConDescuento)
                .fecha(LocalDate.now())
                .codigoQR(codigoQR)
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

    public double aplicarDescuento(double precioOriginal, Reserva reserva) {
        double precioConDescuento = precioOriginal;

        for (Oferta oferta : servicioOferta.getOfertaRepository().obtenerTodos()) {
            if (oferta.estaVigente(reserva.getFechaInicio())
                    && (oferta.isEsGlobal() || oferta.getAlojamientosAplicables().contains(reserva.getAlojamiento()))) {
                precioConDescuento = oferta.aplicarDescuento(precioConDescuento, reserva);
            }
        }

        return precioConDescuento;
    }

    public void cancelarReserva(UUID idReserva) throws Exception {
        Reserva reserva = reservaRepository.buscarPorId(idReserva);
        Cliente cliente = reserva.getCliente();
        cliente.getBilletera().setSaldo((float) (cliente.getBilletera().getSaldo() + reserva.getTotal()));
        reserva.setEstadoReserva(false);
        reservaRepository.actualizar(reserva);
    }




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

    public void reservarAlojamiento(String cedula, Alojamiento alojamiento) {
    }

    public void obtenerReservasPorCliente(String idUsuario) {
    }

    public void eliminarResena(UUID idResena) throws Exception {
        reservaRepository.eliminarResena(idResena);
    }
}






