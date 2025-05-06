package co.edu.uniquindio.proyectofinalhotelfx.Controladores;

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
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.util.Objects;

public class PantallaPrincipal {

    @FXML private ComboBox<Ciudad> cbCiudad;
    @FXML private ComboBox<TipoAlojamiento> cbTipoAlojamiento;
    @FXML private TextField txtPrecioMax;
    @FXML private Button btnBuscar;
    @FXML private FlowPane flowAlojamientos;
    @FXML private Button btnLogin;
    @FXML private Button btnRegistro;

    @FXML
    void initialize() {
        // Inicialización de comboboxes
        ObservableList<Ciudad> ciudades = FXCollections.observableArrayList(Ciudad.values());
        FXCollections.sort(ciudades, (c1, c2) -> c1.name().compareTo(c2.name()));
        cbCiudad.setItems(ciudades);

        ObservableList<TipoAlojamiento> tipos = FXCollections.observableArrayList(TipoAlojamiento.values());
        cbTipoAlojamiento.setItems(tipos);

        // Configuración de eventos
        btnBuscar.setOnAction(this::onBuscarClick);
        btnLogin.setOnAction(this::irLogin);
        btnRegistro.setOnAction(this::irRegistroCliente);
    }

    private void onBuscarClick(ActionEvent event) {
        Ciudad ciudadSeleccionada = cbCiudad.getValue();
        TipoAlojamiento tipoSeleccionado = cbTipoAlojamiento.getValue();
        String precioMax = txtPrecioMax.getText();

        if (ciudadSeleccionada == null || tipoSeleccionado == null) return;

        flowAlojamientos.getChildren().clear();

        for (int i = 0; i < 5; i++) {
            VBox alojamiento = new VBox(10);
            ImageView alojamientoImg = new ImageView(new Image("https://www.example.com/imagen.jpg"));
            alojamientoImg.setFitWidth(150);
            alojamientoImg.setFitHeight(150);
            alojamiento.getChildren().add(alojamientoImg);
            Label label = new Label("Alojamiento " + (i + 1) + " - " + ciudadSeleccionada + " - " + tipoSeleccionado);
            alojamiento.getChildren().add(label);
            flowAlojamientos.getChildren().add(alojamiento);
        }
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

            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setResizable(false);
            stage.setTitle(tituloVentana);
            stage.show();

            cerrarVentana();

        } catch (Exception e) {
            crearAlerta("No se pudo cargar la ventana: " + e.getMessage(), Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }

    private void cerrarVentana() {
        Stage stage = (Stage) btnRegistro.getScene().getWindow();
        stage.close();
    }

    private void crearAlerta(String mensaje, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle("Alerta");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}