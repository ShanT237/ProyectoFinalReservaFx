package co.edu.uniquindio.proyectofinalhotelfx.Controladores;


import co.edu.uniquindio.proyectofinalhotelfx.Servicios.Plataforma;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import lombok.Getter;

@Getter
public class ControladorPrincipal {

    private static ControladorPrincipal instancia;

    private final Plataforma plataforma;


    private ControladorPrincipal(){
        this.plataforma = new Plataforma();
    }


    public static ControladorPrincipal getInstancia(){
        if(instancia == null){
            instancia = new ControladorPrincipal();
        }
        return instancia;
    }


    public void cerrarVentana(Node node){
        Stage stage = (Stage) node.getScene().getWindow();
        stage.close();
    }


    public void crearAlerta(String mensaje, Alert.AlertType tipo){
        Alert alert = new Alert(tipo);
        alert.setTitle("Alerta");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

}


