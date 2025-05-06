package co.edu.uniquindio.proyectofinalhotelfx.Modelo.Entidades;

import co.edu.uniquindio.proyectofinalhotelfx.Modelo.Enums.ServiciosIncluidos;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Builder
@Getter
@Setter
public class Habitacion {

    private int capacidad;
    private double precioPorNoche;
    private boolean disponible;
    private List<ServiciosIncluidos> servicios;
    private Hotel hotel;
}
