package co.edu.uniquindio.proyectofinalhotelfx.Controladores;

import co.edu.uniquindio.proyectofinalhotelfx.Controladores.ControladoresCliente.CodigoVerificacionPassword;
import co.edu.uniquindio.proyectofinalhotelfx.Notificacion.Notificacion;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class RecuperarPassword implements Initializable {

    @FXML
    private Label lblMensajeError;

    @FXML
    private TextField txtCorreo;

    @FXML
    void recuperarContrasena(ActionEvent event) {
        try {
            String correo = txtCorreo.getText().trim();

            if (correo.isEmpty()) {
                mostrarError("El correo ingresado es obligatorio");
                return;
            }

            if (!esCorreoValido(correo)) {
                mostrarError("El formato del correo es inválido.");
                return;
            }

            ControladorPrincipal controladorPrincipal = ControladorPrincipal.getInstancia();
            ControladorPrincipal.getInstancia().getPlataforma().recuperarContrasena(correo);

            mostrarMensaje("Se ha enviado un código de verificación a tu correo.");
            limpiarCampos();

            // Espera 2 segundos y abre la nueva ventana
            new Thread(() -> {
                try {
                    Thread.sleep(2000);

                    Platform.runLater(() -> {
                        try {
                            FXMLLoader loader = new FXMLLoader(getClass().getResource("/co/edu/uniquindio/proyectofinalhotelfx/CodigoVerificacionPassword.fxml"));
                            Parent root = loader.load();

                            // Establecer el correo en el controlador
                            CodigoVerificacionPassword codigoVerificacion = loader.getController();
                            codigoVerificacion.setCorreo(correo);

                            Stage stage = new Stage();
                            File archivoImagen = new File("Img/ImagenesApp/icon.png");
                            Image icono = new Image(archivoImagen.toURI().toString());
                            stage.getIcons().add(icono);
                            stage.setTitle("Verificación de Código");
                            stage.setScene(new Scene(root));
                            stage.setResizable(false);
                            stage.show();

                            // Cerrar la ventana actual
                            ((Stage) ((Node) event.getSource()).getScene().getWindow()).close();

                        } catch (IOException e) {
                            e.printStackTrace();
                            mostrarError("Error al cargar la ventana de verificación.");
                        }
                    });

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();

        } catch (Exception e) {
            mostrarError("Ocurrió un error al intentar recuperar la contraseña.");
            e.printStackTrace();
        }
    }

    private boolean esCorreoValido(String correo) {
        return correo.matches("^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$");
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
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        assert lblMensajeError != null : "fx:id=\"lblMensajeError\" was not injected: check your FXML file 'RecuperarPassword.fxml'.";
        assert txtCorreo != null : "fx:id=\"txtCorreo\" was not injected: check your FXML file 'RecuperarPassword.fxml'.";
    }
}
