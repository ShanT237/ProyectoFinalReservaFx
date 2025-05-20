package co.edu.uniquindio.proyectofinalhotelfx.Modelo.Entidades;

import co.edu.uniquindio.proyectofinalhotelfx.Modelo.Enums.OfertaTipo;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
@Getter
@Setter
public abstract class Oferta {
    private String id;
    private String nombre;
    private String descripcion;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private List<Alojamiento> alojamientosAplicables;
    private boolean esGlobal;
    private boolean activa;
    private OfertaTipo tipo;



    public boolean aplicaA(Alojamiento alojamiento) {
        return esGlobal || (alojamientosAplicables != null && alojamientosAplicables.contains(alojamiento));
    }

    public abstract boolean estaVigente(LocalDateTime fecha);

    public abstract double aplicarDescuento(double precioOriginal, Reserva reserva);
}
