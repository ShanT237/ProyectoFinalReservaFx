package co.edu.uniquindio.proyectofinalhotelfx.Controlador;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;

public class PantallaPrincipal {

    @FXML
    private ComboBox<String> cbTipoAlojamiento;

    public void initialize() {
        cbTipoAlojamiento.getItems().addAll("Casa", "Apartamento", "Hotel");
    }
}
