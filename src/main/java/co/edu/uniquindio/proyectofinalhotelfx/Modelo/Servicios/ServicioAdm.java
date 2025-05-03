package co.edu.uniquindio.proyectofinalhotelfx.Modelo.Servicios;


import co.edu.uniquindio.proyectofinalhotelfx.Modelo.Repo.AlojamientoRepository;
import co.edu.uniquindio.proyectofinalhotelfx.Modelo.Repo.ClienteRepository;
import co.edu.uniquindio.proyectofinalhotelfx.Modelo.Repo.ReservaRepository;

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

      public void buscarAlojamiento(){

      }
      public void eliminarAlojamiento(){

      }
      public void verHistorialdeReservasAlojamiento(){

      }

      public void editarInformacionAlojamiento(){

      }
    /*
    Gestionar Reservas
     */


}
