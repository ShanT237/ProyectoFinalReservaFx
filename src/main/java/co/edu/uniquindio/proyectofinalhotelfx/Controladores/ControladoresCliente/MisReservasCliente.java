package co.edu.uniquindio.proyectofinalhotelfx.Controladores.ControladoresCliente;

import javafx.fxml.FXML;
import javafx.scene.control.TableView;

public class MisReservasCliente {
    @FXML private TableView<String> tblReservas;

    @FXML
    public void initialize() {
        tblReservas.getItems().addAll(
                "Reserva #001 - Hotel Ejemplo - 15/Nov/2023",
                "Reserva #002 - Casa Campestre - 20/Nov/2023"
        );
    }
}