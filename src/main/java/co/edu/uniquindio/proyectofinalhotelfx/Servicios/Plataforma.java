package co.edu.uniquindio.proyectofinalhotelfx.Servicios;

import co.edu.uniquindio.proyectofinalhotelfx.Controladores.ControladorPrincipal;
import co.edu.uniquindio.proyectofinalhotelfx.Controladores.ControladoresCliente.CodigoVerificacion;
import co.edu.uniquindio.proyectofinalhotelfx.Modelo.Entidades.Administrador;
import co.edu.uniquindio.proyectofinalhotelfx.Modelo.Entidades.Alojamiento;
import co.edu.uniquindio.proyectofinalhotelfx.Modelo.Entidades.Cliente;
import co.edu.uniquindio.proyectofinalhotelfx.Modelo.Enums.Ciudad;
import co.edu.uniquindio.proyectofinalhotelfx.Modelo.Enums.ServiciosIncluidos;
import co.edu.uniquindio.proyectofinalhotelfx.Modelo.Enums.TipoAlojamiento;
import co.edu.uniquindio.proyectofinalhotelfx.Notificacion.Notificacion;
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
    private ServicioOferta servicioOferta;

    private AlojamientoRepository alojamientoRepository;
    private ClienteRepository clienteRepository;
    private ReservaRepository reservaRepository;
    private OfertaRepository ofertaRepository;

    public Plataforma() {

        /*
        Intancias de Repositorios
         */
        this.alojamientoRepository = new AlojamientoRepository() ;
        this.clienteRepository = new ClienteRepository();
        this.reservaRepository = new ReservaRepository();
        this.ofertaRepository = new OfertaRepository();


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

        this.servicioOferta = ServicioOferta.builder()
                .ofertaRepository(ofertaRepository)
                .servicioAlojamiento(serviciosAlojamiento)
                .build();

        this.servicioAdm = ServicioAdm.builder()
                .servicioAlojamiento(serviciosAlojamiento)
                .servicioReserva(servicioReserva)
                .servicioCliente(servicioCliente)
                .servicioOferta(servicioOferta)
                .build();


    }

    public Administrador loginAdm(String correo, String contrasena){
        return servicioAdm.loginAdm(correo,contrasena);
    }

    @Override
    public Cliente loginCliente(String correo, String password) throws Exception {
        return servicioCliente.iniciarSesion(correo, password);
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
    public void registrarAlojamiento(String nombre, Ciudad ciudad, String descripcion, double precioPorNocheBase, String imagen, List<ServiciosIncluidos> serviciosIncluidos, int capacidadPersonas, int numeroHabitaciones, boolean admiteMascotas, TipoAlojamiento tipoAlojamiento) {
       servicioAdm.registrarAlojamiento(nombre,ciudad,descripcion,precioPorNocheBase,imagen,serviciosIncluidos,capacidadPersonas,numeroHabitaciones,admiteMascotas,tipoAlojamiento);
    }

    @Override
    public void eliminarAlojamiento(String idAlojamiento) {
        servicioAdm.eliminarAlojamiento(idAlojamiento);
    }

    @Override
    public void actualizarAlojamiento(String idAlojamiento, String nombre, Ciudad ciudad, String descripcion, double precioPorNocheBase, String imagen, List<ServiciosIncluidos> serviciosIncluidos, int capacidadPersonas, int numeroHabitaciones, boolean admiteMascotas, TipoAlojamiento tipoAlojamiento) {
        servicioAdm.actualizarAlojamiento(idAlojamiento,nombre,ciudad,descripcion,precioPorNocheBase,imagen,serviciosIncluidos,capacidadPersonas,numeroHabitaciones,admiteMascotas,tipoAlojamiento);
    }

    @Override
    public List<Alojamiento> obtenerListaAlojamientos() {
        return serviciosAlojamiento.obtenerTodosAlojamientos();
    }

    @Override
    public void registrarReserva() {

    }

    @Override
    public void registrarOferta() {

    }

    @Override
    public void registrarCliente(String nombre, String cedula, String telefono, String  correo, String password, String confirmarPassword) throws Exception {
        servicioCliente.registrarCliente(nombre, cedula, telefono, correo, password, confirmarPassword);

    }



    @Override
    public boolean validarCodigoVerificacion(String correo, String codigoIngresado) {
        return servicioCliente.validarCodigoVerificacion(correo, codigoIngresado);
    }

    @Override
    public void recuperarContrasena(String correo) throws Exception {
        servicioCliente.recuperarContrasena(correo);
    }

    @Override
    public void actualizarContrasena(String correo, String nuevaContrasena, String confirmarPassword, String codigoIngresado) throws Exception {
        servicioCliente.actualizarContrasena(correo, nuevaContrasena, confirmarPassword, codigoIngresado);
    }
}
