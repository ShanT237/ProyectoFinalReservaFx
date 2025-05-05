package co.edu.uniquindio.proyectofinalhotelfx.Modelo.Entidades;
import javafx.scene.image.Image;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter

public abstract class Alojamiento {

    public String nombre;
    public String ciudad;
    public String descripcion;
    public String id;
    public double precioPorNoche;
    public int capacidadMaxima;
    public Image imagen;
    public List<String> serviciosIncluidos;
    public List<Review> reviews;

    public Alojamiento(int capacidadMaxima, String ciudad, String nombre, String descripcion, String id, double precioPorNoche, Image image) {
        this.capacidadMaxima = capacidadMaxima;
        this.ciudad = ciudad;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.id = id;
        this.precioPorNoche = precioPorNoche;
        this.imagen = imagen;
        this.serviciosIncluidos = new ArrayList<>();
        this.reviews = new ArrayList<>();
    }
}
