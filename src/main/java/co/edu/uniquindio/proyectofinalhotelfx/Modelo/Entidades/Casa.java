package co.edu.uniquindio.proyectofinalhotelfx.Modelo.Entidades;

import co.edu.uniquindio.proyectofinalhotelfx.Modelo.Enums.Ciudad;
import co.edu.uniquindio.proyectofinalhotelfx.Modelo.Enums.ServiciosIncluidos;
import co.edu.uniquindio.proyectofinalhotelfx.Modelo.Enums.TipoAlojamiento;
import javafx.scene.image.Image;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter

public class Casa extends Alojamiento{
    public Casa(boolean admiteMascotas, int numeroHabitaciones, int capacidadHuespedes, List<ServiciosIncluidos> serviciosIncluidos, String imagen, double precioPorNoche, String id, String descripcion, Ciudad ciudad, String nombre, TipoAlojamiento tipoAlojamiento) {
        super(admiteMascotas, numeroHabitaciones, capacidadHuespedes, serviciosIncluidos, imagen, precioPorNoche, id, descripcion, ciudad, nombre, tipoAlojamiento);
    }
}
