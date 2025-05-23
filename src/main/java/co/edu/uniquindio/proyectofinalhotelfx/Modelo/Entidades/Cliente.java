package co.edu.uniquindio.proyectofinalhotelfx.Modelo.Entidades;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;

@Getter
@Setter
@SuperBuilder
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Cliente extends Usuario implements Serializable {
    private int codigoActivacion;
    private String cedula,nombre,telefono;
    private BilleteraVirtual billetera;
    private ArrayList<Review> resenias;
    private ArrayList<Reserva> reservas;
    private boolean activo;

}