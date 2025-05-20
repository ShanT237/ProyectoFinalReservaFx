package co.edu.uniquindio.proyectofinalhotelfx.Controladores.ControladoresCliente;

import co.edu.uniquindio.proyectofinalhotelfx.Modelo.Entidades.Usuario;
import co.edu.uniquindio.proyectofinalhotelfx.Servicios.ServicioBilleteraVirtual;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;

import java.io.IOException;

public class HomeCliente {

    private Usuario usuario;
    private ServicioBilleteraVirtual servicioBilleteraVirtual;

    @FXML
    private Label lblNombreUsuario;

    @FXML
    private Label lblSaldo;

    @FXML
    private StackPane contenidoDinamico;

    // Establece el usuario y actualiza la etiqueta con su nombre
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
        if (lblNombreUsuario != null) {
            lblNombreUsuario.setText(usuario.getNombre());
        }
        actualizarSaldo();
    }

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

    // Carga un archivo FXML dentro del StackPane "contenidoDinamico"
    private void cargarContenido(String fxml) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
            Parent root = loader.load();
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
        cargarContenido("/co/edu/uniquindio/proyectofinalhotelfx/MiBilleteraCliente.fxml");
    }

    @FXML
    private void cargarResenas() {
        cargarContenido("/co/edu/uniquindio/proyectofinalhotelfx/ResenasCliente.fxml");
    }

    @FXML
    private void cerrarSesion() {
        // Aquí deberías cargar el login o cerrar la aplicación, según tu lógica
        System.out.println("Cerrando sesión...");
    }

    // Los siguientes métodos con ActionEvent ya no son necesarios si usas onAction en FXML
    // Puedes eliminarlos si no los usas
}
