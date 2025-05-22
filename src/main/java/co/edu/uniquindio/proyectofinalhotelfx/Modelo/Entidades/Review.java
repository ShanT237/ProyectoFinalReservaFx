package co.edu.uniquindio.proyectofinalhotelfx.Modelo.Entidades;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;


@Getter
@Setter
@Builder
@AllArgsConstructor
public class Review implements Serializable{
    private UUID codigo;
    private Cliente cliente;
    private String comentario;
    private int valoracion;
    private Alojamiento alojamiento;
    private LocalDateTime fecha;
    private String titulo, descripcion, nombreCliente;
    private String nombreAlojamiento;

}