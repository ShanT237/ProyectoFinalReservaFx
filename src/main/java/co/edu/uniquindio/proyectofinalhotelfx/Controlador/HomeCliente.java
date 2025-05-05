package co.edu.uniquindio.proyectofinalhotelfx.Controlador;

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
        // Configuración inicial
        lblNombreUsuario.setText(SesionManager.getUsuarioActual().getNombre());
        lblSaldo.setText("Saldo: $" + SesionManager.getClienteActual().getBilletera());
    }

    @FXML
    private void cargarBuscarAlojamientos() {
        cargarContenido("/fxml/BuscarAlojamientos.fxml");
    }

    @FXML
    private void cargarMisReservas() {
        cargarContenido("/fxml/MisReservas.fxml");
    }

    @FXML
    private void cargarBilletera() {
        cargarContenido("/fxml/Billetera.fxml");
    }

    @FXML
    private void cargarResenas() {
        cargarContenido("/fxml/Resenas.fxml");
    }

    @FXML
    private void cerrarSesion() {
        SesionManager.cerrarSesion();
        // Redirigir a login
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