package co.edu.uniquindio.proyectofinalhotelfx;

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
    public void calcularPorcentajeReservasPorTipoTest() {
        assertDoesNotThrow(() -> plataforma.calcularPorcentajeReservasPorTipo());
    }

    @Test
    public void registrarAlojamientoTest() {

        Ciudad ciudad = Ciudad.ARMENIA;
        List<ServiciosIncluidos> servicios = List.of(ServiciosIncluidos.WIFI);
        TipoAlojamiento tipo = TipoAlojamiento.APARTAMENTO;

        assertThrows(IllegalArgumentException.class, () ->
                plataforma.registrarAlojamiento(null, ciudad, "Bonito lugar", 100000, "imagen.png", servicios, 2, 1, true, tipo));

        assertThrows(IllegalArgumentException.class, () ->
                plataforma.registrarAlojamiento("", ciudad, "Bonito lugar", 100000, "imagen.png", servicios, 2, 1, true, tipo));

        assertThrows(IllegalArgumentException.class, () ->
                plataforma.registrarAlojamiento("Casa Linda", null, "Bonito lugar", 100000, "imagen.png", servicios, 2, 1, true, tipo));

        assertThrows(IllegalArgumentException.class, () ->
                plataforma.registrarAlojamiento("Casa Linda", ciudad, null, 100000, "imagen.png", servicios, 2, 1, true, tipo));

        assertThrows(IllegalArgumentException.class, () ->
                plataforma.registrarAlojamiento("Casa Linda", ciudad, "", 100000, "imagen.png", servicios, 2, 1, true, tipo));

        assertThrows(IllegalArgumentException.class, () ->
                plataforma.registrarAlojamiento("Casa Linda", ciudad, "Bonito lugar", 0, "imagen.png", servicios, 2, 1, true, tipo));

        assertThrows(IllegalArgumentException.class, () ->
                plataforma.registrarAlojamiento("Casa Linda", ciudad, "Bonito lugar", -50000, "imagen.png", servicios, 2, 1, true, tipo));

        assertThrows(IllegalArgumentException.class, () ->
                plataforma.registrarAlojamiento("Casa Linda", ciudad, "Bonito lugar", 100000, "imagen.png", null, 2, 1, true, tipo));

        assertThrows(IllegalArgumentException.class, () ->
                plataforma.registrarAlojamiento("Casa Linda", ciudad, "Bonito lugar", 100000, "imagen.png", servicios, 2, 1, true, null));

        assertDoesNotThrow(() ->
                plataforma.registrarAlojamiento("Casa Linda", ciudad, "Bonito lugar", 100000, "imagen.png", servicios, 2, 1, true, tipo));
    }

    @Test
    public void consultarSaldoTest() {
        String cedulaValida = "87654321";
        String cedulaInvalida = "";

        assertThrows(Exception.class, () -> plataforma.consultarSaldo(cedulaInvalida));

        assertThrows(Exception.class, () -> plataforma.consultarSaldo("99999999"));

        assertDoesNotThrow(() -> plataforma.registrarCliente("Maria Lopez", cedulaValida, "987654321", "maria@gmail.com", "Password1", "Password1"));
        assertDoesNotThrow(() -> plataforma.validarCodigoVerificacion("maria@gmail.com", "1234"));
        assertDoesNotThrow(() -> plataforma.consultarSaldo(cedulaValida));
    }

    @Test
    public void recuperarContrasenaTest() {
        String correoValido = "test@gmail.com";
        String correoVacio = "";
        String correoInexistente = "noexiste@gmail.com";

        assertThrows(Exception.class, () -> plataforma.recuperarContrasena(correoVacio));

        assertThrows(Exception.class, () -> plataforma.recuperarContrasena(correoInexistente));

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

        assertThrows(Exception.class, () ->
                plataforma.agregarReserva("", idAlojamiento, fechaInicial, fechaFinal, 2, 200000.0, fechaCreacion));

        assertThrows(Exception.class, () ->
                plataforma.agregarReserva(null, idAlojamiento, fechaInicial, fechaFinal, 2, 200000.0, fechaCreacion));

        assertThrows(Exception.class, () ->
                plataforma.agregarReserva(idCliente, "", fechaInicial, fechaFinal, 2, 200000.0, fechaCreacion));

        assertThrows(Exception.class, () ->
                plataforma.agregarReserva(idCliente, null, fechaInicial, fechaFinal, 2, 200000.0, fechaCreacion));

        assertThrows(Exception.class, () ->
                plataforma.agregarReserva(idCliente, idAlojamiento, null, fechaFinal, 2, 200000.0, fechaCreacion));

        assertThrows(Exception.class, () ->
                plataforma.agregarReserva(idCliente, idAlojamiento, fechaInicial, null, 2, 200000.0, fechaCreacion));

        assertThrows(Exception.class, () ->
                plataforma.agregarReserva(idCliente, idAlojamiento, fechaInicial, fechaFinal, 0, 200000.0, fechaCreacion));

        assertThrows(Exception.class, () ->
                plataforma.agregarReserva(idCliente, idAlojamiento, fechaInicial, fechaFinal, -1, 200000.0, fechaCreacion));

        assertThrows(Exception.class, () ->
                plataforma.agregarReserva(idCliente, idAlojamiento, fechaInicial, fechaFinal, 2, 0.0, fechaCreacion));

        assertThrows(Exception.class, () ->
                plataforma.agregarReserva(idCliente, idAlojamiento, fechaInicial, fechaFinal, 2, -100000.0, fechaCreacion));

        assertThrows(Exception.class, () ->
                plataforma.agregarReserva(idCliente, idAlojamiento, fechaInicial, fechaFinal, 2, 200000.0, null));
    }

    @Test
    public void listarReservasTest() {
        assertDoesNotThrow(() -> plataforma.getServicioReserva().listarReservas());
    }

    @Test
    public void listarReservasPorClienteTest() {
        assertDoesNotThrow(() -> plataforma.getServicioReserva().getReservaRepository().obtenerTodos());
    }

    @Test
    public void bloquearClienteTest() {
        assertThrows(Exception.class, () -> plataforma.bloquearCliente(null));
        assertThrows(Exception.class, () -> plataforma.bloquearCliente(""));
    }

    @Test
    public void loginClienteTest() {
        assertThrows(Exception.class, () -> plataforma.loginCliente(null, "password123"));
        assertThrows(Exception.class, () -> plataforma.loginCliente("", "password123"));
        assertThrows(Exception.class, () -> plataforma.loginCliente("email@example.com", ""));
        assertThrows(Exception.class, () -> plataforma.loginCliente("nonexistent@example.com", "password123"));
    }

    @Test
    public void registrarOfertaTest() {
        Ciudad ciudad = Ciudad.ARMENIA;
        TipoAlojamiento tipoAlojamiento = TipoAlojamiento.APARTAMENTO;
        LocalDateTime fechaInicio = LocalDateTime.now();
        LocalDateTime fechaFin = fechaInicio.plusDays(5);

        assertThrows(Exception.class, () -> plataforma.registrarOferta(ciudad, tipoAlojamiento, "1", null, "desc", fechaInicio, fechaFin, true, OfertaTipo.ESTADIAPROLONGADA, 2, 10.0, "image.png"));

        assertThrows(Exception.class, () -> plataforma.registrarOferta(ciudad, tipoAlojamiento, "1", "Offer", "desc", fechaInicio, fechaInicio.minusDays(1), true, OfertaTipo.TEMPORADA, 2, 10.0, "image.png"));

        assertThrows(Exception.class, () -> plataforma.registrarOferta(ciudad, tipoAlojamiento, "1", "Offer", "desc", fechaInicio, fechaFin, true, OfertaTipo.TEMPORADA, 2, -5.0, "image.png"));
    }

    @Test
    public void actualizarAlojamientoTest() {
        Ciudad ciudad = Ciudad.ARMENIA;

        assertThrows(Exception.class, () -> plataforma.actualizarAlojamiento(null, "Casa Bella", ciudad, "Moderno y elegante", 200000, "image.png", List.of(ServiciosIncluidos.WIFI), 4, 2, true, TipoAlojamiento.APARTAMENTO));

        assertThrows(Exception.class, () -> plataforma.actualizarAlojamiento("ALO001", "Casa Bella", ciudad, "Moderno y elegante", -200000, "image.png", List.of(ServiciosIncluidos.WIFI), 4, 2, true, TipoAlojamiento.APARTAMENTO));

        assertThrows(Exception.class, () -> plataforma.actualizarAlojamiento("ALO001", "Casa Bella", ciudad, "Moderno y elegante", 200000, "image.png", List.of(ServiciosIncluidos.WIFI), 4, 2, true, null));
    }
}

