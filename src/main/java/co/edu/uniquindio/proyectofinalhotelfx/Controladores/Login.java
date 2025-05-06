package co.edu.uniquindio.proyectofinalhotelfx.Controladores;

import co.edu.uniquindio.proyectofinalhotelfx.Modelo.Entidades.Administrador;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;

import java.net.URL;
import java.util.ResourceBundle;

public class Login {
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Hyperlink linkRecuperar;

    @FXML
    private Label lblMensajeError;

    @FXML
    private Button btnIngresar;

    @FXML
    private TextField txtCorreo;

    @FXML
    private PasswordField txtPassword;

    @FXML
    void iniciarSesion(javafx.event.ActionEvent event) {
        iniciarSesionCliente(event);
    }

    @FXML
    void initialize() {
        assert linkRecuperar != null : "fx:id=\"linkRecuperar\" was not injected: check your FXML file 'Login.fxml'.";
        assert lblMensajeError != null : "fx:id=\"lblMensajeError\" was not injected: check your FXML file 'Login.fxml'.";
        assert btnIngresar != null : "fx:id=\"btnIngresar\" was not injected: check your FXML file 'Login.fxml'.";
        assert txtCorreo != null : "fx:id=\"txtCorreo\" was not injected: check your FXML file 'Login.fxml'.";
        assert txtPassword != null : "fx:id=\"txtPassword\" was not injected: check your FXML file 'Login.fxml'.";
    }

    @FXML
    private void iniciarSesionCliente(javafx.event.ActionEvent event) {
        try {
            String correo = txtCorreo.getText().trim();
            String password = txtPassword.getText().trim();

            if (correo.isEmpty() || password.isEmpty()) {
                mostrarError("Todos los campos son obligatorios");
                return;
            }

            if (!correo.matches("^[a-zA-Z0-9._%+-]+@gmail\\.com$")) {
                mostrarError("El correo debe ser un correo Gmail válido");
                return;
            }

            mostrarMensaje("¡Inicio de sesión exitoso!");
            limpiarCampos();
        } catch (Exception e) {
            mostrarError("Error al iniciar sesión: " + e.getMessage());
        }
    }

    private void mostrarError(String mensaje) {
        lblMensajeError.setTextFill(Color.RED);
        lblMensajeError.setText(mensaje);
    }

    private void mostrarMensaje(String mensaje) {
        lblMensajeError.setTextFill(Color.GREEN);
        lblMensajeError.setText(mensaje);
    }

    private void limpiarCampos() {
        txtCorreo.clear();
        txtPassword.clear();
        lblMensajeError.setText("");
    }
}