package co.edu.uniquindio.proyectofinalhotelfx.Controladores.ControladoresAdm;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

import co.edu.uniquindio.proyectofinalhotelfx.App;
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
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;


public class GestionAlojamientosAdmin {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;


    @FXML
    private Button buttonRegistrarHab;


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
    private ObservableList<Alojamiento> listaAlojamientos = FXCollections.observableArrayList();

    @FXML
    void initialize() {
        configurarColumnas();
        actualizarTabla();
        configurarListenerSeleccion();

    }
    public void configurarColumnas() {
        colNombre.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNombre()));
        colCiudad.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getCiudad()));
        colTipo.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getTipoAlojamiento()));
        colPrecio.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getPrecioNoche()));
        colImagen.setCellValueFactory(cellData -> {
            String ruta = cellData.getValue().getImagen();
            File file = new File(ruta);
            Image imagen = new Image(file.toURI().toString());
            ImageView imageView = new ImageView(imagen);
            imageView.setFitWidth(80);
            imageView.setFitHeight(60);
            return new SimpleObjectProperty<>(imageView);
        });
    }

    public void actualizarTabla() {
        listaAlojamientos.setAll(ControladorPrincipal.getInstancia().getPlataforma().getServiciosAlojamiento().getAlojamientoRepository().obtenerTodos());
        tblAlojamientos.setItems(listaAlojamientos);
        tblAlojamientos.refresh();
    }

    @FXML
    void btnAgregar(ActionEvent event) {
        mostrarVentana(event, "/co/edu/uniquindio/proyectofinalhotelfx/FXMLDW(ADMIN)/RegistrarAlojamiento.fxml", "Registrar Alojamiento");

    }

    @FXML
    void eliminarAlojamiento(ActionEvent event) {
        String id = tblAlojamientos.getSelectionModel().getSelectedItem().getId();
        controladorPrincipal.getPlataforma().eliminarAlojamiento(id);
        actualizarTabla();
    }

    @FXML
    void acturalizarAlojamiento(ActionEvent event) {
        mostrarVentana(event,"/co/edu/uniquindio/proyectofinalhotelfx/FXMLDW(ADMIN)/ActualizarAlojamiento.fxml", "Registrar Alojamiento");
    }

    @FXML
    private void mostrarVentana(ActionEvent event, String ruta, String titulo) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(ruta));
            Parent root = loader.load();

            Object controller = loader.getController();

            if (controller instanceof RegistrarAlojamientoAdmin) {
                ((RegistrarAlojamientoAdmin) controller).setControladorAlojamientos(this);
            }

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

            actualizarTabla();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void configurarListenerSeleccion() {
        tblAlojamientos.getSelectionModel().selectedItemProperty().addListener((obs, oldSel, newSel) -> {
            if (newSel != null && newSel.getTipoAlojamiento() == TipoAlojamiento.HOTEL) {
                buttonRegistrarHab.setVisible(true);
            } else {
                buttonRegistrarHab.setVisible(false);
            }
        });
    }

    @FXML
    void registrarHabitacionHotel(ActionEvent event) {
        Alojamiento alojamientoSeleccionado = tblAlojamientos.getSelectionModel().getSelectedItem();

        if (alojamientoSeleccionado != null && alojamientoSeleccionado.getTipoAlojamiento() == TipoAlojamiento.HOTEL) {
            mostrarVentana(event, "/co/edu/uniquindio/proyectofinalhotelfx/FXMLDW(ADMIN)/RegistrarHabitacion.fxml", "Registrar Habitación Hotel");
            actualizarTabla();


        }
    }
}