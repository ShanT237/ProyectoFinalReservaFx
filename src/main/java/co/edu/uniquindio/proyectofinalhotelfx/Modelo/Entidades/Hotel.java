package co.edu.uniquindio.proyectofinalhotelfx.Modelo.Entidades;

import co.edu.uniquindio.proyectofinalhotelfx.Modelo.Enums.Ciudad;
import co.edu.uniquindio.proyectofinalhotelfx.Modelo.Enums.ServiciosIncluidos;
import co.edu.uniquindio.proyectofinalhotelfx.Modelo.Enums.TipoAlojamiento;
import javafx.scene.image.Image;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter

public class Hotel extends Alojamiento{
    private int numeroEstrellas;
    private List<Habitacion> habitaciones;

    public Hotel(boolean admiteMascotas, int numeroHabitaciones, int capacidadPersonas, List<ServiciosIncluidos> serviciosIncluidos, Image imagen, int capacidadMaxima, double precioPorNoche, String id, String descripcion, Ciudad ciudad, String nombre, TipoAlojamiento tipoAlojamiento) {
        super(admiteMascotas, numeroHabitaciones, capacidadPersonas, serviciosIncluidos, imagen, capacidadMaxima, precioPorNoche, id, descripcion, ciudad, nombre, tipoAlojamiento);
        this.numeroEstrellas = 0;
        this.habitaciones = new ArrayList<>();
    }
}
