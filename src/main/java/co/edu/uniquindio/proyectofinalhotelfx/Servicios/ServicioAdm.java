package co.edu.uniquindio.proyectofinalhotelfx.Servicios;


import co.edu.uniquindio.proyectofinalhotelfx.Modelo.Entidades.Administrador;
import co.edu.uniquindio.proyectofinalhotelfx.Modelo.Entidades.Alojamiento;
import co.edu.uniquindio.proyectofinalhotelfx.Modelo.Entidades.Oferta;
import co.edu.uniquindio.proyectofinalhotelfx.Modelo.Entidades.Review;
import co.edu.uniquindio.proyectofinalhotelfx.Modelo.Enums.Ciudad;
import co.edu.uniquindio.proyectofinalhotelfx.Modelo.Enums.ServiciosIncluidos;
import co.edu.uniquindio.proyectofinalhotelfx.Modelo.Enums.TipoAlojamiento;
import co.edu.uniquindio.proyectofinalhotelfx.Singleton.SesionAdm;
import javafx.scene.image.Image;
import lombok.Builder;

import java.util.List;

@Builder
public class ServicioAdm {

    private final ServicioAlojamiento servicioAlojamiento;
    private final ServicioReserva servicioReserva;
    private final ServicioCliente servicioCliente;


    SesionAdm sesionAdm = SesionAdm.instancia();

    public boolean loginAdm(String correo, String password){
        if(sesionAdm.getUsuario().getCorreo().equals(correo) && sesionAdm.getUsuario().getPassword().equals(password)){
            return true;
        }
        return false;
    }



    /*
    Metodos gestionar usuario
     */

    public void bloquearCuentaCliente(String idUsuario) throws Exception {
       servicioCliente.bloquearUsuario(idUsuario);
    }
    public void verActividadesDeCliente(String idUsuario){
        servicioReserva.obtenerReservasPorCliente(idUsuario);

    }

    /*
    GestionarAlojamientos
     */

    public void registrarAlojamiento(String nombre, Ciudad ciudad, String descripcion, double precioPorNocheBase, int capacidadMaxima, Image imagen, List<ServiciosIncluidos> serviciosIncluidos, int capacidadPersonas, int numeroHabitaciones, boolean admiteMascotas, TipoAlojamiento tipoAlojamiento){

    }
    public void actualizarAlojamiento(String idAlojamiento, Alojamiento nuevoAlojamiento){


    }
    public void eliminarAlojamiento(String idAlojamiento){


    }

    public void agregarOfertaEspecial(String idAlojamiento, Oferta oferta){

    }
    public void actualizarOfertaEspecial(String idAlojamiento, Oferta nuevaOferta){

    }
    public void eliminarOfertaEspecial(String idAlojamiento, String idOferta){

    }
    /*
    Gestionar Reservas
     */


}
