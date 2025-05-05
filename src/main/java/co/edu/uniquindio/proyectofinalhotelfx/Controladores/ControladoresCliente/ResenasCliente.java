package co.edu.uniquindio.proyectofinalhotelfx.Controladores.ControladoresCliente;

import javafx.fxml.FXML;
import javafx.scene.control.TableView;

public class ResenasCliente {
    @FXML private TableView<String> tblResenas;

    @FXML
    public void initialize() {
        // Datos de prueba
        tblResenas.getItems().addAll(
                "Hotel Ejemplo - 4 estrellas - Buen servicio",
                "Casa Campestre - 5 estrellas - Excelente"
        );
    }
}