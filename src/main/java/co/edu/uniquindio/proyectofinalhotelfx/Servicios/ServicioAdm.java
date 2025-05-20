package co.edu.uniquindio.proyectofinalhotelfx.Servicios;


import co.edu.uniquindio.proyectofinalhotelfx.Modelo.Entidades.Administrador;
import co.edu.uniquindio.proyectofinalhotelfx.Modelo.Enums.Ciudad;
import co.edu.uniquindio.proyectofinalhotelfx.Modelo.Enums.OfertaTipo;
import co.edu.uniquindio.proyectofinalhotelfx.Modelo.Enums.ServiciosIncluidos;
import co.edu.uniquindio.proyectofinalhotelfx.Modelo.Enums.TipoAlojamiento;
import co.edu.uniquindio.proyectofinalhotelfx.Persistencia.Ruta;
import co.edu.uniquindio.proyectofinalhotelfx.Persistencia.Persistencia;
import lombok.Builder;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@Builder
public class ServicioAdm {

    private final ServicioAlojamiento servicioAlojamiento;
    private final ServicioReserva servicioReserva;
    private final ServicioCliente servicioCliente;
    private final ServicioOferta servicioOferta;

    public Administrador loginAdm(String correo, String password){
        System.out.println(leerDatos());
        Administrador adm = leerDatos();
        if (adm != null && adm.getCorreo().equals(correo) && adm.getPassword().equals(password)){
            return adm;
        }else{
            return null;
        }
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

    private void guardarDatos(Administrador adm) {
        try {
            Persistencia.serializarObjeto(Ruta.RUTA_ADM, adm);
        } catch (IOException e) {
            System.err.println("Error guardando adm: " + e.getMessage());
        }
    }

    private Administrador leerDatos() {
        try {
            Object datos = Persistencia.deserializarObjeto(Ruta.RUTA_ADM);
            if (datos instanceof Administrador) {
                return (Administrador) datos;
            }
        } catch (Exception e) {
            System.err.println("Error cargando admin: " + e.getMessage());
        }
        return null;
    }

    public void registrarOferta(Ciudad ciudad, TipoAlojamiento tipoAlojamiento, String id, String nombre, String descripcion, LocalDateTime fechaInicio, LocalDateTime fechaFin, boolean esGlobal, OfertaTipo tipoOferta, int nochesMinimas, double porcentajeDescuento) throws Exception {
        servicioOferta.agregarOfertaEspecial(ciudad, tipoAlojamiento, id, nombre, descripcion, fechaInicio, fechaFin, esGlobal, tipoOferta, nochesMinimas, porcentajeDescuento);
    }

    public void eliminarOfertaEspecial(String idOferta) {
        servicioOferta.eliminarOfertaEspecial(idOferta);
    }

    public void actualizarOfertaEspecial(String idOferta, String nombre, Ciudad ciudad, TipoAlojamiento tipoAlojamiento, String descripcion, LocalDateTime fechaInicio, LocalDateTime fechaFin, boolean esGlobal, OfertaTipo tipoOferta, int nochesMinimas, double porcentajeDescuento) throws Exception {
        servicioOferta.actualizarOfertaEspecial(idOferta, nombre, ciudad, tipoAlojamiento, descripcion, fechaInicio, fechaFin, esGlobal, tipoOferta, nochesMinimas, porcentajeDescuento);
    }


    /*
    Gestionar Reservas
     */


}
