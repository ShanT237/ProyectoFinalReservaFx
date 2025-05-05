package co.edu.uniquindio.proyectofinalhotelfx.Modelo.Entidades;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class Cliente extends Usuario{
    BilleteraVirtual billetera;

}
