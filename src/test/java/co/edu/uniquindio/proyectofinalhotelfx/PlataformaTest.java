package co.edu.uniquindio.proyectofinalhotelfx;

import co.edu.uniquindio.proyectofinalhotelfx.Servicios.Plataforma;
import org.junit.jupiter.api.Test;

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
    public void registrarAlojamientoTest(){}



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
