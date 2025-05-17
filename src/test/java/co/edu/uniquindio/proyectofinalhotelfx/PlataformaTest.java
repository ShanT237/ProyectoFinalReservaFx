package co.edu.uniquindio.proyectofinalhotelfx;

import co.edu.uniquindio.proyectofinalhotelfx.Modelo.Enums.Ciudad;
import co.edu.uniquindio.proyectofinalhotelfx.Modelo.Enums.ServiciosIncluidos;
import co.edu.uniquindio.proyectofinalhotelfx.Modelo.Enums.TipoAlojamiento;
import co.edu.uniquindio.proyectofinalhotelfx.Servicios.Plataforma;
import org.junit.jupiter.api.Test;

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
    public void registrarReservaTest(){}




    @Test
    public void registrarPagoTest(){}



    @Test
    public void registrarOfertaTest(){}


    @Test
    public void registrarEstadiaTest(){}

    @Test
    public void actualizarContrasenaTest(){}




}
