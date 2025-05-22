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

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import co.edu.uniquindio.proyectofinalhotelfx.Controladores.ControladorPrincipal;
import co.edu.uniquindio.proyectofinalhotelfx.Modelo.Entidades.Cliente;

/**
 * Controlador para la gestión de usuarios por parte del administrador
 */
public class GestionUsuariosAdmin {

    // Componentes FXML
    @FXML private ResourceBundle resources;
    @FXML private URL location;
    @FXML private TableColumn<Cliente, Boolean> colActivo;
    @FXML private TableColumn<Cliente, String> colCedula;
    @FXML private TableColumn<Cliente, String> colNombre;
    @FXML private TableColumn<Cliente, String> colTelefono;
    @FXML private TableColumn<Cliente, String> coldCorreo;
    @FXML private TableView<Cliente> tblCliente;

    // Variables de instancia
    private final ControladorPrincipal controladorPrincipal = ControladorPrincipal.getInstancia();
    private final ObservableList<Cliente> listaClientes = FXCollections.observableArrayList();

    /**
     * Inicializa el controlador configurando las columnas y cargando los datos
     */
    @FXML
    void initialize() {
        configurarColumnas();
        actualizarTabla();
    }

    /**
     * Elimina/bloquea el cliente seleccionado
     */
    @FXML
    void eliminar(ActionEvent event) {
        try {
            Cliente seleccionado = tblCliente.getSelectionModel().getSelectedItem();
            if (seleccionado != null) {
                controladorPrincipal.getPlataforma().bloquearCliente(seleccionado.getCedula());
                actualizarTabla();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Configura las columnas de la tabla de clientes
     */
    public void configurarColumnas() {
        colNombre.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNombre()));
        colTelefono.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTelefono()));
        colCedula.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCedula()));
        coldCorreo.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCorreo()));
        colActivo.setCellValueFactory(cellData -> new SimpleBooleanProperty(cellData.getValue().isActivo()));
    }

    /**
     * Actualiza los datos mostrados en la tabla
     */
    public void actualizarTabla() {
        listaClientes.setAll(controladorPrincipal.getPlataforma().getClienteRepository().obtenerTodos());
        tblCliente.setItems(listaClientes);
        tblCliente.refresh();
    }
}