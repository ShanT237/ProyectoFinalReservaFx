package co.edu.uniquindio.proyectofinalhotelfx.Controladores.ControladoresCliente;

import co.edu.uniquindio.proyectofinalhotelfx.Controladores.ControladorPrincipal;
import co.edu.uniquindio.proyectofinalhotelfx.Modelo.Entidades.Alojamiento;
import co.edu.uniquindio.proyectofinalhotelfx.Modelo.Entidades.Reserva;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.time.LocalDate;

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
        // Configurar columnas
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

        // ASIGNAR la lista global observable
        ControladorPrincipal controladorPrincipal = ControladorPrincipal.getInstancia();
        tblReservas.setItems(controladorPrincipal.getListaReservas());
    }
}
