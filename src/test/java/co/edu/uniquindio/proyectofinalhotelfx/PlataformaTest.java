package co.edu.uniquindio.proyectofinalhotelfx;

import co.edu.uniquindio.proyectofinalhotelfx.Modelo.Entidades.Alojamiento;
import co.edu.uniquindio.proyectofinalhotelfx.Modelo.Entidades.Cliente;
import co.edu.uniquindio.proyectofinalhotelfx.Modelo.Enums.Ciudad;
import co.edu.uniquindio.proyectofinalhotelfx.Modelo.Enums.OfertaTipo;
import co.edu.uniquindio.proyectofinalhotelfx.Modelo.Enums.ServiciosIncluidos;
import co.edu.uniquindio.proyectofinalhotelfx.Modelo.Enums.TipoAlojamiento;
import co.edu.uniquindio.proyectofinalhotelfx.Servicios.Plataforma;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class PlataformaTest {

    Plataforma plataforma = new Plataforma();

    @Test
    public void registrarClienteTest() {

        // 1. Todos los campos vacíos
        assertThrows(Exception.class, () -> plataforma.registrarCliente("", "", "", "", "", ""));

        // 2. Nombre inválido
        assertThrows(Exception.class, () -> plataforma.registrarCliente("Juan123", "12345678", "12345678", "correo@gmail.com", "Password1", "Password1"));

        // 3. Cédula con letras
        assertThrows(Exception.class, () -> plataforma.registrarCliente("Juan Perez", "abc12345", "12345678", "correo@gmail.com", "Password1", "Password1"));

        // 4. Cédula muy corta
        assertThrows(Exception.class, () -> plataforma.registrarCliente("Juan Perez", "1234567", "12345678", "correo@gmail.com", "Password1", "Password1"));

        // 5. Cédula muy larga
        assertThrows(Exception.class, () -> plataforma.registrarCliente("Juan Perez", "12345678901", "12345678", "correo@gmail.com", "Password1", "Password1"));

        // 6. Teléfono muy corto
        assertThrows(Exception.class, () -> plataforma.registrarCliente("Juan Perez", "12345678", "1234567", "correo@gmail.com", "Password1", "Password1"));

        // 7. Teléfono con letras
        assertThrows(Exception.class, () -> plataforma.registrarCliente("Juan Perez", "12345678", "123abc45", "correo@gmail.com", "Password1", "Password1"));

        // 8. Correo no Gmail
        assertThrows(Exception.class, () -> plataforma.registrarCliente("Juan Perez", "12345678", "12345678", "correo@yahoo.com", "Password1", "Password1"));

        // 9. Contraseña sin mayúscula
        assertThrows(Exception.class, () -> plataforma.registrarCliente("Juan Perez", "12345678", "12345678", "correo@gmail.com", "password1", "password1"));

        // 10. Contraseña sin número
        assertThrows(Exception.class, () -> plataforma.registrarCliente("Juan Perez", "12345678", "12345678", "correo@gmail.com", "Password", "Password"));

        // 11. Contraseña muy corta
        assertThrows(Exception.class, () -> plataforma.registrarCliente("Juan Perez", "12345678", "12345678", "correo@gmail.com", "Pass1", "Pass1"));

        // 12. Contraseñas diferentes
        assertThrows(Exception.class, () -> plataforma.registrarCliente("Juan Perez", "12345678", "12345678", "correo@gmail.com", "Password1", "Password2"));

        // 13. Caso exitoso
        assertDoesNotThrow(() -> plataforma.registrarCliente("Juan Perez", "12345678", "123456789", "correo@gmail.com", "Password1", "Password1"));
    }


    @Test
    public void registrarAlojamientoTest() {

        Ciudad ciudad = Ciudad.ARMENIA;
        List<ServiciosIncluidos> servicios = List.of(ServiciosIncluidos.WIFI);
        TipoAlojamiento tipo = TipoAlojamiento.APARTAMENTO;

        // 1. Nombre nulo
        assertThrows(IllegalArgumentException.class, () ->
                plataforma.registrarAlojamiento(null, ciudad, "Bonito lugar", 100000, "imagen.png", servicios, 2, 1, true, tipo));

        // 2. Nombre vacío
        assertThrows(IllegalArgumentException.class, () ->
                plataforma.registrarAlojamiento("", ciudad, "Bonito lugar", 100000, "imagen.png", servicios, 2, 1, true, tipo));

        // 3. Ciudad nula
        assertThrows(IllegalArgumentException.class, () ->
                plataforma.registrarAlojamiento("Casa Linda", null, "Bonito lugar", 100000, "imagen.png", servicios, 2, 1, true, tipo));

        // 4. Descripción nula
        assertThrows(IllegalArgumentException.class, () ->
                plataforma.registrarAlojamiento("Casa Linda", ciudad, null, 100000, "imagen.png", servicios, 2, 1, true, tipo));

        // 5. Descripción vacía
        assertThrows(IllegalArgumentException.class, () ->
                plataforma.registrarAlojamiento("Casa Linda", ciudad, "", 100000, "imagen.png", servicios, 2, 1, true, tipo));

        // 6. Precio cero
        assertThrows(IllegalArgumentException.class, () ->
                plataforma.registrarAlojamiento("Casa Linda", ciudad, "Bonito lugar", 0, "imagen.png", servicios, 2, 1, true, tipo));

        // 7. Precio negativo
        assertThrows(IllegalArgumentException.class, () ->
                plataforma.registrarAlojamiento("Casa Linda", ciudad, "Bonito lugar", -50000, "imagen.png", servicios, 2, 1, true, tipo));

        // 8. Servicios nulos
        assertThrows(IllegalArgumentException.class, () ->
                plataforma.registrarAlojamiento("Casa Linda", ciudad, "Bonito lugar", 100000, "imagen.png", null, 2, 1, true, tipo));

        // 9. Tipo de alojamiento nulo
        assertThrows(IllegalArgumentException.class, () ->
                plataforma.registrarAlojamiento("Casa Linda", ciudad, "Bonito lugar", 100000, "imagen.png", servicios, 2, 1, true, null));

        // 10. Caso exitoso
        assertDoesNotThrow(() ->
                plataforma.registrarAlojamiento("Casa Linda", ciudad, "Bonito lugar", 100000, "imagen.png", servicios, 2, 1, true, tipo));
    }


    @Test
    public void consultarSaldoTest() {
        String cedulaValida = "87654321";
        String cedulaInvalida = "";

        // 1. Cédula vacía
        assertThrows(Exception.class, () -> plataforma.consultarSaldo(cedulaInvalida));

        // 2. Cliente no existe
        assertThrows(Exception.class, () -> plataforma.consultarSaldo("99999999"));

        // 3. Caso exitoso (primero registrar cliente)
        assertDoesNotThrow(() -> plataforma.registrarCliente("Maria Lopez", cedulaValida, "987654321", "maria@gmail.com", "Password1", "Password1"));
        assertDoesNotThrow(() -> plataforma.validarCodigoVerificacion("maria@gmail.com", "1234"));
        assertDoesNotThrow(() -> plataforma.consultarSaldo(cedulaValida));
    }

    @Test
    public void recuperarContrasenaTest() {
        String correoValido = "test@gmail.com";
        String correoVacio = "";
        String correoInexistente = "noexiste@gmail.com";

        // 1. Correo vacío
        assertThrows(Exception.class, () -> plataforma.recuperarContrasena(correoVacio));

        // 2. Correo que no existe
        assertThrows(Exception.class, () -> plataforma.recuperarContrasena(correoInexistente));

        // 3. Caso exitoso (primero registrar cliente)
        assertDoesNotThrow(() -> plataforma.registrarCliente("Test User", "11111111", "111111111", correoValido, "Password1", "Password1"));
        assertDoesNotThrow(() -> plataforma.validarCodigoVerificacion(correoValido, "1234"));
        assertDoesNotThrow(() -> plataforma.recuperarContrasena(correoValido));
    }


    @Test
    public void agregarReservaTest() {
        String idCliente = "12345678";
        String idAlojamiento = "ALJ001";
        LocalDateTime fechaInicial = LocalDateTime.now().plusDays(1);
        LocalDateTime fechaFinal = LocalDateTime.now().plusDays(5);
        LocalDateTime fechaCreacion = LocalDateTime.now();

        // 1. ID Cliente vacío
        assertThrows(Exception.class, () ->
                plataforma.agregarReserva("", idAlojamiento, fechaInicial, fechaFinal, 2, 200000.0, fechaCreacion));

        // 2. ID Cliente nulo
        assertThrows(Exception.class, () ->
                plataforma.agregarReserva(null, idAlojamiento, fechaInicial, fechaFinal, 2, 200000.0, fechaCreacion));

        // 3. ID Alojamiento vacío
        assertThrows(Exception.class, () ->
                plataforma.agregarReserva(idCliente, "", fechaInicial, fechaFinal, 2, 200000.0, fechaCreacion));

        // 4. ID Alojamiento nulo
        assertThrows(Exception.class, () ->
                plataforma.agregarReserva(idCliente, null, fechaInicial, fechaFinal, 2, 200000.0, fechaCreacion));

        // 5. Fecha inicial nula
        assertThrows(Exception.class, () ->
                plataforma.agregarReserva(idCliente, idAlojamiento, null, fechaFinal, 2, 200000.0, fechaCreacion));

        // 6. Fecha final nula
        assertThrows(Exception.class, () ->
                plataforma.agregarReserva(idCliente, idAlojamiento, fechaInicial, null, 2, 200000.0, fechaCreacion));

        // 7. Número de huéspedes cero
        assertThrows(Exception.class, () ->
                plataforma.agregarReserva(idCliente, idAlojamiento, fechaInicial, fechaFinal, 0, 200000.0, fechaCreacion));

        // 8. Número de huéspedes negativo
        assertThrows(Exception.class, () ->
                plataforma.agregarReserva(idCliente, idAlojamiento, fechaInicial, fechaFinal, -1, 200000.0, fechaCreacion));

        // 9. Total cero
        assertThrows(Exception.class, () ->
                plataforma.agregarReserva(idCliente, idAlojamiento, fechaInicial, fechaFinal, 2, 0.0, fechaCreacion));

        // 10. Total negativo
        assertThrows(Exception.class, () ->
                plataforma.agregarReserva(idCliente, idAlojamiento, fechaInicial, fechaFinal, 2, -100000.0, fechaCreacion));

        // 11. Fecha creación nula
        assertThrows(Exception.class, () ->
                plataforma.agregarReserva(idCliente, idAlojamiento, fechaInicial, fechaFinal, 2, 200000.0, null));
    }


}


