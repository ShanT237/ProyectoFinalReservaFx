package co.edu.uniquindio.proyectofinalhotelfx.Controladores.ControladoresCliente;

import co.edu.uniquindio.proyectofinalhotelfx.Controladores.ControladorPrincipal;
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

    private ControladorPrincipal controladorPrincipal = ControladorPrincipal.getInstancia();

    public void setServicioBilleteraVirtual(ServicioBilleteraVirtual servicio) {
        this.servicioBilleteraVirtual = servicio;

    }

    // Consulta y muestra el saldo del usuario
    protected void actualizarSaldo() throws Exception {

            float saldo = controladorPrincipal.getPlataforma().consultarSaldo(usuario.getCedula());
            System.out.println("Saldo actual: " + saldo);
            if (lblSaldo != null) {
                lblSaldo.setText("Saldo: $" + String.format("%.2f", saldo));

        }
    }

    // Carga contenido FXML dentro del StackPane dinámico
    private void cargarContenido(String fxml) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
            Parent root = loader.load();
            actualizarSaldo();
            contenidoDinamico.getChildren().setAll(root); // Reemplaza el contenido actual
        } catch (Exception e) {
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
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/co/edu/uniquindio/proyectofinalhotelfx/MisReservasCliente.fxml"));
            Parent root = loader.load();
            actualizarSaldo();
            contenidoDinamico.getChildren().setAll(root); // Reemplaza el contenido actual
            MisReservasCliente misReservasCliente = loader.getController();
            misReservasCliente.setHomeCliente(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void cargarBilletera() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/co/edu/uniquindio/proyectofinalhotelfx/MiBilleteraCliente.fxml"));
            Parent root = loader.load();
            actualizarSaldo();
            contenidoDinamico.getChildren().setAll(root); // Reemplaza el contenido actual
            MiBilleteraCliente miBilletera = loader.getController();
            miBilletera.setHomeCliente(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void cargarResenas() {
        cargarContenido("/co/edu/uniquindio/proyectofinalhotelfx/ResenasCliente.fxml");
    }

    @FXML
    private void cerrarSesion() {
        // Limpiar usuario en sesión
        SesionUsuario sesion = SesionUsuario.instancia();
        sesion.cerrarSesion(); // método que debes implementar en SesionUsuario para limpiar la sesión

        // Opcional: redirigir a la pantalla de login (ejemplo)
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/co/edu/uniquindio/proyectofinalhotelfx/PantallaPrincipal.fxml"));
            Parent root = loader.load();

            // Obtener la escena actual (asumiendo que 'contenidoDinamico' está dentro de la escena)
            contenidoDinamico.getScene().setRoot(root);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    // Inicializa usuario desde sesión
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        SesionUsuario sesionUsuario = SesionUsuario.instancia();
        this.usuario = sesionUsuario.getUsuario();
        try {
            actualizarSaldo();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        if (usuario != null) {
            lblNombreUsuario.setText(usuario.getCorreo());
        }
    }

}

