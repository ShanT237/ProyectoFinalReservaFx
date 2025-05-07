package co.edu.uniquindio.proyectofinalhotelfx.Controladores.ControladoresCliente;

import co.edu.uniquindio.proyectofinalhotelfx.Controladores.ControladorPrincipal;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

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

            //llamar el método de registro del servicio
            ControladorPrincipal controladorPrincipal= ControladorPrincipal.getInstancia().getPlataforma().registrarCliente(nombre, cedula, telefono, correo, password, confirmarPassword);

            // Si todo es válido, mostrar mensaje de éxito y limpiar campos
            mostrarMensaje("¡Registro exitoso!");
            limpiarCampos();

            // Opcional: navegar a la pantalla de inicio de sesión después de unos segundos
            new Thread(() -> {
                try {
                    Thread.sleep(2000);
                    //abrir la ventana de CodigoVerficacion (pasar el correo)
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