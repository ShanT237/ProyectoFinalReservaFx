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


    public ArrayList<Reserva> listarReservas() {
        return reservaRepository.listarReservas();
    }


    public void agregarReserva(String idCliente, String idAlojamiento, LocalDateTime fechainicial, LocalDateTime fechafin, int numeroHuespedes, double total, LocalDateTime fechaCreacion) throws Exception {

        validarDatos(idCliente, idAlojamiento, fechainicial, fechafin, numeroHuespedes, total, fechaCreacion);
        Cliente cliente = servicioCliente.buscarCliente(idCliente);
        Alojamiento alojamiento = servicioAlojamiento.getAlojamientoRepository().buscarPorId(idAlojamiento);

        Factura factura = crearFactura();

        Reserva reserva = crearReserva( cliente,  alojamiento,  numeroHuespedes,  fechainicial,  fechafin,  factura,  precioFinal);
        byte[] imagenQR = GeneradorQR.generarQRComoBytes(factura.getId().toString(), 300, 300);
        DataSource ds = new ByteArrayDataSource(imagenQR, "image/png");

        Notificacion.enviarNotificacionImagen(
                cliente.getCorreo(),
                "Aquí tiene su factura de la reserva realizada",
                "Reserva:\n" + reserva.toString() + "\nFactura:\n" + factura.toString(),
                ds
        );
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

    public ArrayList<LocalDate> guardarFechasAgregadas(LocalDate fechaInicial,int dias){
        ArrayList<LocalDate> fechas = new ArrayList<>();
        for (int i = 0; i < dias; i++) {
            fechas.add(fechaInicial);
            fechaInicial = fechaInicial.plusDays(1);
        }
        return fechas;
    }

    public boolean verificarDisponibilidadReservas(Alojamiento alojamiento, ArrayList<LocalDate> fechas) {
        ArrayList<Reserva> reservas = listarReservas();

        for (Reserva reserva : reservas) {
            if (reserva.getAlojamiento().equals(alojamiento)) {

                LocalDate inicioReserva = reserva.getFechaInicio().toLocalDate();
                LocalDate finReserva = reserva.getFechaFin().toLocalDate();

                for (LocalDate fecha : fechas) {
                    // Si la fecha nueva está dentro del rango de la reserva existente
                    if (!fecha.isBefore(inicioReserva) && !fecha.isAfter(finReserva)) {
                        return false; // Hay conflicto de fechas
                    }
                }
            }
        }

        return true; // No hay conflictos
    }

    public float verificarDescuento(Alojamiento alojamiento,float precio, int cantidadhuespedes, int dias, ArrayList<LocalDate>fechas, ArrayList<Oferta> ofertas) {
        float preciofinal = 0;
        for (Oferta oferta : ofertas) {
            float flag = oferta.verificarDescuento(alojamiento,precio,cantidadhuespedes,dias,fechas);
            if(flag < precio && flag> preciofinal)preciofinal = flag;
        }
        return preciofinal == 0? precio : preciofinal;
    }

    public void cancelarReserva(Reserva reserva) throws Exception {
        if (reserva == null) {
            throw new Exception("Seleccione una reserva para cancelarla.");
        }

        // Compara solo fechas, sin hora
        LocalDate hoy = LocalDate.now();
        LocalDate fechaInicio = reserva.getFechaInicio().toLocalDate();

        // Por ejemplo, impedir cancelación si la reserva empieza hoy o ya pasó
        if (!fechaInicio.isAfter(hoy)) {
            throw new Exception("No puede cancelar una reserva que ya ha iniciado o está en curso.");
        }

        // Cancelar y hacer el reembolso
        reservaRepository.eliminarReserva(reserva);
        reserva.getCliente().eliminarReserva(reserva);
        reserva.getCliente().recargarBilletera(String.valueOf(reserva.getFactura().getTotal()));

    }


    // CREAR RESERVA SIMPLE
    public Reserva crearReserva(Cliente cliente, Alojamiento alojamiento, int numeroHuespedes, LocalDateTime fechainicial, LocalDateTime fechafin, Factura factura, double precioFinal) throws Exception {
        // Crear la reserva
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

    //
    public Factura crearFactura(double precioBase, double precioFinal, UUID idReserva, UUID idCliente, UUID idAlojamiento) {
        // Crear la factura
        return Factura.builder()
                .fecha(LocalDate.now())
                .subtotal(precioBase)
                .total(precioFinal)
                .id(UUID.randomUUID())
                .build();
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






