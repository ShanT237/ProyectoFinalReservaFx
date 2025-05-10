package co.edu.uniquindio.proyectofinalhotelfx.Modelo.Entidades;

import co.edu.uniquindio.proyectofinalhotelfx.Modelo.Enums.TipoTransaccion;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

public class Transaccion implements Serializable {
    private TipoTransaccion tipoTransaccion;
    private UUID id;
    private float monto;
    private LocalDateTime fecha;
    private BilleteraVirtual billeteraOrigen, billeteraDestino;
    private float comision;
    private String descripcion;

}
