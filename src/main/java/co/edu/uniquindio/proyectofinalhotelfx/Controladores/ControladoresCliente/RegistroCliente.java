package co.edu.uniquindio.proyectofinalhotelfx.Controladores.ControladoresCliente;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;

public class RegistroCliente {

    @FXML private TextField txtNombre, txtCorreo, txtTelefono, txtCedula;
    @FXML private PasswordField txtPassword, txtConfirmarPassword;
    @FXML private Button btnRegistrarse, btnVolver;
    @FXML private Label lblMensajeError;

    @FXML
    private void initialize() {
        btnRegistrarse.setOnAction(e -> registrarUsuario());
        btnVolver.setOnAction(e -> volverPantallaPrincipal());
    }

    @FXML
    private void registrarUsuario() {
        String nombre = txtNombre.getText().trim();
        String correo = txtCorreo.getText().trim();
        String telefono = txtTelefono.getText().trim();
        String cedula = txtCedula.getText().trim();
        String password = txtPassword.getText();
        String confirmarPassword = txtConfirmarPassword.getText();

        if (nombre.isEmpty() || correo.isEmpty() || telefono.isEmpty() || cedula.isEmpty() || password.isEmpty() || confirmarPassword.isEmpty()) {
            mostrarError("Todos los campos son obligatorios");
            return;
        }

        if (!cedula.matches("\\d+")) {
            mostrarError("La cédula debe contener solo números");
            return;
        }

        if (!telefono.matches("\\d{8,10}")) {
            mostrarError("El número de teléfono debe tener entre 8 y 10 dígitos");
            return;
        }

        if (!correo.matches("^[\\w.-]+@gmail\\.com$")) {
            mostrarError("El correo debe ser de dominio @gmail.com");
            return;
        }

        if (!password.equals(confirmarPassword)) {
            mostrarError("Las contraseñas no coinciden");
            return;
        }

        System.out.println("Registro exitoso para: " + nombre);
        mostrarMensaje("¡Registro exitoso!");
        limpiarCampos();
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
        txtNombre.clear();
        txtCorreo.clear();
        txtTelefono.clear();
        txtCedula.clear();
        txtPassword.clear();
        txtConfirmarPassword.clear();
    }

    @FXML
    private void volverPantallaPrincipal() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/co/edu/uniquindio/proyectofinalhotelfx/Vistas/pantallaPrincipal.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) btnVolver.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
