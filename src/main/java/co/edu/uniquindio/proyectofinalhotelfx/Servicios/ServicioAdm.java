package co.edu.uniquindio.proyectofinalhotelfx.Servicios;


import co.edu.uniquindio.proyectofinalhotelfx.Modelo.Entidades.Alojamiento;
import co.edu.uniquindio.proyectofinalhotelfx.Repo.AlojamientoRepository;
import co.edu.uniquindio.proyectofinalhotelfx.Repo.ClienteRepository;
import co.edu.uniquindio.proyectofinalhotelfx.Repo.ReservaRepository;

public class ServicioAdm {

    private final AlojamientoRepository alojamientoRepository;
    private final ReservaRepository reservaRepository;
    private final ClienteRepository usuarioRepository;

    public ServicioAdm(AlojamientoRepository alojamientoRepository, ClienteRepository clienteRepository, ReservaRepository reservaRepository) {
        this.alojamientoRepository = alojamientoRepository;
        this.usuarioRepository = clienteRepository;
        this.reservaRepository = reservaRepository;
    }


    public void loginAdm(){
        admExiste();
    }

    public void admExiste(){

    }

    /*
    Metodos gestionar usuario
     */
    public void verListaDeUsuarios(){

    }
    public void bloquearCuentaUsuario(){

    }
    public void verActividadesDeUsuario(){

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
