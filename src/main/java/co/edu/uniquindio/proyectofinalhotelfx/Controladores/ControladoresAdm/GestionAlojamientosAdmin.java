package co.edu.uniquindio.proyectofinalhotelfx.Controladores.ControladoresAdm;

import java.io.File;
import java.io.IOException;
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

/**
 * Controlador para la gestión de alojamientos por parte del administrador
 */
public class GestionAlojamientosAdmin {

    // Componentes FXML
    @FXML private ResourceBundle resources;
    @FXML private URL location;
    @FXML private Button buttonRegistrarHab;
    @FXML private TableColumn<Alojamiento, ImageView> colImagen;
    @FXML private TableColumn<Alojamiento, Ciudad> colCiudad;
    @FXML private TableColumn<Alojamiento, String> colNombre;
    @FXML private TableColumn<Alojamiento, Double> colPrecio;
    @FXML private TableColumn<Alojamiento, TipoAlojamiento> colTipo;
    @FXML private TableView<Alojamiento> tblAlojamientos;

    // Variables de instancia
    private final ControladorPrincipal controladorPrincipal = ControladorPrincipal.getInstancia();
    private final ObservableList<Alojamiento> listaAlojamientos = FXCollections.observableArrayList();

    /**
     * Inicializa el controlador configurando las columnas y listeners
     */
    @FXML
    void initialize() {
        configurarColumnas();
        actualizarTabla();
        configurarListenerSeleccion();
    }

    /**
     * Configura las columnas de la tabla de alojamientos
     */
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

    /**
     * Actualiza los datos mostrados en la tabla
     */
    public void actualizarTabla() {
        listaAlojamientos.setAll(controladorPrincipal.getPlataforma().getServiciosAlojamiento().getAlojamientoRepository().obtenerTodos());
        tblAlojamientos.setItems(listaAlojamientos);
        tblAlojamientos.refresh();
    }

    /**
     * Muestra la ventana para agregar un nuevo alojamiento
     */
    @FXML
    void btnAgregar(ActionEvent event) {
        mostrarVentana(event, "/co/edu/uniquindio/proyectofinalhotelfx/FXMLDW(ADMIN)/RegistrarAlojamiento.fxml", "Registrar Alojamiento");
    }

    /**
     * Elimina el alojamiento seleccionado
     */
    @FXML
    void eliminarAlojamiento(ActionEvent event) {
        Alojamiento seleccionado = tblAlojamientos.getSelectionModel().getSelectedItem();
        if (seleccionado != null) {
            controladorPrincipal.getPlataforma().eliminarAlojamiento(seleccionado.getId());
            actualizarTabla();
        }
    }

    /**
     * Muestra la ventana para actualizar un alojamiento seleccionado
     */
    @FXML
    void acturalizarAlojamiento(ActionEvent event) {
        Alojamiento alojamientoSeleccionado = tblAlojamientos.getSelectionModel().getSelectedItem();
        if (alojamientoSeleccionado != null) {
            mostrarVentanaConAlojamiento(event,
                    "/co/edu/uniquindio/proyectofinalhotelfx/FXMLDW(ADMIN)/ActualizarAlojamiento.fxml",
                    "Actualizar Alojamiento", alojamientoSeleccionado);
        }
    }

    /**
     * Muestra la ventana para registrar una habitación en un hotel seleccionado
     */
    @FXML
    void registrarHabitacionHotel(ActionEvent event) {
        Alojamiento alojamientoSeleccionado = tblAlojamientos.getSelectionModel().getSelectedItem();
        if (alojamientoSeleccionado != null && alojamientoSeleccionado.getTipoAlojamiento() == TipoAlojamiento.HOTEL) {
            mostrarVentanaConHotel(event,
                    "/co/edu/uniquindio/proyectofinalhotelfx/FXMLDW(ADMIN)/RegistrarHabitacion.fxml",
                    "Registrar Habitación Hotel", alojamientoSeleccionado);
        }
    }

    /**
     * Configura el listener para la selección en la tabla
     */
    private void configurarListenerSeleccion() {
        tblAlojamientos.getSelectionModel().selectedItemProperty().addListener((obs, oldSel, newSel) -> {
            buttonRegistrarHab.setVisible(newSel != null && newSel.getTipoAlojamiento() == TipoAlojamiento.HOTEL);
        });
    }

    /**
     * Muestra una ventana modal genérica
     */
    private void mostrarVentana(ActionEvent event, String ruta, String titulo) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(ruta));
            Parent root = loader.load();

            configurarControlador(loader);

            Stage stage = crearStage(event, titulo);
            stage.setScene(new Scene(root));
            stage.showAndWait();

            actualizarTabla();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Muestra una ventana modal para actualizar alojamiento
     */
    private void mostrarVentanaConAlojamiento(ActionEvent event, String ruta, String titulo, Alojamiento alojamiento) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(ruta));
            Parent root = loader.load();

            Object controller = loader.getController();
            if (controller instanceof ActualizarAlojamientoAdmin actualizarController) {
                actualizarController.setControladorAlojamientos(this);
                actualizarController.setAlojamiento(alojamiento);
            }

            Stage stage = crearStage(event, titulo);
            stage.setScene(new Scene(root));
            stage.showAndWait();

            actualizarTabla();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Muestra una ventana modal para registrar habitación en hotel
     */
    private void mostrarVentanaConHotel(ActionEvent event, String ruta, String titulo, Alojamiento alojamientoHotel) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(ruta));
            Parent root = loader.load();

            Object controller = loader.getController();
            if (controller instanceof RegistrarHabitacionAdmin registrarController) {
                registrarController.setAlojamiento(alojamientoHotel);
            }

            Stage stage = crearStage(event, titulo);
            stage.setScene(new Scene(root));
            stage.showAndWait();

            actualizarTabla();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Configura el controlador de la ventana emergente
     */
    private void configurarControlador(FXMLLoader loader) {
        Object controller = loader.getController();
        if (controller instanceof RegistrarAlojamientoAdmin) {
            ((RegistrarAlojamientoAdmin) controller).setControladorAlojamientos(this);
        }
        if (controller instanceof ActualizarAlojamientoAdmin) {
            ((ActualizarAlojamientoAdmin) controller).setControladorAlojamientos(this);
        }
    }

    /**
     * Crea y configura un Stage para ventanas emergentes
     */
    private Stage crearStage(ActionEvent event, String titulo) {
        Stage stage = new Stage();
        File archivoImagen = new File("Img/ImagenesApp/icon.png");
        Image icono = new Image(archivoImagen.toURI().toString());
        stage.getIcons().add(icono);
        stage.setResizable(false);
        stage.setTitle(titulo);

        Stage parentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.initOwner(parentStage);
        stage.initModality(Modality.WINDOW_MODAL);

        return stage;
    }
}