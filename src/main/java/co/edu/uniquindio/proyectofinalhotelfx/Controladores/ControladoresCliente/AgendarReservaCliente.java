package co.edu.uniquindio.proyectofinalhotelfx.Controladores.ControladoresCliente;

import co.edu.uniquindio.proyectofinalhotelfx.Controladores.ControladorPrincipal;
import co.edu.uniquindio.proyectofinalhotelfx.Modelo.Entidades.Alojamiento;
import co.edu.uniquindio.proyectofinalhotelfx.Modelo.Entidades.Cliente;
import co.edu.uniquindio.proyectofinalhotelfx.Singleton.SesionUsuario;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
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

        System.out.println("Cliente obtenido: " + (usuario != null ? usuario.getCedula() : "Usuario es null"));

        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 20, 1);
        huespedesSpinner.setValueFactory(valueFactory);

        // Limitar fechas pasadas
        fechaInicioPicker.setDayCellFactory(picker -> new DateCell() {
            @Override public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                setDisable(empty || date.isBefore(LocalDate.now()));
            }
        });
        fechaFinPicker.setDayCellFactory(picker -> new DateCell() {
            @Override public void updateItem(LocalDate date, boolean empty) {
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
            System.err.println("Error: " + e.getMessage());
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
    private void confirmarReserva(ActionEvent event) {
        mostrarMensaje("");
        mostrarError("");

        if (usuario == null || alojamiento == null) {
            mostrarError("⚠ Usuario o alojamiento no cargado.");
            return;
        }

        int huespedesSeleccionados = huespedesSpinner.getValue();
        if (huespedesSeleccionados > alojamiento.getCapacidadHuespedes()) {
            mostrarError("⚠ Huéspedes superan capacidad.");
            return;
        }

        if (fechaInicioPicker.getValue() == null || fechaFinPicker.getValue() == null) {
            mostrarError("⚠ Seleccione fechas inicio y fin.");
            return;
        }

        LocalDate hoy = LocalDate.now();
        LocalDate inicio = fechaInicioPicker.getValue();
        LocalDate fin = fechaFinPicker.getValue();

        if (inicio.isBefore(hoy) || fin.isBefore(hoy)) {
            mostrarError("⚠ Las fechas no pueden ser pasadas.");
            return;
        }

        long dias = ChronoUnit.DAYS.between(inicio, fin) + 1;
        if (dias <= 0) {
            mostrarError("⚠ Fecha fin debe ser posterior o igual a inicio.");
            return;
        }

        double totalReserva = dias * alojamiento.getPrecioNoche();

        try {
            double saldoActual = controladorPrincipal.getPlataforma().consultarSaldo(usuario.getCedula());
            if (saldoActual < totalReserva) {
                mostrarError("⚠ Saldo insuficiente.");
                return;
            }
        } catch (Exception e) {
            mostrarError("Error al consultar saldo.");
            return;
        }

        try {
            // Descontar saldo
            controladorPrincipal.getPlataforma().getServicioBilleteraVirtual()
                    .descontarSaldo(usuario.getCedula(), (float) totalReserva);

            // Agregar reserva en servicioReserva
            controladorPrincipal.getPlataforma().getServicioReserva().agregarReserva(
                    usuario.getCedula(),
                    alojamiento.getId(),
                    inicio.atStartOfDay(),
                    fin.atStartOfDay(),
                    huespedesSeleccionados,
                    totalReserva,
                    LocalDateTime.now()
            );

            mostrarMensaje("✅ Reserva confirmada. Se descontó $" + String.format("%.2f", totalReserva));
            actualizarSaldo();

            // Abrir ventana MisReservasCliente en nuevo Stage y cerrar la actual
            abrirVentanaMisReservas(event);

        } catch (Exception e) {
            mostrarError("Error al crear reserva: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void abrirVentanaMisReservas(ActionEvent event) {
        new Thread(() -> {
            try {
                // Espera opcional (no necesaria)
                Thread.sleep(300);

                Platform.runLater(() -> {
                    try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/co/edu/uniquindio/proyectofinalhotelfx/AgendarReservaCliente.fxml"));
                        Parent root = loader.load();

                        MisReservasCliente controlador = loader.getController();
                        controlador.cargarReservas(usuario.getCedula());

                        Stage stage = new Stage();

                        // Opcional: icono para la ventana
                        File archivoImagen = new File("Img/ImagenesApp/icon.png");
                        if (archivoImagen.exists()) {
                            Image icono = new Image(archivoImagen.toURI().toString());
                            stage.getIcons().add(icono);
                        }

                        stage.setTitle("Mis Reservas");
                        stage.setScene(new Scene(root));
                        stage.setResizable(false);
                        stage.show();

                        // Cerrar la ventana actual
                        Stage stageActual = (Stage) ((Node) event.getSource()).getScene().getWindow();
                        stageActual.close();

                    } catch (IOException e) {
                        e.printStackTrace();
                        mostrarError("No se pudo abrir la ventana de Mis Reservas.");
                    }
                });

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

    @FXML
    private void cancelarReserva(ActionEvent event) {
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

    private void actualizarSaldo() throws Exception {
        if (usuario != null) {
            double saldoActualizado = controladorPrincipal.getPlataforma().consultarSaldo(usuario.getCedula());
            saldoLabel.setText("$" + String.format("%.2f", saldoActualizado));
        } else {
            saldoLabel.setText("No disponible");
        }
    }
}
