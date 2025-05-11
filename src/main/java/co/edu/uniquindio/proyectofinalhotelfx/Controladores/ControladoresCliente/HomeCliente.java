package co.edu.uniquindio.proyectofinalhotelfx.Controladores.ControladoresCliente;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;

public class HomeCliente{
    @FXML private Label lblNombreUsuario;
    @FXML private Label lblSaldo;
    @FXML private StackPane contenidoDinamico;

    @FXML
    public void initialize() {
        // Configuración
    }

    @FXML
    private void cargarBuscarAlojamientos() {
        cargarContenido("/co/edu/uniquindio/proyectofinalhotelfx/BuscarAlojamientosCliente.fxml");
    }

    @FXML
    private void cargarMisReservas() {
        cargarContenido("/co/edu/uniquindio/proyectofinalhotelfx/MisReservasCliente.fxml");
    }

    @FXML
    private void cargarBilletera() {
        cargarContenido("/co/edu/uniquindio/proyectofinalhotelfx/MiBilleteraCliente.fxml");
    }

    @FXML
    private void cargarResenas() {
        cargarContenido("/co/edu/uniquindio/proyectofinalhotelfx/ResenasCliente.fxml");
    }

    @FXML
    private void cerrarSesion() {

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