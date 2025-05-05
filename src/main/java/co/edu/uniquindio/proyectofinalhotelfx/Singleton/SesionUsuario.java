package co.edu.uniquindio.proyectofinalhotelfx.Singleton;

import co.edu.uniquindio.proyectofinalhotelfx.Modelo.Entidades.Usuario;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder

public class SesionUsuario {
    public static SesionUsuario INSTANCIA;
    private Usuario usuario;


    private SesionUsuario() {

    }


    public static SesionUsuario instancia() {
        if (INSTANCIA == null) {
            INSTANCIA = new SesionUsuario();
        }
        return INSTANCIA;
    }

    public void cerrarSesion() {
        usuario = null;
    }
}
