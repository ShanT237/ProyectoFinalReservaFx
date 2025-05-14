package co.edu.uniquindio.proyectofinalhotelfx.Controladores.ControladoresCliente;


import co.edu.uniquindio.proyectofinalhotelfx.Modelo.Entidades.Alojamiento;
import co.edu.uniquindio.proyectofinalhotelfx.Modelo.Entidades.Cliente;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

public class AgendarReservaCliente{

    private Cliente clienteActual;
    private Alojamiento alojamiento;

    public void setDatos(Cliente cliente, Alojamiento alojamiento) {
        this.clienteActual = cliente;
        this.alojamiento = alojamiento;
        // Puedes usar esto para mostrar datos y procesar reserva
    }
    public void confirmarReserva(ActionEvent actionEvent) {
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
            controller.setDatos(clienteActual, alojamiento);  // <--- Asegúrate que clienteActual y alojamiento no son null

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Agendar Reserva");
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
            mostrarAlerta("No se pudo cargar la ventana de agendar reserva", Alert.AlertType.ERROR);
        }

    }

}
