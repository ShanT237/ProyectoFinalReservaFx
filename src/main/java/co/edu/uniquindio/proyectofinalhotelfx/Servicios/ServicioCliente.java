package co.edu.uniquindio.proyectofinalhotelfx.Servicios;

import co.edu.uniquindio.proyectofinalhotelfx.Controladores.ControladorPrincipal;
import co.edu.uniquindio.proyectofinalhotelfx.Modelo.Entidades.Cliente;
import co.edu.uniquindio.proyectofinalhotelfx.Modelo.Entidades.Usuario;
import co.edu.uniquindio.proyectofinalhotelfx.Notificacion.Notificacion;
import co.edu.uniquindio.proyectofinalhotelfx.Repo.ClienteRepository;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.*;

@Builder
@Getter
public class ServicioCliente {

    private final ClienteRepository clienteRepository;
    private final ServicioAlojamiento servicioAlojamiento;
    private final ServicioBilleteraVirtual servicioBilleteraVirtual;
    private final ServicioReserva servicioReserva;

    private final Map<String, String> codigosRecuperacion = new HashMap<>();
    private final Map<String, String> codigosVerificacion = new HashMap<>();
    private static final long CODIGO_EXPIRACION = 600000; // 10 minutos


    public void registrarCliente(String nombre, String cedula, String telefono, String correo, String password, String confirmarPassword) throws Exception {
        validarDatos(nombre, cedula, telefono, correo, password, confirmarPassword);

        int codigo = generarCodigoVerificacion(correo);
        codigosVerificacion.put(correo, String.valueOf(codigo));

        if (!password.equals(confirmarPassword)) {
            throw new Exception("Las contraseñas no coinciden");
        }

        enviarCorreo(String.valueOf(correo), String.valueOf(codigo));
        Cliente cliente = crearCliente(nombre, cedula, telefono, codigo, correo, password, confirmarPassword);
        cliente.setActivo(false);
        clienteRepository.guardar(cliente);

        System.out.println(codigo);

    }

    public String generarCodigoRecuperacion(String correo) throws Exception {
        if (correo == null || correo.isEmpty()) {
            throw new Exception("El correo no puede estar vacío");
        }

        int codigo = crearCodigo();
        String codigoStr = String.valueOf(codigo);

        // Almacenar el código con su tiempo de generación
        codigosVerificacion.put(correo, codigoStr);

        // Enviar el código por correo
        try {
            Notificacion.enviarNotificacion(
                    correo,
                    "Su código de recuperación es: " + codigoStr,
                    "Código de Recuperación de Contraseña"
            );
        } catch (Exception e) {
            codigosVerificacion.remove(correo);
            throw new Exception("Error al enviar el código de verificación: " + e.getMessage());
        }

        return codigoStr;
    }


    public int crearCodigo() {
        Random random = new Random();
        return random.nextInt(9000) + 1000;
    }

    public Cliente crearCliente(String nombre, String cedula, String telefono, int codigo, String correo, String password, String confirmarPassword) {
        return Cliente.builder()
                .nombre(nombre)
                .correo(correo)
                .password(password)
                .cedula(cedula)
                .activo(false)
                .codigoActivacion(codigo)
                .telefono(telefono)
                .build();
    }

    public void validarDatoCorreo(String correo) throws Exception {
        if (correo.isEmpty()) {
            throw new Exception("Todos los campos son obligatorios");
        }
    }

    public void validarDatos(String nombre, String cedula, String telefono, String correo, String password, String confirmarPassword) throws Exception {
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

        if (!correo.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$")) {
            throw new Exception("El correo debe ser un correo electrónico válido");
        }


        if (!password.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{8,}$")) {
            throw new Exception("La contraseña debe tener al menos 8 caracteres, una mayúscula, una minúscula y un número");
        }

        if (!password.equals(confirmarPassword)) {
            throw new Exception("Las contraseñas no coinciden");
        }
    }

