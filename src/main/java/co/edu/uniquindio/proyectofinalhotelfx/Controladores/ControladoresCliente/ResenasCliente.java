package co.edu.uniquindio.proyectofinalhotelfx.Controladores.ControladoresCliente;

import co.edu.uniquindio.proyectofinalhotelfx.Controladores.ControladorPrincipal;
import co.edu.uniquindio.proyectofinalhotelfx.Modelo.Entidades.Reserva;
import co.edu.uniquindio.proyectofinalhotelfx.Modelo.Entidades.Review;
import co.edu.uniquindio.proyectofinalhotelfx.Servicios.Plataforma;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

public class ResenasCliente {

    @FXML private TableView<ReviewData> tblResenas;
    @FXML private TableColumn<ReviewData, String> colResena;
    @FXML private Button btnEliminarResena;

    private Plataforma plataforma = ControladorPrincipal.INSTANCIA_PLATAFORMA;
    private ObservableList<ReviewData> listaResenas = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        configurarTabla();
        cargarResenas();
        configurarEventos();
    }

    private void configurarTabla() {
        // Configurar la columna para mostrar información completa de la reseña
        colResena.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getDescripcionCompleta())
        );

        // Hacer que la tabla sea de selección única
        tblResenas.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        // Asignar los datos a la tabla
        tblResenas.setItems(listaResenas);
    }

    private void configurarEventos() {
        btnEliminarResena.setOnAction(event -> eliminarResenaSeleccionada());
    }

    private void cargarResenas() {
        try {
            listaResenas.clear();

            // Obtener el cliente actual desde la sesión
            String cedulaCliente = ControladorPrincipal.INSTANCIA_CLIENTE_ACTUAL.getCedula();

            // Obtener todas las reservas del cliente
            List<Reserva> reservasCliente = plataforma.obtenerReservasPorCliente(cedulaCliente);

            // Filtrar solo las reservas que tienen reseña
            for (Reserva reserva : reservasCliente) {
                if (reserva.getReview() != null) {
                    ReviewData reviewData = new ReviewData(
                            reserva.getReview().getCodigo(),
                            reserva.getAlojamiento().getNombre(),
                            reserva.getReview().getValoracion(),
                            reserva.getReview().getComentario(),
                            reserva.getReview().getFecha().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"))
                    );
                    listaResenas.add(reviewData);
                }
            }

            // Si no hay reseñas, mostrar mensaje
            if (listaResenas.isEmpty()) {
                mostrarMensaje("Información", "No tienes reseñas registradas.", Alert.AlertType.INFORMATION);
            }

        } catch (Exception e) {
            mostrarMensaje("Error", "Error al cargar las reseñas: " + e.getMessage(), Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }

    private void eliminarResenaSeleccionada() {
        ReviewData resenaSeleccionada = tblResenas.getSelectionModel().getSelectedItem();

        if (resenaSeleccionada == null) {
            mostrarMensaje("Advertencia", "Debes seleccionar una reseña para eliminar.", Alert.AlertType.WARNING);
            return;
        }

        // Confirmar eliminación
        Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
        confirmacion.setTitle("Confirmar eliminación");
        confirmacion.setHeaderText("¿Estás seguro de eliminar esta reseña?");
        confirmacion.setContentText("Esta acción no se puede deshacer.");

        if (confirmacion.showAndWait().orElse(ButtonType.CANCEL) == ButtonType.OK) {
            try {
                // Eliminar la reseña usando el servicio
                plataforma.eliminarResena(resenaSeleccionada.getIdReview());

                // Recargar la tabla
                cargarResenas();

                mostrarMensaje("Éxito", "Reseña eliminada correctamente.", Alert.AlertType.INFORMATION);

            } catch (Exception e) {
                mostrarMensaje("Error", "Error al eliminar la reseña: " + e.getMessage(), Alert.AlertType.ERROR);
            }
        }
    }

    /**
     * Método público para refrescar las reseñas desde otros controladores
     */
    public void refrescarResenas() {
        cargarResenas();
    }

    private void mostrarMensaje(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    /**
     * Clase interna para manejar los datos de las reseñas en la tabla
     */
    public static class ReviewData {
        private UUID idReview;
        private String nombreAlojamiento;
        private int valoracion;
        private String comentario;
        private String fecha;

        public ReviewData(UUID idReview, String nombreAlojamiento, int valoracion, String comentario, String fecha) {
            this.idReview = idReview;
            this.nombreAlojamiento = nombreAlojamiento;
            this.valoracion = valoracion;
            this.comentario = comentario;
            this.fecha = fecha;
        }

        public String getDescripcionCompleta() {
            StringBuilder sb = new StringBuilder();
            sb.append(nombreAlojamiento).append(" - ");
            sb.append(valoracion).append(" estrella").append(valoracion != 1 ? "s" : "").append(" - ");
            sb.append(comentario);
            sb.append(" (").append(fecha).append(")");
            return sb.toString();
        }

        // Getters
        public UUID getIdReview() { return idReview; }
        public String getNombreAlojamiento() { return nombreAlojamiento; }
        public int getValoracion() { return valoracion; }
        public String getComentario() { return comentario; }
        public String getFecha() { return fecha; }
    }
}