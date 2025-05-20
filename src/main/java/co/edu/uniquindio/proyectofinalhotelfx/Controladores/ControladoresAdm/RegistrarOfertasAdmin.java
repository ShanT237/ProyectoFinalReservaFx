package co.edu.uniquindio.proyectofinalhotelfx.Controladores.ControladoresAdm;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class RegistrarOfertasAdmin {

    @FXML
    private TextField txtCodigo, txtDescripcion, txtPorcentaje;

    @FXML
    private DatePicker dpFechaInicio, dpFechaFin;

    @FXML
    public void guardarOferta(ActionEvent event) {
        // Validación básica
        if (txtCodigo.getText().isBlank() || txtDescripcion.getText().isBlank() || txtPorcentaje.getText().isBlank()
                || dpFechaInicio.getValue() == null || dpFechaFin.getValue() == null) {
            System.out.println("Por favor, completa todos los campos.");
            return;
        }

        System.out.println("Guardando oferta...");
        System.out.println("Código: " + txtCodigo.getText());
        System.out.println("Descripción: " + txtDescripcion.getText());
        System.out.println("Descuento: " + txtPorcentaje.getText());
        System.out.println("Fecha inicio: " + dpFechaInicio.getValue());
        System.out.println("Fecha fin: " + dpFechaFin.getValue());

        // Cierra la ventana después de guardar
        Stage stage = (Stage) txtCodigo.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void cancelarRegistro(ActionEvent event) {
        Stage stage = (Stage) txtCodigo.getScene().getWindow();
        stage.close();
    }
}
