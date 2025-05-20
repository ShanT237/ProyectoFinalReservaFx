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

    /*
    /Metodos ADM
     */
    public Administrador loginAdm(String correo, String password);
    public Cliente loginCliente(String correo, String password) throws Exception;
    public void bloquearCliente(String idUsuario) throws Exception;
    public void verActividadesCliente(String idUsuario);

    public void registrarAlojamiento(String nombre, Ciudad ciudad, String descripcion, double precioPorNocheBase, String imagen, List<ServiciosIncluidos> serviciosIncluidos, int capacidadPersonas, int numeroHabitaciones, boolean admiteMascotas, TipoAlojamiento tipoAlojamiento);
    public void eliminarAlojamiento(String idAlojamiento);
    public void actualizarAlojamiento(String idAlojamiento, String nombre, Ciudad ciudad, String descripcion, double precioPorNocheBase, String imagen, List<ServiciosIncluidos> serviciosIncluidos, int capacidadPersonas, int numeroHabitaciones, boolean admiteMascotas, TipoAlojamiento tipoAlojamiento);
    public List<Alojamiento> obtenerListaAlojamientos();

    public void registrarReserva();
    public void registrarOferta(Ciudad ciudad, TipoAlojamiento tipoAlojamiento, String id, String nombre, String descripcion, LocalDateTime fechaInicio, LocalDateTime fechaFin, List<Alojamiento> alojamientos, boolean esGlobal, boolean activa, OfertaTipo tipoOferta, int nochesMinimas, double porcentajeDescuento);
    public void eliminarOferta(String idOferta);
    public void actualizarOferta(String idOferta, String nombre, Ciudad ciudad, TipoAlojamiento tipoAlojamiento, String descripcion, LocalDateTime fechaInicio, LocalDateTime fechaFin, List<Alojamiento> alojamientos, boolean esGlobal, boolean activa, OfertaTipo tipoOferta, int nochesMinimas, double porcentajeDescuento);
    public void registrarCliente(String nombre, String cedula, String telefono, String correo, String password, String confirmarPassword) throws Exception;

    public  boolean validarCodigoVerificacion(String correo, String codigoIngresado);
    public void recuperarContrasena(String correo) throws Exception;
    public void actualizarContrasena(String correo, String nuevaContrasena, String confirmarPassword, String codigoIngresado) throws Exception ;



}

