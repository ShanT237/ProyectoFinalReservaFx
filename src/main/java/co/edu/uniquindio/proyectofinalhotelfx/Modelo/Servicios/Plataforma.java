package co.edu.uniquindio.proyectofinalhotelfx.Modelo.Servicios;

import co.edu.uniquindio.proyectofinalhotelfx.Modelo.Repo.AlojamientoRepository;
import co.edu.uniquindio.proyectofinalhotelfx.Modelo.Repo.ClienteRepository;
import co.edu.uniquindio.proyectofinalhotelfx.Modelo.Repo.ReservaRepository;

public class Plataforma {
    private ServicioAdm servicioAdm;
    private ServicioCliente servicioCliente;
    private ServicioReserva servicioReserva;
    private ServicioAlojamiento serviciosAlojamiento;

    private AlojamientoRepository alojamientoRepository;
    private ClienteRepository clienteRepository;
    private ReservaRepository reservaRepository;

    public Plataforma(AlojamientoRepository alojamientoRepository, ClienteRepository clienteRepository, ReservaRepository reservaRepository) {

        this.alojamientoRepository = alojamientoRepository;
        this.clienteRepository = clienteRepository;
        this.reservaRepository = reservaRepository;

        this.servicioAdm = new ServicioAdm(alojamientoRepository, clienteRepository, reservaRepository);
        this.servicioCliente = new ServicioCliente(clienteRepository, alojamientoRepository);
        this.serviciosAlojamiento = new ServicioAlojamiento(alojamientoRepository);
    }


    public void loginAdm(){

    }
    private void crearAlojamientosRepos(){

    }

}
