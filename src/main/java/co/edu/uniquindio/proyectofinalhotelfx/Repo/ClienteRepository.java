package co.edu.uniquindio.proyectofinalhotelfx.Repo;

import co.edu.uniquindio.proyectofinalhotelfx.Modelo.Entidades.Cliente;

import java.util.ArrayList;
import java.util.List;

/**
 * Repositorio para la gestión de clientes y usuarios bloqueados.
 * Proporciona operaciones CRUD básicas y métodos específicos para búsqueda.
 */
public class ClienteRepository {

    // Lista principal de clientes registrados
    private final List<Cliente> clientes = new ArrayList<>();

    // Lista de clientes que han sido bloqueados
    private final List<Cliente> usuariosBloqueados = new ArrayList<>();

    /**
     * Busca un cliente por su número de cédula.
     * @param cedula Número de cédula a buscar (comparación exacta)
     * @return El cliente encontrado o null si no existe
     */
    public Cliente buscarPorCedula(String cedula) {
        for (Cliente c : clientes) {
            if (c.getCedula().equals(cedula)) {
                return c;
            }
        }
        return null;
    }

    /**
     * Busca un cliente por su correo electrónico.
     * @param correo Dirección de correo a buscar (comparación case-insensitive)
     * @return El cliente encontrado o null si no existe
     */
    public Cliente buscarPorCorreo(String correo) {
        for (Cliente c : clientes) {
            if (c.getCorreo().equalsIgnoreCase(correo)) {
                return c;
            }
        }
        return null;
    }

    /**
     * Agrega un nuevo cliente al repositorio.
     * @param cliente Objeto Cliente a registrar
     */
    public void guardar(Cliente cliente) {
        clientes.add(cliente);
    }

    /**
     * Actualiza la información de un cliente existente.
     * @param clienteActualizado Cliente con los datos actualizados (debe tener misma cédula)
     */
    public void actualizar(Cliente clienteActualizado) {
        for (int i = 0; i < clientes.size(); i++) {
            if (clientes.get(i).getCedula().equals(clienteActualizado.getCedula())) {
                clientes.set(i, clienteActualizado);
                return;
            }
        }
    }

    /**
     * Elimina un cliente del repositorio por su cédula.
     * @param cedula Número de cédula del cliente a eliminar
     */
    public void eliminar(String cedula) {
        clientes.removeIf(c -> c.getCedula().equals(cedula));
    }

    /**
     * Obtiene una copia de la lista completa de clientes.
     * @return Nueva lista con todos los clientes registrados
     */
    public List<Cliente> obtenerTodos() {
        return new ArrayList<>(clientes);
    }

    /* -------------------- */
    /* Métodos para usuarios bloqueados */
    /* -------------------- */

    /**
     * Agrega un cliente a la lista de usuarios bloqueados.
     * @param clienteBloqueado Cliente a bloquear
     */
    public void agregarUsuarioBloqueado(Cliente clienteBloqueado) {
        usuariosBloqueados.add(clienteBloqueado);
    }
}
