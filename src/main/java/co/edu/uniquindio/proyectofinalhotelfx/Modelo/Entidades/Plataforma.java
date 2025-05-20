package co.edu.uniquindio.proyectofinalhotelfx.Modelo.Entidades;

import co.edu.uniquindio.proyectofinalhotelfx.Repo.AlojamientoRepository;
import co.edu.uniquindio.proyectofinalhotelfx.Repo.ClienteRepository;
import co.edu.uniquindio.proyectofinalhotelfx.Repo.ReservaRepository;

public class Plataforma {
    private final ReservaRepository reservaRepository;
    private final AlojamientoRepository alojamientoRepository;
    private final ClienteRepository clienteRepository;

    public Plataforma() {
        this.reservaRepository = new ReservaRepository();
        this.alojamientoRepository = new AlojamientoRepository();
        this.clienteRepository = new ClienteRepository();
    }

    public ReservaRepository getReservaRepository() {
        return reservaRepository;
    }

    // ... otros getters
}
