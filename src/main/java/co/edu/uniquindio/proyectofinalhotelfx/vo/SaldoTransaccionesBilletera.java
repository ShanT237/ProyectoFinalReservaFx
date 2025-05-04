package co.edu.uniquindio.proyectofinalhotelfx.vo;

import co.edu.uniquindio.proyectofinalhotelfx.Modelo.Entidades.Transaccion;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.List;
@Getter
@ToString
@AllArgsConstructor

public class SaldoTransaccionesBilletera {
    private float saldo;
    private List<Transaccion> transacciones;
}
