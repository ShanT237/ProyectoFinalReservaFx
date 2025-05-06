package co.edu.uniquindio.proyectofinalhotelfx.Servicios;

import co.edu.uniquindio.proyectofinalhotelfx.Modelo.Entidades.Alojamiento;
import co.edu.uniquindio.proyectofinalhotelfx.Modelo.Enums.Ciudad;
import co.edu.uniquindio.proyectofinalhotelfx.Modelo.Enums.TipoAlojamiento;
import co.edu.uniquindio.proyectofinalhotelfx.Repo.AlojamientoRepository;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;
@Getter
@Builder
public class ServicioAlojamiento {

    private final AlojamientoRepository alojamientoRepository;


    // Buscar por nombre (ignora mayúsculas/minúsculas)
    public List<Alojamiento> buscarPorNombre(String nombre) {
        return alojamientoRepository.obtenerTodos()
                .stream()
                .filter(a -> a.getNombre().toLowerCase().contains(nombre.toLowerCase()))
                .collect(Collectors.toList());
    }

    // Buscar por ciudad
    public List<Alojamiento> buscarPorCiudad(Ciudad ciudad) {
        return alojamientoRepository.obtenerTodos()
                .stream()
                .filter(a -> a.getCiudad() == ciudad)
                .collect(Collectors.toList());
    }

    // Buscar por tipo (ej: casa, hotel, apartamento)
    public List<Alojamiento> buscarPorTipo(TipoAlojamiento tipo) {
        return alojamientoRepository.obtenerTodos()
                .stream()
                .filter(a -> a.getTipoAlojamiento().equals(tipo))
                .collect(Collectors.toList());
    }

    // Buscar por rango de precios
    public List<Alojamiento> buscarPorRangoPrecio(double min, double max) {
        return alojamientoRepository.obtenerTodos()
                .stream()
                .filter(a -> a.getPrecioPorNocheBase() >= min && a.getPrecioPorNocheBase() <= max)
                .collect(Collectors.toList());
    }

    /*
    Metodos eliminarActualizar
     */
    public void eliminarAlojamiento(String idAlojamiento){

    }
    public void actualizarAlojamiento(String idAlojamiento, Alojamiento nuevoAlojamiento){
    }
}
