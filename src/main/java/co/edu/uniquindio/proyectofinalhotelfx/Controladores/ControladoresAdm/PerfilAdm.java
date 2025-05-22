package co.edu.uniquindio.proyectofinalhotelfx.Controladores.ControladoresAdm;

import java.util.ResourceBundle;

import co.edu.uniquindio.proyectofinalhotelfx.Controladores.ControladorPrincipal;
import co.edu.uniquindio.proyectofinalhotelfx.Singleton.SesionUsuario;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class PerfilAdm {

    @FXML
    private ResourceBundle resources;

    @FXML
    private StackPane stack;

    ControladorPrincipal controladorPrincipal = ControladorPrincipal.getInstancia();
    SesionUsuario sesionAdm = SesionUsuario.instancia();

    @FXML
    void cambiarContrasena(ActionEvent event) throws Exception {
        stack.getChildren().clear();

        controladorPrincipal.getPlataforma().getServicioAdm().cambiarContrasena(sesionAdm.getUsuario().getCorreo());

        VBox formulario = new VBox(15);
        formulario.setMaxWidth(400);
        formulario.setStyle("-fx-padding: 20; -fx-background-color: #f4f4f4; -fx-border-color: #ccc; -fx-border-radius: 10; -fx-background-radius: 10;");

        Label titulo = new Label("Cambiar Contraseña");
        titulo.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        Label instrucciones = new Label("Se ha enviado un código de verificación a tu correo: " + sesionAdm.getUsuario().getCorreo());

        TextField codigoField = new TextField();
        codigoField.setPromptText("Código de verificación");

        PasswordField nuevaContrasenaField = new PasswordField();
        nuevaContrasenaField.setPromptText("Nueva contraseña");

        PasswordField confirmarContrasenaField = new PasswordField();
        confirmarContrasenaField.setPromptText("Confirmar contraseña");

        Label mensaje = new Label();
        mensaje.setStyle("-fx-text-fill: red;");

        Button confirmarBtn = new Button("Confirmar cambio");
        confirmarBtn.setOnAction(e -> {
            String codigoIngresado = codigoField.getText().trim();
            String nuevaPass = nuevaContrasenaField.getText().trim();
            String confirmarPass = confirmarContrasenaField.getText().trim();

            if (codigoIngresado.isEmpty() || nuevaPass.isEmpty() || confirmarPass.isEmpty()) {
                mensaje.setText("Por favor completa todos los campos.");
                return;
            }

            try {
                controladorPrincipal.getPlataforma().getServicioAdm().actualizarContrasena(
                        sesionAdm.getUsuario().getCorreo(),
                        nuevaPass,
                        confirmarPass,
                        codigoIngresado
                );
                mensaje.setStyle("-fx-text-fill: green;");
                mensaje.setText("Contraseña actualizada correctamente.");
            } catch (Exception ex) {
                mensaje.setStyle("-fx-text-fill: red;");
                mensaje.setText(ex.getMessage());
            }
        });

        Button volverBtn = new Button("Volver");
        volverBtn.setOnAction(e -> {
            stack.getChildren().clear();
            initialize();
        });

        formulario.getChildren().addAll(
                titulo, instrucciones, codigoField, nuevaContrasenaField,
                confirmarContrasenaField, confirmarBtn, volverBtn, mensaje
        );

        stack.getChildren().add(formulario);
    }

    @FXML
    void initialize() {
        stack.getChildren().clear();

        if (sesionAdm != null && sesionAdm.getUsuario() != null) {
            VBox contenedor = new VBox(15);
            contenedor.setStyle("-fx-padding: 20;");

            Label lblTitulo = new Label("Perfil del Administrador");
            lblTitulo.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

            Label lblNombre = new Label("Nombre: " + sesionAdm.getUsuario().getNombre());
            Label lblCorreo = new Label("Correo: " + sesionAdm.getUsuario().getCorreo());
            Label lblTelefono = new Label("Teléfono: " + sesionAdm.getUsuario().getTelefono());
            Label lblCedula = new Label("Cédula: " + sesionAdm.getUsuario().getCedula());

            contenedor.getChildren().addAll(lblTitulo, lblNombre, lblCorreo, lblTelefono, lblCedula);
            stack.getChildren().add(contenedor);
        } else {
            Label lblError = new Label("No hay administrador activo en sesión.");
            stack.getChildren().add(lblError);
        }
    }
}