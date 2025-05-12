package co.edu.uniquindio.proyectofinalhotelfx.Repo;

import co.edu.uniquindio.proyectofinalhotelfx.Modelo.Entidades.Cliente;
import co.edu.uniquindio.proyectofinalhotelfx.Persistencia.Constantes;
import co.edu.uniquindio.proyectofinalhotelfx.Persistencia.Persistencia;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ClienteRepository   {

    // Lista principal de clientes registrados
    private final List<Cliente> clientes;

    // Lista de clientes bloqueados
    private final List<Cliente> usuariosBloqueados;

    public ClienteRepository() {
        this.clientes = leerDatos();
        usuariosBloqueados = new ArrayList<>();
    }

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
        guardarDatos();
    }

    public void actualizar(Cliente clienteActualizado) {
        for (int i = 0; i < clientes.size(); i++) {
            if (clientes.get(i).getCedula().equals(clienteActualizado.getCedula())) {
                clientes.set(i, clienteActualizado);
                guardarDatos(); // Persistir después de actualizar
                return;
            }
        }
    }

    public void eliminar(String cedula) {
        clientes.removeIf(c -> c.getCedula().equals(cedula));
        guardarDatos(); // Persistir después de eliminar
    }

    public List<Cliente> obtenerTodos() {
        return new ArrayList<>(clientes);
    }

    public void agregarUsuarioBloqueado(Cliente clienteBloqueado) {
        usuariosBloqueados.add(clienteBloqueado);
        // podrías guardar aparte si manejas archivo de bloqueados
    }

    private void guardarDatos() {
        try {
            Persistencia.serializarObjeto(Constantes.RUTA_CLIENTE, clientes);
        } catch (IOException e) {
            System.err.println("Error guardando clientes: " + e.getMessage());
        }
    }

    private List<Cliente> leerDatos() {
        try {
            Object datos = Persistencia.deserializarObjeto(Constantes.RUTA_CLIENTE);
            if (datos != null) {
                return (List<Cliente>) datos;
            }
        } catch (Exception e) {
            System.err.println("Error cargando clientes: " + e.getMessage());
        }
        return new ArrayList<>();
    }


}
