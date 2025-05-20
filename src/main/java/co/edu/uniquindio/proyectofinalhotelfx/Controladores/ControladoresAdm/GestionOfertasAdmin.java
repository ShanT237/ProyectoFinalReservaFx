package co.edu.uniquindio.proyectofinalhotelfx.Controladores.ControladoresAdm;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import co.edu.uniquindio.proyectofinalhotelfx.Controladores.ControladorPrincipal;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.layout.FlowPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class GestionOfertasAdmin {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<?> boxAlojamiento;

    @FXML
    private ComboBox<?> boxCiudad;

    @FXML
    private CheckBox esGlobal;

    @FXML
    private FlowPane flowPaneOferfas;

    private ControladorPrincipal controladorPrincipal = ControladorPrincipal.getInstancia();


    @FXML
    void initialize() {
    }


    @FXML
    void buscarOferta(ActionEvent event) {

    }

    @FXML
    void btnCrearOferta(ActionEvent event) {
        mostrarVentana(event, "/co/edu/uniquindio/proyectofinalhotelfx/FXMLDW(ADMIN)/RegistrarOfertas.fxml", "Registrar Oferta");

    }

    @FXML
    private void mostrarVentana(ActionEvent event, String ruta, String titulo) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(ruta));
            Parent root = loader.load();

            Stage stage = new Stage();
            File archivoImagen = new File("Img/ImagenesApp/icon.png");
            Image icono = new Image(archivoImagen.toURI().toString());
            stage.setResizable(false);
            stage.setTitle(titulo);

            Stage parentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.initOwner(parentStage);
            stage.initModality(Modality.WINDOW_MODAL);

            stage.setScene(new Scene(root));
            stage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}