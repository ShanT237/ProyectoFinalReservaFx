package co.edu.uniquindio.proyectofinalhotelfx.Servicios;

import co.edu.uniquindio.proyectofinalhotelfx.Controladores.ControladorPrincipal;
import co.edu.uniquindio.proyectofinalhotelfx.Controladores.ControladoresCliente.CodigoVerificacion;
import co.edu.uniquindio.proyectofinalhotelfx.Modelo.Entidades.Alojamiento;
import co.edu.uniquindio.proyectofinalhotelfx.Modelo.Enums.Ciudad;
import co.edu.uniquindio.proyectofinalhotelfx.Modelo.Enums.ServiciosIncluidos;
import co.edu.uniquindio.proyectofinalhotelfx.Modelo.Enums.TipoAlojamiento;
import javafx.scene.image.Image;

import java.util.List;

public interface IPlataforma {

    /*
    /Metodos ADM
     */
    public  boolean loginAdm(String correo, String password);
    public boolean loginCliente(String correo, String password) throws Exception;
    public void bloquearCliente(String idUsuario) throws Exception;
    public void verActividadesCliente(String idUsuario);

    public void registrarAlojamiento(String nombre, Ciudad ciudad, String descripcion, double precioPorNocheBase, String imagen, List<ServiciosIncluidos> serviciosIncluidos, int capacidadPersonas, int numeroHabitaciones, boolean admiteMascotas, TipoAlojamiento tipoAlojamiento);
    public void eliminarAlojamiento(String idAlojamiento);
    public void actualizarAlojamiento(String idAlojamiento, String nombre, Ciudad ciudad, String descripcion, double precioPorNocheBase, String imagen, List<ServiciosIncluidos> serviciosIncluidos, int capacidadPersonas, int numeroHabitaciones, boolean admiteMascotas, TipoAlojamiento tipoAlojamiento);
    public List<Alojamiento> obtenerListaAlojamientos();

    public void registrarReserva();
    public void registrarOferta();
    public void registrarCliente(String nombre, String cedula, String telefono, String correo, String password, String confirmarPassword) throws Exception;

    public  boolean validarCodigoVerificacion(String correo, String codigoIngresado);
    public void recuperarContrasena(String correo, String nuevaContrasena) throws Exception;

    boolean existeUsuarioPorCorreo(String correo);
    String generarCodigoVerificacion(String correo);
    public void actualizarContrasena(String correo, String nuevaContrasena, String confirmarPassword, String codigoIngresado) throws Exception ;



}

