package co.edu.uniquindio.proyectofinalhotelfx.Modelo.Entidades;

import co.edu.uniquindio.proyectofinalhotelfx.Modelo.Enums.OfertaTipo;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;
@Getter
@Setter
public abstract class Oferta {
    private UUID id;
    private OfertaTipo tipoOferta;
    private String descripcion;
    private LocalDateTime fechaInicio;
    private LocalDateTime fechaFin;
    private boolean activa;
    private double descuento;

    abstract boolean esValida();
    abstract double aplicarDescuento(double precioOriginal);

}
