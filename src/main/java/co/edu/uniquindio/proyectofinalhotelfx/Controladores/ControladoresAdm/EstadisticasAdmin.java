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
import java.util.ResourceBundle;

public class EstadisticasAdmin implements Initializable {

    @FXML
    private StackPane contenidoDinamico;

    private final ControladorPrincipal controladorPrincipal = ControladorPrincipal.getInstancia();
    private final Plataforma plataforma = controladorPrincipal.getPlataforma();

    /**
     * Inicializa el controlador mostrando un mensaje inicial
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        mostrarMensajeInicial();
    }

    /**
     * Muestra estadísticas de porcentaje de reservas por tipo de alojamiento
     */
    @FXML
    void estadisticasAlojamiento(ActionEvent event) {
        try {
            VBox contenedor = new VBox(15);
            contenedor.setPadding(new Insets(20));

            Label titulo = new Label("Porcentaje de Reservas por Tipo de Alojamiento");
            titulo.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

            List<TipoAlojamientoGanancia> porcentajes = plataforma.calcularPorcentajeReservasPorTipo();

            if (porcentajes.isEmpty()) {
                Label mensaje = new Label("No hay datos para mostrar.");
                mensaje.setStyle("-fx-font-size: 14px; -fx-text-fill: #666;");
                contenedor.getChildren().add(mensaje);
            } else {
                PieChart pieChart = new PieChart();
                pieChart.setTitle("Distribución de Reservas");

                ObservableList<PieChart.Data> pieData = FXCollections.observableArrayList();

                for (TipoAlojamientoGanancia tipo : porcentajes) {
                    pieData.add(new PieChart.Data(
                            tipo.getTipo().toString() + " (" + String.format("%.1f", tipo.getGananciaTotal()) + "%)",
                            tipo.getGananciaTotal()
                    ));
                }

                pieChart.setData(pieData);
                pieChart.setPrefHeight(400);

                contenedor.getChildren().addAll(titulo, pieChart);
                contenidoDinamico.getChildren().clear();
                contenidoDinamico.getChildren().add(contenedor);
            }

            contenidoDinamico.getChildren().clear();
            contenidoDinamico.getChildren().add(contenedor);

        } catch (Exception e) {
            mostrarError("Error al cargar porcentaje de reservas por tipo: " + e.getMessage());
        }
    }

    /**
     * Muestra los alojamientos más populares por ciudad seleccionada
     */
    @FXML
    void alojamientosPopulares(ActionEvent event) {
        try {
            VBox contenedor = new VBox(15);
            contenedor.setPadding(new Insets(20));

            Label titulo = new Label("Alojamientos Más Populares por Ciudad");
            titulo.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

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

    /**
     * Muestra los tipos de alojamiento más rentables
     */
    @FXML
    void alojamientosRentables(ActionEvent event) {
        try {
            VBox contenedor = new VBox(15);
            contenedor.setPadding(new Insets(20));

            Label titulo = new Label("Tipos de Alojamiento Más Rentables");
            titulo.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

            List<TipoAlojamientoGanancia> tiposRentables = plataforma.getServicioAdm()
                    .obtenerTiposAlojamientoMasRentables();

            if (tiposRentables.isEmpty()) {
                Label mensaje = new Label("No hay datos de rentabilidad disponibles");
                mensaje.setStyle("-fx-font-size: 14px; -fx-text-fill: #666;");
                contenedor.getChildren().add(mensaje);
            } else {
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

    /**
     * Muestra un mensaje inicial en el panel de contenido
     */
    private void mostrarMensajeInicial() {
        Label mensaje = new Label("Seleccione una opción para ver las estadísticas");
        mensaje.setStyle("-fx-font-size: 16px; -fx-text-fill: #666;");
        contenidoDinamico.getChildren().clear();
        contenidoDinamico.getChildren().add(mensaje);
    }

    /**
     * Muestra los alojamientos más populares para una ciudad específica
     * @param ciudad La ciudad para la cual mostrar los alojamientos
     * @param container El contenedor donde se mostrará la información
     */
    private void mostrarAlojamientosPopulares(Ciudad ciudad, VBox container) {
        container.getChildren().clear();

        try {
            List<Alojamiento> alojamientosPopulares = plataforma.getServicioAdm()
                    .obtenerAlojamientosMasPopulares(ciudad);

            if (alojamientosPopulares == null) {
                Label mensaje = new Label("Error: la lista de alojamientos es null");
                mensaje.setStyle("-fx-font-size: 14px; -fx-text-fill: red;");
                container.getChildren().add(mensaje);
                return;
            }

            if (alojamientosPopulares.isEmpty()) {
                Label mensaje = new Label("No hay datos de alojamientos para esta ciudad");
                mensaje.setStyle("-fx-font-size: 14px; -fx-text-fill: #666;");
                container.getChildren().add(mensaje);
                return;
            }

            mostrarGraficoBarrasAlojamientos(alojamientosPopulares, container);

        } catch (Exception e) {
            Label error = new Label("Error al cargar datos: " + e.getMessage());
            error.setStyle("-fx-text-fill: red;");
            container.getChildren().add(error);
        }
    }

    /**
     * Muestra un gráfico de barras con los alojamientos más populares
     * @param alojamientos Lista de alojamientos a mostrar
     * @param container Contenedor donde se mostrará el gráfico
     */
    private void mostrarGraficoBarrasAlojamientos(List<Alojamiento> alojamientos, VBox container) {
        CategoryAxis ejeX = new CategoryAxis();
        ejeX.setLabel("Alojamiento");

        NumberAxis ejeY = new NumberAxis();
        ejeY.setLabel("Número de Reservas");

        BarChart<String, Number> barChart = new BarChart<>(ejeX, ejeY);
        barChart.setTitle("Alojamientos Más Populares");

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Reservas");

        for (Alojamiento a : alojamientos) {
            int cantidadReservas = plataforma.getServicioReserva().contarReservasPorAlojamiento(a.getId());
            series.getData().add(new XYChart.Data<>(a.getNombre(), cantidadReservas));
        }

        barChart.getData().add(series);
        barChart.setPrefHeight(400);

        container.getChildren().add(barChart);
    }

    /**
     * Muestra un mensaje de error en el panel de contenido
     * @param mensaje El mensaje de error a mostrar
     */
    private void mostrarError(String mensaje) {
        Label error = new Label(mensaje);
        error.setStyle("-fx-text-fill: red; -fx-font-size: 14px;");
        error.setWrapText(true);

        contenidoDinamico.getChildren().clear();
        contenidoDinamico.getChildren().add(error);
    }
}