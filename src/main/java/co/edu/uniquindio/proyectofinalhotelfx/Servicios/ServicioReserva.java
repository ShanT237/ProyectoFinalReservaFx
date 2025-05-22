package co.edu.uniquindio.proyectofinalhotelfx.Servicios;

import co.edu.uniquindio.proyectofinalhotelfx.Modelo.Entidades.*;
import co.edu.uniquindio.proyectofinalhotelfx.Modelo.Enums.Ciudad;
import co.edu.uniquindio.proyectofinalhotelfx.Notificacion.Notificacion;
import co.edu.uniquindio.proyectofinalhotelfx.Repo.ClienteRepository;
import co.edu.uniquindio.proyectofinalhotelfx.Repo.ReservaRepository;
import co.edu.uniquindio.proyectofinalhotelfx.utils.GeneradorQR;
import jakarta.activation.DataSource;
import jakarta.mail.util.ByteArrayDataSource;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

@Getter
@Builder
public class ServicioReserva{

    private final ReservaRepository reservaRepository;
    private final ClienteRepository clienteRepository;

    public ServicioReserva(ReservaRepository reservaRepository, ClienteRepository clienteRepository) {
        this.reservaRepository = reservaRepository;
        this.clienteRepository = clienteRepository;
    }



    public ArrayList<Reserva> listarReservas() {
        return reservaRepository.listarReservas();
    }


    public void agregarReserva(Alojamiento alojamiento, Cliente cliente, int numeroHuespedes,
                               LocalDate fechainicial,  int diasReserva, ArrayList<Oferta> ofertas) throws Exception {

        StringBuilder e = new StringBuilder();

        if (alojamiento == null) e.append("Seleccione un alojamiento. ");
        if (numeroHuespedes <= 0) e.append("Seleccione un número de huéspedes válido. ");
        if (fechainicial == null) e.append("Seleccione una fecha inicial. ");
        if (diasReserva <= 0) e.append("Seleccione cuántos días reservar. ");

        if (!e.isEmpty()) {
            throw new Exception(e + "Verifique y rellene los campos correctamente.");
        }

        if (fechainicial.isBefore(LocalDate.now())) {
            throw new Exception("La fecha inicial no puede ser anterior a hoy.");
        }

        // Guardar fechas de reserva
        ArrayList<LocalDate> fechas = guardarFechasAgregadas(fechainicial, diasReserva);

        // Verificar disponibilidad
        boolean disponible = verificarDisponibilidadReservas(alojamiento, fechas);
        if (!disponible) {
            throw new Exception("Este alojamiento no está disponible en el rango de fechas seleccionado.");
        }

        if (numeroHuespedes > alojamiento.getCapacidadHuespedes()) {
            throw new Exception("Este alojamiento no tiene capacidad para tantas personas.");
        }

        // Calcular precios
        float precioBase = alojamiento.calcularPrecioTotal(diasReserva);
        float precioFinal = verificarDescuento(alojamiento, precioBase, numeroHuespedes, diasReserva, fechas, ofertas);

        // Verificar saldo y descontar
        if (!clienteTieneSaldoSuficiente(cliente, precioFinal)) {
            throw new Exception("Saldo insuficiente para realizar la reserva.");
        }
        cliente.cobrarBilletera(precioFinal);

        // Crear fecha de inicio y fin en LocalDateTime
        LocalDateTime fechaInicio = fechainicial.atStartOfDay();
        LocalDateTime fechaFin = fechainicial.plusDays(diasReserva - 1).atTime(23, 59);

        // Crear factura
        Factura factura = crearFactura(LocalDateTime.now(), precioBase, precioFinal, UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID());

        // Crear reserva
        Reserva reserva = crearReserva(cliente, alojamiento, numeroHuespedes, fechaInicio, fechaFin, factura, precioFinal);

        // Guardar reserva
        reservaRepository.agregarReserva(reserva);
        cliente.agregarReserva(reserva);

        // Generar QR y enviar notificación
        byte[] imagenQR = GeneradorQR.generarQRComoBytes(factura.getId().toString(), 300, 300);
        DataSource ds = new ByteArrayDataSource(imagenQR, "image/png");

        Notificacion.enviarNotificacionImagen(
                cliente.getCorreo(),
                "Aquí tiene su factura de la reserva realizada",
                "Reserva:\n" + reserva.toString() + "\nFactura:\n" + factura.toString(),
                ds
        );
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


    public Map<Ciudad, Alojamiento> alojamientosMasPopularesCiudad(){
        ArrayList<Reserva>reservas = listarReservas();
        Map<Alojamiento, Integer> alojamientos = new HashMap<>();
        for (Reserva reserva : reservas) {
            Alojamiento alojamiento = reserva.getAlojamiento();
            alojamientos.put(alojamiento,alojamientos.getOrDefault(alojamiento,0)+1);
        }
        Map<Ciudad, Alojamiento> popularesPorCiudad = new HashMap<>();
        Map<Ciudad, Integer> maxReservasPorCiudad = new HashMap<>();
        for (Map.Entry<Alojamiento, Integer> entry : alojamientos.entrySet()) {
            Alojamiento alojamiento = entry.getKey();
            Ciudad ciudad = alojamiento.getCiudad();
            int cantidad = entry.getValue();

            if (!popularesPorCiudad.containsKey(ciudad)|| cantidad > maxReservasPorCiudad.get(ciudad)) {
                popularesPorCiudad.put(ciudad,alojamiento);
                maxReservasPorCiudad.put(ciudad,cantidad);
            }
        }
        return popularesPorCiudad;
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
    public Factura crearFactura(LocalDateTime fecha, double precioBase, double precioFinal, UUID idReserva, UUID idCliente, UUID idAlojamiento) {
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

    public void reservarAlojamiento(String cedula, Alojamiento alojamiento, int numeroHuespedes,
                                    LocalDate fechaInicial, int diasReserva, ArrayList<Oferta> ofertas) throws Exception {
        // Obtener cliente por cédula
        Cliente cliente = getClienteRepository().buscarPorCedula(cedula);
        if (cliente == null) {
            throw new Exception("Cliente no encontrado con cédula: " + cedula);
        }

        // Llamar al método que agrega la reserva
        agregarReserva(alojamiento, cliente, numeroHuespedes, fechaInicial, diasReserva, ofertas);
    }
}

