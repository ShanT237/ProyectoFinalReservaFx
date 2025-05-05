package co.edu.uniquindio.proyectofinalhotelfx.Repo;

import co.edu.uniquindio.proyectofinalhotelfx.Modelo.Entidades.Reserva;

import java.util.ArrayList;
import java.util.List;

public class ReservaRepository {

    private final List<Reserva> reservas = new ArrayList<>();

    public Reserva buscarPorId(String id) {
        for (Reserva r : reservas) {
            if (r.getId().equals(id)) {
                return r;
            }
        }
        return null;
    }

    public void guardar(Reserva reserva) {
        reservas.add(reserva);
    }

    public void actualizar(Reserva reserva) {
        for (int i = 0; i < reservas.size(); i++) {
            if (reservas.get(i).getId().equals(reserva.getId())) {
                reservas.set(i, reserva);
                return;
            }
        }
    }

    public List<Reserva> obtenerTodos() {
        return new ArrayList<>(reservas); // Devuelve una copia segura
    }
}
