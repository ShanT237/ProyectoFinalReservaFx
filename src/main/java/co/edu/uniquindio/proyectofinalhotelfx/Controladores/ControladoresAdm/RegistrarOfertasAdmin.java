package co.edu.uniquindio.proyectofinalhotelfx.Controladores.ControladoresAdm;

import java.net.URL;
import java.util.Comparator;
import java.util.ResourceBundle;

import co.edu.uniquindio.proyectofinalhotelfx.Modelo.Enums.Ciudad;
import co.edu.uniquindio.proyectofinalhotelfx.Modelo.Enums.OfertaTipo;
import co.edu.uniquindio.proyectofinalhotelfx.Modelo.Enums.TipoAlojamiento;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.util.StringConverter;

public class RegistrarOfertasAdmin {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<TipoAlojamiento> alojamientoBox;

    @FXML
    private ComboBox<Ciudad> ciudadBox;

    @FXML
    private TextField descripcionField;

    @FXML
    private CheckBox esGlobal;

    @FXML
    private ComboBox<Double> descuentoField;

    @FXML
    private DatePicker fechaFin;

    @FXML
    private DatePicker fechaInicio;

    @FXML
    private TextField idField;

    @FXML
    private TextField nombreField;

    @FXML
    private Slider sliderNochesMin;

    @FXML
    private ComboBox<OfertaTipo> tipoOferta;

    @FXML
    void guardarOferta(ActionEvent event) {
        try {

            // Obtener valores
            boolean global = esGlobal.isSelected();
            Ciudad ciudad = global ? null : ciudadBox.getValue();
            TipoAlojamiento tipoAlojamiento = global ? null : alojamientoBox.getValue();
            double descuento = descuentoField.getValue();
            OfertaTipo oferta = tipoOferta.getValue();
            int nochesMinimas = (int) sliderNochesMin.getValue();




            mostrarMensaje("¡Oferta registrada exitosamente!");

        } catch (Exception e) {
            mostrarError("Error al guardar la oferta: " + e.getMessage());
        }
    }

    @FXML
    void initialize() {
        configurarComboDescuentos();
        configurarSliders();
        configurarComboCiudad();
        configurarComboTipoAlojamiento();
        configurarComboTipoOferta();
        condicionEsGlobal();
        agregarTooltipSlider(sliderNochesMin);

    }

    private void configurarComboDescuentos() {
        ObservableList<Double> descuentos = FXCollections.observableArrayList(
                0.05, 0.10, 0.15, 0.20, 0.25, 0.30, 0.40, 0.50
        );

        descuentoField.setEditable(true);
        descuentoField.setItems(descuentos);

        descuentoField.setConverter(new StringConverter<Double>() {
            @Override
            public String toString(Double object) {
                return object == null ? "" : String.format("%d%%", (int)(object * 100));
            }

            @Override
            public Double fromString(String string) {
                try {
                    String value = string.replace("%", "").trim();
                    if (value.isEmpty()) return 0.0;
                    double descuento = Double.parseDouble(value) / 100;
                    return Math.max(0.0, Math.min(1.0, descuento));
                } catch (NumberFormatException e) {
                    return 0.0;
                }
            }
        });

        descuentoField.getEditor().textProperty().addListener((obs, oldVal, newVal) -> {
            if (!newVal.matches("\\d{0,2}%?")) {
                descuentoField.getEditor().setText(oldVal);
            }
        });

        descuentoField.setValue(0.10);
    }

    private void agregarTooltipSlider(Slider slider) {
        Tooltip tooltip = new Tooltip();
        tooltip.setAutoHide(false);

        slider.setOnMousePressed(event -> {
            tooltip.setText(String.valueOf((int) slider.getValue()));
            Node node = (Node) event.getSource();
            Bounds bounds = node.localToScreen(node.getBoundsInLocal());
            tooltip.show(slider, bounds.getMinX() + event.getX(), bounds.getMinY() - 30);
        });

        slider.setOnMouseDragged(event -> {
            tooltip.setText(String.valueOf((int) slider.getValue()));
            Node node = (Node) event.getSource();
            Bounds bounds = node.localToScreen(node.getBoundsInLocal());
            tooltip.setX(bounds.getMinX() + event.getX());
            tooltip.setY(bounds.getMinY() - 30);
        });

        slider.setOnMouseReleased(event -> tooltip.hide());
    }

