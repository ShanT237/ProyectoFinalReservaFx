package co.edu.uniquindio.proyectofinalhotelfx.Servicios;

import co.edu.uniquindio.proyectofinalhotelfx.Modelo.Entidades.Administrador;
import co.edu.uniquindio.proyectofinalhotelfx.Modelo.Entidades.Alojamiento;
import co.edu.uniquindio.proyectofinalhotelfx.Modelo.Entidades.Cliente;
import co.edu.uniquindio.proyectofinalhotelfx.Modelo.Entidades.Habitacion;
import co.edu.uniquindio.proyectofinalhotelfx.Modelo.Enums.*;
import co.edu.uniquindio.proyectofinalhotelfx.Repo.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class Plataforma implements IPlataforma {

    private ServicioAdm servicioAdm;
    private ServicioCliente servicioCliente;
    private ServicioReserva servicioReserva;
    private ServicioAlojamiento serviciosAlojamiento;
    private ServicioOferta servicioOferta;
    private ServicioBilleteraVirtual servicioBilleteraVirtual;

    private AlojamientoRepository alojamientoRepository;
    private ClienteRepository clienteRepository;
    private ReservaRepository reservaRepository;
    private OfertaRepository ofertaRepository;

    public Plataforma() {
        // ==============================
        // INSTANCIAS DE REPOSITORIOS
        // ==============================
        this.alojamientoRepository = new AlojamientoRepository();
        this.clienteRepository = new ClienteRepository();
        this.reservaRepository = new ReservaRepository();
        this.ofertaRepository = new OfertaRepository();

        // ==============================
        // INSTANCIAS DE SERVICIOS
        // ==============================
        this.serviciosAlojamiento = ServicioAlojamiento.builder()
                .alojamientoRepository(alojamientoRepository)
                .build();

        this.servicioBilleteraVirtual = new ServicioBilleteraVirtual(clienteRepository);

        this.servicioOferta = ServicioOferta.builder()
                .ofertaRepository(ofertaRepository)
                .servicioAlojamiento(serviciosAlojamiento)
                .build();

        this.servicioReserva = ServicioReserva.builder()
                .reservaRepository(reservaRepository)
                .servicioCliente(servicioCliente)
                .servicioAlojamiento(serviciosAlojamiento)
                .servicioOferta(servicioOferta)
                .build();

        this.servicioCliente = ServicioCliente.builder()
                .clienteRepository(clienteRepository)
                .servicioAlojamiento(serviciosAlojamiento)
                .servicioBilleteraVirtual(servicioBilleteraVirtual)
                .servicioReserva(servicioReserva)
                .build();
        this.servicioAdm = ServicioAdm.builder()
                .servicioAlojamiento(serviciosAlojamiento)
                .servicioReserva(servicioReserva)
                .servicioCliente(servicioCliente)
                .servicioOferta(servicioOferta)
                .build();

    }

    // ==============================
    // MÉTODOS DEL ADMINISTRADOR
    // ==============================

    @Override
    public Administrador loginAdm(String correo, String contrasena) {
        return servicioAdm.loginAdm(correo, contrasena);
    }

    @Override
    public void bloquearCliente(String idUsuario) throws Exception {
        servicioAdm.bloquearCuentaCliente(idUsuario);
    }

    @Override
    public void verActividadesCliente(String idUsuario) {
        servicioAdm.verActividadesDeCliente(idUsuario);
    }



    // ==============================
    // MÉTODOS DEL CLIENTE
    // ==============================

    @Override
    public Cliente loginCliente(String correo, String password) throws Exception {
        return servicioCliente.iniciarSesion(correo, password);
    }

    @Override
    public void registrarCliente(String nombre, String cedula, String telefono, String correo,
                                 String password, String confirmarPassword) throws Exception {
        servicioCliente.registrarCliente(nombre, cedula, telefono, correo, password, confirmarPassword);
    }

    @Override
    public boolean validarCodigoVerificacion(String correo, String codigoIngresado) {
        return servicioCliente.validarCodigoVerificacion(correo, codigoIngresado);
    }

    @Override
    public void recuperarContrasena(String correo) throws Exception {
        servicioCliente.recuperarContrasena(correo);
    }

    @Override
    public void actualizarContrasena(String correo, String nuevaContrasena,
                                     String confirmarPassword, String codigoIngresado) throws Exception {
        servicioCliente.actualizarContrasena(correo, nuevaContrasena, confirmarPassword, codigoIngresado);
    }



    @Override
    public float consultarSaldo(String cedula) throws Exception {
        return servicioCliente.consultarSaldo(cedula);
    }

    // ==============================
    // GESTIÓN DE ALOJAMIENTOS
    // ==============================
    @Override
    public void registrarHabitacion(String idhotel, int numero, int capacidad, double precioPorNoche, List<ServiciosIncluidos> serviciosIncluidos, TipoHabitacionHotel tipoHabitacionHotel, String imagen) throws Exception {
       servicioAdm.registrarHabitacion(idhotel, numero, capacidad, precioPorNoche, serviciosIncluidos, tipoHabitacionHotel, imagen);
    }

    @Override
    public void eliminarHabitacion(String idHotel, int idHabitacion) throws Exception {
        servicioAdm.eliminarHabitacionHotel(idHotel, idHabitacion);

    }

    @Override
    public void actualizarHabitacion(String idHabitacion, int numero, int capacidad, double precioPorNoche, List<ServiciosIncluidos> serviciosIncluidos, TipoHabitacionHotel tipoHabitacionHotel, String imagen) {

    }

    @Override
    public List<Habitacion> obtenerListaHabitaciones() {
        return List.of();
    }

    @Override
    public void registrarAlojamiento(String nombre, Ciudad ciudad, String descripcion,
                                     double precioPorNocheBase, String imagen,
                                     List<ServiciosIncluidos> serviciosIncluidos,
                                     int capacidadPersonas, int numeroHabitaciones,
                                     boolean admiteMascotas, TipoAlojamiento tipoAlojamiento) {
        servicioAdm.registrarAlojamiento(nombre, ciudad, descripcion, precioPorNocheBase,
                imagen, serviciosIncluidos, capacidadPersonas,
                numeroHabitaciones, admiteMascotas, tipoAlojamiento);
    }

    @Override
    public void eliminarAlojamiento(String idAlojamiento) {
        servicioAdm.eliminarAlojamiento(idAlojamiento);
    }

    @Override
    public void actualizarAlojamiento(String idAlojamiento, String nombre, Ciudad ciudad,
                                      String descripcion, double precioPorNocheBase, String imagen,
                                      List<ServiciosIncluidos> serviciosIncluidos,
                                      int capacidadPersonas, int numeroHabitaciones,
                                      boolean admiteMascotas, TipoAlojamiento tipoAlojamiento) {
        servicioAdm.actualizarAlojamiento(idAlojamiento, nombre, ciudad, descripcion, precioPorNocheBase,
                imagen, serviciosIncluidos, capacidadPersonas, numeroHabitaciones,
                admiteMascotas, tipoAlojamiento);
    }

    @Override
    public List<Alojamiento> obtenerListaAlojamientos() {
        return serviciosAlojamiento.obtenerTodosAlojamientos();
    }



    // ==============================
    // GESTIÓN DE RESERVAS
    // ==============================

    @Override
    public void registrarReserva(String codigoReserva, LocalDateTime fechaReserva, LocalDateTime fechaInicio, LocalDateTime fechaFin, String idCliente, String idAlojamiento, String idOferta, String nombreCliente, String nombreAlojamiento, String nombreOferta, double precioTotal, String imagenAlojamiento) throws Exception {

    }

    // ==============================
    // GESTIÓN DE OFERTAS
    // ==============================

    @Override
    public void registrarOferta(Ciudad ciudad, TipoAlojamiento tipoAlojamiento, String id,
                                String nombre, String descripcion, LocalDateTime fechaInicio,
                                LocalDateTime fechaFin, boolean esGlobal, OfertaTipo tipoOferta,
                                int nochesMinimas, double porcentajeDescuento, String imagen) throws Exception {
        servicioAdm.registrarOferta(ciudad, tipoAlojamiento, id, nombre, descripcion,
                fechaInicio, fechaFin, esGlobal, tipoOferta, nochesMinimas, porcentajeDescuento, imagen);
    }

    @Override
    public void eliminarOferta(String idOferta) {
        servicioAdm.eliminarOfertaEspecial(idOferta);
    }

    @Override
    public void actualizarOferta(String idOferta, String nombre, Ciudad ciudad,
                                 TipoAlojamiento tipoAlojamiento, String descripcion,
                                 LocalDateTime fechaInicio, LocalDateTime fechaFin,
                                 boolean esGlobal, OfertaTipo tipoOferta,
                                 int nochesMinimas, double porcentajeDescuento, String imagen) throws Exception {
        servicioAdm.actualizarOfertaEspecial(idOferta, nombre, ciudad, tipoAlojamiento,
                descripcion, fechaInicio, fechaFin, esGlobal, tipoOferta, nochesMinimas, porcentajeDescuento, imagen);
    }



    @Override
    public void recagarBilletera(String idCliente, float monto) throws Exception {
        servicioCliente.recargarBilletera(idCliente, monto);
    }

    @Override
    public void reservarAlojamiento(Cliente cliente, Alojamiento alojamiento) throws Exception {

    }

    @Override
    public void agregarReserva(String idCliente, String idAlojamiento, LocalDateTime fechaInicial, LocalDateTime fechaFinal, int numeroHuespedes, double subtotal, LocalDateTime fechaCreacion) throws Exception {
        servicioCliente.agregarReserva(idCliente, idAlojamiento, fechaInicial, fechaFinal, numeroHuespedes, subtotal, fechaCreacion);
    }

    @Override
    public void agregarReview(UUID reservaId, String comentario, int valoracion) throws Exception {
        servicioCliente.agregarReview(reservaId, comentario, valoracion);
    }

    @Override
    public void cancelarReserva(UUID idResena) throws Exception {
        servicioCliente.cancelarReserva(idResena);
    }

    @Override
    public void eliminarResena(UUID idResena) throws Exception {
        servicioCliente.eliminarResena(idResena);
    }


}