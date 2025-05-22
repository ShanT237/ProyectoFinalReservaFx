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

    // ✅ Corrección: Obtener correctamente la instancia de Plataforma
    private Plataforma plataforma = ControladorPrincipal.getInstancia().getPlataforma();
    private ObservableList<ReviewData> listaResenas = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        configurarTabla();
        cargarResenas();
        configurarEventos();
    }

    private void configurarTabla() {
        colResena.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getDescripcionCompleta())
        );

        tblResenas.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        tblResenas.setItems(listaResenas);
    }

    private void configurarEventos() {
        btnEliminarResena.setOnAction(event -> eliminarResenaSeleccionada());
    }

    private void cargarResenas() {
        try {
            listaResenas.clear();

            // ✅ Corrección: Obtener cédula del cliente actual desde la sesión
            String cedulaCliente = ControladorPrincipal.getInstancia().obtenerSesion().getCedula();

            List<Reserva> reservasCliente = plataforma.obtenerReservasPorCliente(cedulaCliente);

            for (Reserva reserva : reservasCliente) {
                Review review = reserva.getReview();
                if (review != null) {
                    ReviewData reviewData = new ReviewData(
                            review.getCodigo(),
                            reserva.getAlojamiento().getNombre(),
                            review.getValoracion(),
                            review.getComentario(),
                            review.getFecha().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"))
                    );
                    listaResenas.add(reviewData);
                }
            }

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

        Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
        confirmacion.setTitle("Confirmar eliminación");
        confirmacion.setHeaderText("¿Estás seguro de eliminar esta reseña?");
        confirmacion.setContentText("Esta acción no se puede deshacer.");

        if (confirmacion.showAndWait().orElse(ButtonType.CANCEL) == ButtonType.OK) {
            try {
                plataforma.eliminarResena(resenaSeleccionada.getIdReview());
                cargarResenas();
                mostrarMensaje("Éxito", "Reseña eliminada correctamente.", Alert.AlertType.INFORMATION);
            } catch (Exception e) {
                mostrarMensaje("Error", "Error al eliminar la reseña: " + e.getMessage(), Alert.AlertType.ERROR);
            }
        }
    }

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
            return nombreAlojamiento + " - " +
                    valoracion + " estrella" + (valoracion != 1 ? "s" : "") + " - " +
                    comentario + " (" + fecha + ")";
        }

        public UUID getIdReview() { return idReview; }
        public String getNombreAlojamiento() { return nombreAlojamiento; }
        public int getValoracion() { return valoracion; }
        public String getComentario() { return comentario; }
        public String getFecha() { return fecha; }
    }
}
