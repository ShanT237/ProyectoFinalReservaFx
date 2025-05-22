package co.edu.uniquindio.proyectofinalhotelfx.Modelo.Entidades;

import co.edu.uniquindio.proyectofinalhotelfx.Modelo.Enums.OfertaTipo;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
@Getter
@Setter
public abstract class Oferta implements Serializable {
    private String id;
    private String nombre;
    private String descripcion;
    private LocalDateTime fechaInicio;
    private LocalDateTime fechaFin;
    private List<Alojamiento> alojamientosAplicables;
    private boolean esGlobal;
    private boolean activa;
    private OfertaTipo tipo;
    private int vecesAplicada;
    private String imagen;
    private float valorDescuento;

    private ArrayList<LocalDate> fechasdescuento;
    private int huespedes;
    private int diasReserva;
    private ArrayList<Alojamiento> alojamientos;


    public Oferta(String id, String nombre, String descripcion, LocalDateTime fechaInicio, LocalDateTime fechaFin, List<Alojamiento> alojamientosAplicables, boolean esGlobal, boolean activa, OfertaTipo tipo, String imagen) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.alojamientosAplicables = alojamientosAplicables;
        this.esGlobal = esGlobal;
        this.activa = activa;
        this.tipo = tipo;
        this.vecesAplicada = 0;
        this.imagen = imagen;
    }

    public boolean aplicaA(Alojamiento alojamiento) {
        return esGlobal || (alojamientosAplicables != null && alojamientosAplicables.contains(alojamiento));
    }

    public abstract boolean estaVigente(LocalDateTime fecha);

    public abstract double aplicarDescuento(double precioOriginal, Reserva reserva);

    public void setHuespedes(int cantidadhuespedes) {
    }

    public void setFechasdescuento(ArrayList<LocalDate> fechas) {
    }

    public void setDiasReserva(int diasReserva) {
    }

    public void setValorDescuento(float descuento) {

    }
    public float verificarDescuento(Alojamiento alojamiento,float precio, int cantidadhuespedes, int dias, ArrayList<LocalDate>fechas){
        boolean cumple = true;
        if (!alojamientos.contains(alojamiento)) cumple = false;
        if (huespedes > 0 && cantidadhuespedes < huespedes) cumple = false;
        if (diasReserva>0 && dias < diasReserva) cumple = false;
        LocalDate inicioDescuento = fechasdescuento.getFirst();
        LocalDate finDescuento = fechasdescuento.getLast();
        for (LocalDate fecha : fechas) {
            if (!fecha.isBefore(inicioDescuento) && !fecha.isAfter(finDescuento)) {
                cumple = false;
            }
        }
        if (cumple) return precio - precio*valorDescuento/100;
        return precio;
    }
}