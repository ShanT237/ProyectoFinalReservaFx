package co.edu.uniquindio.proyectofinalhotelfx.Servicios;

import co.edu.uniquindio.proyectofinalhotelfx.Factory.OfertaFactory.OfertaFactory;
import co.edu.uniquindio.proyectofinalhotelfx.Modelo.Entidades.Alojamiento;
import co.edu.uniquindio.proyectofinalhotelfx.Modelo.Entidades.Oferta;
import co.edu.uniquindio.proyectofinalhotelfx.Modelo.Enums.Ciudad;
import co.edu.uniquindio.proyectofinalhotelfx.Modelo.Enums.OfertaTipo;
import co.edu.uniquindio.proyectofinalhotelfx.Modelo.Enums.ServiciosIncluidos;
import co.edu.uniquindio.proyectofinalhotelfx.Modelo.Enums.TipoAlojamiento;
import co.edu.uniquindio.proyectofinalhotelfx.Repo.OfertaRepository;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
public class ServicioOferta {
    private ServicioAlojamiento servicioAlojamiento;
    private OfertaRepository ofertaRepository;

    public void agregarOfertaEspecial(String nombre, Ciudad ciudad, String descripcion, double precioPorNocheBase, String imagen, List<ServiciosIncluidos> serviciosIncluidos, int capacidadPersonas, int numeroHabitaciones, boolean admiteMascotas, TipoAlojamiento tipoAlojamiento){
        verificarDatos(nombre, descripcion, precioPorNocheBase, imagen, serviciosIncluidos, capacidadPersonas, numeroHabitaciones);
        List<Alojamiento> alojamientos = obtenerListaAlojamientos(ciudad, tipoAlojamiento, esGlobal);
    }

    public void eliminarOfertaEspecial(String idOferta){

    }

    public void actualizarOfertaEspecial(String idOferta, String nombre, Ciudad ciudad, String descripcion, double precioPorNocheBase, String imagen, List<ServiciosIncluidos> serviciosIncluidos, int capacidadPersonas, int numeroHabitaciones, boolean admiteMascotas, TipoAlojamiento tipoAlojamiento){
    }

    public boolean existeOfertaEspecial(String idOferta){
        return ofertaRepository.buscarPorId(idOferta) != null;
    }

    public void verificarDatos(String nombre, String descripcion, double precioPorNocheBase, String imagen, List<ServiciosIncluidos> serviciosIncluidos, int capacidadPersonas, int numeroHabitaciones
    ){
        if (nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío.");
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
        if (capacidadPersonas <= 0) {
            throw new IllegalArgumentException("La capacidad de personas debe ser mayor a 0.");
        }
        if (numeroHabitaciones <= 0) {
            throw new IllegalArgumentException("El número de habitaciones debe ser mayor a 0.");
        }
        if (imagen == null || imagen.isBlank()) {
            throw new IllegalArgumentException("La imagen no puede estar vacía.");
        }
    }

    public Oferta crearOferta(String id, String nombre, String descripcion, LocalDateTime fechaInicio, LocalDateTime fechaFin, List<Alojamiento> alojamientos, boolean esGlobal, boolean activa, OfertaTipo tipoOferta, int nochesMinimas, double porcentajeDescuento) throws Exception {
       return OfertaFactory.crearOferta(id, nombre, descripcion, fechaInicio, fechaFin, alojamientos, esGlobal, activa, tipoOferta, nochesMinimas, porcentajeDescuento);
    }

    public List<Alojamiento> obtenerListaAlojamientos(Ciudad ciudad, TipoAlojamiento tipoAlojamiento, boolean esGlobal) {
        if (esGlobal) {
            return servicioAlojamiento.obtenerTodos();
        }

        if (ciudad != null && tipoAlojamiento != null) {
            return servicioAlojamiento.obtenerAlojamientosPorCiudadYTipo(ciudad, tipoAlojamiento);
        } else if (ciudad != null) {
            return servicioAlojamiento.obtenerAlojamientosPorCiudad(ciudad);
        } else if (tipoAlojamiento != null) {
            return servicioAlojamiento.obtenerAlojamientosPorTipo(tipoAlojamiento);
        } else {
            throw new IllegalArgumentException("Debe especificar al menos una ciudad o un tipo de alojamiento, o marcar como global.");
        }

    }
}

