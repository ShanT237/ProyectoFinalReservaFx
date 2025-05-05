package co.edu.uniquindio.proyectofinalhotelfx.Controlador;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class HomeAdmin{
    @FXML private StackPane contenidoDinamico;
    @FXML private Label lblFecha;

    @FXML
    public void initialize() {
        // Configurar fecha actual
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        lblFecha.setText("Fecha: " + LocalDate.now().format(formatter));
    }

    @FXML
    private void cargarGestionAlojamientos() {
        cargarContenido("/fxml/GestionAlojamientos.fxml");
    }

    @FXML
    private void cargarGestionOfertas() {
        cargarContenido("/fxml/GestionOfertas.fxml");
    }

    @FXML
    private void cargarEstadisticas() {
        cargarContenido("/fxml/Estadisticas.fxml");
    }

    @FXML
    private void cargarReportes() {
        cargarContenido("/fxml/Reportes.fxml");
    }

    @FXML
    private void cerrarSesion() {
        // Lógica para cerrar sesión
        System.exit(0); // Ejemplo temporal
    }

    private void cargarContenido(String fxmlPath) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(fxmlPath));
            contenidoDinamico.getChildren().setAll(root);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}