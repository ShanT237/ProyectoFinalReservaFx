package co.edu.uniquindio.proyectofinalhotelfx.Servicios;

import co.edu.uniquindio.proyectofinalhotelfx.Modelo.Entidades.Alojamiento;
import co.edu.uniquindio.proyectofinalhotelfx.Repo.AlojamientoRepository;

import java.util.List;

public class ServicioAlojamiento {
    public ServicioAlojamiento(AlojamientoRepository alojamientoRepository) {

    }

    //MÉTODOS QUE AYUDAN AL CLIENTE
    public List<Alojamiento> buscarPorNombre(String nombre) {
        return null;
    }

    public List<Alojamiento> buscarPorCiudad(String ciudad) {
        return null;
    }

    public List<Alojamiento> buscarPorTipo(String tipo) {
        return null;
    }

    public List<Alojamiento> buscarPorRangoPrecio(double min, double max) {
        return null;
    }

}
