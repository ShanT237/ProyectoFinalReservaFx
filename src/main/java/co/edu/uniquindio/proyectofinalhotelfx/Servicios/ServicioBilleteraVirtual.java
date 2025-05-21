package co.edu.uniquindio.proyectofinalhotelfx.Servicios;

import lombok.Builder;
import lombok.Getter;

import java.util.*;

@Getter
@Builder
public class ServicioBilleteraVirtual {
    private ServicioCliente servicioCliente;

    private final Map<String, Double> saldos = new HashMap<>();



    public void recargarBilletera(String clienteId, double monto) {
        if (monto <= 0) {
            throw new IllegalArgumentException("El monto a recargar debe ser mayor a cero");
        }

        double saldoActual = saldos.getOrDefault(clienteId, 0.0);
        saldos.put(clienteId, saldoActual + monto);



    }
}
