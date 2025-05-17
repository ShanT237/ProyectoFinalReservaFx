package co.edu.uniquindio.proyectofinalhotelfx.Controladores.ControladoresCliente;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;

public class MiBilleteraCliente {

    @FXML
    private TextField txtMontoRecarga;

    @FXML
    private Button btnRecargar;

    @FXML
    private Label lblSaldo;

    @FXML
    private Label lblMensajeError;

    private double saldoActual = 0.0;

    @FXML
    void initialize() {
        actualizarSaldo();
    }

    @FXML
    private void recargarCuenta(ActionEvent event) {
        String montoTexto = txtMontoRecarga.getText().trim();

        if (montoTexto.isEmpty()) {
            mostrarError("El monto es obligatorio.");
            return;
        }

        try {
            double monto = Double.parseDouble(montoTexto);
            if (monto <= 0) {
                mostrarError("Ingrese un monto válido mayor que 0.");
                return;
            }

            saldoActual += monto;
            actualizarSaldo();
            mostrarMensaje("Recarga exitosa por $" + monto);
            txtMontoRecarga.clear();
        } catch (NumberFormatException e) {
            mostrarError("Ingrese un número válido.");
        }
    }

    private void actualizarSaldo() {
        lblSaldo.setText("$" + String.format("%.2f", saldoActual));
    }

    private void mostrarError(String mensaje) {
        lblMensajeError.setTextFill(javafx.scene.paint.Color.RED);
        lblMensajeError.setText(mensaje);
    }

    private void mostrarMensaje(String mensaje) {
        lblMensajeError.setTextFill(javafx.scene.paint.Color.GREEN);
        lblMensajeError.setText(mensaje);
    }
}
