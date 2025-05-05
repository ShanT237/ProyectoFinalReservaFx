package co.edu.uniquindio.proyectofinalhotelfx.Modelo.Entidades;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
@Getter
@Setter
@Builder
@AllArgsConstructor

public class Reserva {
    private String id;
    private Cliente cliente;
    private Alojamiento alojamiento;
    private LocalDateTime fechaInicio, fechaFin;
    private int cantidadPersonas;
    private Factura factura;
    private boolean estado;

    public Object getEstado() {
    }

    public int getValoracion() {
    }

    public Object getComentario() {
    }

    public void setComentario(String comentario) {
    }

    public void setValoracion(int valoracion) {
    }
}
