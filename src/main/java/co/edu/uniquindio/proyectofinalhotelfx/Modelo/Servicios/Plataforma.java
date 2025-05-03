package co.edu.uniquindio.proyectofinalhotelfx.Modelo.Servicios;

import co.edu.uniquindio.proyectofinalhotelfx.Modelo.Repo.AlojamientoRepository;

public class Plataforma {
    private ServicioAdm servicioAdm;
    private ServicioUsuario servicioUsuario;
    private ServicioReserva servicioReserva;
    private ServiviosAlojamiento serviviosAlojamiento;

    public Plataforma(){
        this.alojamientoRepository = new AlojamientoRepository();
        this.usuarioRepository = new UsuarioRepositorio();

        this.administradorServicio = new AdministradorServicio(alojamientoRepository, usuarioRepository);
        this.clienteServicio = new ClienteServicio(usuarioRepository, alojamientoRepository);
        this.anfitrionServicio = new AnfitrionServicio(alojamientoRepository, usuarioRepository);
    }


    public void loginAdm(){

    }

}
