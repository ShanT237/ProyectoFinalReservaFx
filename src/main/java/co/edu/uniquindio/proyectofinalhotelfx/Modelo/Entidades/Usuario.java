package co.edu.uniquindio.proyectofinalhotelfx.Modelo.Entidades;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
public abstract class Usuario implements Serializable {
    private String nombre;
    private String cedula;
    private String telefono;
    private String correo;
    private String password;
}