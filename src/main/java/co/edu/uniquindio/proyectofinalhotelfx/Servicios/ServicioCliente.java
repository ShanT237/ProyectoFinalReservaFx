package co.edu.uniquindio.proyectofinalhotelfx.Servicios;

import co.edu.uniquindio.proyectofinalhotelfx.Modelo.Entidades.Cliente;
import co.edu.uniquindio.proyectofinalhotelfx.Notificacion.Notificacion;
import co.edu.uniquindio.proyectofinalhotelfx.Repo.AlojamientoRepository;
import co.edu.uniquindio.proyectofinalhotelfx.Repo.ClienteRepository;
import co.edu.uniquindio.proyectofinalhotelfx.Singleton.EnvioCorreo;
import co.edu.uniquindio.proyectofinalhotelfx.Singleton.SesionCliente;
import lombok.Builder;
import lombok.Getter;

import java.util.*;
@Builder
@Getter
public class ServicioCliente {

    private final ClienteRepository clienteRepository;
    private final ServicioAlojamiento servicioAlojamiento;
    public final SesionCliente sesionCliente = SesionCliente.instancia();


    private final Map<String, String> codigosRecuperacion = new HashMap<>();
    private final Map<String, String> codigosVerificacion = new HashMap<>();

    public ServicioCliente(ClienteRepository clienteRepository, ServicioAlojamiento servicioAlojamiento) {
        this.clienteRepository = clienteRepository;
        this.servicioAlojamiento = servicioAlojamiento;
    }


    public void registrarCliente(String nombre, String cedula, String telefono, String  correo, String password, String confirmarPassword ) throws Exception {

        validarDatos(nombre, cedula,  telefono, correo, password, confirmarPassword);

        int codigo = crearCodigo();

        codigosVerificacion.put(correo, String.valueOf(codigo));
        Cliente cliente = crearCliente(nombre, cedula,  telefono, codigo, correo, password, confirmarPassword);
        clienteRepository.guardar(cliente);

        Notificacion.enviarNotificacion(correo,
                "Su código de activación es "+ codigo,
                "Código de validación de registro");


    }

    public int crearCodigo(){
        Random random = new Random();
        int codigo = random.nextInt(9000) + 1000;
        return codigo;
    }

    public Cliente crearCliente(String nombre, String cedula,  String telefono, int codigo, String  correo, String password, String confirmarPassword ) throws Exception {

        Cliente cliente = Cliente.builder()
                .correo(correo)
                .password(password)
                .cedula(cedula)
                .activo(false)
                .codigoActivacion(codigo)
                .telefono(telefono).build();
        return cliente;
    }

    public void validarDatos(String nombre, String cedula,  String telefono, String  correo, String password, String confirmarPassword ) throws Exception {
        if (nombre.isEmpty() || correo.isEmpty() || telefono.isEmpty() || cedula.isEmpty() || password.isEmpty() || confirmarPassword.isEmpty()) {
            throw new Exception("Todos los campos son obligatorios");
        }

        if (!nombre.matches("[a-zA-Z\\s]+")) {
            throw new Exception("El nombre solo debe contener letras y espacios");
        }

        if (!cedula.matches("\\d+")) {
            throw new Exception("La cédula debe contener solo números");
        }
        if (cedula.length() < 8 || cedula.length() > 10) {
            throw new Exception("La cédula debe tener entre 8 y 10 dígitos");
        }

        if (!telefono.matches("\\d{8,10}")) {
            throw new Exception("El número de teléfono debe tener entre 8 y 10 dígitos");
        }

        if (!correo.matches("^[a-zA-Z0-9._%+-]+@gmail\\.com$")) {
            throw new Exception("El correo debe ser un correo Gmail válido");
        }

        if (!password.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{8,}$")) {
            throw new Exception("La contraseña debe tener al menos 8 caracteres, una mayúscula, una minúscula y un número");
        }

        if (!password.equals(confirmarPassword)) {
            throw new Exception("Las contraseñas no coinciden");
        }
    }

    public boolean iniciarSesion(String correo, String password) throws Exception {
        verificarDatosSesion(correo, password);
        Cliente cliente = clienteRepository.buscarPorCorreo(correo);

        if (cliente == null) {
            throw new Exception("Correo no registrado");
        }

        if (!cliente.getPassword().equals(password)) {
            throw new Exception("Contraseña incorrecta");
        }

        sesionCliente.setUsuario(cliente);
        return true;
    }

    public void verificarDatosSesion(String correo, String password) throws Exception {
        if (correo.isEmpty() || password.isEmpty()) {
            throw new Exception("Todos los campos son obligatorios");
        }

        if (!correo.matches("^[a-zA-Z0-9._%+-]+@gmail\\.com$")) {
            throw new Exception("El correo debe ser un correo Gmail válido");
        }

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
                cliente.setActivo(true);
                clienteRepository.actualizar(cliente);
                codigosVerificacion.remove(correo);
                return true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }
}

