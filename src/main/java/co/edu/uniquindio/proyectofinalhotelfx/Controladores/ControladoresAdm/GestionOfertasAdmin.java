package co.edu.uniquindio.proyectofinalhotelfx.Controladores.ControladoresAdm;

import co.edu.uniquindio.proyectofinalhotelfx.Modelo.Enums.Ciudad;
import co.edu.uniquindio.proyectofinalhotelfx.Modelo.Enums.TipoAlojamiento;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.FlowPane;

public class GestionOfertasAdmin {

    @FXML
    private ComboBox<TipoAlojamiento> boxAlojamiento;

    @FXML
    private ComboBox<Ciudad> boxCiudad;

    @FXML
    private Button btnCrearOferta;

    @FXML
    private CheckBox esGlobal;

    @FXML
    private FlowPane flowPaneOferfas;


    @FXML
    void initialize() {

    }

    @FXML
    void buscarOferta(ActionEvent event) {

    }

}