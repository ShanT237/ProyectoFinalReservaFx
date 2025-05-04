package co.edu.uniquindio.proyectofinalhotelfx.Modelo.Entidades;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder

public class Sesion {
    public static Sesion INSTANCIA;
    private Usuario usuario;


    private Sesion() {

    }


    public static Sesion instancia() {
        if (INSTANCIA == null) {
            INSTANCIA = new Sesion();
        }
        return INSTANCIA;
    }

    public void cerrarSesion() {
        usuario = null;
    }
}