    public Cliente iniciarSesion(String correo, String password) throws Exception {
        verificarDatosSesion(correo, password);
        Cliente cliente = clienteRepository.buscarPorCorreo(correo);

        if (cliente == null) {
            throw new Exception("Correo no registrado");
        }

        if (!cliente.getPassword().equals(password)) {
            throw new Exception("Contraseña incorrecta");
        }

        if (!cliente.isActivo()) {

            validarEstadoCuenta(correo, cliente.isActivo());
            throw new Exception("Debe validar su estado de cuenta.");
        }

        return cliente;
    }

    public void verificarDatosSesion(String correo, String password) throws Exception {
        if (correo.isEmpty() || password.isEmpty()) {
            throw new Exception("Todos los campos son obligatorios");
        }

        if (!correo.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$")) {
            throw new Exception("El correo debe ser un correo electrónico válido");
        }

    }

    public void editarCliente(Cliente clienteActualizado) throws Exception {
        Cliente clienteExistente = clienteRepository.buscarPorCedula(clienteActualizado.getCedula());
        if (clienteExistente == null) {
            throw new Exception("Cliente no encontrado");
        }
        clienteRepository.actualizar(clienteActualizado);
    }

    public void eliminarCliente(String cedula) throws Exception {
        Cliente cliente = clienteRepository.buscarPorCedula(cedula);
        if (cliente == null) {
            throw new Exception("Cliente no encontrado");
        }
        clienteRepository.eliminar(cedula);
    }

    public Cliente buscarCliente(String cedula) throws Exception {
        Cliente cliente = clienteRepository.buscarPorCedula(cedula);
        if (cliente == null) {
            throw new Exception("Cliente no encontrado");
        }
        return cliente;
    }

    public void cambiarContrasena(String correo) throws Exception {
    }

    public List<Cliente> listarClientes() {
        return clienteRepository.obtenerTodos();
    }

    public void bloquearUsuario(String id) {
        Cliente cliente = clienteRepository.buscarPorCedula(id);
        clienteRepository.agregarUsuarioBloqueado(cliente);
        clienteRepository.eliminar(id);
    }

    public void actualizarContrasena(String correo, String nuevaContrasena, String confirmarPassword, String codigoIngresado) throws Exception {
        // Validaciones básicas
        if (correo == null || correo.isEmpty()) {
            throw new Exception("El correo no puede estar vacío");
        }
        if (codigoIngresado == null || codigoIngresado.isEmpty()) {
            throw new Exception("El código de verificación no puede estar vacío");
        }

        // Validar datos de la contraseña
        //validarDatosActualizarContraseña(codigoIngresado, nuevaContrasena, confirmarPassword);

        // Verificar si existe un código para este correo
        String codigoInfo = codigosVerificacion.get(correo);
        if (codigoInfo == null) {
            throw new Exception("No hay un código de verificación activo para este correo. Por favor, solicite uno nuevo.");
        }
        codigosVerificacion.remove(correo);

        if (!codigoInfo.equals(codigoIngresado)) {
            throw new Exception("El código es incorrecto. Por favor, verifique e intente nuevamente.");
        }

        try {
            // Buscar el cliente
            Cliente cliente = clienteRepository.buscarPorCorreo(correo);
            if (cliente == null) {
                throw new Exception("No se encontró una cuenta asociada a este correo.");
            }

            // Validar el nuevo password
            if (!nuevaContrasena.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{8,}$")) {
                throw new Exception("La nueva contraseña debe tener al menos 8 caracteres, una mayúscula, una minúscula y un número");
            }

            // Actualizar la contraseña
            cliente.setPassword(nuevaContrasena);
            clienteRepository.actualizar(cliente);

            // Limpiar el código usado
            codigosVerificacion.remove(correo);

        } catch (Exception e) {
            throw new Exception("Error al actualizar la contraseña: " + e.getMessage());
        }

    }


    private void validarDatosActualizarContraseña(String codigo, String contrasena, String confirmarPassword) throws Exception {
        if (codigo == null || contrasena == null || confirmarPassword == null ||
                codigo.isEmpty() || contrasena.isEmpty() || confirmarPassword.isEmpty()) {
            throw new Exception("Todos los campos son obligatorios");
        }

        if (!contrasena.equals(confirmarPassword)) {
            throw new Exception("Las contraseñas no coinciden");
        }
    }


