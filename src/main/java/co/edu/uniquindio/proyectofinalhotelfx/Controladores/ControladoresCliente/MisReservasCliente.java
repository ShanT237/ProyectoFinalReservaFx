package co.edu.uniquindio.proyectofinalhotelfx.Controladores.ControladoresCliente;

import co.edu.uniquindio.proyectofinalhotelfx.Controladores.ControladorPrincipal;
import co.edu.uniquindio.proyectofinalhotelfx.Modelo.Entidades.Alojamiento;
import co.edu.uniquindio.proyectofinalhotelfx.Modelo.Entidades.Reserva;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class MisReservasCliente {

    @FXML
    private TableView<Reserva> tblReservas;

    @FXML
    private TableColumn<Reserva, String> colAlojamiento;

    @FXML
    private TableColumn<Reserva, String> colCiudad;

    @FXML
    private TableColumn<Reserva, LocalDate> colFechaInicio;

    @FXML
    private TableColumn<Reserva, LocalDate> colFechaFin;

    @FXML
    public void initialize() {
        // Configurar columnas para mostrar datos correctos
        colAlojamiento.setCellValueFactory(reserva -> {
            Alojamiento a = reserva.getValue().getAlojamiento();
            return new SimpleStringProperty(a != null ? a.getNombre() : "Sin nombre");
        });

        colCiudad.setCellValueFactory(reserva -> {
            Alojamiento a = reserva.getValue().getAlojamiento();
            return new SimpleStringProperty(a != null ? a.getCiudad().toString() : "Sin ciudad");
        });

        colFechaInicio.setCellValueFactory(reserva ->
                new SimpleObjectProperty<>(reserva.getValue().getFechaInicio().toLocalDate()));

        colFechaFin.setCellValueFactory(reserva ->
                new SimpleObjectProperty<>(reserva.getValue().getFechaFin().toLocalDate()));
    }

    /**
     * Carga las reservas de un cliente dado su cédula.
     * @param cedulaCliente La cédula del cliente cuyas reservas se quieren mostrar.
     */
    public void cargarReservas(String cedulaCliente) {
        ControladorPrincipal controladorPrincipal = ControladorPrincipal.getInstancia();

        // Obtener todas las reservas
        ObservableList<Reserva> todasReservas = controladorPrincipal.getListaReservas();

        // Filtrar solo las reservas del cliente
        List<Reserva> reservasCliente = todasReservas.stream()
                .filter(reserva -> reserva.getCliente() != null &&
                        cedulaCliente.equals(reserva.getCliente().getCedula()))
                .collect(Collectors.toList());

        // Mostrar en tabla
        tblReservas.getItems().setAll(reservasCliente);
    }
}
