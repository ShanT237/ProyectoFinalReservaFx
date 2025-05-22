package co.edu.uniquindio.proyectofinalhotelfx.Controladores.ControladoresAdm;

import co.edu.uniquindio.proyectofinalhotelfx.Controladores.ControladorPrincipal;
import co.edu.uniquindio.proyectofinalhotelfx.Modelo.Entidades.Alojamiento;
import co.edu.uniquindio.proyectofinalhotelfx.Modelo.Enums.Ciudad;
import co.edu.uniquindio.proyectofinalhotelfx.Servicios.Plataforma;
import co.edu.uniquindio.proyectofinalhotelfx.vo.TipoAlojamientoGanancia;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.chart.*;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

public class EstadisticasAdmin implements Initializable {

    @FXML private StackPane contenidoDinamico;

    private final ControladorPrincipal controladorPrincipal = ControladorPrincipal.getInstancia();
    private final Plataforma plataforma = controladorPrincipal.getPlataforma();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Mostrar mensaje inicial
        mostrarMensajeInicial();
    }

    private void mostrarMensajeInicial() {
        Label mensaje = new Label("Seleccione una opción para ver las estadísticas");
        mensaje.setStyle("-fx-font-size: 16px; -fx-text-fill: #666;");
        contenidoDinamico.getChildren().clear();
        contenidoDinamico.getChildren().add(mensaje);
    }

    @FXML
    void estadisticasAlojamiento(ActionEvent event) {
        try {
            VBox contenedor = new VBox(15);
            contenedor.setPadding(new Insets(20));

            // Título
            Label titulo = new Label("Estadísticas por Alojamiento");
            titulo.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

            // ComboBox para seleccionar alojamiento
            ComboBox<Alojamiento> comboAlojamientos = new ComboBox<>();
            comboAlojamientos.setPromptText("Seleccione un alojamiento");
            comboAlojamientos.setPrefWidth(300);

            // no sé q haver con esto List<Alojamiento> alojamientos = plataforma.getServicioAlojamiento().obtenerTodosAlojamientos();
            // idk comboAlojamientos.getItems().addAll(alojamientos);

            // Configurar el display del ComboBox
            comboAlojamientos.setCellFactory(param -> new ListCell<Alojamiento>() {
                @Override
                protected void updateItem(Alojamiento item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty || item == null) {
                        setText(null);
                    } else {
                        setText(item.getNombre() + " - " + item.getCiudad());
                    }
                }
            });

            comboAlojamientos.setButtonCell(new ListCell<Alojamiento>() {
                @Override
                protected void updateItem(Alojamiento item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty || item == null) {
                        setText(null);
                    } else {
                        setText(item.getNombre() + " - " + item.getCiudad());
                    }
                }
            });

            // Container para los gráficos
            VBox graficosContainer = new VBox(20);

            // Event handler para cuando se selecciona un alojamiento
            comboAlojamientos.setOnAction(e -> {
                Alojamiento seleccionado = comboAlojamientos.getValue();
                if (seleccionado != null) {
                    mostrarEstadisticasAlojamiento(seleccionado, graficosContainer);
                }
            });

            contenedor.getChildren().addAll(titulo, comboAlojamientos, graficosContainer);

            contenidoDinamico.getChildren().clear();
            contenidoDinamico.getChildren().add(contenedor);

        } catch (Exception e) {
            mostrarError("Error al cargar estadísticas de alojamientos: " + e.getMessage());
        }
    }

    private void mostrarEstadisticasAlojamiento(Alojamiento alojamiento, VBox container) {
        container.getChildren().clear();

        try {
            // Obtener datos
            int totalReservas = plataforma.getServicioReserva().contarReservasPorAlojamiento(alojamiento.getId());
            double ocupacion = plataforma.getServicioAdm().calcularOcupacionAlojamiento(alojamiento.getId());
            double ganancias = plataforma.getServicioAdm().calcularGananciasTotales(alojamiento.getId());

            // Gráfico de barras para estadísticas generales
            CategoryAxis xAxis = new CategoryAxis();
            NumberAxis yAxis = new NumberAxis();
            BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis);
            barChart.setTitle("Estadísticas - " + alojamiento.getNombre());
            barChart.setPrefHeight(300);

            XYChart.Series<String, Number> series = new XYChart.Series<>();
            series.setName("Valores");
            series.getData().add(new XYChart.Data<>("Reservas", totalReservas));
            series.getData().add(new XYChart.Data<>("Ocupación %", ocupacion));
            series.getData().add(new XYChart.Data<>("Ganancias", ganancias));

            barChart.getData().add(series);

            // Gráfico circular para ocupación
            PieChart pieChart = new PieChart();
            pieChart.setTitle("Ocupación vs Disponibilidad");
            pieChart.setPrefHeight(300);

            double disponibilidad = 100 - ocupacion;
            ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
                    new PieChart.Data("Ocupado (" + String.format("%.1f", ocupacion) + "%)", ocupacion),
                    new PieChart.Data("Disponible (" + String.format("%.1f", disponibilidad) + "%)", disponibilidad)
            );
            pieChart.setData(pieChartData);

            container.getChildren().addAll(barChart, pieChart);

        } catch (Exception e) {
            Label error = new Label("Error al cargar estadísticas: " + e.getMessage());
            error.setStyle("-fx-text-fill: red;");
            container.getChildren().add(error);
        }
    }

    @FXML
    void alojamientosPopulares(ActionEvent event) {
        try {
            VBox contenedor = new VBox(15);
            contenedor.setPadding(new Insets(20));

            Label titulo = new Label("Alojamientos Más Populares por Ciudad");
            titulo.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

            // ComboBox para seleccionar ciudad
            ComboBox<Ciudad> comboCiudades = new ComboBox<>();
            comboCiudades.setPromptText("Seleccione una ciudad");
            comboCiudades.getItems().addAll(Ciudad.values());

            VBox graficosContainer = new VBox(20);

            comboCiudades.setOnAction(e -> {
                Ciudad ciudadSeleccionada = comboCiudades.getValue();
                if (ciudadSeleccionada != null) {
                    mostrarAlojamientosPopulares(ciudadSeleccionada, graficosContainer);
                }
            });

            contenedor.getChildren().addAll(titulo, comboCiudades, graficosContainer);

            contenidoDinamico.getChildren().clear();
            contenidoDinamico.getChildren().add(contenedor);

        } catch (Exception e) {
            mostrarError("Error al cargar alojamientos populares: " + e.getMessage());
        }
    }

    private void mostrarAlojamientosPopulares(Ciudad ciudad, VBox container) {
        container.getChildren().clear();

        try {
            List<Alojamiento> alojamientosPopulares = plataforma.getServicioAdm()
                    .obtenerAlojamientosMasPopulares(ciudad);

            if (alojamientosPopulares.isEmpty()) {
                Label mensaje = new Label("No hay datos de alojamientos para esta ciudad");
                mensaje.setStyle("-fx-font-size: 14px; -fx-text-fill: #666;");
                container.getChildren().add(mensaje);
                return;
            }

            // Gráfico de barras horizontales
            CategoryAxis yAxis = new CategoryAxis();
            NumberAxis xAxis = new NumberAxis();
            BarChart<Number, String> barChart = new BarChart<>(xAxis, yAxis);
            barChart.setTitle("Alojamientos Más Populares en " + ciudad);
            barChart.setPrefHeight(400);

            XYChart.Series<Number, String> series = new XYChart.Series<>();
            series.setName("Número de Reservas");

            // Tomar solo los primeros 10 para que el gráfico sea legible
            int limite = Math.min(10, alojamientosPopulares.size());
            for (int i = 0; i < limite; i++) {
                Alojamiento alojamiento = alojamientosPopulares.get(i);
                int reservas = plataforma.getServicioReserva()
                        .contarReservasPorAlojamiento(alojamiento.getId());
                series.getData().add(new XYChart.Data<>(reservas, alojamiento.getNombre()));
            }

            barChart.getData().add(series);
            container.getChildren().add(barChart);

        } catch (Exception e) {
            Label error = new Label("Error al cargar datos: " + e.getMessage());
            error.setStyle("-fx-text-fill: red;");
            container.getChildren().add(error);
        }
    }

    @FXML
    void alojamientosRentables(ActionEvent event) {
        try {
            VBox contenedor = new VBox(15);
            contenedor.setPadding(new Insets(20));

            Label titulo = new Label("Tipos de Alojamiento Más Rentables");
            titulo.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

            // Obtener datos de rentabilidad
            List<TipoAlojamientoGanancia> tiposRentables = plataforma.getServicioAdm()
                    .obtenerTiposAlojamientoMasRentables();

            if (tiposRentables.isEmpty()) {
                Label mensaje = new Label("No hay datos de rentabilidad disponibles");
                mensaje.setStyle("-fx-font-size: 14px; -fx-text-fill: #666;");
                contenedor.getChildren().add(mensaje);
            } else {
                // Gráfico de barras
                CategoryAxis xAxis = new CategoryAxis();
                NumberAxis yAxis = new NumberAxis();
                BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis);
                barChart.setTitle("Ganancias por Tipo de Alojamiento");
                barChart.setPrefHeight(400);

                XYChart.Series<String, Number> series = new XYChart.Series<>();
                series.setName("Ganancias Totales");

                for (TipoAlojamientoGanancia tipo : tiposRentables) {
                    series.getData().add(new XYChart.Data<>(
                            tipo.getTipo().toString(),
                            tipo.getGananciaTotal()
                    ));
                }

                barChart.getData().add(series);

                // Gráfico circular
                PieChart pieChart = new PieChart();
                pieChart.setTitle("Distribución de Ganancias por Tipo");
                pieChart.setPrefHeight(400);

                ObservableList<PieChart.Data> pieData = FXCollections.observableArrayList();
                for (TipoAlojamientoGanancia tipo : tiposRentables) {
                    if (tipo.getGananciaTotal() > 0) {
                        pieData.add(new PieChart.Data(
                                tipo.getTipo().toString() + " ($" + String.format("%.0f", tipo.getGananciaTotal()) + ")",
                                tipo.getGananciaTotal()
                        ));
                    }
                }
                pieChart.setData(pieData);

                contenedor.getChildren().addAll(barChart, pieChart);
            }

            contenidoDinamico.getChildren().clear();
            contenidoDinamico.getChildren().add(contenedor);

        } catch (Exception e) {
            mostrarError("Error al cargar tipos rentables: " + e.getMessage());
        }
    }

    private void mostrarError(String mensaje) {
        Label error = new Label(mensaje);
        error.setStyle("-fx-text-fill: red; -fx-font-size: 14px;");
        error.setWrapText(true);

        contenidoDinamico.getChildren().clear();
        contenidoDinamico.getChildren().add(error);
    }
}