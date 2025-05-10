package co.edu.uniquindio.proyectofinalhotelfx.Controladores;

import co.edu.uniquindio.proyectofinalhotelfx.Modelo.Entidades.Cliente;
import co.edu.uniquindio.proyectofinalhotelfx.Repo.AlojamientoRepository;
import co.edu.uniquindio.proyectofinalhotelfx.Repo.ClienteRepository;
import co.edu.uniquindio.proyectofinalhotelfx.Servicios.ServicioAlojamiento;
import co.edu.uniquindio.proyectofinalhotelfx.Servicios.ServicioCliente;
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

    @FXML private ResourceBundle resources;
    @FXML private URL location;
    @FXML private Hyperlink linkRecuperar;
    @FXML private Label lblMensajeError;
    @FXML private TextField txtCorreo;
    @FXML private PasswordField txtPassword;

    ControladorPrincipal controladorPrincipal = ControladorPrincipal.getInstancia();
    SesionCliente sesionCliente = SesionCliente.instancia();

    @FXML
    void initialize() {
        assert linkRecuperar != null : "fx:id=\"linkRecuperar\" was not injected.";
        assert lblMensajeError != null : "fx:id=\"lblMensajeError\" was not injected.";
        assert txtCorreo != null : "fx:id=\"txtCorreo\" was not injected.";
        assert txtPassword != null : "fx:id=\"txtPassword\" was not injected.";
    }

    @FXML
    private void iniciarSesion(ActionEvent event) {
        try {
            String correo = txtCorreo.getText().trim();
            String password = txtPassword.getText().trim();

            if (controladorPrincipal.getPlataforma().loginAdm(correo, password)) {
                mostrarMensaje("¡Inicio de sesión exitoso como administrador!");

            } else if (controladorPrincipal.getPlataforma().loginCliente(correo, password)) {
                mostrarMensaje("¡Inicio de sesión exitoso como cliente!");
                redirigirAHome(); // Redirigir al home para cliente
            } else {
                mostrarError("Credenciales inválidas.");
            }

            limpiarCampos(); // Limpiar los campos de entrada
        } catch (Exception e) {
            mostrarError("Error al iniciar sesión: " + e.getMessage());
        }
    }

    private void redirigirAHome() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("//co/edu/uniquindio/proyectofinalhotelfx/HomeCliente.fxml"));
        Parent root = loader.load();
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
