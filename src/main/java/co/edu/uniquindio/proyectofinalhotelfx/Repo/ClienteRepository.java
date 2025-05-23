package co.edu.uniquindio.proyectofinalhotelfx.Repo;

import co.edu.uniquindio.proyectofinalhotelfx.Modelo.Entidades.BilleteraVirtual;
import co.edu.uniquindio.proyectofinalhotelfx.Modelo.Entidades.Cliente;
import co.edu.uniquindio.proyectofinalhotelfx.Persistencia.Ruta;
import co.edu.uniquindio.proyectofinalhotelfx.Persistencia.Persistencia;
import lombok.Getter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Getter
public class ClienteRepository   {

    // Lista principal de clientes registrados
    private final List<Cliente> clientes;

    // Lista de clientes bloqueados
    private final List<Cliente> usuariosBloqueados;

    public ClienteRepository() {
        this.clientes = leerDatos();
        usuariosBloqueados = new ArrayList<>();
    }

    public Cliente buscarPorCedula(String cedula) throws Exception {
        for (Cliente c : clientes) {
            if (c.getCedula().equals(cedula)) {
                return c;
            }
        }
        throw new Exception("Cliente no encontrado");
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


        clientes.add(cliente);

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
        return clientes;
    }

    public void agregarUsuarioBloqueado(Cliente clienteBloqueado) {
        usuariosBloqueados.add(clienteBloqueado);
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


    public float consultarSaldo(String clienteId) throws Exception {
        for (Cliente cliente : clientes) {
            if (cliente.getCedula().equals(clienteId)) {
                if (cliente.getBilletera() == null) {
                    // Inicializar la billetera con constructor adecuado
                    cliente.setBilletera(new BilleteraVirtual(cliente.getCedula(), 0f, new ArrayList<>()));
                    guardarDatos();
                }
                return cliente.getBilletera().getSaldo();
            }
        }
        throw new Exception("Cliente no encontrado");
    }

    public void actualizarSaldo(String clienteId, float monto) {
        for (Cliente cliente : clientes) {
            if (cliente.getCedula().equals(clienteId)) {
                if (cliente.getBilletera() == null) {
                    cliente.setBilletera(new BilleteraVirtual(cliente.getCedula(), 0f, new ArrayList<>()));
                }
                cliente.getBilletera().setSaldo(cliente.getBilletera().getSaldo() + monto);
                guardarDatos();
                return;
            }
        }
    }

    public void descontar(String clienteId, float monto) {
        for (Cliente cliente : clientes) {
            if (cliente.getCedula().equals(clienteId)) {
                if (cliente.getBilletera() == null) {
                    cliente.setBilletera(new BilleteraVirtual(cliente.getCedula(), 0f, new ArrayList<>()));
                }
                cliente.getBilletera().setSaldo(cliente.getBilletera().getSaldo() - monto);
                guardarDatos();
                return;
            }
        }
    }


}