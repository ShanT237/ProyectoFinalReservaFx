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
import co.edu.uniquindio.proyectofinalhotelfx.Modelo.Entidades.Alojamiento;
import co.edu.uniquindio.proyectofinalhotelfx.Modelo.Entidades.Habitacion;
import co.edu.uniquindio.proyectofinalhotelfx.Modelo.Entidades.Hotel;
import co.edu.uniquindio.proyectofinalhotelfx.Modelo.Enums.ServiciosIncluidos;
import co.edu.uniquindio.proyectofinalhotelfx.Modelo.Enums.TipoHabitacionHotel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;

public class RegistrarHabitacionAdmin {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField fieldNumero;

    @FXML
    private ImageView imagenPreview;

    @FXML
    private FlowPane habitacionesFlowPane;


    @FXML
    private Label informacionLabel;

    @FXML
    private Slider numPersonas;

    @FXML
    private TextField precioField;

    @FXML
    private VBox serviciosIncluidos;

    @FXML
    private ComboBox<TipoHabitacionHotel> tipoHabitacionBox;

    private File imagenSeleccionada;
    private Hotel alojamientoHotel;
    private ControladorPrincipal controladorPrincipal = ControladorPrincipal.getInstancia();

    public void setAlojamiento(Alojamiento alojamiento) {
        if (alojamiento instanceof Hotel) {
            this.alojamientoHotel = (Hotel) alojamiento;
            mostrarHabitaciones();
        } else {
            throw new IllegalArgumentException("El alojamiento no es un hotel");
        }
    }
    @FXML
    void registrarAlojamiento(ActionEvent event) {
        try {
            String textoNumero = fieldNumero.getText().trim();
            if (textoNumero.isEmpty()) {
                mostrarAlerta("Campo requerido", "El número de habitación es obligatorio.");
                return;
            }

            int numero;
            try {
                numero = Integer.parseInt(textoNumero);
            } catch (NumberFormatException e) {
                mostrarAlerta("Formato inválido", "Debe ingresar un número válido en el campo de número de habitación.");
                return;
            }

            double precio = Double.parseDouble(precioField.getText().trim());
            int capacidad = (int) numPersonas.getValue();
            TipoHabitacionHotel tipo = tipoHabitacionBox.getValue();

            serviciosIncluidos.getChildren().clear();

            List<ServiciosIncluidos> serviciosSeleccionados = new ArrayList<>();
            for (Node node : serviciosIncluidos.getChildren()) {
                if (node instanceof CheckBox) {
                    CheckBox cb = (CheckBox) node;
                    if (cb.isSelected()) {
                        serviciosSeleccionados.add((ServiciosIncluidos) cb.getUserData());
                    }
                }
            }

            String rutaImagen = null;
            if (imagenSeleccionada != null) {
                String userDir = System.getProperty("user.dir");
                File directorio = new File(userDir, "Img/ImagenesAlojamientos/ImagenesHabitaciones/");
                if (!directorio.exists()) {
                    directorio.mkdirs();
                }

                String extension = imagenSeleccionada.getName().substring(imagenSeleccionada.getName().lastIndexOf('.'));
                String nombreArchivo = "habitacion_" + numero + "_" + System.currentTimeMillis() + extension;
                File destino = new File(directorio, nombreArchivo);

                try {
                    Files.copy(imagenSeleccionada.toPath(), destino.toPath(), StandardCopyOption.REPLACE_EXISTING);
                    rutaImagen = "Img/ImagenesAlojamientos/ImagenesHabitaciones/" + nombreArchivo;
                    System.out.println("Imagen de habitación guardada en: " + destino.getAbsolutePath());
                } catch (IOException e) {
                    e.printStackTrace();
                    mostrarAlerta("Error al guardar la imagen", e.getMessage());
                    return;
                }
            }

            controladorPrincipal.getPlataforma().registrarHabitacion(
                    alojamientoHotel.getId(),
                    numero,
                    capacidad,
                    precio,
                    serviciosSeleccionados,
                    tipo,
                    rutaImagen
            );

            mostrarHabitaciones();
            informacionLabel.setText("¡Habitación registrada!");
            informacionLabel.setStyle("-fx-text-fill: green");

        } catch (Exception e) {
            e.printStackTrace();
            informacionLabel.setText("Error al registrar habitación");
            informacionLabel.setStyle("-fx-text-fill: red");
        }
    }

    @FXML
    void seleccionarImagen(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Seleccionar imagen de habitación");
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
    void initialize() {

        tipoHabitacionBox.getItems().setAll(TipoHabitacionHotel.values());
        // En lugar de usar ListView:
        serviciosIncluidos.getChildren().clear();
        for (ServiciosIncluidos servicio : ServiciosIncluidos.values()) {
            CheckBox cb = new CheckBox(servicio.toString());
            cb.setUserData(servicio); // para recuperar luego el enum
            serviciosIncluidos.getChildren().add(cb);
        }
        configurarSliders();
        agregarTooltipSlider(numPersonas);

        fieldNumero.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                fieldNumero.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });

    }

    private void mostrarHabitaciones() {
        habitacionesFlowPane.getChildren().clear();

        for (Habitacion habitacion : alojamientoHotel.getHabitaciones()) {
            VBox tarjeta = new VBox();
            tarjeta.setSpacing(5);
            tarjeta.setPadding(new Insets(10));
            tarjeta.setStyle("-fx-border-color: #ccc; -fx-border-radius: 10; -fx-background-radius: 10; -fx-background-color: #f9f9f9;");

            // Imagen
            ImageView imagenView = new ImageView();
            if (habitacion.getImagen() != null && !habitacion.getImagen().isEmpty()) {
                imagenView.setImage(new Image("file:" + habitacion.getImagen()));
            }
            imagenView.setFitWidth(150);
            imagenView.setFitHeight(100);
            imagenView.setPreserveRatio(true);

            // Info
            Label nombreLabel = new Label("Nombre: " + habitacion.getNumero());
            Label tipoLabel = new Label("Tipo: " + habitacion.getTipoHabitacion());
            Label capacidadLabel = new Label("Capacidad: " + habitacion.getCapacidad() + " personas");
            Label precioLabel = new Label("Precio: $" + habitacion.getPrecioPorNoche());

            tarjeta.getChildren().addAll(imagenView, nombreLabel, tipoLabel, capacidadLabel, precioLabel);
            habitacionesFlowPane.getChildren().add(tarjeta);
        }
    }
    private void mostrarAlerta(String titulo, String contenido) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(contenido);
        alert.showAndWait();
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

    private void configurarSliders() {

        numPersonas.setMin(1);
        numPersonas.setMax(20);
        numPersonas.setMajorTickUnit(1);
    }



}