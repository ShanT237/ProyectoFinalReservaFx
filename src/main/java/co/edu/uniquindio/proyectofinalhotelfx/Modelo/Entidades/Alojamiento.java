package co.edu.uniquindio.proyectofinalhotelfx.Modelo.Entidades;
import co.edu.uniquindio.proyectofinalhotelfx.Modelo.Enums.Ciudad;
import co.edu.uniquindio.proyectofinalhotelfx.Modelo.Enums.ServiciosIncluidos;
import co.edu.uniquindio.proyectofinalhotelfx.Modelo.Enums.TipoAlojamiento;
import javafx.scene.image.Image;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public abstract class Alojamiento implements Serializable {

    private String nombre;
    private Ciudad ciudad;
    private String descripcion;
    private String id;
    private double precioPorNocheBase;
    private int capacidadMaxima;
    private Image imagen;
    private List<ServiciosIncluidos> serviciosIncluidos;
    private List<Review> reviews;
    private Boolean estadoActivo;
    private int capacidadPersonas;
    private int numeroHabitaciones;
    private boolean admiteMascotas;
    private TipoAlojamiento tipoAlojamiento;

    public Alojamiento(boolean admiteMascotas, int numeroHabitaciones, int capacidadPersonas, List<ServiciosIncluidos> serviciosIncluidos, Image imagen, int capacidadMaxima, double precioPorNoche, String id, String descripcion, Ciudad ciudad, String nombre, TipoAlojamiento tipoAlojamiento) {
        this.estadoActivo = true;
        this.admiteMascotas = admiteMascotas;
        this.numeroHabitaciones = numeroHabitaciones;
        this.capacidadPersonas = capacidadPersonas;
        this.reviews = new ArrayList<>();
        this.serviciosIncluidos = serviciosIncluidos;
        this.imagen = imagen;
        this.capacidadMaxima = capacidadMaxima;
        this.precioPorNocheBase = precioPorNoche;
        this.id = id;
        this.descripcion = descripcion;
        this.ciudad = ciudad;
        this.nombre = nombre;
        this.tipoAlojamiento = tipoAlojamiento;
    }
}
