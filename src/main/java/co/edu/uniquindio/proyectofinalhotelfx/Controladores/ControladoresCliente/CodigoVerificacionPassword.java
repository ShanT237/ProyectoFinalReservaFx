package co.edu.uniquindio.proyectofinalhotelfx.Controladores.ControladoresCliente;

import co.edu.uniquindio.proyectofinalhotelfx.Controladores.ControladorPrincipal;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.Duration;

public class CodigoVerificacionPassword {

    @FXML private PasswordField txtConfirmarContrasena;
    @FXML private Label lblMensaje;
    @FXML private Button btnCambiarContrasena;
    @FXML private TextField txtCodigo;
    @FXML private PasswordField txtNuevaContrasena;

    private String correo;

    @FXML
    void initialize() {
        assert txtConfirmarContrasena != null : "fx:id=\"txtConfirmarContrasena\" was not injected: check your FXML file 'CodigoVerificacionPassword.fxml'.";
        assert lblMensaje != null : "fx:id=\"lblMensaje\" was not injected: check your FXML file 'CodigoVerificacionPassword.fxml'.";
        assert btnCambiarContrasena != null : "fx:id=\"btnCambiarContrasena\" was not injected: check your FXML file 'CodigoVerificacionPassword.fxml'.";
        assert txtCodigo != null : "fx:id=\"txtCodigo\" was not injected: check your FXML file 'CodigoVerificacionPassword.fxml'.";
        assert txtNuevaContrasena != null : "fx:id=\"txtNuevaContrasena\" was not injected: check your FXML file 'CodigoVerificacionPassword.fxml'.";

        btnCambiarContrasena.setOnAction(this::cambiarContrasena);
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }


    @FXML
    void cambiarContrasena(ActionEvent event) {
        String codigoIngresado = txtCodigo.getText().trim();
        String nuevaContrasena = txtNuevaContrasena.getText().trim();
        String confirmar = txtConfirmarContrasena.getText().trim();

        lblMensaje.setStyle("-fx-text-fill: red;");

        if (codigoIngresado.isEmpty() || nuevaContrasena.isEmpty() || confirmar.isEmpty()) {
            lblMensaje.setText("Todos los campos son obligatorios.");
            return;
        }

        if (!nuevaContrasena.equals(confirmar)) {
            lblMensaje.setText("Las contraseñas no coinciden.");
            return;
        }

        if (correo == null || correo.isEmpty()) {
            lblMensaje.setText("No se ha proporcionado un correo para recuperación.");
            return;
        }

        ControladorPrincipal controlador = ControladorPrincipal.getInstancia();

        try {
            // Primero verificamos si existe un código activo
            if (!controlador.getPlataforma().existeUsuarioPorCorreo(correo)) {
                lblMensaje.setText("No hay código de verificación activo. Por favor, solicite uno nuevo.");
                return;
            }

            // Si existe un código activo, procedemos con la actualización
            controlador.getPlataforma().actualizarContrasena(correo, nuevaContrasena, confirmar, codigoIngresado);

            lblMensaje.setStyle("-fx-text-fill: green;");
            lblMensaje.setText("Contraseña actualizada exitosamente.");

            PauseTransition pause = new PauseTransition(Duration.seconds(2));
            pause.setOnFinished(e -> cerrarVentana());
            pause.play();

        } catch (Exception e) {
            lblMensaje.setStyle("-fx-text-fill: red;");
            lblMensaje.setText("Error: " + e.getMessage());
        }
    }

    private void cerrarVentana() {
        Stage stage = (Stage) btnCambiarContrasena.getScene().getWindow();
        stage.close();
    }
}
