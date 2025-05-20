package co.edu.uniquindio.proyectofinalhotelfx.Modelo.Entidades;

import co.edu.uniquindio.proyectofinalhotelfx.Modelo.Enums.OfertaTipo;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class OfertaTemporada extends Oferta {


    private double porcentajeDescuento;

    public OfertaTemporada(String id, String nombre, String descripcion, LocalDateTime fechaInicio, LocalDateTime fechaFin, List<Alojamiento> alojamientosAplicables, boolean esGlobal, boolean activa, OfertaTipo tipo, double porcentajeDescuento) {
        super(id, nombre, descripcion, fechaInicio, fechaFin, alojamientosAplicables, esGlobal, activa, tipo);
        this.porcentajeDescuento = porcentajeDescuento;
    }

    @Override
    public boolean estaVigente(LocalDateTime fecha) {
        return isActiva() &&
                (fecha != null &&
                        !fecha.isBefore(getFechaInicio()) &&
                        !fecha.isAfter(getFechaFin()));
    }

    @Override
    public double aplicarDescuento(double precioOriginal, Reserva reserva) {
        if (estaVigente(reserva.getFechaInicio())) {
            return precioOriginal * (1 - porcentajeDescuento);
        }
        return precioOriginal;
    }

}