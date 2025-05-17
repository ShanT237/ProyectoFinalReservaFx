package co.edu.uniquindio.proyectofinalhotelfx.Controladores.ControladoresCliente;

import co.edu.uniquindio.proyectofinalhotelfx.Controladores.ControladorPrincipal;
import co.edu.uniquindio.proyectofinalhotelfx.Modelo.Entidades.Alojamiento;
import co.edu.uniquindio.proyectofinalhotelfx.Modelo.Entidades.Cliente;
import co.edu.uniquindio.proyectofinalhotelfx.Modelo.Enums.Ciudad;
import co.edu.uniquindio.proyectofinalhotelfx.Modelo.Enums.TipoAlojamiento;
import co.edu.uniquindio.proyectofinalhotelfx.Servicios.ServicioReserva;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.File;
import java.util.List;

public class BuscarAlojamientosCliente {

    @FXML private ComboBox<Ciudad> cbCiudad;
    @FXML private ComboBox<TipoAlojamiento> cbTipoAlojamiento;
    @FXML private TextField txtPrecioMax;
    @FXML private Button btnBuscar;
    @FXML private FlowPane flowAlojamientos;

    private final ControladorPrincipal controladorPrincipal = ControladorPrincipal.getInstancia();
    private final List<Alojamiento> alojamientosList = controladorPrincipal.getPlataforma()
            .getServiciosAlojamiento()
            .getAlojamientoRepository()
            .obtenerTodos();
    private final ObservableList<Alojamiento> alojamientos = FXCollections.observableArrayList(alojamientosList);

    @FXML
    void initialize() {
        cargarCiudades();
        cargarTiposAlojamiento();
        btnBuscar.setOnAction(this::onBuscarClick);
    }

    private void cargarCiudades() {
        ObservableList<Ciudad> ciudades = FXCollections.observableArrayList(Ciudad.values());
        FXCollections.sort(ciudades, (c1, c2) -> c1.name().compareTo(c2.name()));
        cbCiudad.setItems(ciudades);
    }

    private void cargarTiposAlojamiento() {
        ObservableList<TipoAlojamiento> tipos = FXCollections.observableArrayList(TipoAlojamiento.values());
        cbTipoAlojamiento.setItems(tipos);
    }

    private void onBuscarClick(ActionEvent event) {
        Ciudad ciudadSeleccionada = cbCiudad.getValue();
        TipoAlojamiento tipoSeleccionado = cbTipoAlojamiento.getValue();
        String precioMaxText = txtPrecioMax.getText();
        double precioMax = Double.MAX_VALUE;

        if (verificarDatos(precioMaxText)) {
            if (!precioMaxText.isEmpty()) {
                precioMax = Double.parseDouble(precioMaxText);
            }

            limpiarResultados();
            mostrarAlojamientos(ciudadSeleccionada, tipoSeleccionado, precioMax);
        }
    }

    public void mostrarAlojamientos(Ciudad ciudadSeleccionada, TipoAlojamiento tipoSeleccionado, double precioMax) {
        for (Alojamiento alojamiento : alojamientos) {
            if (cumpleCriterios(alojamiento, ciudadSeleccionada, tipoSeleccionado, precioMax)) {
                flowAlojamientos.getChildren().add(crearTarjetaAlojamiento(alojamiento));
            }
        }
    }

    private boolean cumpleCriterios(Alojamiento alojamiento, Ciudad ciudad,
                                    TipoAlojamiento tipo, double precioMax) {
        if (ciudad != null && !alojamiento.getCiudad().equals(ciudad)) return false;
        if (tipo != null && !alojamiento.getTipoAlojamiento().equals(tipo)) return false;
        return alojamiento.getPrecioNoche() <= precioMax;
    }

    private VBox crearTarjetaAlojamiento(Alojamiento alojamiento) {
        // Contenedor principal de la tarjeta
        VBox tarjeta = new VBox(10);
        tarjeta.setStyle("""
        -fx-padding: 10;
        -fx-border-color: #ccc;
        -fx-border-radius: 10;
        -fx-background-color: #f9f9f9;
    """);
        tarjeta.setPrefWidth(500);

        // Contenedor del contenido (imagen + detalles)
        HBox contenido = new HBox(10);
        contenido.setStyle("-fx-alignment: center-left;");

        // Imagen del alojamiento
        ImageView imagenView = new ImageView();
        File archivoImagen = new File(alojamiento.getImagen());

        if (archivoImagen.exists()) {
            Image imagen = new Image(archivoImagen.toURI().toString());
            imagenView.setImage(imagen);
        } else {
            System.out.println("Imagen no encontrada: " + alojamiento.getImagen());
        }

        imagenView.setFitWidth(150);
        imagenView.setFitHeight(100);
        imagenView.setPreserveRatio(true);

        // Evento: click sobre la imagen
        imagenView.setOnMouseClicked(event -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource(
                        "/co/edu/uniquindio/proyectofinalhotelfx/InformacionImagenCliente.fxml"));
                Parent root = loader.load();

                InformacionImagenCliente controller = loader.getController();
                controller.setDatos(alojamiento);
                controller.setAlojamiento(alojamiento); // PASAS EL CLIENTE

                Stage stage = new Stage();
                stage.getIcons().add(new Image(new File("Img/ImagenesApp/icon.png").toURI().toString()));
                stage.setScene(new Scene(root));
                stage.setTitle("Detalles del alojamiento");
                stage.show();

            } catch (Exception e) {
                e.printStackTrace();
                mostrarAlerta("No se pudo cargar la ventana de detalles", Alert.AlertType.ERROR);
            }
        });

        // Detalles del alojamiento
        VBox detalles = new VBox(5);

        Label nombreLabel = new Label(alojamiento.getNombre());
        nombreLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");

        Label ciudadLabel = new Label(alojamiento.getCiudad().name());
        Label precioLabel = new Label(String.format("%.2f COP por noche", alojamiento.getPrecioNoche()));

        detalles.getChildren().addAll(nombreLabel, ciudadLabel, precioLabel);

        // Ensamblar tarjeta
        contenido.getChildren().addAll(imagenView, detalles);
        tarjeta.getChildren().add(contenido);

        return tarjeta;
    }


    public boolean verificarDatos(String precioTexto) {
        if (!precioTexto.isEmpty()) {
            try {
                Double.parseDouble(precioTexto);
            } catch (NumberFormatException e) {
                mostrarAlerta("Precio máximo debe ser numérico", Alert.AlertType.WARNING);
                return false;
            }
        }

        if (cbCiudad.getValue() == null && cbTipoAlojamiento.getValue() == null && precioTexto.isEmpty()) {
            mostrarAlerta("Debe seleccionar al menos un criterio de búsqueda.", Alert.AlertType.WARNING);
            return false;
        }

        return true;
    }

    public void limpiarResultados() {
        flowAlojamientos.getChildren().clear();
    }

    private void mostrarAlerta(String mensaje, Alert.AlertType tipo) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle("Alerta");
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }

    private final ServicioReserva servicioReserva = ControladorPrincipal.getInstancia()
            .getPlataforma().getServicioReserva();

}
