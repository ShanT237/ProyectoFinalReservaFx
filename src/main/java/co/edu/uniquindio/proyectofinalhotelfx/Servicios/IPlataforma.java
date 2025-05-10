package co.edu.uniquindio.proyectofinalhotelfx.Servicios;

import co.edu.uniquindio.proyectofinalhotelfx.Controladores.ControladorPrincipal;
import co.edu.uniquindio.proyectofinalhotelfx.Controladores.ControladoresCliente.CodigoVerificacion;
import co.edu.uniquindio.proyectofinalhotelfx.Modelo.Enums.Ciudad;
import co.edu.uniquindio.proyectofinalhotelfx.Modelo.Enums.ServiciosIncluidos;
import co.edu.uniquindio.proyectofinalhotelfx.Modelo.Enums.TipoAlojamiento;
import javafx.scene.image.Image;

import java.util.List;

public interface IPlataforma {

    /*
    /Metodos ADM
     */
    public void loginAdm(String correo, String password);
    public void bloquearCliente(String idUsuario) throws Exception;
    public void verActividadesCliente(String idUsuario);
    public void registrarAlojamiento(String nombre, Ciudad ciudad, String descripcion, double precioPorNocheBase, int capacidadMaxima, Image imagen, List<ServiciosIncluidos> serviciosIncluidos, int capacidadPersonas, int numeroHabitaciones, boolean admiteMascotas, TipoAlojamiento tipoAlojamiento);
    public void eliminarAlojamiento(String idAlojamiento);

    public void registrarReserva();
    public void registrarOferta();
    public void registrarCliente(String nombre, String id, String telefono, String  email, String password, String confirmarPassword) throws Exception;

    public boolean validarCodigoVerificacion(String correo, String codigoIngresado);


}

