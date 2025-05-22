package co.edu.uniquindio.proyectofinalhotelfx.Controladores.ControladoresCliente;

import co.edu.uniquindio.proyectofinalhotelfx.Controladores.ControladorPrincipal;
import co.edu.uniquindio.proyectofinalhotelfx.Modelo.Entidades.Alojamiento;
import co.edu.uniquindio.proyectofinalhotelfx.Modelo.Entidades.Cliente;
import co.edu.uniquindio.proyectofinalhotelfx.Modelo.Entidades.Reserva;
import co.edu.uniquindio.proyectofinalhotelfx.Singleton.SesionUsuario;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class MisReservasCliente implements Initializable {

    @FXML private TableView<Reserva> tblReservas;
    @FXML private TableColumn<Reserva, String> colAlojamiento;
    @FXML private TableColumn<Reserva, String> colCiudad;
    @FXML private TableColumn<Reserva, LocalDate> colFechaInicio;
    @FXML private TableColumn<Reserva, LocalDate> colFechaFin;
    @FXML private Button btnCancelarReserva;
    @FXML private Button btnDejarResena;

    private final SesionUsuario sesionUsuario = SesionUsuario.instancia();
    private final ControladorPrincipal controladorPrincipal = ControladorPrincipal.getInstancia();
    private ObservableList<Reserva> reservasDelCliente = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Configurar columnas
        colAlojamiento.setCellValueFactory(reserva -> {
            Alojamiento a = reserva.getValue().getAlojamiento();
            return new SimpleStringProperty(a != null ? a.getNombre() : "Sin nombre");
        });

        colCiudad.setCellValueFactory(reserva -> {
            Alojamiento a = reserva.getValue().getAlojamiento();
            return new SimpleStringProperty(a != null ? a.getCiudad().toString() : "Sin ciudad");
        });

        colFechaInicio.setCellValueFactory(reserva ->
                new SimpleObjectProperty<>(reserva.getValue().getFechaInicio().toLocalDate()));

        colFechaFin.setCellValueFactory(reserva ->
                new SimpleObjectProperty<>(reserva.getValue().getFechaFin().toLocalDate()));

        // **FILTRAR RESERVAS DEL CLIENTE ACTUAL**
        cargarReservasDelCliente();

        // Configurar eventos de botones
        btnCancelarReserva.setOnAction(e -> cancelarReservaSeleccionada());
        btnDejarResena.setOnAction(e -> dejarResenaReservaSeleccionada());
    }

    private void cargarReservasDelCliente() {
        try {
            Cliente clienteActual = (Cliente) sesionUsuario.getUsuario();
            if (clienteActual != null) {
                // Obtener todas las reservas y filtrar por cliente
                List<Reserva> todasLasReservas = controladorPrincipal.getPlataforma()
                        .getServicioReserva().listarReservas();

                List<Reserva> reservasCliente = todasLasReservas.stream()
                        .filter(reserva -> reserva.getCliente().getCedula().equals(clienteActual.getCedula()))
                        .filter(reserva -> reserva.isEstadoReserva()) // Solo reservas activas
                        .collect(Collectors.toList());

                reservasDelCliente.setAll(reservasCliente);
                tblReservas.setItems(reservasDelCliente);
            }
        } catch (Exception e) {
            System.err.println("Error al cargar reservas del cliente: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void cancelarReservaSeleccionada() {
        Reserva reservaSeleccionada = tblReservas.getSelectionModel().getSelectedItem();
        if (reservaSeleccionada == null) {
            mostrarAlerta("Debe seleccionar una reserva para cancelar.");
            return;
        }

        // Confirmar cancelación
        Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
        confirmacion.setTitle("Confirmar Cancelación");
        confirmacion.setHeaderText("¿Está seguro de cancelar esta reserva?");
        confirmacion.setContentText("El dinero será devuelto a su billetera.");

        if (confirmacion.showAndWait().orElse(ButtonType.CANCEL) == ButtonType.OK) {
            try {
                controladorPrincipal.getPlataforma().cancelarReserva(reservaSeleccionada.getCodigo());
                cargarReservasDelCliente(); // Recargar lista
                mostrarAlerta("Reserva cancelada exitosamente. El dinero ha sido devuelto a su billetera.");
            } catch (Exception e) {
                mostrarAlerta("Error al cancelar la reserva: " + e.getMessage());
            }
        }
    }

    private void dejarResenaReservaSeleccionada() {
        Reserva reservaSeleccionada = tblReservas.getSelectionModel().getSelectedItem();
        if (reservaSeleccionada == null) {
            mostrarAlerta("Debe seleccionar una reserva para dejar una reseña.");
            return;
        }

        if (reservaSeleccionada.getReview() != null) {
            mostrarAlerta("Ya ha dejado una reseña para esta reserva.");
            return;
        }

        // Crear diálogo para reseña
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Dejar Reseña");
        dialog.setHeaderText("Escriba su reseña para " + reservaSeleccionada.getAlojamiento().getNombre());
        dialog.setContentText("Comentario:");

        dialog.showAndWait().ifPresent(comentario -> {
            if (!comentario.trim().isEmpty()) {
                // Solicitar valoración
                ChoiceDialog<Integer> valoracionDialog = new ChoiceDialog<>(5, 1, 2, 3, 4, 5);
                valoracionDialog.setTitle("Valoración");
                valoracionDialog.setHeaderText("Seleccione una valoración del 1 al 5");
                valoracionDialog.setContentText("Valoración:");

                valoracionDialog.showAndWait().ifPresent(valoracion -> {
                    try {
                        controladorPrincipal.getPlataforma().agregarReview(
                                reservaSeleccionada.getCodigo(),
                                comentario,
                                valoracion
                        );
                        cargarReservasDelCliente(); // Recargar lista
                        mostrarAlerta("Reseña agregada exitosamente.");
                    } catch (Exception e) {
                        mostrarAlerta("Error al agregar la reseña: " + e.getMessage());
                    }
                });
            }
        });
    }

    private void mostrarAlerta(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Información");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}
