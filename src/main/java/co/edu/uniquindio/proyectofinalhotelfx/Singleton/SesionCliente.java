package co.edu.uniquindio.proyectofinalhotelfx.Singleton;

import co.edu.uniquindio.proyectofinalhotelfx.Modelo.Entidades.Usuario;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder

public class SesionCliente {
    public static SesionCliente INSTANCIA;
    private Usuario usuario;


    private SesionCliente() {

    }


    public static SesionCliente instancia() {
        if (INSTANCIA == null) {
            INSTANCIA = new SesionCliente();
        }
        return INSTANCIA;
    }

    public void cerrarSesion() {
        usuario = null;
    }
}
