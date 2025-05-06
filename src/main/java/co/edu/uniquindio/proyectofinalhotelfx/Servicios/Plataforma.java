package co.edu.uniquindio.proyectofinalhotelfx.Servicios;

import co.edu.uniquindio.proyectofinalhotelfx.Modelo.Enums.Ciudad;
import co.edu.uniquindio.proyectofinalhotelfx.Modelo.Enums.ServiciosIncluidos;
import co.edu.uniquindio.proyectofinalhotelfx.Modelo.Enums.TipoAlojamiento;
import co.edu.uniquindio.proyectofinalhotelfx.Repo.*;
import javafx.scene.image.Image;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Plataforma implements IPlataforma {
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



    public void loginAdm(String correo, String contrasena){
        servicioAdm.loginAdm(correo,contrasena);

    }

    @Override
    public void bloquearCliente(String idUsuario) throws Exception {
        servicioAdm.bloquearCuentaCliente(idUsuario);

    }

    @Override
    public void verActividadesCliente(String idUsuario) {
        servicioAdm.verActividadesDeCliente(idUsuario);

    }

    @Override
    public void registrarAlojamiento(String nombre, Ciudad ciudad, String descripcion, double precioPorNocheBase, int capacidadMaxima, Image imagen, List<ServiciosIncluidos> serviciosIncluidos, int capacidadPersonas, int numeroHabitaciones, boolean admiteMascotas, TipoAlojamiento tipoAlojamiento) {
       servicioAdm.registrarAlojamiento(nombre,ciudad,descripcion,precioPorNocheBase,capacidadMaxima,imagen,serviciosIncluidos,capacidadPersonas,numeroHabitaciones,admiteMascotas,tipoAlojamiento);
    }

    @Override
    public void eliminarAlojamiento(String idAlojamiento) {
        servicioAdm.eliminarAlojamiento(idAlojamiento);
    }

    @Override
    public void registrarReserva() {

    }

    @Override
    public void registrarOferta() {

    }

    @Override
    public void registrarCliente(String nombre, String id, String telefono, String  email, String password) throws Exception {
        servicioCliente.registrarCliente(nombre, id, telefono, email, password);

    }


}
