package co.edu.uniquindio.proyectofinalhotelfx.Servicios;

import co.edu.uniquindio.proyectofinalhotelfx.Modelo.Entidades.*;
import co.edu.uniquindio.proyectofinalhotelfx.Modelo.Enums.*;
import java.time.LocalDateTime;
import java.util.List;

public interface IPlataforma {

    // -------------------------------
    // 🔐 Autenticación y usuarios
    // -------------------------------

    Administrador loginAdm(String correo, String password);

    Cliente loginCliente(String correo, String password) throws Exception;

    void bloquearCliente(String idUsuario) throws Exception;

    void verActividadesCliente(String idUsuario);

    // -------------------------------
    // 🏨 Gestión de Alojamientos
    // -------------------------------

    void registrarHabitacion(String idhotel, int numero, int capacidad, double precioPorNoche, List<ServiciosIncluidos> serviciosIncluidos, TipoHabitacionHotel tipoHabitacionHotel, String imagen) throws Exception;

    void eliminarHabitacion(String idHotel, int idHabitacion) throws Exception;

    void actualizarHabitacion(String idHabitacion, int numero, int capacidad, double precioPorNoche, List<ServiciosIncluidos> serviciosIncluidos, TipoHabitacionHotel tipoHabitacionHotel, String imagen);

    List<Habitacion> obtenerListaHabitaciones();

    void registrarAlojamiento(String nombre, Ciudad ciudad, String descripcion, double precioPorNocheBase,
                              String imagen, List<ServiciosIncluidos> serviciosIncluidos, int capacidadPersonas,
                              int numeroHabitaciones, boolean admiteMascotas, TipoAlojamiento tipoAlojamiento);

    void eliminarAlojamiento(String idAlojamiento);

    void actualizarAlojamiento(String idAlojamiento, String nombre, Ciudad ciudad, String descripcion,
                               double precioPorNocheBase, String imagen, List<ServiciosIncluidos> serviciosIncluidos,
                               int capacidadPersonas, int numeroHabitaciones, boolean admiteMascotas,
                               TipoAlojamiento tipoAlojamiento);

    List<Alojamiento> obtenerListaAlojamientos();

    // -------------------------------
    // 📆 Gestión de Reservas
    // -------------------------------

    void registrarReserva(String codigoReserva, LocalDateTime fechaReserva, LocalDateTime fechaInicio, LocalDateTime fechaFin,
                          String idCliente, String idAlojamiento, String idOferta, String nombreCliente,
                          String nombreAlojamiento, String nombreOferta, double precioTotal, String imagenAlojamiento) throws Exception;

    // -------------------------------
    // 🎯 Gestión de Ofertas
    // -------------------------------

    void registrarOferta(Ciudad ciudad, TipoAlojamiento tipoAlojamiento, String id, String nombre,
                         String descripcion, LocalDateTime fechaInicio, LocalDateTime fechaFin, boolean esGlobal, OfertaTipo tipoOferta,
                         int nochesMinimas, double porcentajeDescuento, String imagen) throws Exception;

    void eliminarOferta(String idOferta);

    void actualizarOferta(String idOferta, String nombre, Ciudad ciudad, TipoAlojamiento tipoAlojamiento,
                          String descripcion, LocalDateTime fechaInicio, LocalDateTime fechaFin, boolean esGlobal, OfertaTipo tipoOferta,
                          int nochesMinimas, double porcentajeDescuento, String imagen) throws Exception;

    // -------------------------------
    // 👤 Gestión de Clientes
    // -------------------------------

    void registrarCliente(String nombre, String cedula, String telefono, String correo,
                          String password, String confirmarPassword) throws Exception;

    // -------------------------------
    // 🔐 Recuperación de Credenciales
    // -------------------------------



    boolean validarCodigoVerificacion(String correo, String codigoIngresado);

    void recuperarContrasena(String correo) throws Exception;

    void actualizarContrasena(String correo, String nuevaContrasena,
                              String confirmarPassword, String codigoIngresado) throws Exception;



    void recagarBilletera(String idCliente, float monto) throws Exception;
    double consultarSaldo(String cedula) throws Exception;

    public void reservarAlojamiento(Cliente cliente, Alojamiento alojamiento) throws Exception;
}
