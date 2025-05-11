package co.edu.uniquindio.proyectofinalhotelfx.Controladores;

import co.edu.uniquindio.proyectofinalhotelfx.App;
import co.edu.uniquindio.proyectofinalhotelfx.Singleton.SesionAdm;
import co.edu.uniquindio.proyectofinalhotelfx.Singleton.SesionCliente;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
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
    SesionAdm sesionAdm = SesionAdm.instancia();

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
    public void redirigirAHomeAdm() throws IOException {
        mostrarVentana("/co/edu/uniquindio/proyectofinalhotelfx/HomeAdmin.fxml", "BookYourStay - Home Admin");
    }

    public void redirigirAHomeCliente() throws IOException {
        mostrarVentana("/co/edu/uniquindio/proyectofinalhotelfx/HomeCliente.fxml", "BookYourStay - Home Cliente");
    }


    private void mostrarVentana(String ruta, String tituloVentana) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(ruta));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = (Stage) txtCorreo.getScene().getWindow();
        stage.getIcons().add(new Image(Objects.requireNonNull(App.class.getResourceAsStream("/Img/ImagenesApp/icon.png"))));
        stage.setTitle(tituloVentana);
        stage.setResizable(true);
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
