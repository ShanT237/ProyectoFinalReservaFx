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
@SuperBuilder
public class OfertaTemporada extends Oferta {


    private double porcentajeDescuento;



    @Override
    public boolean estaVigente(LocalDateTime fecha) {
        return isActiva() &&
                (fecha != null &&
                        !fecha.isBefore(getFechaInicio().atStartOfDay()) &&
                        !fecha.isAfter(getFechaFin().atStartOfDay()));
    }

    @Override
    public double aplicarDescuento(double precioOriginal, Reserva reserva) {
        if (estaVigente(reserva.getFechaInicio())) {
            return precioOriginal * (1 - porcentajeDescuento);
        }
        return precioOriginal;
    }

}