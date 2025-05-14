package co.edu.uniquindio.proyectofinalhotelfx.Servicios;


import co.edu.uniquindio.proyectofinalhotelfx.Modelo.Entidades.Administrador;
import co.edu.uniquindio.proyectofinalhotelfx.Modelo.Entidades.Oferta;
import co.edu.uniquindio.proyectofinalhotelfx.Modelo.Enums.Ciudad;
import co.edu.uniquindio.proyectofinalhotelfx.Modelo.Enums.ServiciosIncluidos;
import co.edu.uniquindio.proyectofinalhotelfx.Modelo.Enums.TipoAlojamiento;
import co.edu.uniquindio.proyectofinalhotelfx.Singleton.SesionUsuario;
import lombok.Builder;

import java.util.List;

@Builder
public class ServicioAdm {

    private final ServicioAlojamiento servicioAlojamiento;
    private final ServicioReserva servicioReserva;
    private final ServicioCliente servicioCliente;

    public Administrador loginAdm(String correo, String password){
        if(correo.equals("shanrt@gmail.com") &&  password.equals("1234password") ){
            return Administrador.builder()
                    .nombre("Santiago")
                    .cedula("12323")
                    .telefono("3216549870")
                    .correo("shanrt@gmail.com")
                    .password("1234password")
                    .build();
        }

        return null;
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

    public void registrarAlojamiento(String nombre, Ciudad ciudad, String descripcion, double precioPorNocheBase, String imagen, List<ServiciosIncluidos> serviciosIncluidos, int capacidadPersonas, int numeroHabitaciones, boolean admiteMascotas, TipoAlojamiento tipoAlojamiento){
        verificarDatosAlojamiento(nombre, ciudad, descripcion, precioPorNocheBase, imagen, serviciosIncluidos,
                capacidadPersonas, numeroHabitaciones, admiteMascotas,
                tipoAlojamiento);
        servicioAlojamiento.registrarAlojamiento(nombre, ciudad, descripcion, precioPorNocheBase,imagen, serviciosIncluidos,
                capacidadPersonas, numeroHabitaciones, admiteMascotas,
                tipoAlojamiento);


    }

    public void verificarDatosAlojamiento(String nombre, Ciudad ciudad, String descripcion, double precioPorNocheBase, String imagen, List<ServiciosIncluidos> serviciosIncluidos, int capacidadPersonas, int numeroHabitaciones, boolean admiteMascotas, TipoAlojamiento tipoAlojamiento){

        if (nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío.");
        }
        if (ciudad == null) {
            throw new IllegalArgumentException("La ciudad no puede ser nula.");
        }
        if (descripcion == null || descripcion.isBlank()) {
            throw new IllegalArgumentException("La descripción no puede estar vacía.");
        }
        if (precioPorNocheBase <= 0) {
            throw new IllegalArgumentException("El precio debe ser mayor a 0.");
        }
        if (serviciosIncluidos == null) {
            throw new IllegalArgumentException("La lista de servicios no puede ser nula.");
        }
        if (tipoAlojamiento == null) {
            throw new IllegalArgumentException("El tipo de alojamiento no puede ser nulo.");
        }
    }
    public void actualizarAlojamiento(String idAlojamiento, String nombre, Ciudad ciudad, String descripcion, double precioPorNocheBase, String imagen, List<ServiciosIncluidos> serviciosIncluidos, int capacidadPersonas, int numeroHabitaciones, boolean admiteMascotas, TipoAlojamiento tipoAlojamiento){
       verificarDatosAlojamiento(nombre, ciudad, descripcion, precioPorNocheBase, imagen, serviciosIncluidos,
               capacidadPersonas, numeroHabitaciones, admiteMascotas,
               tipoAlojamiento);

       servicioAlojamiento.actualizarAlojamiento( idAlojamiento, nombre,  ciudad,  descripcion,  precioPorNocheBase, imagen, serviciosIncluidos,  capacidadPersonas,  numeroHabitaciones, admiteMascotas, tipoAlojamiento);

    }
    public void eliminarAlojamiento(String idAlojamiento){
        if (idAlojamiento == null){
            throw new IllegalArgumentException("El id del alojamiento no puede ser nulo.");
        }

        servicioAlojamiento.eliminarAlojamiento(idAlojamiento);

    }

    public void agregarOfertaEspecial(String idAlojamiento, Oferta oferta){


    }
    public void agregarOfertaEspecialGlobal(Ciudad ciudad, Oferta oferta){


    }
    public void actualizarOfertaEspecial(String idAlojamiento, Oferta nuevaOferta){

    }
    public void eliminarOfertaEspecial(String idAlojamiento, String idOferta){

    }
    /*
    Gestionar Reservas
     */


}
