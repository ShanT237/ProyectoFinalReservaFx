package co.edu.uniquindio.proyectofinalhotelfx.Modelo.Entidades;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
public class Administrador extends Usuario {
    private int codigoActivacion;

}