package co.edu.uniquindio.proyectofinalhotelfx.Servicios;

import co.edu.uniquindio.proyectofinalhotelfx.Controladores.ControladorPrincipal;
import co.edu.uniquindio.proyectofinalhotelfx.Controladores.ControladoresCliente.CodigoVerificacion;
import co.edu.uniquindio.proyectofinalhotelfx.Modelo.Entidades.Administrador;
import co.edu.uniquindio.proyectofinalhotelfx.Modelo.Entidades.Alojamiento;
import co.edu.uniquindio.proyectofinalhotelfx.Modelo.Entidades.Cliente;
import co.edu.uniquindio.proyectofinalhotelfx.Modelo.Enums.Ciudad;
import co.edu.uniquindio.proyectofinalhotelfx.Modelo.Enums.OfertaTipo;
import co.edu.uniquindio.proyectofinalhotelfx.Modelo.Enums.ServiciosIncluidos;
import co.edu.uniquindio.proyectofinalhotelfx.Modelo.Enums.TipoAlojamiento;
import javafx.scene.image.Image;

import java.time.LocalDateTime;
import java.util.List;

public interface IPlataforma {

    // -------------------------------
    // 🔐 Autenticación y usuarios
    // -------------------------------

    /**
     * Permite el inicio de sesión del administrador del sistema.
     * @param correo Correo del administrador.
     * @param password Contraseña del administrador.
     * @return Objeto Administrador si las credenciales son válidas.
     */
    Administrador loginAdm(String correo, String password);

    /**
     * Permite el inicio de sesión de un cliente registrado.
     * @param correo Correo del cliente.
     * @param password Contraseña del cliente.
     * @return Cliente autenticado si las credenciales son correctas.
     * @throws Exception si los datos son inválidos o no se encuentra el cliente.
     */
    Cliente loginCliente(String correo, String password) throws Exception;

    /**
     * Bloquea a un cliente por su ID.
     * @param idUsuario ID del cliente a bloquear.
     * @throws Exception si no se puede bloquear.
     */
    void bloquearCliente(String idUsuario) throws Exception;

    /**
     * Visualiza las actividades (reservas, acciones, historial) de un cliente.
     * @param idUsuario ID del cliente.
     */
    void verActividadesCliente(String idUsuario);


    // -------------------------------
    // 🏨 Gestión de Alojamientos
    // -------------------------------

    /**
     * Registra un nuevo alojamiento en el sistema.
     */
    void registrarAlojamiento(String nombre, Ciudad ciudad, String descripcion, double precioPorNocheBase,
                              String imagen, List<ServiciosIncluidos> serviciosIncluidos, int capacidadPersonas,
                              int numeroHabitaciones, boolean admiteMascotas, TipoAlojamiento tipoAlojamiento);

    /**
     * Elimina un alojamiento según su ID.
     * @param idAlojamiento ID del alojamiento.
     */
    void eliminarAlojamiento(String idAlojamiento);

    /**
     * Actualiza los datos de un alojamiento existente.
     */
    void actualizarAlojamiento(String idAlojamiento, String nombre, Ciudad ciudad, String descripcion,
                               double precioPorNocheBase, String imagen, List<ServiciosIncluidos> serviciosIncluidos,
                               int capacidadPersonas, int numeroHabitaciones, boolean admiteMascotas,
                               TipoAlojamiento tipoAlojamiento);

    /**
     * Retorna la lista de alojamientos registrados.
     */
    List<Alojamiento> obtenerListaAlojamientos();


    // -------------------------------
    // 📆 Gestión de Reservas
    // -------------------------------

    /**
     * Registra una nueva reserva (solo definición, falta parametrización).
     */
    void registrarReserva();


    // -------------------------------
    // 🎯 Gestión de Ofertas
    // -------------------------------

    /**
     * Registra una nueva oferta con base en parámetros personalizados.
     */
    void registrarOferta(Ciudad ciudad, TipoAlojamiento tipoAlojamiento, String id, String nombre,
                         String descripcion, LocalDateTime fechaInicio, LocalDateTime fechaFin, boolean esGlobal, OfertaTipo tipoOferta,
                         int nochesMinimas, double porcentajeDescuento);

    /**
     * Elimina una oferta del sistema por su ID.
     */
    void eliminarOferta(String idOferta);

    /**
     * Actualiza una oferta existente.
     */
    void actualizarOferta(String idOferta, String nombre, Ciudad ciudad, TipoAlojamiento tipoAlojamiento,
                          String descripcion, LocalDateTime fechaInicio, LocalDateTime fechaFin, boolean esGlobal, OfertaTipo tipoOferta,
                          int nochesMinimas, double porcentajeDescuento);


    // -------------------------------
    // 👤 Gestión de Clientes
    // -------------------------------

    /**
     * Registra un nuevo cliente en el sistema.
     */
    void registrarCliente(String nombre, String cedula, String telefono, String correo,
                          String password, String confirmarPassword) throws Exception;


    // -------------------------------
    // 🔐 Recuperación de Credenciales
    // -------------------------------

    /**
     * Valida el código de verificación enviado al correo.
     */
    boolean validarCodigoVerificacion(String correo, String codigoIngresado);

    /**
     * Envía un código de recuperación al correo del usuario.
     */
    void recuperarContrasena(String correo) throws Exception;

    /**
     * Actualiza la contraseña de un usuario si el código es válido.
     */
    void actualizarContrasena(String correo, String nuevaContrasena,
                              String confirmarPassword, String codigoIngresado) throws Exception;
}




