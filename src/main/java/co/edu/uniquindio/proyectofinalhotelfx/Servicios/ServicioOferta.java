package co.edu.uniquindio.proyectofinalhotelfx.Servicios;

import co.edu.uniquindio.proyectofinalhotelfx.Repo.OfertaRepository;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ServicioOferta {
    private ServicioAlojamiento servicioAlojamiento;
    private OfertaRepository ofertaRepository;
}
