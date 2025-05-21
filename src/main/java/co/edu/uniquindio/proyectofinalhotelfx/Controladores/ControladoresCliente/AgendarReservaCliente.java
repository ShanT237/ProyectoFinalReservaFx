package co.edu.uniquindio.proyectofinalhotelfx.Controladores.ControladoresCliente;

import co.edu.uniquindio.proyectofinalhotelfx.Controladores.ControladorPrincipal;
import co.edu.uniquindio.proyectofinalhotelfx.Modelo.Entidades.Alojamiento;
import co.edu.uniquindio.proyectofinalhotelfx.Modelo.Entidades.Cliente;
import co.edu.uniquindio.proyectofinalhotelfx.Singleton.SesionUsuario;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

import java.net.URL;
import java.time.temporal.ChronoUnit;
import java.util.ResourceBundle;

public class AgendarReservaCliente implements Initializable {

    @FXML private Label nombreLabel;
    @FXML private Label ciudadLabel;
    @FXML private Label capacidadLabel;
    @FXML private Label precioLabel;
    @FXML private TextArea descripcionArea;
    @FXML private DatePicker fechaInicioPicker;
    @FXML private DatePicker fechaFinPicker;
    @FXML private Spinner<Integer> huespedesSpinner;
    @FXML private Label totalLabel;
    @FXML private Label saldoLabel;
    @FXML private ImageView qrImageView;
    @FXML private Label mensajeReservaLabel;

    private Cliente usuario;
    private Alojamiento alojamiento;

    private final SesionUsuario sesionUsuario = SesionUsuario.instancia();
    private final ControladorPrincipal controladorPrincipal = ControladorPrincipal.getInstancia();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.usuario = (Cliente) sesionUsuario.getUsuario();
        System.out.println("Usuario cargado en AgendarReservaCliente: " + usuario);

        // Inicializar spinner con valores
        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 20, 1);
        huespedesSpinner.setValueFactory(valueFactory);

        // Agregar listeners para recalcular total al cambiar fechas
        fechaInicioPicker.valueProperty().addListener((obs, oldValue, newValue) -> calcularYMostrarTotal());
        fechaFinPicker.valueProperty().addListener((obs, oldValue, newValue) -> calcularYMostrarTotal());

        actualizarSaldo(); // Mostrar saldo actualizado al cargar la vista
    }


    public void setDatos(Alojamiento alojamiento) {
        this.alojamiento = alojamiento;

        if (alojamiento != null) {
            nombreLabel.setText(alojamiento.getNombre());
            ciudadLabel.setText(alojamiento.getCiudad().toString());
            capacidadLabel.setText(String.valueOf(alojamiento.getCapacidadHuespedes()));
            precioLabel.setText("$" + alojamiento.getPrecioNoche());
            descripcionArea.setText(alojamiento.getDescripcion());
        }

        actualizarSaldo(); // Actualizar saldo siempre que se cambien datos del alojamiento
        calcularYMostrarTotal();
    }


    private void calcularYMostrarTotal() {
        if (fechaInicioPicker.getValue() != null && fechaFinPicker.getValue() != null && alojamiento != null) {
            long dias = ChronoUnit.DAYS.between(fechaInicioPicker.getValue(), fechaFinPicker.getValue());
            if (dias <= 0) {
                totalLabel.setText("$0");
            } else {
                double total = dias * alojamiento.getPrecioNoche();
                totalLabel.setText("$" + total);
            }
        }
    }

    @FXML
    private void confirmarReserva(ActionEvent actionEvent) {
        if (usuario == null || alojamiento == null) {
            mostrarError("⚠ Usuario o alojamiento no cargado correctamente.");
            return;
        }

        try {
            controladorPrincipal.getPlataforma().reservarAlojamiento(usuario, alojamiento);
            mostrarMensaje("✅ Reserva confirmada.");
            mensajeReservaLabel.setVisible(true);
        } catch (Exception e) {
            mostrarError("❌ Error al realizar la reserva: " + e.getMessage());
        }
    }

    @FXML
    private void cancelarReserva(ActionEvent actionEvent) {
        mostrarMensaje("Reserva cancelada.");
    }

    private void mostrarError(String mensaje) {
        mensajeReservaLabel.setTextFill(Color.RED);
        mensajeReservaLabel.setText(mensaje);
        mensajeReservaLabel.setVisible(true);
    }

    private void mostrarMensaje(String mensaje) {
        mensajeReservaLabel.setTextFill(Color.GREEN);
        mensajeReservaLabel.setText(mensaje);
        mensajeReservaLabel.setVisible(true);
    }

    private void actualizarSaldo() {
        if (usuario != null) {
            double saldoActualizado = controladorPrincipal.getPlataforma().consultarSaldo(usuario.getCedula());
            saldoLabel.setText("$" + String.format("%.2f", saldoActualizado));
        } else {
            saldoLabel.setText("No disponible");
        }
    }
}
