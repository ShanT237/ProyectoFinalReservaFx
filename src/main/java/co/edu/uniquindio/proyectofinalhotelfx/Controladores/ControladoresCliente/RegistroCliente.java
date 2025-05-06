package co.edu.uniquindio.proyectofinalhotelfx.Controladores.ControladoresCliente;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.awt.event.ActionEvent;
import java.util.Objects;

public class RegistroCliente {

    @FXML private TextField txtNombre;
    @FXML private TextField txtCorreo;
    @FXML private TextField txtTelefono;
    @FXML private TextField txtCedula;
    @FXML private PasswordField txtPassword;
    @FXML private PasswordField txtConfirmarPassword;
    @FXML private Button btnVolver;
    @FXML private Label lblMensajeError;

    @FXML
    void initialize() {
        // Configuración de eventos
        btnVolver.setOnAction(this::irPantallaPrincipal);
    }

    private void irPantallaPrincipal(javafx.event.ActionEvent actionEvent) {
    }


    @FXML
    private void registrarUsuario(ActionEvent event) {
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

    public void irPantallaPrincipal(ActionEvent actionEvent) {
        navegarVentana("/co/edu/uniquindio/proyectofinalhotelfx/PantallaPrincipal.fxml", "BookYourStay - Inicio");
    }

    private void navegarVentana(String nombreArchivoFxml, String tituloVentana) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(nombreArchivoFxml));
            Parent root = loader.load();

            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle(tituloVentana);
            stage.setResizable(false);
            stage.show();

            cerrarVentana();

        } catch (Exception e) {
            mostrarError("Error al cargar la ventana: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void cerrarVentana() {
        Stage stage = (Stage) btnVolver.getScene().getWindow();
        stage.close();
    }

    public void volverPantallaPrincipal(javafx.event.ActionEvent actionEvent) {
    }

    public void registrarUsuario(javafx.event.ActionEvent actionEvent) {
    }
}