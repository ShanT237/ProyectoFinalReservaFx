package co.edu.uniquindio.proyectofinalhotelfx.Controladores.ControladoresAdm;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import co.edu.uniquindio.proyectofinalhotelfx.Controladores.ControladorPrincipal;
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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class RegistrarAlojamientoAdmin {

    // --- FXML Components ---
    @FXML private ResourceBundle resources;
    @FXML private URL location;
    @FXML private CheckBox admiteMascotas;
    @FXML
    private Label informacionLabel;
    @FXML private ImageView imagenPreview;
    @FXML private ComboBox<Ciudad> ciudadBox;
    @FXML private TextField descripcionField;
    @FXML private TextField fieldNombre;
    @FXML private Slider numHabitaciones;
    @FXML private Slider numPersonas;
    @FXML private TextField precioField;
    @FXML private ListView<ServiciosIncluidos> serviciosIncluidos;
    @FXML private ComboBox<TipoAlojamiento> tipoAlojamientoBox;

    // --- Variables auxiliares ---
    private File imagenSeleccionada;
    private final ControladorPrincipal controladorPrincipal = ControladorPrincipal.getInstancia();

    private GestionAlojamientosAdmin controladorAlojamientos;

    public void setControladorAlojamientos(GestionAlojamientosAdmin controladorAlojamientos) {
        this.controladorAlojamientos = controladorAlojamientos;
    }

    // --- Inicialización ---
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

    // --- Métodos auxiliares ---
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

        numPersonas.setMin(1);
        numPersonas.setMax(20);
        numPersonas.setMajorTickUnit(1);
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

    // --- Acciones del usuario ---
    @FXML
    void seleccionarImagen(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Seleccionar imagen de alojamiento");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Imágenes", "*.png", "*.jpg", "*.jpeg")
        );

        File archivo = fileChooser.showOpenDialog(null);
        if (archivo != null) {
            imagenSeleccionada = archivo;
            Image imagen = new Image(archivo.toURI().toString());
            imagenPreview.setImage(imagen);
        }
    }

    @FXML
    void registrarAlojamiento(ActionEvent event) {
        try {
            String nombre = fieldNombre.getText().trim();
            String descripcion = descripcionField.getText().trim();
            Ciudad ciudad = ciudadBox.getValue();
            TipoAlojamiento tipo = tipoAlojamientoBox.getValue();
            boolean permiteMascotas = admiteMascotas.isSelected();
            int habitaciones = (int) numHabitaciones.getValue();
            int personas = (int) numPersonas.getValue();
            double precio = Double.parseDouble(precioField.getText().trim());

            // Servicios seleccionados
            List<ServiciosIncluidos> serviciosSeleccionados = new ArrayList<>();
            for (ServiciosIncluidos servicio : serviciosIncluidos.getItems()) {
                CheckBoxListCell<ServiciosIncluidos> cell = (CheckBoxListCell<ServiciosIncluidos>) serviciosIncluidos.lookup("#" + servicio.name());
                if (cell != null && cell.isSelected()) {
                    serviciosSeleccionados.add(servicio);
                }
            }

            // Guardar imagen si hay
            String rutaImagen = null;
            Image imagen = null;
            if (imagenSeleccionada != null && !nombre.isEmpty()) {
                File directorio = new File("co/edu/uniquindio/proyectofinalhotelfx/ImagenesAlojamientos");
                if (!directorio.exists()) {
                    directorio.mkdirs();
                }

                String extension = imagenSeleccionada.getName().substring(imagenSeleccionada.getName().lastIndexOf('.'));
                File destino = new File(directorio, nombre.replaceAll("\\s+", "_") + extension);
                Files.copy(imagenSeleccionada.toPath(), destino.toPath(), StandardCopyOption.REPLACE_EXISTING);

                rutaImagen = destino.getAbsolutePath();
                imagen = new Image(new File(rutaImagen).toURI().toString());
                System.out.println("Imagen guardada en: " + imagen.getUrl());
            }

            controladorPrincipal.getPlataforma().registrarAlojamiento(nombre, ciudad, descripcion, precio, imagen, serviciosSeleccionados, personas, habitaciones, permiteMascotas, tipo);

            limpiarCampos(); // Limpia todos los campos

            informacionLabel.setText("¡Alojamiento registrado exitosamente!");
            informacionLabel.setStyle("-fx-text-fill: green;");
            informacionLabel.setVisible(true);

            if (controladorAlojamientos != null) {
                controladorAlojamientos.actualizarTabla();
            }


        } catch (Exception e) {
            e.printStackTrace();
            mostrarAlerta("Error", "Verifica que todos los campos estén completos y válidos.");
        }
    }

    private void mostrarAlerta(String titulo, String contenido) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(contenido);
        alert.showAndWait();
    }

    private void limpiarCampos() {
        fieldNombre.clear();
        descripcionField.clear();
        precioField.clear();
        ciudadBox.getSelectionModel().clearSelection();
        tipoAlojamientoBox.getSelectionModel().clearSelection();
        admiteMascotas.setSelected(false);
        numHabitaciones.setValue(1);
        numPersonas.setValue(1);
        imagenPreview.setImage(null);
        imagenSeleccionada = null;
        informacionLabel.setVisible(false); // por si estaba visible de antes

        serviciosIncluidos.setCellFactory(CheckBoxListCell.forListView(servicio -> {
            BooleanProperty observable = new SimpleBooleanProperty(false);
            return observable;
        }));
        serviciosIncluidos.refresh();
    }
}