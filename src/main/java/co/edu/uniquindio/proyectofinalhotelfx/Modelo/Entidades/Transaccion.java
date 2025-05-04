package co.edu.uniquindio.proyectofinalhotelfx.Modelo.Entidades;

import co.edu.uniquindio.proyectofinalhotelfx.Enums.TipoTransaccion;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

public class Transaccion {
    private TipoTransaccion tipoTransaccion;
    private String id;
    private float monto;
    private LocalDateTime fecha;
    private BilleteraVirtual billeteraOrigen, billeteraDestino;
    private float comision;

}
