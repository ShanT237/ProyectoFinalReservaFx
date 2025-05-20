package co.edu.uniquindio.proyectofinalhotelfx.Controladores.ControladoresAdm;

import java.net.URL;
import java.util.ResourceBundle;

import co.edu.uniquindio.proyectofinalhotelfx.Modelo.Enums.ServiciosIncluidos;
import co.edu.uniquindio.proyectofinalhotelfx.Modelo.Enums.TipoHabitacionHotel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

public class RegistrarHabitacionAdmin {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField fieldNombre;

    @FXML
    private ImageView imagenPreview;

    @FXML
    private Label informacionLabel;

    @FXML
    private Slider numPersonas;

    @FXML
    private TextField precioField;

    @FXML
    private ListView<ServiciosIncluidos> serviciosIncluidos;

    @FXML
    private ComboBox<TipoHabitacionHotel> tipoHabitacionBox;

    @FXML
    void registrarAlojamiento(ActionEvent event) {

    }

    @FXML
    void seleccionarImagen(ActionEvent event) {

    }

    @FXML
    void initialize() {
    }

}