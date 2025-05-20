package co.edu.uniquindio.proyectofinalhotelfx.vo;

import co.edu.uniquindio.proyectofinalhotelfx.Modelo.Enums.TipoAlojamiento;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Builder
@Setter
public class TipoAlojamientoGanancia {
    private TipoAlojamiento tipo;
    private double gananciaTotal;
}