    private void configurarSliders() {

        sliderNochesMin.setMin(1);
        sliderNochesMin.setMax(100);
        sliderNochesMin.setMajorTickUnit(1);
        sliderNochesMin.setDisable(true);
    }

    private void configurarComboTipoAlojamiento() {
        // Agregar "NO_APLICA" como primer elemento
        ObservableList<TipoAlojamiento> tipos = FXCollections.observableArrayList(TipoAlojamiento.values());
        tipos.add(0, null); // null representará "No aplica"

        alojamientoBox.setItems(tipos);
        alojamientoBox.setConverter(new StringConverter<TipoAlojamiento>() {
            @Override
            public String toString(TipoAlojamiento object) {
                return object == null ? "No aplica" : object.toString();
            }

            @Override
            public TipoAlojamiento fromString(String string) {
                return string.equals("No aplica") ? null : TipoAlojamiento.valueOf(string);
            }
        });
        alojamientoBox.getSelectionModel().selectFirst(); // Seleccionar "No aplica" por defecto
    }

    private void configurarComboCiudad() {

        ObservableList<Ciudad> ciudades = FXCollections.observableArrayList(Ciudad.values());
        ciudades.sort(Comparator.comparing(Enum::name));
        ciudades.add(0, null);

        ciudadBox.setItems(ciudades);
        ciudadBox.setConverter(new StringConverter<Ciudad>() {
            @Override
            public String toString(Ciudad object) {
                return object == null ? "No aplica" : object.toString();
            }

            @Override
            public Ciudad fromString(String string) {
                return string.equals("No aplica") ? null : Ciudad.valueOf(string);
            }
        });
        ciudadBox.getSelectionModel().selectFirst();
    }
    private void configurarComboTipoOferta() {
        ObservableList<OfertaTipo> tiposOferta = FXCollections.observableArrayList(OfertaTipo.values());
        tipoOferta.setItems(tiposOferta);
        tipoOferta.setConverter(createEnumStringConverter());


        tipoOferta.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            boolean esEstadiaProlongada = newVal == OfertaTipo.ESTADIAPROLONGADA;
            sliderNochesMin.setDisable(!esEstadiaProlongada);

            if (!esEstadiaProlongada) {
                sliderNochesMin.setValue(sliderNochesMin.getMin());
            }
        });

        if (!tiposOferta.isEmpty()) {
            tipoOferta.getSelectionModel().selectFirst();
        }
    }

    private <T extends Enum<T>> StringConverter<T> createEnumStringConverter() {
        return new StringConverter<T>() {
            @Override
            public String toString(T object) {
                return object == null ? "" : object.toString();
            }

            @Override
            public T fromString(String string) {
                return string == null ? null : (T) Enum.valueOf((Class<T>) getClass().getEnclosingClass(), string);
            }
        };
    }

    public void condicionEsGlobal(){
        esGlobal.selectedProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal) {
                ciudadBox.setDisable(true);
                alojamientoBox.setDisable(true);

                ciudadBox.setUserData(ciudadBox.getValue());
                alojamientoBox.setUserData(alojamientoBox.getValue());

                ciudadBox.getSelectionModel().selectFirst();
                alojamientoBox.getSelectionModel().selectFirst();
            } else {
                ciudadBox.setDisable(false);
                alojamientoBox.setDisable(false);

                if (ciudadBox.getUserData() != null) {
                    ciudadBox.getSelectionModel().select((Ciudad) ciudadBox.getUserData());
                }
                if (alojamientoBox.getUserData() != null) {
                    alojamientoBox.getSelectionModel().select((TipoAlojamiento) alojamientoBox.getUserData());
                }
            }
        });
    }

    private void mostrarError(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    private void mostrarMensaje(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Éxito");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }



}