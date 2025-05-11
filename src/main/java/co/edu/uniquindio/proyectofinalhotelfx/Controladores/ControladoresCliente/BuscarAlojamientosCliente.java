package co.edu.uniquindio.proyectofinalhotelfx.Controladores.ControladoresCliente;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;

public class BuscarAlojamientosCliente {
    @FXML private ListView<String> lvAlojamientos;

    @FXML
    public void initialize() {
        // Datos de ejemplo
        lvAlojamientos.getItems().addAll(
                "Hotel Ejemplo - $120/noche",
                "Casa Campestre - $90/noche",
                "Apartamento Premium - $150/noche"
        );
    }
}