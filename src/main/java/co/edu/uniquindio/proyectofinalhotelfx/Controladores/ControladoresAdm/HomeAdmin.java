package co.edu.uniquindio.proyectofinalhotelfx.Controladores.ControladoresAdm;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class HomeAdmin {
    @FXML
    private StackPane contenidoDinamico;
    @FXML
    private Label lblFecha;

    @FXML
    public void initialize() {
        // Configurar fecha actual
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        lblFecha.setText("Fecha: " + LocalDate.now().format(formatter));
    }

    @FXML
    private void cargarGestionAlojamientos() {
        cargarContenido("/co/edu/uniquindio/proyectofinalhotelfx/GestionAlojamientosAdmin.fxml");
    }

    @FXML
    private void cargarGestionOfertas() {
        cargarContenido("/co/edu/uniquindio/proyectofinalhotelfx/GestionOfertasAdmin.fxml");
    }

    @FXML
    private void cargarEstadisticas() {
        cargarContenido("/co/edu/uniquindio/proyectofinalhotelfx/EstadisticasAdmin.fxml");
    }

    @FXML
    private void cargarReportes() {
        cargarContenido("/co/edu/uniquindio/proyectofinalhotelfx/ReportesAdmin.fxml");
    }
    @FXML
    public void cargarGestionUsuarios() {
        cargarContenido("/co/edu/uniquindio/proyectofinalhotelfx/GestionUsuariosAdmin.fxml");


    }



    @FXML
    private void cerrarSesion() {
        irAPantallaPrincipal("/co/edu/uniquindio/proyectofinalhotelfx/PantallaPrincipal.fxml", "BookYourStay - Inicio");

    }

    @FXML
    private void irAPantallaPrincipal(String ruta, String titulo) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(ruta));
            Parent root = loader.load();

            Stage nuevaVentana = new Stage();
            nuevaVentana.setResizable(true);
            nuevaVentana.setTitle(titulo);
            nuevaVentana.setScene(new Scene(root));
            nuevaVentana.show();

            Stage ventanaActual = (Stage) contenidoDinamico.getScene().getWindow();
            ventanaActual.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
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