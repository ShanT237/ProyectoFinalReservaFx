package co.edu.uniquindio.proyectofinalhotelfx.Controladores.ControladoresAdm;
import co.edu.uniquindio.proyectofinalhotelfx.Modelo.Enums.TipoAlojamiento;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class TipoAlojamientoGanancia {
    private TipoAlojamiento tipo;
    private double gananciaTotal;

    @Override
    public String toString() {
        return tipo.toString() + ": $" + String.format("%.2f", gananciaTotal);
    }
}
