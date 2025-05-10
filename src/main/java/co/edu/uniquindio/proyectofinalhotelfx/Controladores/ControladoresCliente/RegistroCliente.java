package co.edu.uniquindio.proyectofinalhotelfx.Controladores.ControladoresCliente;

import co.edu.uniquindio.proyectofinalhotelfx.Controladores.ControladorPrincipal;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;

public class RegistroCliente {

    @FXML
    public  TextField txtNombre;
    @FXML
    public  TextField txtCorreo;
    @FXML
    public  TextField txtTelefono;
    @FXML
    public  TextField txtCedula;
    @FXML
    public  PasswordField txtPassword;
    @FXML
    public  PasswordField txtConfirmarPassword;
    @FXML
    public  Button btnRegistrarse;
    @FXML
    public  Button btnVolver;
    @FXML
    public  Label lblMensajeError;

    @FXML
    void initialize() {
        // Configuración de eventos
        btnRegistrarse.setOnAction(this::registrarUsuario);
        btnVolver.setOnAction(this::irPantallaPrincipal);
    }

    private void irPantallaPrincipal(javafx.event.ActionEvent actionEvent) {
        // Regresar a la pantalla principal
        navegarVentana("/co/edu/uniquindio/proyectofinalhotelfx/PantallaPrincipal.fxml", "BookYourStay - Inicio");
    }

    @FXML
    private void registrarUsuario(javafx.event.ActionEvent event) {
        try {
            // Obtener los valores de los campos
            String nombre = txtNombre.getText().trim();
            String correo = txtCorreo.getText().trim();
            String telefono = txtTelefono.getText().trim();
            String cedula = txtCedula.getText().trim();
            String password = txtPassword.getText();
            String confirmarPassword = txtConfirmarPassword.getText();

            ControladorPrincipal controladorPrincipal= ControladorPrincipal.getInstancia();

            controladorPrincipal.getPlataforma().registrarCliente(nombre, cedula, telefono, correo, password, confirmarPassword);
            mostrarMensaje("¡Registro exitoso!");
            limpiarCampos();

            new Thread(() -> {
                try {
                    Thread.sleep(2000); // Esperar 2 segundos

                    // Luego abrir la ventana en el hilo de JavaFX
                    Platform.runLater(() -> {
                        try {
                            FXMLLoader loader = new FXMLLoader(getClass().getResource("/co/edu/uniquindio/proyectofinalhotelfx/CodigoVerificacion.fxml"));
                            Parent root = loader.load();

                            CodigoVerificacion codigoVerificacion = loader.getController();
                            codigoVerificacion.setCorreo(correo); // Asegúrate de tener este método

                            Stage stage = new Stage();
                            stage.setTitle("Verificación de Código");
                            stage.setScene(new Scene(root));
                            stage.setResizable(false);
                            stage.show();

                            // Cerrar la ventana actual
                            ((Stage) ((Node) event.getSource()).getScene().getWindow()).close();

                        } catch (IOException e) {
                            e.printStackTrace();
                            mostrarError("No se pudo abrir la ventana de verificación.");
                        }
                    });

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();

        } catch (Exception e) {
            mostrarError("Error en el registro: " + e.getMessage());
            e.printStackTrace();
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
        // Limpiar los campos del formulario
        txtNombre.clear();
        txtCorreo.clear();
        txtTelefono.clear();
        txtCedula.clear();
        txtPassword.clear();
        txtConfirmarPassword.clear();
        lblMensajeError.setText("");
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

            // Cerrar ventana actual
            cerrarVentana();

        } catch (Exception e) {
            mostrarError("Error al cargar la ventana: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void cerrarVentana() {
        // Cerrar la ventana actual
        Stage stage = (Stage) btnVolver.getScene().getWindow();
        stage.close();
    }

    public void volverPantallaPrincipal(ActionEvent actionEvent) {
        irPantallaPrincipal(actionEvent);
    }
}