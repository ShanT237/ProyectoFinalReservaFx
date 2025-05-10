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
    private final Map<String, String> codigosVerificacion = new HashMap<>();



    public void registrarCliente(String nombre, String cedula,  String telefono, String  correo, String password, String confirmarPassword ) throws Exception {

        String error = "";

        // Validar campos vacíos
        if (nombre.isEmpty() || correo.isEmpty() || telefono.isEmpty() || cedula.isEmpty() || password.isEmpty() || confirmarPassword.isEmpty()) {
            error = ("Todos los campos son obligatorios");
        }

        // Validar formato de nombre (solo letras y espacios)
        if (!nombre.matches("[a-zA-Z\\s]+")) {
            error += ("El nombre solo debe contener letras y espacios");
        }

        // Validar cédula
        if (!cedula.matches("\\d+") || cedula.length() < 8 || cedula.length() > 10) {
            error += ("La cédula debe contener entre 8 y 10 números");
        }

        // Validar teléfono
        if (!telefono.matches("\\d{8,10}")) {
            error += ("El número de teléfono debe tener entre 8 y 10 dígitos");
        }

        // Validar correo
        if (!correo.matches("^[a-zA-Z0-9._%+-]+@gmail\\.com$")) {
            error += ("El correo debe ser un correo Gmail válido");
        }

        // Validar contraseña (mínimo 8 caracteres, al menos una mayúscula, una minúscula y un número)
        if (!password.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{8,}$")) {
            error += ("La contraseña debe tener al menos 8 caracteres, una mayúscula, una minúscula y un número");
        }

        // Validar coincidencia de contraseñas
        if (!password.equals(confirmarPassword)) {
            error += ("Las contraseñas no coinciden");
        }

        if(!error.isEmpty()){
            throw new Exception(error);
        }

        Random random = new Random();
        int codigo = random.nextInt(9000) + 1000;

        codigosVerificacion.put(correo, String.valueOf(codigo));

        Cliente cliente = Cliente.builder()
                .correo(correo)
                .password(password)
                .cedula(cedula)
                .activo(false)
                .codigoActivacion(codigo)
                .telefono(telefono).build();
        clienteRepository.guardar(cliente);

        EnvioCorreo.enviarCodigo(
                correo,
                "Su código de activación es "+codigo,
                "Código de validación de registro"
        );

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


    // VALIDAR CÓDIGO DE RECUPERACIÓN


    // LISTAR TODOS LOS CLIENTES
    public List<Cliente> listarClientes() {
        return clienteRepository.obtenerTodos();
    }

    public void bloquearUsuario(String id){
        Cliente cliente = clienteRepository.buscarPorCedula(id);
        clienteRepository.agregarUsuarioBloqueado(cliente);
        clienteRepository.eliminar(id);

    }

    // VALIDAR CÓDIGO DE RECUPERACIÓN
    public boolean validarCodigoVerificacion(String correo, String codigoIngresado) {
        String codigoCorrecto = codigosVerificacion.get(correo);
        if (codigoCorrecto != null && codigoCorrecto.equals(codigoIngresado)) {
            try {
                Cliente cliente = clienteRepository.buscarPorCorreo(correo);
                cliente.setActivo(true); // activar
                clienteRepository.actualizar(cliente); // guardar el cambio
                codigosVerificacion.remove(correo); // opcional: eliminar código
                return true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }
}

