package co.edu.uniquindio.proyectofinalhotelfx.Modelo.Entidades;

import co.edu.uniquindio.proyectofinalhotelfx.Constantes.Constantes;
import co.edu.uniquindio.proyectofinalhotelfx.vo.PorcentajeGastosIngresos;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class BilleteraVirtual implements Serializable {
    private String numero;
    private float saldo;
    private ArrayList<Transaccion> transacciones;


}

