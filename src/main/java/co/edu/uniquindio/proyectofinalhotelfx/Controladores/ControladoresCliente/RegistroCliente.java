package co.edu.uniquindio.proyectofinalhotelfx.Controladores.ControladoresCliente;

import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.application.Platform;

public class RegistroCliente {

    // Elementos inyectados desde el archivo FXML
    @FXML private TextField txtNombre;
    @FXML private TextField txtCorreo;
    @FXML private TextField txtTelefono;
    @FXML private TextField txtCedula;
    @FXML private PasswordField txtPassword;
    @FXML private PasswordField txtConfirmarPassword;
    @FXML private Button btnRegistrarse;
    @FXML private Button btnVolver;
    @FXML private Label lblMensajeError;

    @FXML
    void initialize() {
        assert txtNombre != null : "fx:id=\"txtNombre\" was not injected: check your FXML file 'RegistroCliente.fxml'.";
        assert btnVolver != null : "fx:id=\"btnVolver\" was not injected: check your FXML file 'RegistroCliente.fxml'.";
        assert lblMensajeError != null : "fx:id=\"lblMensajeError\" was not injected: check your FXML file 'RegistroCliente.fxml'.";
        assert txtCedula != null : "fx:id=\"txtCedula\" was not injected: check your FXML file 'RegistroCliente.fxml'.";
        assert txtCorreo != null : "fx:id=\"txtCorreo\" was not injected: check your FXML file 'RegistroCliente.fxml'.";
        assert txtConfirmarPassword != null : "fx:id=\"txtConfirmarPassword\" was not injected: check your FXML file 'RegistroCliente.fxml'.";
        assert btnRegistrarse != null : "fx:id=\"btnRegistrarse\" was not injected: check your FXML file 'RegistroCliente.fxml'.";
        assert txtTelefono != null : "fx:id=\"txtTelefono\" was not injected: check your FXML file 'RegistroCliente.fxml'.";
        assert txtPassword != null : "fx:id=\"txtPassword\" was not injected: check your FXML file 'RegistroCliente.fxml'.";

        // Configuración de eventos
        btnRegistrarse.setOnAction(this::registrarUsuario);
        btnVolver.setOnAction(this::irPantallaPrincipal);
    }

    private void irPantallaPrincipal(ActionEvent actionEvent) {
        Platform.runLater(() -> 
            navegarVentana("/co/edu/uniquindio/proyectofinalhotelfx/PantallaPrincipal.fxml", "BookYourStay - Inicio")
        );
    }

    @FXML
    private void registrarUsuario(ActionEvent event) {
        try {
            // Obtener los valores de los campos
            String nombre = txtNombre.getText().trim();
            String correo = txtCorreo.getText().trim();
            String telefono = txtTelefono.getText().trim();
            String cedula = txtCedula.getText().trim();
            String password = txtPassword.getText();
            String confirmarPassword = txtConfirmarPassword.getText();

            // Validar campos vacíos
            if (nombre.isEmpty() || correo.isEmpty() || telefono.isEmpty() || cedula.isEmpty() || 
                password.isEmpty() || confirmarPassword.isEmpty()) {
                mostrarError("Todos los campos son obligatorios");
                return;
            }

            // Validar formato de nombre
            if (!nombre.matches("[a-zA-Z\\s]+")) {
                mostrarError("El nombre solo debe contener letras y espacios");
                return;
            }

            // Validar cédula
            if (!cedula.matches("\\d+") || cedula.length() < 8 || cedula.length() > 10) {
                mostrarError("La cédula debe contener entre 8 y 10 números");
                return;
            }

            // Validar teléfono
            if (!telefono.matches("\\d{8,10}")) {
                mostrarError("El número de teléfono debe tener entre 8 y 10 dígitos");
                return;
            }

            // Validar correo
            if (!correo.matches("^[a-zA-Z0-9._%+-]+@gmail\\.com$")) {
                mostrarError("El correo debe ser un correo Gmail válido");
                return;
            }

            // Validar contraseña
            if (!password.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{8,}$")) {
                mostrarError("La contraseña debe tener al menos 8 caracteres, una mayúscula, una minúscula y un número");
                return;
            }

            // Validar coincidencia de contraseñas
            if (!password.equals(confirmarPassword)) {
                mostrarError("Las contraseñas no coinciden");
                return;
            }

            // Si todo es válido, mostrar mensaje de éxito y limpiar campos
            mostrarMensaje("¡Registro exitoso!");
            limpiarCampos();

            // Usar PauseTransition para la navegación retardada
            PauseTransition delay = new PauseTransition(Duration.seconds(2));
            delay.setOnFinished(e -> irPantallaPrincipal(null));
            delay.play();

        } catch (Exception e) {
            mostrarError("Error en el registro: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void mostrarError(String mensaje) {
        Platform.runLater(() -> {
            lblMensajeError.setTextFill(Color.RED);
            lblMensajeError.setText(mensaje);
        });
    }

    private void mostrarMensaje(String mensaje) {
        Platform.runLater(() -> {
            lblMensajeError.setTextFill(Color.GREEN);
            lblMensajeError.setText(mensaje);
        });
    }

    private void limpiarCampos() {
        Platform.runLater(() -> {
            txtNombre.clear();
            txtCorreo.clear();
            txtTelefono.clear();
            txtCedula.clear();
            txtPassword.clear();
            txtConfirmarPassword.clear();
            lblMensajeError.setText("");
        });
    }

    private void navegarVentana(String nombreArchivoFxml, String tituloVentana) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(nombreArchivoFxml));
            Parent root = loader.load();

            Platform.runLater(() -> {
                try {
                    Scene scene = new Scene(root);
                    Stage stage = new Stage();
                    stage.setScene(scene);
                    stage.setTitle(tituloVentana);
                    stage.setResizable(false);
                    stage.show();

                    // Cerrar la ventana actual
                    cerrarVentana();
                } catch (Exception e) {
                    mostrarError("Error al mostrar la ventana: " + e.getMessage());
                    e.printStackTrace();
                }
            });

        } catch (Exception e) {
            mostrarError("Error al cargar la ventana: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void cerrarVentana() {
        Stage stage = (Stage) btnVolver.getScene().getWindow();
        stage.close();
    }

    public void volverPantallaPrincipal(ActionEvent actionEvent) {
        irPantallaPrincipal(actionEvent);
    }
}