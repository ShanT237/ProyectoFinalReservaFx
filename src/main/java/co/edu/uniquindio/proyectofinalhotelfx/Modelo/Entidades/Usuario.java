package co.edu.uniquindio.proyectofinalhotelfx.Modelo.Entidades;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public  class Usuario {
    public String nombre ;
    public String cedula;
    public String telefono;
    public String correo;
    public String password;

    
}