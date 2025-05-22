package co.edu.uniquindio.proyectofinalhotelfx.Controladores.ControladoresCliente;

import co.edu.uniquindio.proyectofinalhotelfx.Modelo.Entidades.Usuario;
import co.edu.uniquindio.proyectofinalhotelfx.Servicios.ServicioBilleteraVirtual;
import co.edu.uniquindio.proyectofinalhotelfx.Singleton.SesionUsuario;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class HomeCliente implements Initializable {

    private Usuario usuario;
    private ServicioBilleteraVirtual servicioBilleteraVirtual;

    @FXML
    private Label lblNombreUsuario;

    @FXML
    private Label lblSaldo;

    @FXML
    private StackPane contenidoDinamico;

    public void setServicioBilleteraVirtual(ServicioBilleteraVirtual servicio) {
        this.servicioBilleteraVirtual = servicio;

    }

    // Consulta y muestra el saldo del usuario
    private void actualizarSaldo() throws Exception {
        if (usuario != null && servicioBilleteraVirtual != null) {
            float saldo = servicioBilleteraVirtual.consultarSaldo(usuario.getCedula());
            if (lblSaldo != null) {
                lblSaldo.setText("Saldo: $" + String.format("%.2f", saldo));
            }
        }
    }

    // Carga contenido FXML dentro del StackPane dinámico
    private void cargarContenido(String fxml) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
            Parent root = loader.load();
            contenidoDinamico.getChildren().setAll(root); // Reemplaza el contenido actual
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Métodos conectados con botones del menú
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
        System.out.println("Cerrando sesión...");
        // Lógica para cerrar sesión o volver al login
    }

    // Inicializa usuario desde sesión
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        SesionUsuario sesionUsuario = SesionUsuario.instancia();
        this.usuario = sesionUsuario.getUsuario();

        if (usuario != null) {
            lblNombreUsuario.setText(usuario.getCorreo());
        }
    }

}

