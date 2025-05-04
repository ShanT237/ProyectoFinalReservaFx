package co.edu.uniquindio.proyectofinalhotelfx.Modelo.Entidades;

import co.edu.uniquindio.proyectofinalhotelfx.Constantes.Constantes;
import co.edu.uniquindio.proyectofinalhotelfx.vo.PorcentajeGastosIngresos;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


import java.time.LocalDateTime;
import java.util.ArrayList;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class BilleteraVirtual {
    private String numero;
    private float saldo;
    private Usuario usuario;
    private ArrayList<Transaccion> transacciones;

    public BilleteraVirtual() {
        this.transacciones = new ArrayList<>();
    }

    public BilleteraVirtual(String numero, float saldo, Usuario usuario) {
        this.numero = numero;
        this.saldo = saldo;
        this.usuario = usuario;
        this.transacciones = new ArrayList<>();
    }

    public boolean tieneSaldo(float monto) {
        float montoConComision = monto + Constantes.COMISION;
        return saldo >= montoConComision;
    }

    public void retirar(float monto, Transaccion transaccion) throws Exception {
        if (monto <= 0) {
            throw new Exception("El monto a retirar debe ser mayor a cero");
        }

        float montoConComision = monto + Constantes.COMISION;
        if (!tieneSaldo(monto)) {
            throw new Exception("Saldo insuficiente");
        }

        transaccion.setComision(Constantes.COMISION);
        saldo -= montoConComision;
        transacciones.add(transaccion);
    }

    public void depositar(float monto, Transaccion transaccion) throws Exception {
        if (monto <= 0) {
            throw new Exception("El monto a depositar debe ser mayor a cero");
        }

        if (transaccion.getBilleteraOrigen() == null) {
            throw new Exception("La transacción no tiene una billetera origen asignada");
        }

        saldo += monto;
        transacciones.add(transaccion);
    }

    public ArrayList<Transaccion> obtenerTransaccionesPeriodo(LocalDateTime inicio, LocalDateTime fin) {
        if (transacciones == null) {
            return new ArrayList<>();
        }

        ArrayList<Transaccion> transaccionesMes = new ArrayList<>();
        for (Transaccion transaccion : transacciones) {
            LocalDateTime fecha = transaccion.getFecha();
            if ((fecha.isEqual(inicio) || fecha.isAfter(inicio)) &&
                    (fecha.isEqual(fin) || fecha.isBefore(fin))) {
                transaccionesMes.add(transaccion);
            }
        }
        return transaccionesMes;
    }

    public PorcentajeGastosIngresos obtenerPorcentajeGastosIngresos(int mes, int anio) {
        if (transacciones == null || transacciones.isEmpty()) {
            return new PorcentajeGastosIngresos(0, 0);
        }

        float ingresos = 0;
        float egresos = 0;

        for (Transaccion transaccion : transacciones) {
            if (transaccion.getFecha().getMonthValue() == mes &&
                    transaccion.getFecha().getYear() == anio) {
                if (transaccion.getBilleteraOrigen() == this) {
                    egresos += transaccion.getMonto() + transaccion.getComision();
                } else {
                    ingresos += transaccion.getMonto();
                }
            }
        }

        float total = ingresos + egresos;
        if (total == 0) {
            return new PorcentajeGastosIngresos(0, 0);
        }

        float porcentajeGastos = (egresos / total) * 100;
        float porcentajeIngresos = (ingresos / total) * 100;

        return new PorcentajeGastosIngresos(porcentajeGastos, porcentajeIngresos);
    }

    public ArrayList<Transaccion> obtenerTransacciones() {
        return transacciones != null ? transacciones : new ArrayList<>();
    }

    public float consultarSaldo() {
        return saldo;
    }
}

