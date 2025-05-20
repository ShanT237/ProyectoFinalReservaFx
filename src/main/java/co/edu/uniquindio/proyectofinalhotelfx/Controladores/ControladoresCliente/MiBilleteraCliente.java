package co.edu.uniquindio.proyectofinalhotelfx.Controladores.ControladoresCliente;

import co.edu.uniquindio.proyectofinalhotelfx.Modelo.Entidades.Usuario;
import co.edu.uniquindio.proyectofinalhotelfx.Servicios.ServicioBilleteraVirtual;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;

public class MiBilleteraCliente {

    @FXML
    private TextField txtMontoRecarga;

    @FXML
    private Button btnRecargar;

    @FXML
    private Label lblSaldo;

    @FXML
    private Label lblMensajeError;

    private ServicioBilleteraVirtual servicioBilleteraVirtual;
    private Usuario usuario;

    // Método llamado desde HomeCliente para inyectar servicio y usuario
    public void setServicioBilletera(ServicioBilleteraVirtual servicio, Usuario usuario) {
        this.servicioBilleteraVirtual = servicio;
        this.usuario = usuario;
        actualizarSaldo();
    }

    @FXML
    void initialize() {
        // Espera a que se llame setServicioBilletera antes de mostrar el saldo
    }

    @FXML
    private void recargarCuenta() {
        if (usuario == null) {
            mostrarError("Error: usuario no cargado. Reinicie la aplicación.");
            return;
        }

        String montoTexto = txtMontoRecarga.getText().trim();

        if (montoTexto.isEmpty()) {
            mostrarError("El monto es obligatorio.");
            return;
        }

        try {
            double monto = Double.parseDouble(montoTexto);
            if (monto <= 0) {
                mostrarError("Ingrese un monto mayor a 0.");
                return;
            }

            servicioBilleteraVirtual.recargarBilletera(usuario.getCedula(), monto);

            actualizarSaldo();
            mostrarMensaje("Recarga exitosa de $" + monto);
            txtMontoRecarga.clear();

        } catch (NumberFormatException e) {
            mostrarError("Monto inválido. Ingrese un número.");
        } catch (IllegalArgumentException e) {
            mostrarError(e.getMessage());
        }
    }

    private void actualizarSaldo() {
        if (usuario != null && servicioBilleteraVirtual != null) {
            double saldo = servicioBilleteraVirtual.consultarSaldo(usuario.getCedula());
            lblSaldo.setText("$" + String.format("%.2f", saldo));
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
}
