package co.edu.uniquindio.proyectofinalhotelfx.Controladores.ControladoresCliente;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class MiBilleteraCliente {
    @FXML private Label lblSaldo;
    @FXML private TextField txtMontoRecarga;
    @FXML private TableView<?> tblTransacciones;

    @FXML
    public void initialize() {
        lblSaldo.setText("$500.00");
        // Configuración adicional para la tabla
    }
}