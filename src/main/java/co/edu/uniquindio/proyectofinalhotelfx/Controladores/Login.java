package co.edu.uniquindio.proyectofinalhotelfx.Controladores;

import co.edu.uniquindio.proyectofinalhotelfx.Modelo.Entidades.Cliente;
import co.edu.uniquindio.proyectofinalhotelfx.Singleton.SesionCliente;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
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
    Cliente iniciarSesion(ActionEvent event) {
        iniciarSesionCliente(event);
        return null;
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
            Cliente cliente = iniciarSesion(correo ,password);

            if (cliente == null) {
                mostrarMensaje("");

                limpiarCampos();


                SesionCliente.instancia().setUsuario(cliente);

                redirigirAHome();
            }else {
                mostrarMensaje("Usuario no registrado");
            }

            mostrarMensaje("¡Inicio de sesión exitoso!");
            limpiarCampos();
        } catch (Exception e) {
            mostrarError("Error al iniciar sesión: " + e.getMessage());
        }
    }


    private void redirigirAHome() throws IOException {
        // Cargar la vista de la página principal
        FXMLLoader loader = new FXMLLoader(getClass().getResource("//co/edu/uniquindio/proyectofinalhotelfx/HomeCliente.fxml"));
        Parent root = loader.load();

        // Obtener la escena actual y cambiar a la nueva vista
        Scene scene = new Scene(root);
        Stage stage = (Stage) txtCorreo.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
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

    public void irARegistro(ActionEvent actionEvent) {

    }
}