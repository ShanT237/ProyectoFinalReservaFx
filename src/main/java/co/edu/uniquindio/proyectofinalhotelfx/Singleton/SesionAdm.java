package co.edu.uniquindio.proyectofinalhotelfx.Singleton;

import co.edu.uniquindio.proyectofinalhotelfx.Modelo.Entidades.Administrador;
import co.edu.uniquindio.proyectofinalhotelfx.Modelo.Entidades.Usuario;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SesionAdm {
    private static SesionAdm INSTANCIA;
    public Administrador usuario;

    private SesionAdm() { }

    public static SesionAdm instancia() {
        if (INSTANCIA == null) {
            INSTANCIA = new SesionAdm();
        }
        return INSTANCIA;
    }

    public void iniciarSesion(Administrador admin) {

        this.usuario = admin;
        System.out.println("Usuario: " + admin.getNombre());
    }

    public void cerrarSesion() {
        this.usuario = null;
    }
    public Administrador getUsuario() {
        return usuario;
    }


}
