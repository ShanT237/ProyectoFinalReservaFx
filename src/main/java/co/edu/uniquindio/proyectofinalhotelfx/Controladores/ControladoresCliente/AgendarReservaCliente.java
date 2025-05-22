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
import java.time.LocalDate;
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

        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 20, 1);
        huespedesSpinner.setValueFactory(valueFactory);

        // Restringir selección de fechas pasadas en los DatePicker
        fechaInicioPicker.setDayCellFactory(picker -> new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                setDisable(empty || date.isBefore(LocalDate.now()));
            }
        });

        fechaFinPicker.setDayCellFactory(picker -> new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                setDisable(empty || date.isBefore(LocalDate.now()));
            }
        });

        fechaInicioPicker.valueProperty().addListener((obs, oldVal, newVal) -> calcularYMostrarTotal());
        fechaFinPicker.valueProperty().addListener((obs, oldVal, newVal) -> calcularYMostrarTotal());

        try {
            actualizarSaldo();
        } catch (Exception e) {
            saldoLabel.setText("Error al cargar saldo");
            System.err.println("Error al actualizar saldo: " + e.getMessage());
        }
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

        try {
            actualizarSaldo();
        } catch (Exception e) {
            saldoLabel.setText("Error al cargar saldo");
            System.err.println("Error al actualizar el saldo: " + e.getMessage());
        }

        calcularYMostrarTotal();
    }


    private void calcularYMostrarTotal() {
        if (fechaInicioPicker.getValue() != null && fechaFinPicker.getValue() != null && alojamiento != null) {
            LocalDate hoy = LocalDate.now();
            LocalDate inicio = fechaInicioPicker.getValue();
            LocalDate fin = fechaFinPicker.getValue();

            if (inicio.isBefore(hoy) || fin.isBefore(hoy)) {
                totalLabel.setText("$0");
                return;
            }

            long dias = ChronoUnit.DAYS.between(inicio, fin);
            if (dias <= 0) {
                totalLabel.setText("$0");
            } else {
                double total = dias * alojamiento.getPrecioNoche();
                totalLabel.setText("$" + total);
            }
        } else {
            totalLabel.setText("$0");
        }
    }

    @FXML
    private void confirmarReserva(ActionEvent actionEvent) throws Exception {
        mostrarMensaje("");
        mostrarError("");

        if (usuario == null || alojamiento == null) {
            mostrarError("⚠ Usuario o alojamiento no cargado correctamente.");
            return;
        }

        int huespedesSeleccionados = huespedesSpinner.getValue();
        if (huespedesSeleccionados > alojamiento.getCapacidadHuespedes()) {
            mostrarError("⚠ La cantidad de huéspedes supera la capacidad del alojamiento.");
            return;
        }

        if (fechaInicioPicker.getValue() == null || fechaFinPicker.getValue() == null) {
            mostrarError("⚠ Debe seleccionar fecha de inicio y fin.");
            return;
        }

        LocalDate hoy = LocalDate.now();
        LocalDate inicio = fechaInicioPicker.getValue();
        LocalDate fin = fechaFinPicker.getValue();

        if (inicio.isBefore(hoy) || fin.isBefore(hoy)) {
            mostrarError("⚠ Las fechas no pueden estar en el pasado.");
            return;
        }

        long dias = ChronoUnit.DAYS.between(inicio, fin) + 1; // +1 si quieres incluir el día fin
        if (dias <= 0) {
            mostrarError("⚠ La fecha fin debe ser posterior o igual a la fecha inicio.");
            return;
        }

        double totalReserva = dias * alojamiento.getPrecioNoche();

        double saldoActual = controladorPrincipal.getPlataforma().consultarSaldo(usuario.getCedula());

        if (saldoActual < totalReserva) {
            mostrarError("⚠ Saldo insuficiente. Recargue su billetera.");
            return;
        }

        try {
            controladorPrincipal.getPlataforma().getServicioBilleteraVirtual().descontarSaldo(usuario.getCedula(), (float) totalReserva);

        } catch (Exception e) {
            mostrarError("Error al descontar saldo: " + e.getMessage());
            return;
        }

        try {
            controladorPrincipal.getPlataforma().reservarAlojamiento(usuario, alojamiento);
            mostrarMensaje("✅ Reserva confirmada. Se descontó $" + String.format("%.2f", totalReserva));
            actualizarSaldo();
        } catch (Exception e) {
            // Aquí podrías intentar devolver el saldo descontado si tienes la lógica para eso
            mostrarError("Error al registrar la reserva: " + e.getMessage());
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

    private void actualizarSaldo() throws Exception{
        if (usuario != null) {
            double saldoActualizado = controladorPrincipal.getPlataforma().consultarSaldo(usuario.getCedula());
            saldoLabel.setText("$" + String.format("%.2f", saldoActualizado));
        } else {
            saldoLabel.setText("No disponible");
        }
    }
}

