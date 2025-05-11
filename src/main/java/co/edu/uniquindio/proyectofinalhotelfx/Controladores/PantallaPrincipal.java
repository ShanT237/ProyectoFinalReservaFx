package co.edu.uniquindio.proyectofinalhotelfx.Controladores;

import co.edu.uniquindio.proyectofinalhotelfx.App;
import co.edu.uniquindio.proyectofinalhotelfx.Controladores.ControladoresCliente.InformacionImagenCliente;
import co.edu.uniquindio.proyectofinalhotelfx.Modelo.Entidades.Alojamiento;
import co.edu.uniquindio.proyectofinalhotelfx.Modelo.Enums.Ciudad;
import co.edu.uniquindio.proyectofinalhotelfx.Modelo.Enums.TipoAlojamiento;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.File;
import java.util.List;
import java.util.Objects;

    /**
     * Controlador FXML para la pantalla principal de la aplicación.
     * Maneja la búsqueda y visualización de alojamientos, así como la navegación
     * a otras pantallas del sistema.
     */
    public class PantallaPrincipal {

        // ---------------------------
        // Componentes de la interfaz
        // ---------------------------

        @FXML private ComboBox<Ciudad> cbCiudad; // Selector de ciudades
        @FXML private ComboBox<TipoAlojamiento> cbTipoAlojamiento; // Selector de tipos de alojamiento
        @FXML private TextField txtPrecioMax; // Campo para precio máximo
        @FXML private Button btnBuscar; // Botón de búsqueda
        @FXML private FlowPane flowAlojamientos; // Contenedor de resultados
        @FXML private Button btnLogin; // Botón para ir a login
        @FXML private Button btnRegistro; // Botón para ir a registro

        // ---------------------------
        // Atributos del controlador
        // ---------------------------

        private final ControladorPrincipal controladorPrincipal = ControladorPrincipal.getInstancia();
        private final List<Alojamiento> alojamientosList = controladorPrincipal.getPlataforma()
                .getServiciosAlojamiento()
                .getAlojamientoRepository()
                .obtenerTodos();
        private final ObservableList<Alojamiento> alojamientos = FXCollections.observableArrayList(alojamientosList);

        // ---------------------------
        // Métodos de inicialización
        // ---------------------------

        /**
         * Método de inicialización del controlador.
         * Configura los datos iniciales y los manejadores de eventos.
         */
        @FXML
        void initialize() {
            datosInicio();
            configurarEventos();
        }

        /**
         * Carga los datos iniciales para los componentes de la interfaz.
         */
        public void datosInicio() {
            cargarCiudades();
            cargarTiposAlojamiento();
        }

        private void cargarCiudades() {
            ObservableList<Ciudad> ciudades = FXCollections.observableArrayList(Ciudad.values());
            FXCollections.sort(ciudades, (c1, c2) -> c1.name().compareTo(c2.name()));
            cbCiudad.setItems(ciudades);
        }

        private void cargarTiposAlojamiento() {
            ObservableList<TipoAlojamiento> tipos = FXCollections.observableArrayList(TipoAlojamiento.values());
            cbTipoAlojamiento.setItems(tipos);
        }

        /**
         * Configura los manejadores de eventos para los botones.
         */
        private void configurarEventos() {
            btnBuscar.setOnAction(this::onBuscarClick);
            btnLogin.setOnAction(this::irLogin);
            btnRegistro.setOnAction(this::irRegistroCliente);
        }

        // ---------------------------
        // Lógica de búsqueda
        // ---------------------------

        /**
         * Maneja el evento de clic en el botón de búsqueda.
         * @param event Evento de acción
         */
        private void onBuscarClick(ActionEvent event) {
            Ciudad ciudadSeleccionada = cbCiudad.getValue();
            TipoAlojamiento tipoSeleccionado = cbTipoAlojamiento.getValue();
            String precioMaxText = txtPrecioMax.getText();
            double precioMax = Double.MAX_VALUE;

            if (verificarDatos(precioMaxText)) {
                if (!precioMaxText.isEmpty()) {
                    precioMax = Double.parseDouble(precioMaxText);
                }

                limpiarResultados();
                mostrarAlojamientos(ciudadSeleccionada, tipoSeleccionado, precioMax);
            }
        }

        /**
         * Muestra los alojamientos que coinciden con los criterios de búsqueda.
         * @param ciudadSeleccionada Ciudad seleccionada para filtrar
         * @param tipoSeleccionado Tipo de alojamiento seleccionado
         * @param precioMax Precio máximo para filtrar
         */
        public void mostrarAlojamientos(Ciudad ciudadSeleccionada, TipoAlojamiento tipoSeleccionado, double precioMax) {
            for (Alojamiento alojamiento : alojamientos) {
                if (cumpleCriterios(alojamiento, ciudadSeleccionada, tipoSeleccionado, precioMax)) {
                    flowAlojamientos.getChildren().add(crearTarjetaAlojamiento(alojamiento));
                }
            }
        }

        /**
         * Verifica si un alojamiento cumple con los criterios de búsqueda especificados.
         * @param alojamiento Alojamiento a evaluar
         * @param ciudad Ciudad solicitada (puede ser null para no filtrar por ciudad)
         * @param tipo Tipo de alojamiento solicitado (puede ser null para no filtrar por tipo)
         * @param precioMax Precio máximo permitido
         * @return true si el alojamiento cumple con todos los criterios no nulos, false de lo contrario
         */
        private boolean cumpleCriterios(Alojamiento alojamiento, Ciudad ciudad,
                                        TipoAlojamiento tipo, double precioMax) {
            // Filtro por ciudad
            if (ciudad != null && !alojamiento.getCiudad().equals(ciudad)) {
                return false;
            }

            // Filtro por tipo de alojamiento
            if (tipo != null && !alojamiento.getTipoAlojamiento().equals(tipo)) {
                return false;
            }

            // Filtro por precio
            if (alojamiento.getPrecioPorNocheBase() > precioMax) {
                return false;
            }

            return true;
        }

        /**
         * Crea una tarjeta visual para un alojamiento.
         * @param alojamiento Alojamiento a mostrar
         * @return VBox con la representación visual del alojamiento
         */
        private VBox crearTarjetaAlojamiento(Alojamiento alojamiento) {
            VBox tarjeta = new VBox(10);
            tarjeta.setStyle("-fx-padding: 10; -fx-border-color: #ccc; -fx-border-radius: 10; -fx-background-color: #f9f9f9;");
            tarjeta.setPrefWidth(500);

            HBox contenido = new HBox(10);
            contenido.setStyle("-fx-alignment: center-left;");

            ImageView imagen = new ImageView(alojamiento.getImagen());
            imagen.setFitWidth(150);
            imagen.setFitHeight(100);
            imagen.setPreserveRatio(true);

            // Agregar el evento de clic a la imagen
            imagen.setOnMouseClicked(event -> {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/co/edu/uniquindio/proyectofinalhotelfx/InformacionImagenCliente.fxml"));
                    Parent root = loader.load();

                    // Obtener el controlador y pasar los datos del alojamiento
                    InformacionImagenCliente controller = loader.getController();
                    controller.setAlojamiento(alojamiento);

                    Stage stage = new Stage();
                    File archivoImagen = new File("Img/ImagenesApp/icon.png");
                    Image icono = new Image(archivoImagen.toURI().toString());
                    stage.getIcons().add(icono);
                    stage.setScene(new Scene(root));
                    stage.setTitle("Detalles del alojamiento");
                    stage.show();

                } catch (Exception e) {
                    e.printStackTrace();
                    mostrarError("No se pudo cargar la ventana de detalles");
                }
            });

            VBox detalles = new VBox(5);
            detalles.getChildren().addAll(
                    new Label(alojamiento.getNombre()) {{
                        setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");
                    }},
                    new Label(alojamiento.getCiudad().name()),
                    new Label(String.format("%.2f COP por noche", alojamiento.getPrecioPorNocheBase()))
            );

            contenido.getChildren().addAll(imagen, detalles);
            tarjeta.getChildren().add(contenido);

            return tarjeta;
        }
        // ---------------------------
        // Métodos de navegación
        // ---------------------------

        /**
         * Navega a la pantalla de inicio de sesión.
         */
        public void irLogin(ActionEvent actionEvent) {
            navegarVentana("/co/edu/uniquindio/proyectofinalhotelfx/Login.fxml", "BookYourStay - Iniciar Sesión");
        }

        /**
         * Navega a la pantalla de registro de cliente.
         */
        public void irRegistroCliente(ActionEvent actionEvent) {
            navegarVentana("/co/edu/uniquindio/proyectofinalhotelfx/RegistroCliente.fxml", "BookYourStay - Registro");
        }

        /**
         * Método genérico para navegar a otras ventanas.
         * @param nombreArchivoFxml Ruta del archivo FXML
         * @param tituloVentana Título de la ventana
         */
        private void navegarVentana(String nombreArchivoFxml, String tituloVentana) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource(nombreArchivoFxml));
                Parent root = loader.load();
                Stage stage = new Stage();
                File archivoImagen = new File("Img/ImagenesApp/icon.png");
                Image icono = new Image(archivoImagen.toURI().toString());
                stage.getIcons().add(icono);
                stage.setScene(new Scene(root));

                stage.setResizable(false);
                stage.setTitle(tituloVentana);
                stage.show();

                cerrarVentanaActual();
            } catch (Exception e) {
                mostrarError("No se pudo cargar la ventana: " + e.getMessage());
                e.printStackTrace();
            }
        }

        // ---------------------------
        // Métodos de utilidad
        // ---------------------------

        /**
         * Verifica que los datos de búsqueda sean válidos.
         * @return true si los datos son válidos, false en caso contrario
         */
        public boolean verificarDatos(String precioTexto) {
            if (!precioTexto.isEmpty()) {
                try {
                    Double.parseDouble(precioTexto);
                } catch (NumberFormatException e) {
                    mostrarAdvertencia("Precio máximo debe ser numérico");
                    return false;
                }
            }

            if (cbCiudad.getValue() == null && cbTipoAlojamiento.getValue() == null && precioTexto.isEmpty()) {
                mostrarAdvertencia("Debe seleccionar al menos un criterio de búsqueda.");
                return false;
            }

            return true;
        }

        /**
         * Limpia los resultados de búsqueda anteriores.
         */
        public void limpiarResultados() {
            flowAlojamientos.getChildren().clear();
        }

        /**
         * Cierra la ventana actual.
         */
        private void cerrarVentanaActual() {
            ((Stage) btnRegistro.getScene().getWindow()).close();
        }

        /**
         * Muestra una alerta de error.
         */
        private void mostrarError(String mensaje) {
            crearAlerta(mensaje, Alert.AlertType.ERROR);
        }

        /**
         * Muestra una alerta de advertencia.
         */
        private void mostrarAdvertencia(String mensaje) {
            crearAlerta(mensaje, Alert.AlertType.WARNING);
        }

        /**
         * Crea y muestra una alerta.
         */
        private void crearAlerta(String mensaje, Alert.AlertType tipo) {
            new Alert(tipo, mensaje) {{
                setTitle("Alerta");
                setHeaderText(null);
            }}.showAndWait();
        }
    }
