package co.edu.uniquindio.proyectofinalhotelfx.Modelo.Entidades;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter

public class Reserva {

    private UUID codigo;
    private Cliente cliente;
    private Alojamiento alojamiento;
    private LocalDateTime fechaInicio;
    private LocalDateTime fechaFin;
    private int numeroHuespedes;
    private double total;
    private boolean EstadoReserva;
    private LocalDateTime fechaCreacion;
    private Factura factura;
    private String qrCode;
    private Review review;

    public Reserva(UUID codigo, Cliente cliente, Alojamiento alojamiento, LocalDateTime fechaInicio, LocalDateTime fechaFin, int numeroHuespedes, double total, LocalDateTime fechaCreacion, boolean estadoReserva, Factura factura, String qrCode) {
        this.codigo = codigo;
        this.cliente = cliente;
        this.alojamiento = alojamiento;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.numeroHuespedes = numeroHuespedes;
        this.total = total;
        this.fechaCreacion = fechaCreacion;
        EstadoReserva = estadoReserva;
        this.factura = factura;
        this.qrCode = qrCode;
        review = null;
    }
}
