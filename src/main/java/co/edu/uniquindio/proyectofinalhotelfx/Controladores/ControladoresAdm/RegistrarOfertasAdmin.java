package co.edu.uniquindio.proyectofinalhotelfx.Controladores.ControladoresAdm;

import java.net.URL;
import java.util.ResourceBundle;

import co.edu.uniquindio.proyectofinalhotelfx.Modelo.Enums.Ciudad;
import co.edu.uniquindio.proyectofinalhotelfx.Modelo.Enums.OfertaTipo;
import co.edu.uniquindio.proyectofinalhotelfx.Modelo.Enums.TipoAlojamiento;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;

public class RegistrarOfertasAdmin {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<TipoAlojamiento> alojamientoBox;

    @FXML
    private ComboBox<Ciudad> ciudadBox;

    @FXML
    private TextField descripcionField;

    @FXML
    private ComboBox<Double> descuentoField;

    @FXML
    private DatePicker fechaFin;

    @FXML
    private DatePicker fechaInicio;

    @FXML
    private TextField idField;

    @FXML
    private TextField nombreField;

    @FXML
    private Slider sliderNochesMin;

    @FXML
    private ComboBox<OfertaTipo> tipoOferta;

    @FXML
    void guardarOferta(ActionEvent event) {

    }

    @FXML
    void initialize() {

    }

}