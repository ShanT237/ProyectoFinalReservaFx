package co.edu.uniquindio.proyectofinalhotelfx.Controladores.ControladoresAdm;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

import java.io.IOException;

public class GestionOfertasAdmin {

    @FXML
    private TableView<?> tblOfertas;

    @FXML
    private TableColumn<?, ?> colCodigo, colDescripcion, colPorcentaje, colFechaInicio, colFechaFin;

    @FXML
    public void btnAgregarOferta(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/co/edu/uniquindio/proyectofinalhotelfx/FXMLDW(ADMIN)/RegistrarOfertas.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Registrar Nueva Oferta");
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    public void eliminarOferta(ActionEvent event) {
        System.out.println("Eliminar oferta");
    }

    @FXML
    public void actualizarOferta(ActionEvent event) {
        System.out.println("Actualizar oferta");
    }
}
