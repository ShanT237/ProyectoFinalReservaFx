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

    public void agregarOfertaEspecial(Ciudad ciudad, TipoAlojamiento tipoAlojamiento, String id, String nombre, String
                                              descripcion, LocalDateTime fechaInicio, LocalDateTime fechaFin,
                                      boolean esGlobal, OfertaTipo tipoOferta,int nochesMinimas, double porcentajeDescuento) throws Exception {
        verificarDatos(id, nombre,
                descripcion, fechaInicio, fechaFin, tipoOferta, nochesMinimas, porcentajeDescuento);

        if (ofertaRepository.buscarPorId(id) != null) {
            throw new Exception("Ya existe una oferta con ese id.");

        }
        List<Alojamiento> alojamientos = obtenerListaAlojamientos(ciudad, tipoAlojamiento, esGlobal);
        Oferta oferta = crearOferta(id, nombre, descripcion, fechaInicio, fechaFin, alojamientos, esGlobal, true, tipoOferta, nochesMinimas, porcentajeDescuento);
        ofertaRepository.guardar(oferta);
    }

    public void eliminarOfertaEspecial(String idOferta){
        ofertaRepository.eliminarOferta(idOferta);

    }

    public void actualizarOfertaEspecial(String idOferta, String nombre, Ciudad ciudad, TipoAlojamiento
                                                 tipoAlojamiento, String descripcion, LocalDateTime fechaInicio, LocalDateTime
                                                 fechaFin,boolean esGlobal, OfertaTipo tipoOferta,int nochesMinimas,
                                         double porcentajeDescuento) throws Exception {
        Oferta oferta = crearOferta(idOferta, nombre, descripcion, fechaInicio, fechaFin, obtenerListaAlojamientos(ciudad, tipoAlojamiento, esGlobal), esGlobal, true, tipoOferta, nochesMinimas, porcentajeDescuento);
        ofertaRepository.actualizarOferta(oferta);
    }

    public boolean existeOfertaEspecial(String idOferta){
        return ofertaRepository.buscarPorId(idOferta) != null;
    }

    public void verificarDatos(String id, String nombre, String
            descripcion, LocalDateTime fechaInicio, LocalDateTime fechaFin, OfertaTipo tipoOferta,int nochesMinimas, double porcentajeDescuento) throws Exception {
        if (id == null || id.isBlank()) {
            throw new Exception("El id de la oferta no puede ser nulo o vacío.");
        }
        if (nombre == null || nombre.isBlank()) {
            throw new Exception("El nombre de la oferta no puede ser nulo o vacío.");
        }
        if (descripcion == null || descripcion.isBlank()) {
            throw new Exception("La descripción de la oferta no puede ser nula o vacía.");
        }
        if (fechaInicio == null) {
            throw new Exception("La fecha de inicio de la oferta no puede ser nula.");
        }
        if (fechaFin == null) {
            throw new Exception("La fecha de fin de la oferta no puede ser nula.");
        }
        if (tipoOferta == null) {
            throw new Exception("El tipo de oferta no puede ser nulo.");
        }
        if (nochesMinimas < 1) {
            throw new Exception("La cantidad mínima de noches debe ser mayor a 0.");
        }
        if (porcentajeDescuento < 0 || porcentajeDescuento > 100) {
            throw new Exception("El porcentaje de descuento debe estar entre 0 y 100.");
        }
    }

    public Oferta crearOferta(String id, String nombre, String descripcion, LocalDateTime fechaInicio, LocalDateTime fechaFin, List<Alojamiento> alojamientos, boolean esGlobal, boolean activa, OfertaTipo tipoOferta, int nochesMinimas, double porcentajeDescuento) throws Exception {
        return OfertaFactory.crearOferta(id, nombre, descripcion, fechaInicio, fechaFin, alojamientos, esGlobal, activa, tipoOferta, nochesMinimas, porcentajeDescuento);
    }

    public List<Alojamiento> obtenerListaAlojamientos(Ciudad ciudad, TipoAlojamiento tipoAlojamiento, boolean esGlobal) throws Exception {
        if (esGlobal) {
            return servicioAlojamiento.getAlojamientoRepository().obtenerTodos();
        }

        if (ciudad != null && tipoAlojamiento != null) {
            return servicioAlojamiento.obtenerAlojamientosPorCiudadYTipo(ciudad, tipoAlojamiento);
        } else if (ciudad != null) {
            return servicioAlojamiento.obtenerAlojamientosPorCiudad(ciudad);
        } else if (tipoAlojamiento != null) {
            return servicioAlojamiento.obtenerAlojamientosPorTipo(tipoAlojamiento);
        } else {
            throw new Exception("Debe especificar al menos una ciudad o un tipo de alojamiento, o marcar como global.");
        }

    }
}
