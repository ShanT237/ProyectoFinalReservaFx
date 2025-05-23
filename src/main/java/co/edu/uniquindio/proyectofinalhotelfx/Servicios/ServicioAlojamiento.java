package co.edu.uniquindio.proyectofinalhotelfx.Servicios;

import co.edu.uniquindio.proyectofinalhotelfx.Factory.AlojamientoFactory.AlojamientoFactory;
import co.edu.uniquindio.proyectofinalhotelfx.Modelo.Entidades.Alojamiento;
import co.edu.uniquindio.proyectofinalhotelfx.Modelo.Entidades.Habitacion;
import co.edu.uniquindio.proyectofinalhotelfx.Modelo.Enums.Ciudad;
import co.edu.uniquindio.proyectofinalhotelfx.Modelo.Enums.ServiciosIncluidos;
import co.edu.uniquindio.proyectofinalhotelfx.Modelo.Enums.TipoAlojamiento;
import co.edu.uniquindio.proyectofinalhotelfx.Modelo.Enums.TipoHabitacionHotel;
import co.edu.uniquindio.proyectofinalhotelfx.Repo.AlojamientoRepository;
import javafx.scene.image.Image;
import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
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

    public void registrarAlojamiento(String nombre, Ciudad ciudad, String descripcion, double precioPorNocheBase, String imagen, List<ServiciosIncluidos> serviciosIncluidos, int capacidadPersonas, int numeroHabitaciones, boolean admiteMascotas, TipoAlojamiento tipoAlojamiento){

        String id = generarIdAlojamiento();
        Alojamiento alojamiento = crearAlojamiento(nombre, ciudad, descripcion, precioPorNocheBase, imagen, serviciosIncluidos, capacidadPersonas, numeroHabitaciones, admiteMascotas, tipoAlojamiento, id);
        alojamientoRepository.guardar(alojamiento);
    }

    public Alojamiento crearAlojamiento(String nombre, Ciudad ciudad, String descripcion, double precioPorNocheBase, String imagen, List<ServiciosIncluidos> serviciosIncluidos, int capacidadPersonas, int numeroHabitaciones, boolean admiteMascotas, TipoAlojamiento tipoAlojamiento, String id){
        Alojamiento alojamiento = AlojamientoFactory.crearAlojamiento(nombre, ciudad, descripcion, precioPorNocheBase, imagen, serviciosIncluidos, capacidadPersonas, numeroHabitaciones, admiteMascotas, tipoAlojamiento, id);
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

    public void actualizarAlojamiento(String idAlojamiento, String nombre, Ciudad ciudad, String descripcion, double precioPorNocheBase, String imagen, List<ServiciosIncluidos> serviciosIncluidos, int capacidadPersonas, int numeroHabitaciones, boolean admiteMascotas, TipoAlojamiento tipoAlojamiento){
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
                .filter(a -> a.getPrecioNoche() >= min && a.getPrecioNoche() <= max)
                .collect(Collectors.toList());
    }


    public List<Alojamiento> obtenerAlojamientosPorCiudadYTipo(Ciudad ciudad, TipoAlojamiento tipoAlojamiento) {
        return alojamientoRepository.obtenerTodos()
                .stream()
                .filter(a -> a.getCiudad() == ciudad && a.getTipoAlojamiento() == tipoAlojamiento).toList();
    }

    public List<Alojamiento> obtenerAlojamientosPorCiudad(Ciudad ciudad) {
        return new ArrayList<>(
                alojamientoRepository.obtenerTodos()
                        .stream()
                        .filter(a -> a.getCiudad() == ciudad)
                        .toList()
        );
    }

    public List<Alojamiento> obtenerAlojamientosPorTipo(TipoAlojamiento tipoAlojamiento) {
        return alojamientoRepository.obtenerTodos()
                .stream()
                .filter(a -> a.getTipoAlojamiento() == tipoAlojamiento).toList();
    }

    public void registrarHabitacion(String idhotel, int numero, int capacidad, double precioPorNoche, List<ServiciosIncluidos> serviciosIncluidos, TipoHabitacionHotel tipoHabitacionHotel, String imagen) throws Exception {

        validarDatosHabitacion(numero, capacidad, precioPorNoche, serviciosIncluidos, tipoHabitacionHotel, imagen);
        Habitacion habitacion = crearHabitacion(numero, capacidad, precioPorNoche, serviciosIncluidos, tipoHabitacionHotel, imagen);
        alojamientoRepository.registrarHabitacionHotel(idhotel, habitacion);
    }
    public void validarDatosHabitacion(int numero, int capacidad, double precioPorNoche, List<ServiciosIncluidos> serviciosIncluidos, TipoHabitacionHotel tipoHabitacionHotel, String imagen) throws Exception {
        if (numero <= 0) {
            throw new Exception("El número de habitación debe ser mayor a 0.");
        }
        if (capacidad <= 0) {
            throw new Exception("La capacidad debe ser mayor a 0.");
        }
        if (precioPorNoche <= 0) {
            throw new Exception("El precio por noche debe ser mayor a 0.");
        }
        if (serviciosIncluidos == null) {
            throw new Exception("La lista de servicios no puede ser nula.");
        }
        if (tipoHabitacionHotel == null) {
            throw new Exception("El tipo de habitación no puede ser nulo.");
        }
        if (imagen == null || imagen.isBlank()) {
            throw new Exception("La imagen no puede estar vacía.");
        }
    }

    public Habitacion crearHabitacion(int numero, int capacidad, double precioPorNoche, List<ServiciosIncluidos> serviciosIncluidos, TipoHabitacionHotel tipoHabitacionHotel, String imagen){
        System.out.println(serviciosIncluidos.toString());
        return new Habitacion(numero, capacidad, precioPorNoche, serviciosIncluidos, tipoHabitacionHotel, imagen);
    }

    public void eliminarHabitacion(String idAlojamiento, int numeroHabitacion) throws Exception {
        alojamientoRepository.eliminarHabitacionHotel(idAlojamiento, numeroHabitacion);
    }
}