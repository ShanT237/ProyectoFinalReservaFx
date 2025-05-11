package co.edu.uniquindio.proyectofinalhotelfx.Controladores.ControladoresAdm;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import co.edu.uniquindio.proyectofinalhotelfx.Modelo.Enums.Ciudad;
import co.edu.uniquindio.proyectofinalhotelfx.Modelo.Enums.ServiciosIncluidos;
import co.edu.uniquindio.proyectofinalhotelfx.Modelo.Enums.TipoAlojamiento;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class RegistrarAlojamientoAdmin {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private CheckBox admiteMascotas;

    @FXML
    private ComboBox<Ciudad> ciudadBox;

    @FXML
    private TextField descripcionField;

    @FXML
    private TextField fieldNombre;

    @FXML
    private Slider numHabitaciones;

    @FXML
    private Slider numPersonas;

    @FXML
    private TextField precioField;

    @FXML
    private ListView<ServiciosIncluidos> serviciosIncluidos;

    @FXML
    private ComboBox<TipoAlojamiento> tipoAlojamientoBox;

    @FXML
    void registrarAlojamiento(ActionEvent event) {

    }

    @FXML
    void seleccionarImagen(ActionEvent event) {
       datosInicio();
    }
    @FXML
    void initialize() {

    }

    public void datosInicio() {
        cargarCiudades();
        cargarTiposAlojamiento();
    }

    private void cargarCiudades() {
        ObservableList<Ciudad> ciudades = FXCollections.observableArrayList(Ciudad.values());
        FXCollections.sort(ciudades, (c1, c2) -> c1.name().compareTo(c2.name()));
        ciudadBox.setItems(ciudades);
    }

    private void cargarTiposAlojamiento() {
        ObservableList<TipoAlojamiento> tipos = FXCollections.observableArrayList(TipoAlojamiento.values());
        tipoAlojamientoBox.setItems(tipos);
    }



}
