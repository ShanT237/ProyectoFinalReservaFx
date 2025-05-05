package co.edu.uniquindio.proyectofinalhotelfx.Servicios;

import co.edu.uniquindio.proyectofinalhotelfx.Repo.*;

public class Plataforma {
    private ServicioAdm servicioAdm;
    private ServicioCliente servicioCliente;
    private ServicioReserva servicioReserva;
    private ServicioAlojamiento serviciosAlojamiento;
    private ServicioBilleteraVirtual servicioBilleteraVirtual;

    private AlojamientoRepository alojamientoRepository;
    private ClienteRepository clienteRepository;
    private ReservaRepository reservaRepository;
    private AdmRepository admRepository;
    private BilleteraVirtualRepository billeteraVirtualRepository;

    public Plataforma() {

        /*
        Intancias de Repositorios
         */
        this.alojamientoRepository = new AlojamientoRepository() ;
        this.clienteRepository = new ClienteRepository();
        this.reservaRepository = new ReservaRepository();
        this.admRepository = new AdmRepository();
        this.billeteraVirtualRepository = new BilleteraVirtualRepository();

        /*
        Intancias de Servicios
         */
        this.servicioBilleteraVirtual = ServicioBilleteraVirtual.builder()
                .billeteraVirtualRepository(billeteraVirtualRepository)
                .build();
        this.serviciosAlojamiento = ServicioAlojamiento.builder()
                .alojamientoRepository(alojamientoRepository)
                .build();
        this.servicioReserva = ServicioReserva.builder()
                .reservaRepository(reservaRepository)
                .build();


        this.servicioCliente = ServicioCliente.builder()
                .clienteRepository(clienteRepository)
                .servicioAlojamiento(serviciosAlojamiento)
                .build();

        this.servicioAdm = ServicioAdm.builder()
                .servicioAlojamiento(serviciosAlojamiento)
                .servicioReserva(servicioReserva)
                .servicioCliente(servicioCliente)
                .admRepository(admRepository)
                .build();


    }



    public void loginAdm(){

    }
    private void crearAlojamientosRepos(){

    }

}
