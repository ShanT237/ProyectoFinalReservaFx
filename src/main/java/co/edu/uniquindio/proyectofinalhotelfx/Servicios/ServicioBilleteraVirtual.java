package co.edu.uniquindio.proyectofinalhotelfx.Servicios;

import lombok.Builder;
import lombok.Getter;

import java.util.*;

@Getter
@Builder
public class ServicioBilleteraVirtual {
    private ServicioCliente servicioCliente;

    private final Map<String, Double> saldos = new HashMap<>();
    private final List<ObservadorSaldo> observadores = new ArrayList<>();

    public void agregarObservador(ObservadorSaldo observador) {
        observadores.add(observador);
    }

    public void eliminarObservador(ObservadorSaldo observador) {
        observadores.remove(observador);
    }

    private void notificarObservadores(String clienteId) {
        double nuevoSaldo = saldos.getOrDefault(clienteId, 0.0);
        for (ObservadorSaldo obs : observadores) {
            obs.saldoActualizado(clienteId, nuevoSaldo);
        }
    }

    public void recargarBilletera(String clienteId, double monto) {
        if (monto <= 0) {
            throw new IllegalArgumentException("El monto a recargar debe ser mayor a cero");
        }

        double saldoActual = saldos.getOrDefault(clienteId, 0.0);
        saldos.put(clienteId, saldoActual + monto);

        // Notificar a los observadores
        notificarObservadores(clienteId);
    }

    public double consultarSaldo(String clienteId) {
        return saldos.getOrDefault(clienteId, 0.0);
    }

    public boolean descontar(String clienteId, double monto) {
        double saldoActual = saldos.getOrDefault(clienteId, 0.0);
        if (saldoActual >= monto) {
            saldos.put(clienteId, saldoActual - monto);

            // Notificar a los observadores
            notificarObservadores(clienteId);
            return true;
        }
        return false;
    }
}
