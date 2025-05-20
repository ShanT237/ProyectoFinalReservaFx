package co.edu.uniquindio.proyectofinalhotelfx.Modelo.Entidades;

import co.edu.uniquindio.proyectofinalhotelfx.Modelo.Enums.OfertaTipo;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Getter
@Setter
public class OfertaEstadiaProlongada extends Oferta {

    private int nochesMinimas;
    private double porcentajeDescuento;

    public OfertaEstadiaProlongada(String id, String nombre, String descripcion, LocalDateTime fechaInicio, LocalDateTime fechaFin, List<Alojamiento> alojamientosAplicables, boolean esGlobal, boolean activa, OfertaTipo tipo, int nochesMinimas, double porcentajeDescuento, String imagen) {
        super(id, nombre, descripcion, fechaInicio, fechaFin, alojamientosAplicables, esGlobal, activa, tipo, imagen);
        this.nochesMinimas = nochesMinimas;
        this.porcentajeDescuento = porcentajeDescuento;
    }

    @Override
    public boolean estaVigente(LocalDateTime fechaReserva) {
        return isActiva()
                && fechaReserva != null
                && !fechaReserva.isBefore(getFechaInicio())
                && !fechaReserva.isAfter(getFechaFin());
    }

    @Override
    public double aplicarDescuento(double precioOriginal, Reserva reserva) {
        long noches = ChronoUnit.DAYS.between(reserva.getFechaInicio(), reserva.getFechaFin());
        if (noches >= nochesMinimas && estaVigente(reserva.getFechaInicio())) {
            return precioOriginal * (1 - porcentajeDescuento);
        }
        return precioOriginal;
    }
}