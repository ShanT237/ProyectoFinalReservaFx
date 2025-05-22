package co.edu.uniquindio.proyectofinalhotelfx.Controladores.ControladoresAdm;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import co.edu.uniquindio.proyectofinalhotelfx.Controladores.ControladorPrincipal;
import co.edu.uniquindio.proyectofinalhotelfx.Modelo.Entidades.Oferta;
import co.edu.uniquindio.proyectofinalhotelfx.Modelo.Enums.TipoAlojamiento;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Controlador para la gestión de ofertas por parte del administrador
 */
public class GestionOfertasAdmin {

    // Componentes FXML
    @FXML private ResourceBundle resources;
    @FXML private URL location;
    @FXML private FlowPane flowPaneOferfas;

    // Variables de instancia
    private final ControladorPrincipal controladorPrincipal = ControladorPrincipal.getInstancia();
    private String idOferta;

    /**
     * Inicializa el controlador cargando las ofertas
     */
    @FXML
    void initialize() {
        cargarOfertas();
    }

    /**
     * Actualiza la lista de ofertas
     */
    @FXML
    void actualizar(ActionEvent event) {
        cargarOfertas();
    }

    /**
     * Abre la ventana para crear una nueva oferta
     */
    @FXML
    void btnCrearOferta(ActionEvent event) {
        mostrarVentana(event, "/co/edu/uniquindio/proyectofinalhotelfx/FXMLDW(ADMIN)/RegistrarOfertas.fxml", "Registrar Oferta");
    }

    /**
     * Elimina la oferta seleccionada
     */
    @FXML
    void eliminarOferta(ActionEvent event) {
        if (idOferta != null) {
            controladorPrincipal.getPlataforma().eliminarOferta(idOferta);
            cargarOfertas();
        }
    }

    /**
     * Carga las ofertas en el FlowPane
     */
    public void cargarOfertas() {
        flowPaneOferfas.getChildren().clear();
        List<Oferta> ofertas = controladorPrincipal.getPlataforma().getOfertaRepository().obtenerTodos();

        for (Oferta oferta : ofertas) {
            flowPaneOferfas.getChildren().add(crearTarjetaOferta(oferta));
        }
    }

    /**
     * Crea una tarjeta visual para una oferta
     */
    private VBox crearTarjetaOferta(Oferta oferta) {
        VBox tarjeta = new VBox(10);
        tarjeta.setStyle("-fx-padding: 10; -fx-border-color: #2a2a2a; -fx-border-radius: 10; -fx-background-color: #f4f4f4;");
        tarjeta.setPrefWidth(300);

        ImageView imagenView = crearImageView(oferta.getImagen());
        VBox detalles = crearDetallesOferta(oferta);

        tarjeta.getChildren().addAll(imagenView, detalles);
        tarjeta.setOnMouseClicked(e -> llenarCamposDesdeOferta(oferta));

        return tarjeta;
    }

    /**
     * Crea un ImageView para la imagen de la oferta
     */
    private ImageView crearImageView(String rutaImagen) {
        ImageView imagenView = new ImageView();
        File archivoImagen = new File(rutaImagen);

        if (archivoImagen.exists()) {
            imagenView.setImage(new Image(archivoImagen.toURI().toString()));
        }

        imagenView.setFitWidth(200);
        imagenView.setFitHeight(120);
        imagenView.setPreserveRatio(true);

        return imagenView;
    }

    /**
     * Crea los detalles de la oferta para mostrar en la tarjeta
     */
    private VBox crearDetallesOferta(Oferta oferta) {
        VBox detalles = new VBox(5);
        detalles.getChildren().addAll(
                new Label(oferta.getNombre()) {{
                    setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");
                }},
                new Label("Tipo: " + oferta.getTipo().name())
        );
        return detalles;
    }

    /**
     * Almacena el ID de la oferta seleccionada
     */
    public void llenarCamposDesdeOferta(Oferta oferta) {
        idOferta = oferta.getId();
    }

    /**
     * Muestra una ventana modal
     */
    private void mostrarVentana(ActionEvent event, String ruta, String titulo) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(ruta));
            Parent root = loader.load();

            Stage stage = crearStage(event, titulo);
            stage.setScene(new Scene(root));
            stage.showAndWait();

        } catch (IOException e) {
            mostrarAlerta("Error", "No se pudo cargar la ventana: " + e.getMessage());
        }
    }

    /**
     * Crea y configura un Stage para ventanas emergentes
     */
    private Stage crearStage(ActionEvent event, String titulo) {
        Stage stage = new Stage();

        try {
            File archivoImagen = new File("Img/ImagenesApp/icon.png");
            if (archivoImagen.exists()) {
                Image icono = new Image(archivoImagen.toURI().toString());
                stage.getIcons().add(icono);
            }
        } catch (Exception e) {
            System.err.println("Error cargando el icono: " + e.getMessage());
        }

        stage.setTitle(titulo);
        stage.setResizable(false);

        Stage parentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.initOwner(parentStage);
        stage.initModality(Modality.WINDOW_MODAL);

        return stage;
    }

    /**
     * Muestra una alerta de error
     */
    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}