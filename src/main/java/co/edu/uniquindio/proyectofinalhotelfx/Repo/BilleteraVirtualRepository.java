package co.edu.uniquindio.proyectofinalhotelfx.Repo;

import co.edu.uniquindio.proyectofinalhotelfx.Constantes.Constantes;
import co.edu.uniquindio.proyectofinalhotelfx.Modelo.Entidades.BilleteraVirtual;
import co.edu.uniquindio.proyectofinalhotelfx.Modelo.Entidades.Transaccion;
import co.edu.uniquindio.proyectofinalhotelfx.vo.PorcentajeGastosIngresos;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Repository class for managing virtual wallets
 * Clase repositorio para gestionar billeteras virtuales
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BilleteraVirtualRepository {
    private List<BilleteraVirtual> billeteras = new ArrayList<>();

    /**
     * Find a wallet by its number
     * Encuentra una billetera por su número
     */
    public Optional<BilleteraVirtual> obtenerBilleteraPorNumero(String numero) {
        if (billeteras == null) return Optional.empty();
        return billeteras.stream()
                .filter(b -> b.getNumero().equals(numero))
                .findFirst();
    }

    /**
     * Add a new virtual wallet
     * Agregar una nueva billetera virtual
     */
    public void agregarBilletera(BilleteraVirtual billetera) {
        billeteras.add(billetera);
    }

    /**
     * Delete a wallet by its number
     * Eliminar una billetera por su número
     */
    public void eliminarBilletera(String numero) {
        if (billeteras != null) {
            billeteras.removeIf(b -> b.getNumero().equals(numero));
        }
    }

    /**
     * Check if wallet has sufficient balance
     * Verificar si la billetera tiene saldo suficiente
     */
    public boolean tieneSaldo(String numeroBilletera, float monto) {
        return obtenerBilleteraPorNumero(numeroBilletera)
                .map(b -> b.getSaldo() >= monto)
                .orElse(false);
    }

    /**
     * Withdraw money from wallet
     * Retirar dinero de la billetera
     */
    public void retirar(String numeroBilletera, float monto, Transaccion transaccion) throws Exception {
        BilleteraVirtual billetera = obtenerBilleteraPorNumero(numeroBilletera)
                .orElseThrow(() -> new Exception("Billetera no encontrada"));

        if (billetera.getSaldo() >= monto) {
            billetera.setSaldo(billetera.getSaldo() - monto);
            billetera.getTransacciones().add(transaccion);
        } else {
            throw new Exception("Saldo insuficiente");
        }
    }

    /**
     * Deposit money into wallet
     * Depositar dinero en la billetera
     */
    public void depositar(String numeroBilletera, float monto, Transaccion transaccion) throws Exception {
        BilleteraVirtual billetera = obtenerBilleteraPorNumero(numeroBilletera)
                .orElseThrow(() -> new Exception("Billetera no encontrada"));

        billetera.setSaldo(billetera.getSaldo() + monto);
        billetera.getTransacciones().add(transaccion);
    }

    /**
     * Get transactions between two dates
     * Obtener transacciones entre dos fechas
     */
    public ArrayList<Transaccion> obtenerTransaccionesPeriodo(String numeroBilletera, LocalDateTime inicio, LocalDateTime fin) throws Exception {
        BilleteraVirtual billetera = obtenerBilleteraPorNumero(numeroBilletera)
                .orElseThrow(() -> new Exception("Billetera no encontrada"));

        ArrayList<Transaccion> transaccionesPeriodo = new ArrayList<>();
        for (Transaccion t : billetera.getTransacciones()) {
            if (t.getFecha().isAfter(inicio) && t.getFecha().isBefore(fin)) {
                transaccionesPeriodo.add(t);
            }
        }
        return transaccionesPeriodo;
    }

    /**
     * Get percentage of expenses and income
     * Obtener porcentaje de gastos e ingresos
     */
    public PorcentajeGastosIngresos obtenerPorcentajeGastosIngresos(String numeroBilletera, int mes, int anio) throws Exception {
        BilleteraVirtual billetera = obtenerBilleteraPorNumero(numeroBilletera)
                .orElseThrow(() -> new Exception("Billetera no encontrada"));

        float totalGastos = 0;
        float totalIngresos = 0;

        for (Transaccion t : billetera.getTransacciones()) {
            if (t.getFecha().getMonthValue() == mes && t.getFecha().getYear() == anio) {
                if (t.getMonto() < 0) {
                    totalGastos += Math.abs(t.getMonto());
                } else {
                    totalIngresos += t.getMonto();
                }
            }
        }

        float total = totalGastos + totalIngresos;
        float porcentajeGastos = total > 0 ? (totalGastos / total) * 100 : 0;
        float porcentajeIngresos = total > 0 ? (totalIngresos / total) * 100 : 0;

        return new PorcentajeGastosIngresos(porcentajeGastos, porcentajeIngresos);
    }

    /**
     * Get all transactions for a wallet
     * Obtener todas las transacciones de una billetera
     */
    public ArrayList<Transaccion> obtenerTransacciones(String numeroBilletera) throws Exception {
        return obtenerBilleteraPorNumero(numeroBilletera)
                .orElseThrow(() -> new Exception("Billetera no encontrada"))
                .getTransacciones();
    }

    /**
     * Check wallet balance
     * Consultar saldo de la billetera
     */
    public float consultarSaldo(String numeroBilletera) throws Exception {
        return obtenerBilleteraPorNumero(numeroBilletera)
                .orElseThrow(() -> new Exception("Billetera no encontrada"))
                .getSaldo();
    }
}