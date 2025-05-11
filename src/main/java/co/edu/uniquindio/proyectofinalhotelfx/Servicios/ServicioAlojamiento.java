package co.edu.uniquindio.proyectofinalhotelfx.Servicios;

import co.edu.uniquindio.proyectofinalhotelfx.Factory.AlojamientoFactory.AlojamientoFactory;
import co.edu.uniquindio.proyectofinalhotelfx.Modelo.Entidades.Alojamiento;
import co.edu.uniquindio.proyectofinalhotelfx.Modelo.Enums.Ciudad;
import co.edu.uniquindio.proyectofinalhotelfx.Modelo.Enums.ServiciosIncluidos;
import co.edu.uniquindio.proyectofinalhotelfx.Modelo.Enums.TipoAlojamiento;
import co.edu.uniquindio.proyectofinalhotelfx.Repo.AlojamientoRepository;
import javafx.scene.image.Image;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
@Getter
@Builder
public class ServicioAlojamiento {

    private final AlojamientoRepository alojamientoRepository;

    /*/
    Metodos Principales
     */

    public void registrarAlojamiento(String nombre, Ciudad ciudad, String descripcion, double precioPorNocheBase, Image imagen, List<ServiciosIncluidos> serviciosIncluidos, int capacidadPersonas, int numeroHabitaciones, boolean admiteMascotas, TipoAlojamiento tipoAlojamiento){
        String id = generarIdAlojamiento();
        Alojamiento alojamiento = crearAlojamiento(nombre, ciudad, descripcion, precioPorNocheBase, imagen, serviciosIncluidos, capacidadPersonas, numeroHabitaciones, admiteMascotas, tipoAlojamiento, id);
        alojamientoRepository.guardar(alojamiento);
    }

    public Alojamiento crearAlojamiento(String nombre, Ciudad ciudad, String descripcion, double precioPorNocheBase, Image imagen, List<ServiciosIncluidos> serviciosIncluidos, int capacidadPersonas, int numeroHabitaciones, boolean admiteMascotas, TipoAlojamiento tipoAlojamiento, String id){
        AlojamientoFactory alojamientoFactory = new AlojamientoFactory();
        Alojamiento alojamiento = alojamientoFactory.crearAlojamiento(nombre, ciudad, descripcion, precioPorNocheBase, imagen, serviciosIncluidos, capacidadPersonas, numeroHabitaciones, admiteMascotas, tipoAlojamiento, id);
        return alojamiento;

    }

    public List<Alojamiento> obtenerTodosAlojamientos(){
        return alojamientoRepository.obtenerTodos();
    }


    private String generarIdAlojamiento() {
        String caracteres = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder id = new StringBuilder();
        Random random = new Random();

        for (int i = 0; i < 8; i++) {
            int index = random.nextInt(caracteres.length());
            id.append(caracteres.charAt(index));
        }

        return id.toString();
    }

    public void actualizarAlojamiento(String idAlojamiento, String nombre, Ciudad ciudad, String descripcion, double precioPorNocheBase, Image imagen, List<ServiciosIncluidos> serviciosIncluidos, int capacidadPersonas, int numeroHabitaciones, boolean admiteMascotas, TipoAlojamiento tipoAlojamiento){
        Alojamiento alojamientoActualizar = crearAlojamiento(nombre, ciudad, descripcion, precioPorNocheBase, imagen, serviciosIncluidos, capacidadPersonas, numeroHabitaciones, admiteMascotas, tipoAlojamiento, idAlojamiento);
        alojamientoRepository.actualizar(alojamientoActualizar);
    }

    /*
 Metodos eliminar
  */
    public void eliminarAlojamiento(String idAlojamiento){
        alojamientoRepository.eliminar(idAlojamiento);

    }
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



}
