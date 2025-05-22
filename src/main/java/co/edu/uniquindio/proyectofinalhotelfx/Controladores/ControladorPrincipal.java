package co.edu.uniquindio.proyectofinalhotelfx.Controladores;

import co.edu.uniquindio.proyectofinalhotelfx.Modelo.Entidades.Usuario;
import co.edu.uniquindio.proyectofinalhotelfx.Modelo.Entidades.Reserva;
import co.edu.uniquindio.proyectofinalhotelfx.Servicios.Plataforma;
import co.edu.uniquindio.proyectofinalhotelfx.Singleton.SesionUsuario;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import lombok.Getter;

import java.util.List;

@Getter
public class ControladorPrincipal {

    private static ControladorPrincipal instancia;

    private final Plataforma plataforma;

    // NUEVO: Lista observable para reservas
    private final ObservableList<Reserva> listaReservas = FXCollections.observableArrayList();


    private ControladorPrincipal(){
        this.plataforma = new Plataforma() {
            @Override
            public List<Reserva> obtenerReservasPorCliente(String cedula) throws Exception {
                return List.of();
            }
        };
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

    public void guardarSesion(Usuario usuario){
        SesionUsuario.instancia().iniciarSesion(usuario);
    }

    public Usuario obtenerSesion(){
        return SesionUsuario.instancia().getUsuario();
    }
    public void cargarReservas() {
        try {
            List<Reserva> reservas = plataforma.getServicioReserva().listarReservas();
            // Si tienes una lista observable global, actualizarla aquí
            if (listaReservas != null) {
                listaReservas.setAll(reservas);
            }
        } catch (Exception e) {
            System.err.println("Error al cargar reservas: " + e.getMessage());
        }
    }
}
