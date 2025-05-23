package co.edu.uniquindio.proyectofinalhotelfx.Controladores.ControladoresCliente;

import co.edu.uniquindio.proyectofinalhotelfx.Modelo.Entidades.*;
import co.edu.uniquindio.proyectofinalhotelfx.Singleton.SesionUsuario;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.File;

public class InformacionImagenCliente {

    public FlowPane habitacionesFlowPane;
    @FXML private ImageView imagenAlojamiento;
    @FXML private Label lblNombre;
    @FXML private Label lblCiudad;
    @FXML private Label lblTipo;
    @FXML private Label lblPrecio;
    @FXML private Label lblDescripcion;
    @FXML private Button btnAgendar;
    @FXML private VBox contenedorPrincipal;

    private Alojamiento alojamiento;
    private Oferta oferta;
    private SesionUsuario sesionUsuario = SesionUsuario.instancia();


    public void setDatos(Alojamiento alojamiento) {
        this.alojamiento = alojamiento;
        cargarDatosEnPantalla(); // Llenas los labels, etc.
    }

    private void cargarDatosEnPantalla() {
    }

    @FXML
    void initialize() {
        // Configurar el botón de agendar
        btnAgendar.setOnAction(event -> agendarAlojamiento());
    }

    public void setAlojamiento(Alojamiento alojamiento) {
        this.alojamiento = alojamiento;
        cargarDatosAlojamiento();
    }
    public void setOferta(Oferta oferta) {
        this.oferta = oferta;
        cargarDatosOferta();
    }

    private void cargarDatosOferta() {
        if (oferta != null) {
            // Cargar imagen solo si ruta no es nula ni vacía y archivo existe
            String rutaImagen = oferta.getImagen();
            if (rutaImagen != null && !rutaImagen.isEmpty()) {
                File file = new File(rutaImagen);
                if (file.exists()) {
                    Image imagen = new Image(file.toURI().toString());
                    imagenAlojamiento.setImage(imagen);
                } else {
                    imagenAlojamiento.setImage(null); // o una imagen por defecto
                }
            } else {
                imagenAlojamiento.setImage(null);
            }

            lblNombre.setText(oferta.getNombre());
            lblDescripcion.setText("Descripción: " + oferta.getDescripcion());

            // Limpiar antes de agregar datos al FlowPane
            habitacionesFlowPane.getChildren().clear();


                }
            }





    private void cargarDatosAlojamiento() {
        if (alojamiento != null) {
            String rutaImagen = alojamiento.getImagen();
            File file = new File(rutaImagen);
            Image imagen = new Image(file.toURI().toString());

            // Configurar la imagen en la interfaz
            imagenAlojamiento.setImage(imagen);
            lblNombre.setText(alojamiento.getNombre());
            lblCiudad.setText("Ciudad: " + alojamiento.getCiudad().name());
            lblTipo.setText("Tipo: " + alojamiento.getTipoAlojamiento().name());
            lblPrecio.setText(String.format("Precio: %.2f COP por noche", alojamiento.getPrecioNoche()));
            lblDescripcion.setText("Descripción: " + alojamiento.getDescripcion());

            if(alojamiento instanceof Hotel){

                for (Habitacion habitacion : ((Hotel) alojamiento).getHabitaciones()) {
                    VBox tarjeta = new VBox();
                    tarjeta.setSpacing(5);
                    tarjeta.setPadding(new Insets(10));
                    tarjeta.setStyle("-fx-border-color: #ccc; -fx-border-radius: 10; -fx-background-radius: 10; -fx-background-color: #f9f9f9;");


                    ImageView imagenView = new ImageView();
                    if (habitacion.getImagen() != null && !habitacion.getImagen().isEmpty()) {
                        imagenView.setImage(new Image("file:" + habitacion.getImagen()));
                    }
                    imagenView.setFitWidth(150);
                    imagenView.setFitHeight(100);
                    imagenView.setPreserveRatio(true);


                    Label nombreLabel = new Label("Nombre: " + habitacion.getNumero());
                    Label tipoLabel = new Label("Tipo: " + habitacion.getTipoHabitacion());
                    Label capacidadLabel = new Label("Capacidad: " + habitacion.getCapacidad() + " personas");
                    Label precioLabel = new Label("Precio: $" + habitacion.getPrecioPorNoche());

                    tarjeta.getChildren().addAll(imagenView, nombreLabel, tipoLabel, capacidadLabel, precioLabel);

                    habitacionesFlowPane.getChildren().add(tarjeta);
                }

            }
        }
    }

    private void agendarAlojamiento() {
        if(sesionUsuario.getUsuario() == null) {
            // 1. Mostrar mensaje de confirmación
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Acción requerida");
            alert.setHeaderText(null);
            alert.setContentText("Por favor inicia sesión para agendar este alojamiento");

            // 2. Esperar a que el usuario cierre el mensaje
            alert.showAndWait();

            // 3. Redirigir al login después de cerrar el mensaje
            try {
                // Cerrar la ventana actual de detalles
                Stage stageActual = (Stage) btnAgendar.getScene().getWindow();
                stageActual.close();

                // Abrir la ventana de Login
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/co/edu/uniquindio/proyectofinalhotelfx/Login.fxml"));
                Parent root = loader.load();

                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.setTitle("Iniciar Sesión");
                stage.show();

            } catch (Exception e) {
                e.printStackTrace();
                System.err.println("Error al abrir la ventana de Login: " + e.getMessage());
            }
        }else{
            agendarReserva();
        }
    }

    private void mostrarAlerta(String mensaje, Alert.AlertType tipo) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle("Alerta");
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }

    @FXML
    private void agendarReserva() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(
                    "/co/edu/uniquindio/proyectofinalhotelfx/AgendarReservaCliente.fxml"));
            Parent root = loader.load();

            AgendarReservaCliente controller = loader.getController();
            controller.setDatos(alojamiento);  // LE PASAS AMBOS

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Agendar reserva");
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
            mostrarAlerta("No se pudo cargar la ventana de agendar reserva", Alert.AlertType.ERROR);
        }
    }

}
