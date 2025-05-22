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

    public void recargarBilletera(String valorRecarga) throws Exception {
        billetera.recargarBilletera(valorRecarga);
    }

    public void cobrarBilletera(float valorRecarga) throws Exception {
        billetera.cobrarBilletera(valorRecarga);
    }

    public float consultarSaldo() {
        return billetera.getSaldo();
    }

    public void agregarResenia(String titulo, String descripcion, int valoracion, Reserva reserva) throws Exception {
        StringBuilder e = new StringBuilder();

        if (titulo.isEmpty()) e.append("Rellena el título. ");
        if (descripcion.isEmpty()) e.append("Rellena la descripción. ");
        if (reserva == null) e.append("Seleccione la reserva. ");

        if (!e.isEmpty()) {
            throw new Exception(e + "Por favor, rellene todos los datos.");
        }

        // Verificar que la reserva ya haya finalizado
        LocalDate fechaFinReserva = reserva.getFechaFin().toLocalDate();
        if (fechaFinReserva.isAfter(LocalDate.now())) {
            throw new Exception("No puedes escribir una reseña si la reserva aún no ha terminado.");
        }

        Review resenia = Review.builder()
                .titulo(titulo)
                .descripcion(descripcion)
                .valoracion(valoracion)
                .nombreCliente(this.nombre)
                .nombreAlojamiento(reserva.getAlojamiento().getNombre())
                .build();

        resenias.add(resenia);
    }


    public void eliminarResenia(Review resenia) throws Exception {
        if (resenia == null) throw new Exception("Seleccione una reseña para eliminar.");
        if (!resenias.contains(resenia)) throw new Exception("No se encontró la reseña");
        resenias.remove(resenia);
    }

    public ArrayList<Review> listarResenias() {
        return resenias;
    }

    public void eliminarReserva(Reserva reserva) {
        reservas.remove(reserva);
    }

    public void agregarReserva(Reserva reserva) {
        reservas.add(reserva);
    }


    @Override
    public String toString() {
        return "Cliente{" +
                "codigoActivacion=" + codigoActivacion +
                ", activo=" + activo +
                '}';
    }
}