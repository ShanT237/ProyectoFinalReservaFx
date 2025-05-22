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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;

/**
 * Controlador para la actualización de información de alojamientos
 */
public class ActualizarAlojamientoAdmin {

    // Componentes FXML
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

    // Variables de instancia
    private File imagenSeleccionada;
    private final ControladorPrincipal controladorPrincipal = ControladorPrincipal.getInstancia();
    private GestionAlojamientosAdmin controladorAlojamientos;
    private Alojamiento alojamiento;

    /**
     * Método de inicialización que configura los componentes
     */
    @FXML
    void initialize() {
        cargarCiudades();
        cargarTiposAlojamiento();
        configurarSliders();
        configurarCampoPrecio();
        agregarTooltipSlider(numHabitaciones);
        agregarTooltipSlider(numPersonas);
    }

    /**
     * Establece el alojamiento a editar y carga sus datos
     * @param alojamiento El alojamiento a actualizar
     */
    public void setAlojamiento(Alojamiento alojamiento) {
        this.alojamiento = alojamiento;
        if (alojamiento != null) {
            cargarDatosAlojamiento();
        }
    }

    /**
     * Carga las ciudades disponibles en el ComboBox
     */
    private void cargarCiudades() {
        ObservableList<Ciudad> ciudades = FXCollections.observableArrayList(Ciudad.values());
        FXCollections.sort(ciudades, Comparator.comparing(Enum::name));
        ciudadBox.setItems(ciudades);
    }

    /**
     * Carga los tipos de alojamiento disponibles en el ComboBox
     */
    private void cargarTiposAlojamiento() {
        ObservableList<TipoAlojamiento> tipos = FXCollections.observableArrayList(TipoAlojamiento.values());
        tipoAlojamientoBox.setItems(tipos);
    }

    /**
     * Configura los valores de los sliders
     */
    private void configurarSliders() {
        numHabitaciones.setMin(1);
        numHabitaciones.setMax(10);
        numHabitaciones.setMajorTickUnit(1);

        numPersonas.setMin(1);
        numPersonas.setMax(20);
        numPersonas.setMajorTickUnit(1);
    }

    /**
     * Maneja la selección de una nueva imagen
     */
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

    /**
     * Actualiza la información del alojamiento
     */
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

            List<ServiciosIncluidos> serviciosSeleccionados = obtenerServiciosSeleccionados();

            if (imagenSeleccionada != null) {
                rutaImagen = guardarImagen(nombre);
            }

            controladorPrincipal.getPlataforma().actualizarAlojamiento(id, nombre, ciudad, descripcion, precio,
                    rutaImagen, serviciosSeleccionados, personas, habitaciones,
                    permiteMascotas, tipo);

            mostrarMensajeExito();

            if (controladorAlojamientos != null) {
                controladorAlojamientos.actualizarTabla();
            }

        } catch (Exception e) {
            mostrarMensajeError();
        }
    }

    /**
     * Establece el controlador de alojamientos
     */
    public void setControladorAlojamientos(GestionAlojamientosAdmin gestionAlojamientosAdmin) {
        this.controladorAlojamientos = gestionAlojamientosAdmin;
    }

    /**
     * Agrega un tooltip a un slider para mostrar su valor
     */
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

    /**
     * Configura el campo de precio para aceptar solo valores numéricos
     */
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

    /**
     * Carga los datos del alojamiento en los campos del formulario
     */
    private void cargarDatosAlojamiento() {
        fieldNombre.setText(alojamiento.getNombre());
        descripcionField.setText(alojamiento.getDescripcion());
        ciudadBox.setValue(alojamiento.getCiudad());
        tipoAlojamientoBox.setValue(alojamiento.getTipoAlojamiento());
        tipoAlojamientoBox.setDisable(true);
        precioField.setText(String.valueOf(alojamiento.getPrecioNoche()));
        admiteMascotas.setSelected(alojamiento.isAdmiteMascotas());
        numPersonas.setValue(alojamiento.getCapacidadHuespedes());
        numHabitaciones.setValue(alojamiento.getNumeroHabitaciones());

        serviciosIncluidos.getChildren().clear();
        for (ServiciosIncluidos servicio : ServiciosIncluidos.values()) {
            CheckBox checkBox = new CheckBox(servicio.toString());
            checkBox.setSelected(alojamiento.getServiciosIncluidos().contains(servicio));
            serviciosIncluidos.getChildren().add(checkBox);
        }

        if (alojamiento.getImagen() != null) {
            File file = new File(alojamiento.getImagen());
            if (file.exists()) {
                imagenPreview.setImage(new Image(file.toURI().toString()));
            }
        }
    }

    /**
     * Obtiene los servicios seleccionados
     */
    private List<ServiciosIncluidos> obtenerServiciosSeleccionados() {
        List<ServiciosIncluidos> serviciosSeleccionados = new ArrayList<>();
        for (Node node : serviciosIncluidos.getChildren()) {
            if (node instanceof CheckBox) {
                CheckBox cb = (CheckBox) node;
                if (cb.isSelected()) {
                    serviciosSeleccionados.add(ServiciosIncluidos.valueOf(cb.getText()));
                }
            }
        }
        return serviciosSeleccionados;
    }

    /**
     * Guarda la imagen seleccionada
     */
    private String guardarImagen(String nombre) throws Exception {
        String userDir = System.getProperty("user.dir");
        File destino = new File(userDir + "/Img/ImagenesAlojamientos/", nombre.replaceAll("\\s+", "_") + ".jpg");
        Files.copy(imagenSeleccionada.toPath(), destino.toPath(), StandardCopyOption.REPLACE_EXISTING);
        return destino.getPath();
    }

    /**
     * Muestra mensaje de éxito
     */
    private void mostrarMensajeExito() {
        informacionLabel.setText("\u00a1Alojamiento actualizado!");
        informacionLabel.setStyle("-fx-text-fill: green");
        informacionLabel.setVisible(true);
    }

    /**
     * Muestra mensaje de error
     */
    private void mostrarMensajeError() {
        informacionLabel.setText("Error al actualizar. Verifica los campos.");
        informacionLabel.setStyle("-fx-text-fill: red");
        informacionLabel.setVisible(true);
    }
}