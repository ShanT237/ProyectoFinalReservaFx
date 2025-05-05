package co.edu.uniquindio.proyectofinalhotelfx.Servicios;


import co.edu.uniquindio.proyectofinalhotelfx.Modelo.Entidades.Alojamiento;
import co.edu.uniquindio.proyectofinalhotelfx.Modelo.Entidades.Cliente;
import co.edu.uniquindio.proyectofinalhotelfx.Modelo.Entidades.Oferta;
import co.edu.uniquindio.proyectofinalhotelfx.Repo.AdmRepository;
import co.edu.uniquindio.proyectofinalhotelfx.Repo.AlojamientoRepository;
import co.edu.uniquindio.proyectofinalhotelfx.Repo.ClienteRepository;
import co.edu.uniquindio.proyectofinalhotelfx.Repo.ReservaRepository;
import lombok.Builder;

@Builder
public class ServicioAdm {

    private final ServicioAlojamiento servicioAlojamiento;
    private final ServicioReserva servicioReserva;
    private final ServicioCliente clienteServicio;
    private final AdmRepository admRepository;


    public void loginAdm(){
        admExiste();
    }

    public void admExiste(){

    }

    /*
    Metodos gestionar usuario
     */

    public void bloquearCuentaCliente(String idUsuario){
        Cliente cliente = clienteServicio.ge   .buscarPorCedula(idUsuario);
        clienteRepository.agregarUsuarioBloqueado(cliente);
        clienteRepository.eliminar(idUsuario);

    }
    public void verActividadesDeCliente(String idUsuario){
        reservaRepository.obten

    }
    public void buscarUsuario(){

    }

    /*
    GestionarAlojamientos
     */

    public void registrarAlojamiento(Alojamiento alojamiento){

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
