package co.edu.uniquindio.proyectofinalhotelfx.Singleton;

import co.edu.uniquindio.proyectofinalhotelfx.Modelo.Entidades.Administrador;
import co.edu.uniquindio.proyectofinalhotelfx.Modelo.Entidades.Usuario;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SesionUsuario {
    private static SesionUsuario INSTANCIA;
    public Usuario usuario;

    private SesionUsuario() { }

    public static SesionUsuario instancia() {
        if (INSTANCIA == null) {
            INSTANCIA = new SesionUsuario();
        }
        return INSTANCIA;
    }

    public void iniciarSesion(Usuario admin) {

        this.usuario = admin;
    }

    public void cerrarSesion() {
        this.usuario = null;
    }
    public Usuario getUsuario() {
        return usuario;
    }


}
