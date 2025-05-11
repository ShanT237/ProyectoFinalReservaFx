package co.edu.uniquindio.proyectofinalhotelfx.Factory.AlojamientoFactory;

import co.edu.uniquindio.proyectofinalhotelfx.Modelo.Entidades.Alojamiento;
import co.edu.uniquindio.proyectofinalhotelfx.Modelo.Entidades.Apartamento;
import co.edu.uniquindio.proyectofinalhotelfx.Modelo.Entidades.Casa;
import co.edu.uniquindio.proyectofinalhotelfx.Modelo.Entidades.Hotel;
import co.edu.uniquindio.proyectofinalhotelfx.Modelo.Enums.Ciudad;
import co.edu.uniquindio.proyectofinalhotelfx.Modelo.Enums.ServiciosIncluidos;
import co.edu.uniquindio.proyectofinalhotelfx.Modelo.Enums.TipoAlojamiento;
import javafx.scene.image.Image;

import java.util.List;

public class AlojamientoFactory {

    public static Alojamiento crearAlojamiento(String nombre, Ciudad ciudad, String descripcion, double precioPorNocheBase, Image imagen, List<ServiciosIncluidos> serviciosIncluidos, int capacidadPersonas, int numeroHabitaciones, boolean admiteMascotas, TipoAlojamiento tipoAlojamiento, String id) {
        return switch (tipoAlojamiento) {
            case HOTEL ->
                    new Hotel(admiteMascotas, numeroHabitaciones, capacidadPersonas, serviciosIncluidos, imagen, precioPorNocheBase, id, descripcion, ciudad, nombre, tipoAlojamiento);
            case CASA ->
                    new Casa(admiteMascotas, numeroHabitaciones, capacidadPersonas, serviciosIncluidos, imagen, precioPorNocheBase, id, descripcion, ciudad, nombre, tipoAlojamiento);
            case APARTAMENTO ->
                    new Apartamento(admiteMascotas, numeroHabitaciones, capacidadPersonas, serviciosIncluidos, imagen, precioPorNocheBase, id, descripcion, ciudad, nombre, tipoAlojamiento);
            default -> throw new IllegalArgumentException("Tipo de alojamiento no válido");
        };
    }
}