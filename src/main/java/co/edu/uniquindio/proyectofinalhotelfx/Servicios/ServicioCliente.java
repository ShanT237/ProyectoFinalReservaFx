package co.edu.uniquindio.proyectofinalhotelfx.Servicios;

import co.edu.uniquindio.proyectofinalhotelfx.Modelo.Entidades.Cliente;
import co.edu.uniquindio.proyectofinalhotelfx.Repo.AlojamientoRepository;
import co.edu.uniquindio.proyectofinalhotelfx.Repo.ClienteRepository;
import co.edu.uniquindio.proyectofinalhotelfx.Singleton.EnvioCorreo;
import lombok.Builder;
import lombok.Getter;

import java.util.*;

@Getter
@Builder
public class ServicioCliente {

    private final ClienteRepository clienteRepository;
    private final ServicioAlojamiento servicioAlojamiento;

    private final Map<String, String> codigosRecuperacion = new HashMap<>();



    public void registrarCliente(String nombre, String id, String telefono, String  email, String password ) throws Exception {

    }


    public Cliente iniciarSesion(String correo, String password) throws Exception {

        return  null;
    }

    // EDITAR CLIENTE
    public void editarCliente(Cliente clienteActualizado) throws Exception {
        Cliente clienteExistente = clienteRepository.buscarPorCedula(clienteActualizado.getCedula());
        if (clienteExistente == null) {
            throw new Exception("Cliente no encontrado");
        }
        clienteRepository.actualizar(clienteActualizado);
    }

    // ELIMINAR CLIENTE
    public void eliminarCliente(String cedula) throws Exception {
        Cliente cliente = clienteRepository.buscarPorCedula(cedula);
        if (cliente == null) {
            throw new Exception("Cliente no encontrado");
        }
        clienteRepository.eliminar(cedula);
    }

    // BUSCAR CLIENTE POR CÉDULA
    public Cliente buscarCliente(String cedula) throws Exception {
        Cliente cliente = clienteRepository.buscarPorCedula(cedula);
        if (cliente == null) {
            throw new Exception("Cliente no encontrado");
        }
        return cliente;
    }


    // CAMBIAR CONTRASEÑA
    public void cambiarContrasena(String correo, String nuevaContrasena) throws Exception {
        Cliente cliente = clienteRepository.buscarPorCorreo(correo);
        if (cliente == null) {
            throw new Exception("Correo no registrado");
        }
        cliente.setPassword(nuevaContrasena);
        clienteRepository.actualizar(cliente);
    }

    // ENVIAR CÓDIGO DE RECUPERACIÓN
    public void enviarCodigoRecuperacion(String correo) throws Exception {
        Cliente cliente = clienteRepository.buscarPorCorreo(correo);
        if (cliente == null) {
            throw new Exception("Correo no registrado");
        }

        String codigo = String.valueOf((int)(Math.random() * 900000) + 100000); // Código de 6 cifras
        codigosRecuperacion.put(correo, codigo);

        EnvioCorreo.enviarCodigo(correo, codigo);
    }

    // VALIDAR CÓDIGO DE RECUPERACIÓN
    public boolean validarCodigoRecuperacion(String correo, String codigoIngresado) {
        String codigoCorrecto = codigosRecuperacion.get(correo);
        return codigoCorrecto != null && codigoCorrecto.equals(codigoIngresado);
    }

    // LISTAR TODOS LOS CLIENTES
    public List<Cliente> listarClientes() {
        return clienteRepository.obtenerTodos();
    }

    public void bloquearUsuario(String id){
        Cliente cliente = clienteRepository.buscarPorCedula(id);
        clienteRepository.agregarUsuarioBloqueado(cliente);
        clienteRepository.eliminar(id);

    }

}

