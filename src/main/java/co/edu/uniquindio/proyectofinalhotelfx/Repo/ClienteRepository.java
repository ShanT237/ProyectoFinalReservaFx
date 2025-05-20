package co.edu.uniquindio.proyectofinalhotelfx.Repo;

import co.edu.uniquindio.proyectofinalhotelfx.Modelo.Entidades.Cliente;
import co.edu.uniquindio.proyectofinalhotelfx.Persistencia.Ruta;
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

    public void guardar(Cliente cliente) throws Exception {
        // Validación para evitar contraseñas vacías
        if (cliente.getPassword() == null || cliente.getPassword().isEmpty()) {
            throw new Exception("La contraseña no puede estar vacía.");
        }

        // Validación para evitar correos y cédulas duplicados
        Cliente clienteExistentePorCorreo = buscarPorCorreo(cliente.getCorreo());
        if (clienteExistentePorCorreo != null) {
            throw new Exception("Ya existe un cliente con ese correo.");
        }

        Cliente clienteExistentePorCedula = buscarPorCedula(cliente.getCedula());
        if (clienteExistentePorCedula != null) {
            throw new Exception("Ya existe un cliente con esa cédula.");
        }

        // Si no hay errores, agregar el cliente a la lista
        clientes.add(cliente);

        // Guardar los datos si el cliente fue agregado correctamente
        guardarDatos();
    }

    public boolean actualizar(Cliente clienteActualizado) {
        System.out.println("actualizar");
        for (int i = 0; i < clientes.size(); i++) {
            Cliente actual = clientes.get(i);
            System.out.println("for cliente");
            if (actual.getCedula().equals(clienteActualizado.getCedula())) {
                System.out.println("Existe un cliente");
                if (!actual.getPassword().equals(clienteActualizado.getPassword())) {
                    throw new IllegalArgumentException("La contraseña no puede ser modificada de manera no autorizada.");
                }
                    clientes.set(i, clienteActualizado);
                    System.out.println(clientes);
                    guardarDatos();
                    return true; // Hubo cambios
            }
        }
        return false;
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
            Persistencia.serializarObjeto(Ruta.RUTA_CLIENTE, clientes);
        } catch (IOException e) {
            System.err.println("Error guardando clientes: " + e.getMessage());
        }
    }

    private List<Cliente> leerDatos() {
        try {
            Object datos = Persistencia.deserializarObjeto(Ruta.RUTA_CLIENTE);
            if (datos != null) {
                return (List<Cliente>) datos;
            }
        } catch (Exception e) {
            System.err.println("Error cargando clientes: " + e.getMessage());
        }
        return new ArrayList<>();
    }



}
