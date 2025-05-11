package co.edu.uniquindio.proyectofinalhotelfx.Controladores.ControladoresAdm;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import co.edu.uniquindio.proyectofinalhotelfx.Modelo.Enums.Ciudad;
import co.edu.uniquindio.proyectofinalhotelfx.Modelo.Enums.ServiciosIncluidos;
import co.edu.uniquindio.proyectofinalhotelfx.Modelo.Enums.TipoAlojamiento;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxListCell;
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
        int habitaciones = (int) numHabitaciones.getValue();
        int personas = (int) numPersonas.getValue();

    }

    @FXML
    void seleccionarImagen(ActionEvent event) {

    }
    @FXML
    void initialize() {
        datosInicio();
    }

    public void datosInicio() {
        cargarCiudades();
        cargarTiposAlojamiento();
        cargarServiciosIncluidos();
        configurarSliders();
        agregarTooltipSlider(numHabitaciones);
        agregarTooltipSlider(numPersonas);
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

    private void cargarServiciosIncluidos() {
        ObservableList<ServiciosIncluidos> servicios = FXCollections.observableArrayList(ServiciosIncluidos.values());
        serviciosIncluidos.setItems(servicios);

        serviciosIncluidos.setCellFactory(CheckBoxListCell.forListView(servicio -> {
            BooleanProperty observable = new SimpleBooleanProperty();
            observable.addListener((obs, wasSelected, isNowSelected) -> {
                System.out.println(servicio + " seleccionado: " + isNowSelected);
            });
            return observable;
        }));
    }

    private void configurarSliders() {
        numHabitaciones.setMin(1);
        numHabitaciones.setMax(10);
        numHabitaciones.setMajorTickUnit(1);
        numHabitaciones.setMinorTickCount(0);
        numHabitaciones.setSnapToTicks(false);
        numHabitaciones.setShowTickLabels(false);
        numHabitaciones.setShowTickMarks(false);

        numPersonas.setMin(1);
        numPersonas.setMax(20);
        numPersonas.setMajorTickUnit(1);
        numPersonas.setMinorTickCount(0);
        numPersonas.setSnapToTicks(false);
        numPersonas.setShowTickLabels(false);
        numPersonas.setShowTickMarks(false);
    }

    private void agregarTooltipSlider(Slider slider) {
        Tooltip tooltip = new Tooltip();
        tooltip.setAutoHide(false);

        slider.setOnMousePressed(event -> {
            tooltip.setText(String.valueOf((int) slider.getValue()));
            Node node = (Node) event.getSource();
            Bounds bounds = node.localToScreen(node.getBoundsInLocal());
            tooltip.show(slider, bounds.getMinX() + event.getX(), bounds.getMinY() - 30);
        });

        slider.setOnMouseDragged(event -> {
            tooltip.setText(String.valueOf((int) slider.getValue()));
            Node node = (Node) event.getSource();
            Bounds bounds = node.localToScreen(node.getBoundsInLocal());
            tooltip.setX(bounds.getMinX() + event.getX());
            tooltip.setY(bounds.getMinY() - 30);
        });

        slider.setOnMouseReleased(event -> tooltip.hide());
    }

}
