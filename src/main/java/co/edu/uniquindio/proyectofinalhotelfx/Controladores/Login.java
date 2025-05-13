package co.edu.uniquindio.proyectofinalhotelfx.Controladores;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;

public class Login {

    @FXML
    private Hyperlink linkRegistro;

    @FXML
    private Hyperlink linkRecuperar;

    @FXML
    private Label lblMensajeError;

    @FXML
    private TextField txtCorreo;

    @FXML
    private PasswordField txtPassword;

    // Instancias necesarias (en el contexto de tu aplicación, ajusta según sea necesario)
    ControladorPrincipal controladorPrincipal = ControladorPrincipal.getInstancia();

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert linkRegistro != null : "fx:id=\"linkRegistro\" was not injected: check your FXML file 'Login.fxml'.";
        assert linkRecuperar != null : "fx:id=\"linkRecuperar\" was not injected: check your FXML file 'Login.fxml'.";
        assert lblMensajeError != null : "fx:id=\"lblMensajeError\" was not injected: check your FXML file 'Login.fxml'.";
        assert txtCorreo != null : "fx:id=\"txtCorreo\" was not injected: check your FXML file 'Login.fxml'.";
        assert txtPassword != null : "fx:id=\"txtPassword\" was not injected: check your FXML file 'Login.fxml'.";

    }

    @FXML
    void iniciarSesion(ActionEvent event) {
        try {
            String correo = txtCorreo.getText().trim();
            String password = txtPassword.getText().trim();

            // Validación de las credenciales y redirección
            if (controladorPrincipal.getPlataforma().loginAdm(correo, password)) {
                mostrarMensaje("¡Inicio de sesión exitoso como administrador!");
                redirigirAHomeAdm();

            } else if (controladorPrincipal.getPlataforma().loginCliente(correo, password)) {
                mostrarMensaje("¡Inicio de sesión exitoso como cliente!");
                redirigirAHomeCliente(); // Redirigir al home para cliente
            } else {
                mostrarError("Credenciales inválidas.");
            }

            limpiarCampos(); // Limpiar los campos de entrada
        } catch (Exception e) {
            mostrarError("Error al iniciar sesión: " + e.getMessage());
        }
    }

    @FXML
    void irARegistro(ActionEvent actionEvent) throws IOException {
        // Redirigir a la ventana de registro
        mostrarVentana("/co/edu/uniquindio/proyectofinalhotelfx/RegistroCliente.fxml", "Registro Cliente");
    }

    @FXML
    void irARecuperar(ActionEvent actionEvent) throws IOException {
        // Redirigir a la ventana de recuperación de contraseña
        mostrarVentana("/co/edu/uniquindio/proyectofinalhotelfx/RecuperarPassword.fxml", "Recuperar Contraseña");
    }

    // Método de redirección para Home Admin
    private void redirigirAHomeAdm() throws IOException {
        mostrarVentana("/co/edu/uniquindio/proyectofinalhotelfx/HomeAdmin.fxml", "BookYourStay - Home Admin");
    }

    // Método de redirección para Home Cliente
    private void redirigirAHomeCliente() throws IOException {
        mostrarVentana("/co/edu/uniquindio/proyectofinalhotelfx/HomeCliente.fxml", "BookYourStay - Home Cliente");
    }

    // Método para mostrar una ventana específica
    private void mostrarVentana(String ruta, String tituloVentana) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(ruta));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = (Stage) txtCorreo.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle(tituloVentana);
        stage.setResizable(false);
        stage.show();
    }

    // Método para mostrar mensajes de error
    private void mostrarError(String mensaje) {
        lblMensajeError.setTextFill(Color.RED);
        lblMensajeError.setText(mensaje);
    }

    // Método para mostrar mensajes de éxito
    private void mostrarMensaje(String mensaje) {
        lblMensajeError.setTextFill(Color.GREEN);
        lblMensajeError.setText(mensaje);
    }

    // Limpiar los campos de texto y el mensaje de error
    private void limpiarCampos() {
        txtCorreo.clear();
        txtPassword.clear();
        lblMensajeError.setText("");
    }
}
