package co.edu.uniquindio.proyectofinalhotelfx.Controladores.ControladoresAdm;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.FlowPane;

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
    private Button btnCrearOferta;

    @FXML
    private CheckBox esGlobal;

    @FXML
    private FlowPane flowPaneOferfas;

    @FXML
    void buscarOferta(ActionEvent event) {

    }

    @FXML
    void initialize() {
        assert boxAlojamiento != null : "fx:id=\"boxAlojamiento\" was not injected: check your FXML file 'GestionOfertasAdmin.fxml'.";
        assert boxCiudad != null : "fx:id=\"boxCiudad\" was not injected: check your FXML file 'GestionOfertasAdmin.fxml'.";
        assert btnCrearOferta != null : "fx:id=\"btnCrearOferta\" was not injected: check your FXML file 'GestionOfertasAdmin.fxml'.";
        assert esGlobal != null : "fx:id=\"esGlobal\" was not injected: check your FXML file 'GestionOfertasAdmin.fxml'.";
        assert flowPaneOferfas != null : "fx:id=\"flowPaneOferfas\" was not injected: check your FXML file 'GestionOfertasAdmin.fxml'.";

    }

}