package co.edu.uniquindio.proyectofinalhotelfx.Controladores.ControladoresAdm;

import java.net.URL;
import java.util.ResourceBundle;

import co.edu.uniquindio.proyectofinalhotelfx.Controladores.ControladorPrincipal;
import co.edu.uniquindio.proyectofinalhotelfx.Modelo.Entidades.Alojamiento;
import co.edu.uniquindio.proyectofinalhotelfx.Modelo.Enums.Ciudad;
import co.edu.uniquindio.proyectofinalhotelfx.Modelo.Enums.TipoAlojamiento;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


public class GestionAlojamientosAdmin {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableColumn<Alojamiento, ImageView> colImagen;

    @FXML
    private TableColumn<Alojamiento, Ciudad> colCiudad;

    @FXML
    private TableColumn<Alojamiento, String> colNombre;

    @FXML
    private TableColumn<Alojamiento, Double> colPrecio;

    @FXML
    private TableColumn<Alojamiento, TipoAlojamiento> colTipo;

    @FXML
    private TableView<Alojamiento> tblAlojamientos;

    ControladorPrincipal controladorPrincipal = ControladorPrincipal.getInstancia();
    private final ObservableList<Alojamiento> listaAlojamientos = FXCollections.observableArrayList();

    @FXML
    void initialize() {
        configurarColumnas();
        actualizarTabla();

    }
    public void configurarColumnas() {
        colNombre.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNombre()));
        colCiudad.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getCiudad()));
        colTipo.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getTipoAlojamiento()));
        colPrecio.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getPrecioPorNocheBase()));
        colImagen.setCellValueFactory(cellData -> {
            Image imagen = cellData.getValue().getImagen();
            ImageView imageView = new ImageView(imagen);
            imageView.setFitWidth(80);
            imageView.setFitHeight(60);
            return new SimpleObjectProperty<>(imageView);
        });
    }

    public void actualizarTabla() {
        listaAlojamientos.setAll(controladorPrincipal.getPlataforma().obtenerListaAlojamientos());
        tblAlojamientos.setItems(listaAlojamientos);
        tblAlojamientos.refresh();
    }

    @FXML
    void btnAgregar(ActionEvent event) {

    }

    @FXML
    void eliminarAlojamiento(ActionEvent event) {
        String id = tblAlojamientos.getSelectionModel().getSelectedItem().getId();
        controladorPrincipal.getPlataforma().eliminarAlojamiento(id);
        actualizarTabla();
    }

    @FXML
    void acturalizarAlojamiento(ActionEvent event) {

    }


}
