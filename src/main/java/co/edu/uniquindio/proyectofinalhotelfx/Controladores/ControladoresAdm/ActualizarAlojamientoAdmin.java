package co.edu.uniquindio.proyectofinalhotelfx.Controladores.ControladoresAdm;

import java.io.File;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;

import co.edu.uniquindio.proyectofinalhotelfx.Controladores.ControladorPrincipal;
import co.edu.uniquindio.proyectofinalhotelfx.Modelo.Entidades.Alojamiento;
import co.edu.uniquindio.proyectofinalhotelfx.Modelo.Enums.Ciudad;
import co.edu.uniquindio.proyectofinalhotelfx.Modelo.Enums.ServiciosIncluidos;
import co.edu.uniquindio.proyectofinalhotelfx.Modelo.Enums.TipoAlojamiento;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;

public class ActualizarAlojamientoAdmin {


    @FXML private ResourceBundle resources;
    @FXML private URL location;
    @FXML private CheckBox admiteMascotas;
    @FXML private ComboBox<Ciudad> ciudadBox;
    @FXML private TextField descripcionField;
    @FXML private TextField fieldNombre;
    @FXML private ImageView imagenPreview;
    @FXML private Label informacionLabel;
    @FXML private Slider numHabitaciones;
    @FXML private Slider numPersonas;
    @FXML private TextField precioField;
    @FXML private VBox serviciosIncluidos;
    @FXML private ComboBox<TipoAlojamiento> tipoAlojamientoBox;

    private File imagenSeleccionada;
    private final ControladorPrincipal controladorPrincipal = ControladorPrincipal.getInstancia();
    private GestionAlojamientosAdmin controladorAlojamientos;
    private Alojamiento alojamiento;

    public void setAlojamiento(Alojamiento alojamiento) {
        this.alojamiento = alojamiento;

        if (alojamiento != null) {
            fieldNombre.setText(alojamiento.getNombre());
            descripcionField.setText(alojamiento.getDescripcion());
            ciudadBox.setValue(alojamiento.getCiudad());
            tipoAlojamientoBox.setValue(alojamiento.getTipoAlojamiento());
            tipoAlojamientoBox.setDisable(true); // No se puede modificar
            precioField.setText(String.valueOf(alojamiento.getPrecioNoche()));
            admiteMascotas.setSelected(alojamiento.isAdmiteMascotas());
            numPersonas.setValue(alojamiento.getCapacidadHuespedes());
            numHabitaciones.setValue(alojamiento.getNumeroHabitaciones());

            serviciosIncluidos.getChildren().clear(); // Limpiar VBox existente

            for (ServiciosIncluidos servicio : ServiciosIncluidos.values()) {
                CheckBox checkBox = new CheckBox(servicio.toString());
                checkBox.setSelected(alojamiento.getServiciosIncluidos().contains(servicio)); // Marcar si está en la lista
                serviciosIncluidos.getChildren().add(checkBox);
            }

            if (alojamiento.getImagen() != null) {
                File file = new File(alojamiento.getImagen());
                if (file.exists()) {
                    imagenPreview.setImage(new Image(file.toURI().toString()));
                }
            }
        }
    }

    @FXML
    void initialize() {
        cargarCiudades();
        cargarTiposAlojamiento();
        configurarSliders();
        configurarCampoPrecio();
        agregarTooltipSlider(numHabitaciones);
        agregarTooltipSlider(numPersonas);
    }

    private void cargarCiudades() {
        ObservableList<Ciudad> ciudades = FXCollections.observableArrayList(Ciudad.values());
        FXCollections.sort(ciudades, Comparator.comparing(Enum::name));
        ciudadBox.setItems(ciudades);
    }

    private void cargarTiposAlojamiento() {
        ObservableList<TipoAlojamiento> tipos = FXCollections.observableArrayList(TipoAlojamiento.values());
        tipoAlojamientoBox.setItems(tipos);
    }

    private void configurarSliders() {
        numHabitaciones.setMin(1);
        numHabitaciones.setMax(10);
        numHabitaciones.setMajorTickUnit(1);

        numPersonas.setMin(1);
        numPersonas.setMax(20);
        numPersonas.setMajorTickUnit(1);
    }

    @FXML
    void seleccionarImagen(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Seleccionar imagen");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Imágenes", "*.jpg", "*.png", "*.jpeg"));

        File archivo = fileChooser.showOpenDialog(null);
        if (archivo != null) {
            imagenSeleccionada = archivo;
            imagenPreview.setImage(new Image(archivo.toURI().toString()));
        }
    }

    @FXML
    void actualizarAlojamiento(ActionEvent event) {
        try {
            String nombre = fieldNombre.getText().trim();
            String descripcion = descripcionField.getText().trim();
            Ciudad ciudad = Ciudad.valueOf(String.valueOf(ciudadBox.getValue()));
            double precio = Double.parseDouble(precioField.getText().trim());
            boolean permiteMascotas = admiteMascotas.isSelected();
            int personas = (int) numPersonas.getValue();
            int habitaciones = (int) numHabitaciones.getValue();
            String rutaImagen = alojamiento.getImagen();
            TipoAlojamiento tipo = tipoAlojamientoBox.getValue();
            String id = alojamiento.getId();

            List<ServiciosIncluidos> serviciosSeleccionados = new ArrayList<>();

            for (Node node : serviciosIncluidos.getChildren()) {
                if (node instanceof CheckBox) {
                    CheckBox cb = (CheckBox) node;
                    if (cb.isSelected()) {
                        // Convertir el texto del CheckBox al enum correspondiente
                        ServiciosIncluidos servicio = ServiciosIncluidos.valueOf(cb.getText());
                        serviciosSeleccionados.add(servicio);
                    }
                }
            }

            if (imagenSeleccionada != null) {
                String userDir = System.getProperty("user.dir");
                File destino = new File(userDir + "/Img/ImagenesAlojamientos/", nombre.replaceAll("\\s+", "_") + ".jpg");
                Files.copy(imagenSeleccionada.toPath(), destino.toPath(), StandardCopyOption.REPLACE_EXISTING);
                rutaImagen = destino.getPath();
            }


            controladorPrincipal.getPlataforma().actualizarAlojamiento(id, nombre, ciudad, descripcion, precio,
                    rutaImagen, serviciosSeleccionados, personas, habitaciones,
                    permiteMascotas, tipo);

            informacionLabel.setText("\u00a1Alojamiento actualizado!");
            informacionLabel.setStyle("-fx-text-fill: green");
            informacionLabel.setVisible(true);

            if (controladorAlojamientos != null) {
                controladorAlojamientos.actualizarTabla();
            }

        } catch (Exception e) {
            e.printStackTrace();
            informacionLabel.setText("Error al actualizar. Verifica los campos.");
            informacionLabel.setStyle("-fx-text-fill: red");
            informacionLabel.setVisible(true);
        }
    }

    public void setControladorAlojamientos(GestionAlojamientosAdmin gestionAlojamientosAdmin) {
        this.controladorAlojamientos = gestionAlojamientosAdmin;
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

    private void configurarCampoPrecio() {
        TextFormatter<String> textFormatter = new TextFormatter<>(change -> {
            String newText = change.getControlNewText();
            if (newText.matches("\\d*\\.?\\d*")) {
                return change;
            }
            return null;
        });

        precioField.setTextFormatter(textFormatter);

    }


}