    public boolean validarCodigoVerificacion(String correo, String codigoIngresado) {
        System.out.println("Validando código para: " + correo);

        String codigoCorrecto = codigosVerificacion.get(correo); // HashMap<String, String>

        System.out.println("Código ingresado: " + codigoIngresado);
        System.out.println("Código registrado: " + codigoCorrecto);

        if (codigoCorrecto != null && codigoCorrecto.equals(codigoIngresado)) {
            try {
                Cliente cliente = clienteRepository.buscarPorCorreo(correo);
                cliente.setActivo(true);
                clienteRepository.actualizar(cliente);

                // Eliminar el código ya usado
                codigosVerificacion.remove(correo);

                return true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return false;
    }


    public boolean validarEstadoCuenta(String correo, boolean estado) {
        System.out.println("validarEstadoCuenta");

        // Si el estado es verdadero, el cliente está activo y puede continuar
        if (estado) {
            return true;
        }

        // Si el estado es falso, se realiza el proceso de verificación
        try {
            Cliente cliente = clienteRepository.buscarPorCorreo(correo);
            if (cliente != null && !cliente.isActivo()) {

                // Generar nuevo código
                int nuevoCodigo = crearCodigo();

                // Verificar si ya existe uno anterior
                if (codigosVerificacion.containsKey(correo)) {
                    System.out.println("Reemplazando código anterior de verificación para el correo: " + correo);
                }

                // Guardar o reemplazar el código
                codigosVerificacion.put(correo, String.valueOf(nuevoCodigo));

                // Enviar el nuevo código por correo
                enviarCorreo(correo, String.valueOf(nuevoCodigo));

                // Actualizar el cliente
                cliente.setCodigoActivacion(nuevoCodigo);
                clienteRepository.actualizar(cliente);

                System.out.println("Cuenta inactiva. Se envió nuevo código de activación al correo: " + nuevoCodigo);

                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }


    public void recuperarContrasena(String correo) throws Exception {
        if (correo == null || correo.isEmpty()) {
            throw new Exception("Todos los campos son obligatorios");
        }
        if(!existeUsuarioPorCorreo(correo)){
            throw new Exception("El correo no existe");
        }

        String codigo = generarCodigoRecuperacion(correo);
        enviarCorreo(correo, codigo);


    }

    /*
    Bien
     */
    public void enviarCorreo(String correo, String codigo) throws Exception{

        try {
            Notificacion.enviarNotificacion(
                    correo,
                    "Su codigo de verificación es " + codigo, "Su codigo de verificación " + codigo
            );
        } catch (Exception e) {
            throw new Exception("Error al enviar el correo: " + e.getMessage());
        }

    }
    public int generarCodigoVerificacion(String correo) {
        int codigo = crearCodigo();
        codigosVerificacion.put(correo, String.valueOf(codigo));
        return codigo;
    }

    public boolean existeUsuarioPorCorreo(String correo) {
        Cliente cliente = clienteRepository.buscarPorCorreo(correo);
        return cliente != null;
    }


    public float consultarSaldo(String cedula) throws Exception {
        return servicioBilleteraVirtual.consultarSaldo(cedula);
    }

    public void recargarBilletera(String cedula, float monto) throws Exception {
        servicioBilleteraVirtual.recargarBilletera(cedula, monto);
    }

    public void agregarReserva(String idCliente, String idAlojamiento, LocalDateTime fechaInicial, LocalDateTime fechaFinal, int numeroHuespedes, double subtotal, LocalDateTime fechaCreacion) throws Exception {
        servicioReserva.agregarReserva(idCliente, idAlojamiento, fechaInicial, fechaFinal, numeroHuespedes, subtotal, fechaCreacion);
    }

    public void agregarReview(UUID reservaId, String comentario, int valoracion) throws Exception {
        servicioReserva.agregarReview(reservaId, comentario, valoracion);
    }
}