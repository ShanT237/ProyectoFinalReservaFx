package co.edu.uniquindio.proyectofinalhotelfx.Servicios;

import co.edu.uniquindio.proyectofinalhotelfx.Repo.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Plataforma {
    private ServicioAdm servicioAdm;
    private ServicioCliente servicioCliente;
    private ServicioReserva servicioReserva;
    private ServicioAlojamiento serviciosAlojamiento;

    private AlojamientoRepository alojamientoRepository;
    private ClienteRepository clienteRepository;
    private ReservaRepository reservaRepository;

    public Plataforma() {

        /*
        Intancias de Repositorios
         */
        this.alojamientoRepository = new AlojamientoRepository() ;
        this.clienteRepository = new ClienteRepository();
        this.reservaRepository = new ReservaRepository();

        /*
        Intancias de Servicios
         */


        this.servicioCliente = ServicioCliente.builder()
                .clienteRepository(clienteRepository)
                .servicioAlojamiento(serviciosAlojamiento)
                .build();

        ServicioBilleteraVirtual servicioBilleteraVirtual = ServicioBilleteraVirtual.builder()
                .servicioCliente(servicioCliente).build();

        this.serviciosAlojamiento = ServicioAlojamiento.builder()
                .alojamientoRepository(alojamientoRepository)
                .build();
        this.servicioReserva = ServicioReserva.builder()
                .reservaRepository(reservaRepository)
                .build();


        this.servicioAdm = ServicioAdm.builder()
                .servicioAlojamiento(serviciosAlojamiento)
                .servicioReserva(servicioReserva)
                .servicioCliente(servicioCliente)
                .build();


    }



    public void loginAdm(){

    }
    private void crearAlojamientosRepos(){

    }

}
