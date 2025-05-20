package co.edu.uniquindio.proyectofinalhotelfx.Factory.OfertaFactory;

import co.edu.uniquindio.proyectofinalhotelfx.Modelo.Entidades.*;
import co.edu.uniquindio.proyectofinalhotelfx.Modelo.Enums.Ciudad;
import co.edu.uniquindio.proyectofinalhotelfx.Modelo.Enums.OfertaTipo;
import co.edu.uniquindio.proyectofinalhotelfx.Modelo.Enums.ServiciosIncluidos;
import co.edu.uniquindio.proyectofinalhotelfx.Modelo.Enums.TipoAlojamiento;
import javafx.scene.image.Image;

import java.time.LocalDateTime;
import java.util.List;

public abstract class OfertaFactory {
    public static Oferta crearOferta(String id, String nombre, String descripcion, LocalDateTime fechaInicio, LocalDateTime fechaFin, List<Alojamiento> alojamientos, boolean esGlobal, boolean activa, OfertaTipo tipoOferta, int nochesMinimas, double porcentajeDescuento) throws Exception {

        return switch (tipoOferta) {
            case TEMPORADA -> new OfertaTemporada(id, nombre, descripcion, fechaInicio, fechaFin, alojamientos, esGlobal, activa, tipoOferta, porcentajeDescuento);
            case ESTADIAPROLONGADA -> new OfertaEstadiaProlongada(id, nombre, descripcion, fechaInicio, fechaFin, alojamientos, esGlobal, activa, tipoOferta, nochesMinimas, porcentajeDescuento);
            default -> throw new Exception("Tipo de oferta no válido");
        };
    }


}

