package co.edu.uniquindio.proyectofinalhotelfx.Controladores;

import co.edu.uniquindio.proyectofinalhotelfx.Controladores.ControladoresCliente.InformacionImagenCliente;
import co.edu.uniquindio.proyectofinalhotelfx.Modelo.Entidades.Alojamiento;
import co.edu.uniquindio.proyectofinalhotelfx.Modelo.Entidades.Oferta;
import co.edu.uniquindio.proyectofinalhotelfx.Modelo.Enums.Ciudad;
import co.edu.uniquindio.proyectofinalhotelfx.Modelo.Enums.TipoAlojamiento;
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
import java.io.IOException;
import java.util.List;

public class PantallaPrincipal {

    @FXML private ComboBox<Ciudad> cbCiudad;
    @FXML private ComboBox<TipoAlojamiento> cbTipoAlojamiento;
    @FXML private TextField txtPrecioMax;
    @FXML private Button btnBuscar;
    @FXML private FlowPane flowAlojamientos;
    @FXML private Button btnLogin;
    @FXML private Button btnRegistro;
    @FXML private FlowPane flowOfertas;
    private String idOferta;

    private final ControladorPrincipal controladorPrincipal = ControladorPrincipal.getInstancia();
    private final List<Alojamiento> alojamientosList = controladorPrincipal.getPlataforma()
            .getServiciosAlojamiento()
            .getAlojamientoRepository()
            .obtenerTodos();
    private final ObservableList<Alojamiento> alojamientos = FXCollections.observableArrayList(alojamientosList);

    @FXML
    void initialize() {
        datosInicio();
        cargarOfertas();
        configurarEventos();
        mostrarAlojamientos(null, null, Double.MAX_VALUE); // Mostrar todos al inicio
    }

    public void datosInicio() {
        cargarCiudades();
        cargarTiposAlojamiento();
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

    private void configurarEventos() {
        btnLogin.setOnAction(this::irLogin);
        btnRegistro.setOnAction(this::irRegistroCliente);
    }

    @FXML
    private void buscarAlojamientos(ActionEvent event) {
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
        flowAlojamientos.getChildren().clear();

        for (Alojamiento alojamiento : alojamientos) {
            if (cumpleCriterios(alojamiento, ciudadSeleccionada, tipoSeleccionado, precioMax)) {
                flowAlojamientos.getChildren().add(crearTarjetaAlojamiento(alojamiento));
            }
        }
    }

    /**
     * Carga las ofertas en el FlowPane
     */
    public void cargarOfertas() {
        flowOfertas.getChildren().clear();
        List<Oferta> ofertas = controladorPrincipal.getPlataforma().getOfertaRepository().obtenerTodos();

        for (Oferta oferta : ofertas) {
            flowOfertas.getChildren().add(crearTarjetaOferta(oferta));
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


    private boolean cumpleCriterios(Alojamiento alojamiento, Ciudad ciudad, TipoAlojamiento tipo, double precioMax) {
        if (ciudad != null && !alojamiento.getCiudad().equals(ciudad)) return false;
        if (tipo != null && !alojamiento.getTipoAlojamiento().equals(tipo)) return false;
        return alojamiento.getPrecioNoche() <= precioMax;
    }

    private VBox crearTarjetaAlojamiento(Alojamiento alojamiento) {
        VBox tarjeta = new VBox(10);
        tarjeta.setStyle("-fx-padding: 10; -fx-border-color: #ccc; -fx-border-radius: 10; -fx-background-color: #f9f9f9;");
        tarjeta.setPrefWidth(500);

        HBox contenido = new HBox(10);
        contenido.setStyle("-fx-alignment: center-left;");

        ImageView imagenView = new ImageView();
        File archivoImagen = new File(alojamiento.getImagen());

        if (archivoImagen.exists()) {
            imagenView.setImage(new Image(archivoImagen.toURI().toString()));
        }

        imagenView.setFitWidth(150);
        imagenView.setFitHeight(100);
        imagenView.setPreserveRatio(true);

        imagenView.setOnMouseClicked(event -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/co/edu/uniquindio/proyectofinalhotelfx/InformacionImagenCliente.fxml"));
                Parent root = loader.load();
                InformacionImagenCliente controller = loader.getController();
                controller.setAlojamiento(alojamiento);

                Stage stage = new Stage();
                stage.getIcons().add(new Image(new File("Img/ImagenesApp/icon.png").toURI().toString()));
                stage.setScene(new Scene(root));
                stage.setTitle("Detalles del alojamiento");
                stage.show();
            } catch (Exception e) {
                e.printStackTrace();
                mostrarError("No se pudo cargar la ventana de detalles");
            }
        });

        VBox detalles = new VBox(5);
        detalles.getChildren().addAll(
                new Label(alojamiento.getNombre()) {{ setStyle("-fx-font-size: 14px; -fx-font-weight: bold;"); }},
                new Label(alojamiento.getCiudad().name()),
                new Label(String.format("%.2f COP por noche", alojamiento.getPrecioNoche()))
        );

        contenido.getChildren().addAll(imagenView, detalles);
        tarjeta.getChildren().add(contenido);

        return tarjeta;
    }

    public void irLogin(ActionEvent actionEvent) {
        navegarVentana("/co/edu/uniquindio/proyectofinalhotelfx/Login.fxml", "BookYourStay - Iniciar Sesión");
    }

    public void irRegistroCliente(ActionEvent actionEvent) {
        navegarVentana("/co/edu/uniquindio/proyectofinalhotelfx/RegistroCliente.fxml", "BookYourStay - Registro");
    }

    private void navegarVentana(String nombreArchivoFxml, String tituloVentana) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(nombreArchivoFxml));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.getIcons().add(new Image(new File("Img/ImagenesApp/icon.png").toURI().toString()));
            stage.setScene(new Scene(root));
            stage.setTitle(tituloVentana);
            stage.setResizable(false);
            stage.show();
            cerrarVentanaActual();
        } catch (Exception e) {
            mostrarError("No se pudo cargar la ventana: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void cerrarVentanaActual() {
        Stage stage = (Stage) btnBuscar.getScene().getWindow();
        stage.close();
    }

    private boolean verificarDatos(String precioMax) {
        if (!precioMax.isEmpty()) {
            try {
                Double.parseDouble(precioMax);
                return true;
            } catch (NumberFormatException e) {
                mostrarError("El precio máximo debe ser un número válido.");
                return false;
            }
        }
        return true;
    }

    private void limpiarResultados() {
        flowAlojamientos.getChildren().clear();
    }

    private void mostrarError(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(null);
        alert.setTitle("Error");
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}
