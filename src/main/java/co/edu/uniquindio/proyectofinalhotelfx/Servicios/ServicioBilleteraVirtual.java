package co.edu.uniquindio.proyectofinalhotelfx.Servicios;

import co.edu.uniquindio.proyectofinalhotelfx.Modelo.Entidades.BilleteraVirtual;
import co.edu.uniquindio.proyectofinalhotelfx.Repo.BilleteraVirtualRepository;
import lombok.Builder;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
@Builder
public class ServicioBilleteraVirtual {
    private BilleteraVirtualRepository billeteraVirtualRepository;

    private final Map<String, Double> saldos = new HashMap<>();

    // Recargar la billetera del cliente
    public void recargarBilletera(String clienteId, double monto) {
        if (monto <= 0) {
            throw new IllegalArgumentException("El monto a recargar debe ser mayor a cero");
        }

        double saldoActual = saldos.getOrDefault(clienteId, 0.0);
        saldos.put(clienteId, saldoActual + monto);
    }

    // Consultar el saldo de la billetera
    public double consultarSaldo(String clienteId) {
        return saldos.getOrDefault(clienteId, 0.0);
    }

    // Descontar monto para una transacción
    public boolean descontar(String clienteId, double monto) {
        double saldoActual = saldos.getOrDefault(clienteId, 0.0);
        if (saldoActual >= monto) {
            saldos.put(clienteId, saldoActual - monto);
            return true;
        }
        return false;
    }
}
