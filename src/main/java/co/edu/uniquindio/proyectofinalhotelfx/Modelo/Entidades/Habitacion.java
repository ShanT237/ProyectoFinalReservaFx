package co.edu.uniquindio.proyectofinalhotelfx.Modelo.Entidades;

import co.edu.uniquindio.proyectofinalhotelfx.Modelo.Enums.ServiciosIncluidos;
import co.edu.uniquindio.proyectofinalhotelfx.Modelo.Enums.TipoHabitacionHotel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class Habitacion implements Serializable {

    private int numero;
    private int capacidad;
    private double precioPorNoche;
    private boolean disponible;
    private List<ServiciosIncluidos> serviciosIncluidos;
    private TipoHabitacionHotel tipoHabitacion;
    private String imagen;

    public Habitacion(int numero, int capacidad, double precioPorNoche, List<ServiciosIncluidos> serviciosIncluidos, TipoHabitacionHotel tipoHabitacion, String imagen) {
        this.numero = numero;
        this.capacidad = capacidad;
        this.precioPorNoche = precioPorNoche;
        this.disponible = true;
        this.serviciosIncluidos = serviciosIncluidos;
        this.tipoHabitacion = tipoHabitacion;
        this.imagen = imagen;
    }
}