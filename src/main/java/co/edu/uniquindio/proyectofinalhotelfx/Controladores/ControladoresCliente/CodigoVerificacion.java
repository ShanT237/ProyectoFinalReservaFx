package co.edu.uniquindio.proyectofinalhotelfx.Controladores.ControladoresCliente;

import co.edu.uniquindio.proyectofinalhotelfx.Controladores.ControladorPrincipal;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.animation.PauseTransition;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class CodigoVerificacion {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btnVerificar;

    @FXML
    private Label lblMensajeError;

    @FXML
    private TextField txtCodigo;

    private String correoUsuario;

    @FXML
    void initialize() {
        assert btnVerificar != null : "fx:id=\"btnVerificar\" was not injected: check your FXML file 'CodigoVerificacion.fxml'.";
        assert lblMensajeError != null : "fx:id=\"lblMensajeError\" was not injected: check your FXML file 'CodigoVerificacion.fxml'.";
        assert txtCodigo != null : "fx:id=\"txtCodigo\" was not injected: check your FXML file 'CodigoVerificacion.fxml'.";

        btnVerificar.setOnAction(this::validarCodigo);
    }

    @FXML
    void validarCodigo(ActionEvent event) {
        try {
            String codigoIngresado = txtCodigo.getText().trim();
            String correo = obtenerCorreo();

            boolean validado = ControladorPrincipal.getInstancia()
                    .getPlataforma()
                    .validarCodigoVerificacion(correo, codigoIngresado);

            if (validado) {
                lblMensajeError.setText("¡Código validado con éxito!");

                // Esperar 1.5 segundos antes de cambiar de pantalla
                PauseTransition pause = new PauseTransition(Duration.seconds(1.5));
                pause.setOnFinished(e -> irPantallaPrincipal(event));
                pause.play();

            } else {
                lblMensajeError.setText("Código incorrecto, por favor inténtalo nuevamente.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            lblMensajeError.setText("Error en la validación del código.");
        }
    }

    private void irPantallaPrincipal(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("//co/edu/uniquindio/proyectofinalhotelfx/Login.fxml")); // Cambia esta ruta
            Parent root = loader.load();

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setCorreo(String correo) {
        this.correoUsuario = correo;
    }

    private String obtenerCorreo() {
        return this.correoUsuario;
    }
}
