package co.edu.uniquindio.proyectofinalhotelfx.Controladores.ControladoresAdm;

import java.net.URL;
import java.util.ResourceBundle;

import co.edu.uniquindio.proyectofinalhotelfx.Controladores.ControladorPrincipal;
import co.edu.uniquindio.proyectofinalhotelfx.Modelo.Entidades.Alojamiento;
import co.edu.uniquindio.proyectofinalhotelfx.Modelo.Entidades.Cliente;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class GestionUsuariosAdmin {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableColumn<Cliente, Boolean> colActivo;

    @FXML
    private TableColumn<Cliente, String> colCedula;

    @FXML
    private TableColumn<Cliente, String> colNombre;

    @FXML
    private TableColumn<Cliente, String> colTelefono;

    @FXML
    private TableColumn<Cliente, String> coldCorreo;

    @FXML
    private TableView<Cliente> tblCliente;

    ControladorPrincipal controladorPrincipal = ControladorPrincipal.getInstancia();
    private ObservableList<Cliente> listaClientes = FXCollections.observableArrayList();

    @FXML
    void eliminar(ActionEvent event) throws Exception {
        try {
            String id = tblCliente.getSelectionModel().getSelectedItem().getCedula();
            controladorPrincipal.getPlataforma().bloquearCliente(id);
            actualizarTabla();
        }
        catch (Exception e) {
            e.printStackTrace();
        }


    }

    @FXML
    void initialize() {
        configurarColumnas();
        actualizarTabla();


    }

    public void configurarColumnas(){
        colNombre.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNombre()));
        colTelefono.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTelefono()));
        colCedula.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCedula()));
        coldCorreo.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCorreo()));
        colActivo.setCellValueFactory(cellData -> new SimpleBooleanProperty(cellData.getValue().isActivo()));

    }

    public void actualizarTabla() {
        listaClientes.setAll(ControladorPrincipal.getInstancia().getPlataforma().getClienteRepository().obtenerTodos());
        tblCliente.setItems(listaClientes);
        tblCliente.refresh();
    }

}