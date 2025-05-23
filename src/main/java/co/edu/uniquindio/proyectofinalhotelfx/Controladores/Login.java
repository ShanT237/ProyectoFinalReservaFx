package co.edu.uniquindio.proyectofinalhotelfx.Controladores;

import co.edu.uniquindio.proyectofinalhotelfx.Controladores.ControladoresCliente.CodigoVerificacion;
import co.edu.uniquindio.proyectofinalhotelfx.Modelo.Entidades.Cliente;
import co.edu.uniquindio.proyectofinalhotelfx.Modelo.Entidades.Usuario;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.image.Image;


import java.io.File;
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
    private void iniciarSesion(ActionEvent event) {
        try {
            String correo = txtCorreo.getText().trim();
            String password = txtPassword.getText().trim();

            Usuario usuario1 = controladorPrincipal.getPlataforma().loginAdm(correo, password);

            if (usuario1 != null) {
                controladorPrincipal.guardarSesion(usuario1);
                lblMensajeError.setTextFill(Color.BLACK);
                mostrarMensaje("¡Inicio de sesión exitoso como administrador!");
                redirigirAHomeAdm();
                limpiarCampos(); // SOLO si fue exitoso
            } else {
                try {
                    Usuario usuario2 = controladorPrincipal.getPlataforma().loginCliente(correo, password);
                    controladorPrincipal.guardarSesion(usuario2);
                    lblMensajeError.setTextFill(Color.BLACK);
                    mostrarMensaje("¡Inicio de sesión exitoso como cliente!");

                    redirigirAHomeCliente();
                    limpiarCampos(); // SOLO si fue exitoso
                } catch (Exception ex) {
                    if (ex.getMessage().contains("Debe validar su estado de cuenta")) {
                        lblMensajeError.setTextFill(Color.RED);
                        mostrarMensaje("Cuenta inactiva. Redirigiendo a validación...");

                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/co/edu/uniquindio/proyectofinalhotelfx/CodigoVerificacion.fxml"));
                        Parent root = loader.load();

                        CodigoVerificacion codigoVerificacion = loader.getController();
                        codigoVerificacion.setCorreo(correo);

                        Stage stage = new Stage();
                        File archivoImagen = new File("Img/ImagenesApp/icon.png");
                        Image icono = new Image(archivoImagen.toURI().toString());
                        stage.getIcons().add(icono);
                        stage.setTitle("Verificación de Código");
                        stage.setScene(new Scene(root));
                        stage.setResizable(false);
                        stage.show();

                        ((Stage) ((Node) event.getSource()).getScene().getWindow()).close();
                    } else {
                        mostrarError("Error al iniciar sesión: " + ex.getMessage());
                    }
                }
            }

        } catch (Exception e) {
            lblMensajeError.setTextFill(Color.RED);
            mostrarError("Error al iniciar sesión: " + e.getMessage());
            e.printStackTrace();
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

    private void redirigirCodigoVerificacion() throws IOException {
        mostrarVentana("/co/edu/uniquindio/proyectofinalhotelfx/CodigoVerificacion.fxml", "BookYourStay - Codigo Verificacion");
    }

    // Método para mostrar una ventana específica
    private void mostrarVentana(String ruta, String tituloVentana) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(ruta));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = (Stage) txtCorreo.getScene().getWindow();
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.setTitle(tituloVentana);
        stage.setResizable(true);
        stage.show();
    }

    // Método para mostrar mensajes de error
    private void mostrarError(String mensaje) {
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