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

    public BilleteraVirtual(String numero, float saldo) {
        this.numero = numero;
        this.saldo = saldo;
        this.transacciones = new ArrayList<>();
    }

    public void recargarBilletera(float recarga)throws Exception{
        if (recarga <= 0)throw new Exception("Valor invalido");
        saldo += recarga;
    }

    public void recargarBilletera(String recarga)throws Exception{
        float valorRecarga;
        try{
            valorRecarga = Float.parseFloat(recarga);
        }catch(NumberFormatException e){
            throw new Exception("Valor invalido");
        }
        recargarBilletera(valorRecarga);
    }

    public void cobrarBilletera(float valorRecarga) throws Exception {
        if (saldo < valorRecarga) throw new Exception("Fondos insuficientes");
        saldo -= valorRecarga;
    }
}

