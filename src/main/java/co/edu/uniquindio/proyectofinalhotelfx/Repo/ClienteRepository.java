package co.edu.uniquindio.proyectofinalhotelfx.Repo;

import co.edu.uniquindio.proyectofinalhotelfx.Modelo.Entidades.Cliente;

import java.util.ArrayList;
import java.util.List;

public class ClienteRepository {

    private final List<Cliente> clientes = new ArrayList<>();
    private final List<Cliente> usuariosBloqueados = new ArrayList<>();

    public Cliente buscarPorCedula(String cedula) {
        for (Cliente c : clientes) {
            if (c.getCedula().equals(cedula)) {
                return c;
            }
        }
        return null;
    }

    public Cliente buscarPorCorreo(String correo) {
        for (Cliente c : clientes) {
            if (c.getCorreo().equalsIgnoreCase(correo)) {
                return c;
            }
        }
        return null;
    }

    public void guardar(Cliente cliente) {
        clientes.add(cliente);
    }

    public void actualizar(Cliente clienteActualizado) {
        for (int i = 0; i < clientes.size(); i++) {
            if (clientes.get(i).getCedula().equals(clienteActualizado.getCedula())) {
                clientes.set(i, clienteActualizado);
                return;
            }
        }
    }

    public void eliminar(String cedula) {
        clientes.removeIf(c -> c.getCedula().equals(cedula));

    }

    public List<Cliente> obtenerTodos() {
        return new ArrayList<>(clientes);


    }

     /*
        / Metodos lista clientesBloqueados
         */
    public void agregarUsuarioBloqueado(Cliente clienteBloqueado){
        usuariosBloqueados.add(clienteBloqueado);


    }
}
