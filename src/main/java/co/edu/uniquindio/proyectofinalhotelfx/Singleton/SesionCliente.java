package co.edu.uniquindio.proyectofinalhotelfx.Singleton;

import co.edu.uniquindio.proyectofinalhotelfx.Modelo.Entidades.Cliente;
import co.edu.uniquindio.proyectofinalhotelfx.Modelo.Entidades.Usuario;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

public class SesionCliente {
    public static SesionCliente INSTANCIA;
    private Cliente cliente;
    private SesionCliente() {
    }

    public static SesionCliente instancia() {
        if (INSTANCIA == null) {
            INSTANCIA = new SesionCliente();
        }
        return INSTANCIA;
    }

    public void setUsuario(Cliente cliente) {
        this.cliente = cliente;
    }

    public void cerrarSesion() {
        cliente = null;
    }
}