package co.edu.uniquindio.proyectofinalhotelfx.Controladores.ControladoresCliente;

import co.edu.uniquindio.proyectofinalhotelfx.Modelo.Entidades.Cliente;
import co.edu.uniquindio.proyectofinalhotelfx.Modelo.Entidades.Usuario;
import co.edu.uniquindio.proyectofinalhotelfx.Servicios.ServicioBilleteraVirtual;
import co.edu.uniquindio.proyectofinalhotelfx.Singleton.SesionUsuario;
import javafx.event.ActionEvent;
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



    // Establece el servicio de billetera y actualiza el saldo mostrado
    public void setServicioBilleteraVirtual(ServicioBilleteraVirtual servicio) {
        this.servicioBilleteraVirtual = servicio;
        actualizarSaldo();
    }

    // Consulta el saldo actual del usuario y lo muestra
    private void actualizarSaldo() {
        if (usuario != null && servicioBilleteraVirtual != null) {
            double saldo = servicioBilleteraVirtual.consultarSaldo(usuario.getCedula());
            if (lblSaldo != null) {
                lblSaldo.setText("Saldo: $" + String.format("%.2f", saldo));
            }
        }
    }

    private void cargarContenido(String fxml) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
            Parent root = loader.load();
            contenidoDinamico.getChildren().setAll(root);
            contenidoDinamico.getChildren().setAll(root); // Reemplaza el contenido actual
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    // Métodos conectados con los botones del menú lateral
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
        System.out.println("Cargando billetera, usuario: " + usuario);
        cargarContenido("/co/edu/uniquindio/proyectofinalhotelfx/MiBilleteraCliente.fxml");
    }


    @FXML
    private void cargarResenas() {
        System.out.println("Cargando reseñas, usuario: " + usuario);
        cargarContenido("/co/edu/uniquindio/proyectofinalhotelfx/ResenasCliente.fxml");
    }

    @FXML
    private void cerrarSesion() {
        // Aquí deberías cargar el login o cerrar la aplicación, según tu lógica
        System.out.println("Cerrando sesión...");
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        SesionUsuario sesionUsuario = SesionUsuario.instancia();
        this.usuario = sesionUsuario.getUsuario();

        if(usuario != null) {
            System.out.println("Usuario no es null");
            lblNombreUsuario.setText(usuario.getCorreo());
        }

    }
}
