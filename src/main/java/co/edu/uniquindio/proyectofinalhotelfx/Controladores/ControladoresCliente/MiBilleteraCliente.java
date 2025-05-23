package co.edu.uniquindio.proyectofinalhotelfx.Controladores.ControladoresCliente;

import co.edu.uniquindio.proyectofinalhotelfx.Controladores.ControladorPrincipal;
import co.edu.uniquindio.proyectofinalhotelfx.Modelo.Entidades.Usuario;
import co.edu.uniquindio.proyectofinalhotelfx.Singleton.SesionUsuario;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import lombok.Setter;

import java.net.URL;
import java.util.ResourceBundle;

public class MiBilleteraCliente implements Initializable {

    @FXML
    private TextField txtMontoRecarga;

    @FXML
    private Button btnRecargar;

    @FXML
    private Label lblSaldo;

    @FXML
    private Label lblMensajeError;

    private Usuario usuario;

    private SesionUsuario sesionUsuario = SesionUsuario.instancia();

    private ControladorPrincipal controladorPrincipal = ControladorPrincipal.getInstancia();

    @Setter
    private HomeCliente homeCliente;

    @FXML
    private void recargarCuenta() throws Exception {
        System.out.println("Usuario en recargarCuenta: " + usuario);

        if (usuario == null) {
            mostrarError("⚠ Error: usuario no cargado. Por favor, regrese al menú y reintente.");
            return;
        }

        String montoTexto = txtMontoRecarga.getText().trim();

        if (montoTexto.isEmpty()) {
            mostrarError("⚠ El monto es obligatorio.");
            return;
        }

        try {
            double monto = Double.parseDouble(montoTexto);
            if (monto <= 0) {
                mostrarError("⚠ Ingrese un monto mayor a 0.");
                return;
            }

            // CORRECCIÓN: nombre correcto del método
            controladorPrincipal.getPlataforma().getServicioBilleteraVirtual().recargarBilletera(usuario.getCedula(), (float) monto);

            actualizarSaldo();
            homeCliente.actualizarSaldo();
            mostrarMensaje("✅ Recarga exitosa de $" + String.format("%.2f", monto));
            txtMontoRecarga.clear();

        } catch (NumberFormatException e) {
            mostrarError("⚠ Monto inválido. Ingrese un número.");
        } catch (IllegalArgumentException e) {
            mostrarError("⚠ " + e.getMessage());
        }
    }

    private void actualizarSaldo() throws Exception {
        if (usuario != null) {
            double saldo = controladorPrincipal.getPlataforma().consultarSaldo(usuario.getCedula());
            lblSaldo.setText("$" + String.format("%.2f", saldo));

        } else {
            lblSaldo.setText("No disponible");
        }
    }

    private void mostrarError(String mensaje) {
        lblMensajeError.setTextFill(Color.RED);
        lblMensajeError.setText(mensaje);
    }

    private void mostrarMensaje(String mensaje) {
        lblMensajeError.setTextFill(Color.GREEN);
        lblMensajeError.setText(mensaje);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.usuario = sesionUsuario.getUsuario();
        System.out.println("setServicioBilletera llamado con usuario: " + usuario);
        try {
            actualizarSaldo();
        } catch (Exception e) {
            mostrarError("Error al cargar saldo: " + e.getMessage());
        }
    }
}
