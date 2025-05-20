package co.edu.uniquindio.proyectofinalhotelfx.Modelo.Entidades;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;


@Getter
@Setter
@Builder
@AllArgsConstructor
public class Review {
    private UUID codigo;
    private Cliente cliete;
    private String comentario;
    private int valoracion;
    private Alojamiento alojamiento;
    private LocalDateTime fecha;

}