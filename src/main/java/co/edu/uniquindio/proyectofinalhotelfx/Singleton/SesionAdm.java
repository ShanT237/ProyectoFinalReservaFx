package co.edu.uniquindio.proyectofinalhotelfx.Singleton;

import co.edu.uniquindio.proyectofinalhotelfx.Modelo.Entidades.Administrador;
import co.edu.uniquindio.proyectofinalhotelfx.Modelo.Entidades.Usuario;
import lombok.Getter;

public class SesionAdm {
    private static SesionAdm INSTANCIA;
    private Administrador usuario;

    private SesionAdm() { }

    public static SesionAdm instancia() {
        if (INSTANCIA == null) {
            INSTANCIA = new SesionAdm();
        }
        return INSTANCIA;
    }

    public void iniciarSesion(Administrador admin) {
        this.usuario = admin;
    }

    public void cerrarSesion() {
        this.usuario = null;
    }
    public Administrador getUsuario() {
        return usuario;
    }


}
