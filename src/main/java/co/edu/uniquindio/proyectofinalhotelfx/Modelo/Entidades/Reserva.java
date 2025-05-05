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
@Builder
@AllArgsConstructor

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

}
