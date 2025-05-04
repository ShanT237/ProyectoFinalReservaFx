package co.edu.uniquindio.proyectofinalhotelfx.Modelo.Entidades;

import co.edu.uniquindio.proyectofinalhotelfx.Constantes.Constantes;
import co.edu.uniquindio.proyectofinalhotelfx.Enums.TipoTransaccion;
import co.edu.uniquindio.proyectofinalhotelfx.vo.PorcentajeGastosIngresos;
import co.edu.uniquindio.proyectofinalhotelfx.vo.SaldoTransaccionesBilletera;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static co.edu.uniquindio.proyectofinalhotelfx.Modelo.Entidades.Sesion.instancia;

@Getter
@Setter
@Builder
public class Plataforma {
    private static Plataforma instancia;
    private List<Usuario> usuarios;
    private List<Reserva> reservas;
    private List<Factura> facturas;
    private List<Resena> resenas;
    private List<BilleteraVirtual> billeteras;

    private Plataforma() {
        this.usuarios = new ArrayList<>();
        this.reservas = new ArrayList<>();
        this.facturas = new ArrayList<>();
        this.resenas = new ArrayList<>();
        this.billeteras = new ArrayList<>();
    }

    public static Plataforma getInstance() {
        if (instancia == null) {
            instancia = new Plataforma();
        }
        return instancia;
    }

    public void registrarUsuario(String nombre, String cedula, String telefono, String correo, String password) throws Exception {
        if (nombre == null || nombre.isEmpty()) throw new Exception("El nombre es obligatorio");
        if (cedula == null || cedula.isEmpty()) throw new Exception("El id es obligatorio");
        if (telefono == null || telefono.isEmpty()) throw new Exception("El telefono es obligatorio");
        if (correo == null || correo.isEmpty()) throw new Exception("El correo es obligatorio");
        if (password == null || password.isEmpty()) throw new Exception("La contraseña es obligatorio");

        if (buscarUsuario(cedula) != null) throw new Exception("El usuario ya existe");
        Usuario usuario = new Usuario(nombre, cedula, telefono, correo, password);

        usuarios.add(usuario);
        registrarBilletera(usuario);
    }

    public Usuario buscarUsuario(String cedula) {
        return usuarios.stream()
                .filter(u -> u.getCedula().equals(cedula))
                .findFirst()
                .orElse(null);
    }

    public void registrarBilletera(Usuario usuario) {
        String numero = crearNumeroBilletera();
        BilleteraVirtual billetera = new BilleteraVirtual(numero, 0, usuario);
        billeteras.add(billetera);
    }

    private String crearNumeroBilletera() {
        String numero = generarNumeroAleatorio();
        while (buscarBilletera(numero) != null) {
            numero = generarNumeroAleatorio();
        }
        return numero;
    }

    private String generarNumeroAleatorio() {
        StringBuilder numero = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            numero.append((int) (Math.random() * 10));
        }
        return numero.toString();
    }

    public BilleteraVirtual buscarBilletera(String numero) {
        return billeteras.stream()
                .filter(b -> b.getNumero().equals(numero))
                .findFirst()
                .orElse(null);
    }

    public BilleteraVirtual buscarBilleteraUsuario(String idUsuario) {
        return billeteras.stream()
                .filter(b -> b.getUsuario().getCedula().equals(idUsuario))
                .findFirst()
                .orElse(null);
    }

    public boolean verificarUsuario(String cedula, String password) {
        return billeteras.stream()
                .anyMatch(b -> b.getUsuario().getCedula().equals(cedula) && b.getUsuario().getPassword().equals(password));
    }

    public SaldoTransaccionesBilletera consultarSaldoYTransacciones(String cedula, String password) throws Exception {
        Usuario usuario = buscarUsuario(cedula);
        if (usuario == null) throw new Exception("El usuario no existe");
        if (!usuario.getPassword().equals(password)) throw new Exception("Contraseña incorrecta");

        BilleteraVirtual billetera = buscarBilleteraUsuario(usuario.getCedula());
        return new SaldoTransaccionesBilletera(billetera.consultarSaldo(), billetera.obtenerTransacciones());
    }

    public void recargarBilletera(String numeroBilletera, float monto) throws Exception {
        BilleteraVirtual billetera = buscarBilletera(numeroBilletera);
        if (billetera == null) throw new Exception("La billetera no existe");

        Transaccion transaccion = new Transaccion(
                TipoTransaccion.DEPOSITO,
                UUID.randomUUID().toString(),
                monto,
                LocalDateTime.now(),
                billetera,
                billetera,
                0
        );
        billetera.depositar(monto, transaccion);
    }

    public void realizarTransferencia(String numeroBilleteraOrigen, String numeroBilleteraDestino, float monto) throws Exception {
        BilleteraVirtual billeteraOrigen = buscarBilletera(numeroBilleteraOrigen);
        BilleteraVirtual billeteraDestino = buscarBilletera(numeroBilleteraDestino);

        if (billeteraOrigen == null || billeteraDestino == null) throw new Exception("La billetera no existe");
        if (!billeteraOrigen.tieneSaldo(monto)) throw new Exception("Saldo insuficiente");

        Transaccion salida = new Transaccion(TipoTransaccion.RETIRO, UUID.randomUUID().toString(), monto, LocalDateTime.now(), billeteraOrigen, billeteraDestino, Constantes.COMISION);
        Transaccion entrada = new Transaccion(TipoTransaccion.DEPOSITO, UUID.randomUUID().toString(), monto, LocalDateTime.now(), billeteraOrigen, billeteraDestino, Constantes.COMISION);

        billeteraOrigen.retirar(monto, salida);
        billeteraDestino.depositar(monto, entrada);
    }

    public List<Transaccion> obtenerTransacciones(String numeroBilletera) {
        BilleteraVirtual billetera = buscarBilletera(numeroBilletera);
        return (billetera != null) ? billetera.obtenerTransacciones() : new ArrayList<>();
    }

    public List<Transaccion> obtenerTransaccionesPeriodo(String numeroBilletera, LocalDateTime inicio, LocalDateTime fin) {
        BilleteraVirtual billetera = buscarBilletera(numeroBilletera);
        return (billetera != null) ? billetera.obtenerTransaccionesPeriodo(inicio, fin) : new ArrayList<>();
    }

    public PorcentajeGastosIngresos obtenerPorcentajeGastosIngresos(String numeroBilletera, int mes, int anio) throws Exception {
        BilleteraVirtual billetera = buscarBilletera(numeroBilletera);
        if (billetera == null) throw new Exception("La billetera no existe");

        return billetera.obtenerPorcentajeGastosIngresos(mes, anio);
    }
}