package co.edu.uniquindio.proyectofinalhotelfx.Servicios;

import co.edu.uniquindio.proyectofinalhotelfx.Modelo.Entidades.Cliente;
import co.edu.uniquindio.proyectofinalhotelfx.Repo.AlojamientoRepository;
import co.edu.uniquindio.proyectofinalhotelfx.Repo.ClienteRepository;

import java.util.List;

public class ServicioCliente {
    public ServicioCliente(ClienteRepository clienteRepository, AlojamientoRepository alojamientoRepository) {
    }
    // REGISTRAR UN NUEVO CLIENTE
    public void registrarCliente(Cliente cliente) throws Exception {
        // Validar si ya existe por cédula o correo
        // Guardar en la base o en ClienteRepository
    }

    // INICIAR SESIÓN
    public Cliente iniciarSesion(String correo, String password) throws Exception {
        // Buscar por correo, validar contraseña
        // Retornar cliente si coincide
        return null;
    }

    // EDITAR CLIENTE
    public void editarCliente(Cliente clienteActualizado) throws Exception {
        // Buscar por cédula o ID
        // Reemplazar información
    }

    // ELIMINAR CLIENTE
    public void eliminarCliente(String cedula) throws Exception {
        // Buscar cliente y eliminar del repositorio
    }

    // CAMBIAR CONTRASEÑA
    public void cambiarContrasena(String correo, String nuevaContrasena) throws Exception {
        // Buscar cliente por correo y actualizar contraseña
    }

    // ENVIAR CÓDIGO DE RECUPERACIÓN (usa EnvioCorreo)
    public void enviarCodigoRecuperacion(String correo) throws Exception {
        // Generar código aleatorio
        // Guardar temporalmente (mapa o atributo)
        // Llamar EnvioCorreo.enviarCodigo(...)
    }

    // VALIDAR CÓDIGO DE RECUPERACIÓN
    public boolean validarCodigoRecuperacion(String correo, String codigoIngresado) {
        // Comparar código ingresado con el almacenado
        return false;
    }

    // LISTAR TODOS LOS CLIENTES (opcional si lo usas en GUI)
    public List<Cliente> listarClientes() {
        // Retornar lista de clientes del repositorio
        return null;
    }
}
