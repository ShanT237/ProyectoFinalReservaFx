package co.edu.uniquindio.proyectofinalhotelfx.Servicios;

import co.edu.uniquindio.proyectofinalhotelfx.Repo.ClienteRepository;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.*;

@Getter
@Setter
@Builder
public class ServicioBilleteraVirtual {
    private ClienteRepository clienteRepository;


    public void recargarBilletera(String clienteId, float monto) throws Exception {
        if (monto <= 0) {
            throw new Exception("El monto a recargar debe ser mayor a cero");
        }
        clienteRepository.actualizarSaldo(clienteId, monto);
    }

    public float consultarSaldo(String clienteId) throws Exception {
        return clienteRepository.consultarSaldo(clienteId);
    }

    public void descontar(String clienteId, float monto) throws Exception {
        if (monto <= 0) {
            throw new Exception("El monto a descontar debe ser mayor a cero");
        }
        clienteRepository.descontar(clienteId, monto);
    }


}
