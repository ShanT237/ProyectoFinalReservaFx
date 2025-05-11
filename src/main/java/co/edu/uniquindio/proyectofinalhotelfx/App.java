package co.edu.uniquindio.proyectofinalhotelfx;

import co.edu.uniquindio.proyectofinalhotelfx.Controladores.ControladorPrincipal;
import co.edu.uniquindio.proyectofinalhotelfx.Modelo.Entidades.Administrador;
import co.edu.uniquindio.proyectofinalhotelfx.Modelo.Enums.Ciudad;
import co.edu.uniquindio.proyectofinalhotelfx.Modelo.Enums.ServiciosIncluidos;
import co.edu.uniquindio.proyectofinalhotelfx.Modelo.Enums.TipoAlojamiento;
import co.edu.uniquindio.proyectofinalhotelfx.Singleton.SesionAdm;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class App extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        iniciarDatos(); // Llama al método de inicialización

        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("HomeAdmin.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 600);
        stage.getIcons().add(new Image(App.class.getResourceAsStream("/co/edu/uniquindio/proyectofinalhotelfx/ImagenesApp/icon.png")));
        stage.setTitle("BookYourStay");
        stage.setScene(scene);
        stage.show();
    }

    private void iniciarDatos() throws Exception {
        ControladorPrincipal controladorPrincipal = ControladorPrincipal.getInstancia();

        // 1. Configuración inicial del administrador
        Administrador adm = Administrador.builder()
                .nombre("Santiago")
                .cedula("12323")
                .telefono("3216549870")
                .correo("santiago.rodriguezt@uqvirtual.edu.co")
                .password("1234password")
                .build();

        SesionAdm.instancia().iniciarSesion(adm);
        controladorPrincipal.getPlataforma().registrarCliente("Juan", "1234567890", "3216549870",
                "yesuaesteban@gmail.com", "1234Password23", "1234Password23");

        // 2. Carga de imágenes (versión robusta)
        Image imagenHotel = cargarImagen("/co/edu/uniquindio/proyectofinalhotelfx/ImagenesAlojamientos/hotel.png");
        Image imagenCasa = cargarImagen("/co/edu/uniquindio/proyectofinalhotelfx/ImagenesAlojamientos/casa.png");
        Image imagenApartamento = cargarImagen("/co/edu/uniquindio/proyectofinalhotelfx/ImagenesAlojamientos/apartamento.png");

        // 3. Registro de alojamientos
        Ciudad[] ciudades = Ciudad.values();
        for (Ciudad ciudad : ciudades) {
            registrarAlojamientos(controladorPrincipal, ciudad, imagenHotel, imagenCasa, imagenApartamento);
        }
    }

    private void registrarAlojamientos(ControladorPrincipal controlador, Ciudad ciudad,
                                       Image imgHotel, Image imgCasa, Image imgApto) {
        // Hoteles $350k-$550k
        controlador.getPlataforma().getServiciosAlojamiento().registrarAlojamiento(
                "Hotel " + ciudad.name(), ciudad, "Hotel confortable en " + ciudad.name(),
                350000 + (int) (Math.random() * 200000),
                imgHotel,
                List.of(ServiciosIncluidos.WIFI, ServiciosIncluidos.DESAYUNO, ServiciosIncluidos.PISCINA),
                20, 10, true, TipoAlojamiento.HOTEL);

        // Casas $200k-$300k
        controlador.getPlataforma().getServiciosAlojamiento().registrarAlojamiento(
                "Casa " + ciudad.name(), ciudad, "Casa acogedora en " + ciudad.name(),
                200000 + (int) (Math.random() * 100000),
                imgCasa,
                List.of(ServiciosIncluidos.WIFI, ServiciosIncluidos.COCINA, ServiciosIncluidos.ESTACIONAMIENTO),
                6, 3, true, TipoAlojamiento.CASA);

        // Apartamentos $150k-$230k
        controlador.getPlataforma().getServiciosAlojamiento().registrarAlojamiento(
                "Apto " + ciudad.name(), ciudad, "Apartamento en " + ciudad.name(),
                150000 + (int) (Math.random() * 80000),
                imgApto,
                List.of(ServiciosIncluidos.WIFI, ServiciosIncluidos.TV, ServiciosIncluidos.COCINA),
                2, 1, true, TipoAlojamiento.APARTAMENTO);
    }

    private Image cargarImagen(String ruta) {
        try {
            InputStream inputStream = getClass().getResourceAsStream(ruta);
            if (inputStream == null) {
                throw new IOException("No se encontró la imagen en: " + ruta);
            }
            return new Image(inputStream);
        } catch (Exception e) {
            System.err.println("Error crítico al cargar imagen: " + e.getMessage());
            System.exit(1); // Detiene la aplicación si no puede cargar imágenes críticas
            return null;
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}