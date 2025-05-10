package co.edu.uniquindio.proyectofinalhotelfx.Controladores.ControladoresCliente;

import co.edu.uniquindio.proyectofinalhotelfx.Modelo.Entidades.Alojamiento;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

public class InformacionImagenCliente {

    @FXML private ImageView imagenAlojamiento;
    @FXML private Label lblNombre;
    @FXML private Label lblCiudad;
    @FXML private Label lblTipo;
    @FXML private Label lblPrecio;
    @FXML private Label lblDescripcion;
    @FXML private Button btnAgendar;
    @FXML private VBox contenedorPrincipal;

    private Alojamiento alojamiento;

    @FXML
    void initialize() {
        // Configurar el botón de agendar
        btnAgendar.setOnAction(event -> agendarAlojamiento());
    }

    public void setAlojamiento(Alojamiento alojamiento) {
        this.alojamiento = alojamiento;
        cargarDatosAlojamiento();
    }

    private void cargarDatosAlojamiento() {
        if(alojamiento != null) {
            imagenAlojamiento.setImage(alojamiento.getImagen());
            lblNombre.setText(alojamiento.getNombre());
            lblCiudad.setText("Ciudad: " + alojamiento.getCiudad().name());
            lblTipo.setText("Tipo: " + alojamiento.getTipoAlojamiento().name());
            lblPrecio.setText(String.format("Precio: %.2f COP por noche", alojamiento.getPrecioPorNocheBase()));
            lblDescripcion.setText("Descripción: " + alojamiento.getDescripcion());
        }
    }

    private void agendarAlojamiento() {
        // Lógica para agendar el alojamiento
        System.out.println("Alojamiento agendado: " + alojamiento.getNombre());
        // Aquí puedes implementar la lógica para guardar la reserva
    }
}
