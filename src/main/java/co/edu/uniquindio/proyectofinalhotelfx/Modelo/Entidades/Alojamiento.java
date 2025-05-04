package co.edu.uniquindio.proyectofinalhotelfx.Modelo.Entidades;
import javafx.scene.image.Image;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
@Builder

public abstract class Alojamiento {

    public  String nombre;
    public String ciudad;
    public  String descripcion;
    public String id;
    public double precioPorNoche;
    public int capacidadMaxima;
    public Image imagen;
    public List<String> serviciosIncluidos;
    public List<Resena> resenas;

}
