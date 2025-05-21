package co.edu.uniquindio.proyectofinalhotelfx.Controladores.ControladoresAdm;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import co.edu.uniquindio.proyectofinalhotelfx.Controladores.ControladorPrincipal;
import co.edu.uniquindio.proyectofinalhotelfx.Modelo.Entidades.Oferta;
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

public class GestionOfertasAdmin {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<?> boxAlojamiento;

    @FXML
    private ComboBox<?> boxCiudad;

    @FXML
    private CheckBox esGlobal;

    @FXML
    private FlowPane flowPaneOferfas;

    private ControladorPrincipal controladorPrincipal = ControladorPrincipal.getInstancia();


    @FXML
    void initialize() {
        cargarOfertas();
    }


    @FXML
    void buscarOferta(ActionEvent event) {

    }

    @FXML
    void btnCrearOferta(ActionEvent event) {
        mostrarVentana(event, "/co/edu/uniquindio/proyectofinalhotelfx/FXMLDW(ADMIN)/RegistrarOfertas.fxml", "Registrar Oferta");

    }


    @FXML
    void eliminarOferta(ActionEvent event) {

    }

    @FXML
    private void mostrarVentana(ActionEvent event, String ruta, String titulo) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(ruta));
            Parent root = loader.load();  // Primero cargar la vista


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

            stage.setScene(new Scene(root));  // Usar root en lugar de controller
            stage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
            mostrarAlerta("Error", "No se pudo cargar la ventana: " + e.getMessage());
        }
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    public void cargarOfertas() {
        flowPaneOferfas.getChildren().clear(); // Limpia antes de cargar

        List<Oferta> ofertas = controladorPrincipal.getPlataforma().getOfertaRepository().obtenerTodos(); // adapta esta línea a tu lógica

        for (Oferta oferta : ofertas) {
            flowPaneOferfas.getChildren().add(crearTarjetaOferta(oferta));
        }
    }


    private VBox crearTarjetaOferta(Oferta oferta) {
        VBox tarjeta = new VBox(10);
        tarjeta.setStyle("-fx-padding: 10; -fx-border-color: #2a2a2a; -fx-border-radius: 10; -fx-background-color: #f4f4f4;");
        tarjeta.setPrefWidth(300);

        ImageView imagenView = new ImageView();
        File archivoImagen = new File(oferta.getImagen()); // Asegúrate de que esta ruta sea válida
        if (archivoImagen.exists()) {
            imagenView.setImage(new Image(archivoImagen.toURI().toString()));
        } else {
            System.out.println("Imagen no encontrada en: " + oferta.getImagen());
        }

        imagenView.setFitWidth(200);
        imagenView.setFitHeight(120);
        imagenView.setPreserveRatio(true);

        VBox detalles = new VBox(5);
        detalles.getChildren().addAll(
                new Label(oferta.getNombre()) {{
                    setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");
                }},
                new Label("Tipo: " + oferta.getTipo().name())
        );

        tarjeta.getChildren().addAll(imagenView, detalles);
        return tarjeta;
    }

}