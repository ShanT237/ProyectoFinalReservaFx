package co.edu.uniquindio.proyectofinalhotelfx.Controladores.ControladoresAdm;

import java.net.URL;
import java.util.ResourceBundle;

import co.edu.uniquindio.proyectofinalhotelfx.Controladores.ControladorPrincipal;
import co.edu.uniquindio.proyectofinalhotelfx.Singleton.SesionUsuario;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.StackPane;

public class PerfilAdm {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private StackPane stack;

    ControladorPrincipal controladorPrincipal = ControladorPrincipal.getInstancia();
    SesionUsuario sesionAdm = SesionUsuario.instancia();

    @FXML
    void cambiarContrasena(ActionEvent event) {

    }

    @FXML
    void initialize() {

    }

}