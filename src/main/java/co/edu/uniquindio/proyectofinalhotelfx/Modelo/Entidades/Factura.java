package co.edu.uniquindio.proyectofinalhotelfx.Modelo.Entidades;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.UUID;
@Getter
@Setter
@Builder
@AllArgsConstructor

public class Factura implements Serializable {
    private UUID id;
    private double subtotal;
    private double total;
    private LocalDate fecha;
    private String codigoQR;



}