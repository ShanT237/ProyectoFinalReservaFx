package co.edu.uniquindio.proyectofinalhotelfx;

import co.edu.uniquindio.proyectofinalhotelfx.Controladores.ControladorPrincipal;
import co.edu.uniquindio.proyectofinalhotelfx.Modelo.Entidades.Administrador;
import co.edu.uniquindio.proyectofinalhotelfx.Modelo.Enums.Ciudad;
import co.edu.uniquindio.proyectofinalhotelfx.Modelo.Enums.ServiciosIncluidos;
import co.edu.uniquindio.proyectofinalhotelfx.Modelo.Enums.TipoAlojamiento;
import co.edu.uniquindio.proyectofinalhotelfx.Singleton.SesionAdm;
import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
public class App extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        iniciarDatos(); // Inicialización de datos

        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("PantallaPrincipal.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 600);

        // Ícono de la aplicación (carga robusta)
        Image icono = cargarImagenRobusta("Img/ImagenesApp/icon.png", "/co/edu/uniquindio/proyectofinalhotelfx/Img/ImagenesApp/icon.png");
        stage.getIcons().add(icono);

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
                .correo("shanrt@gmail.com")
                .password("1234password")
                .build();

        SesionAdm.instancia().iniciarSesion(adm);
        controladorPrincipal.getPlataforma().registrarCliente("Juan", "1234567890", "3216549870",
                "yesuaesteban@gmail.com", "1234Password23", "1234Password23");

        // 2. Carga de imágenes (versión robusta)
        Image imagenHotel = cargarImagenRobusta("Img/ImagenesAlojamientos/hotel.png", "/co/edu/uniquindio/proyectofinalhotelfx/Img/ImagenesAlojamientos/hotel.png");
        Image imagenCasa = cargarImagenRobusta("Img/ImagenesAlojamientos/casa.png", "/co/edu/uniquindio/proyectofinalhotelfx/Img/ImagenesAlojamientos/casa.png");
        Image imagenApartamento = cargarImagenRobusta("Img/ImagenesAlojamientos/apartamento.png", "/co/edu/uniquindio/proyectofinalhotelfx/Img/ImagenesAlojamientos/apartamento.png");

        // 3. Registro de alojamientos
        Ciudad[] ciudades = Ciudad.values();
        for (Ciudad ciudad : ciudades) {
            Image imgHotel = cargarImagenRobusta("Img/ImagenesAlojamientos/hotel.png", "/co/edu/uniquindio/proyectofinalhotelfx/Img/ImagenesAlojamientos/hotel.png");
            Image imgCasa = cargarImagenRobusta("Img/ImagenesAlojamientos/casa.png", "/co/edu/uniquindio/proyectofinalhotelfx/Img/ImagenesAlojamientos/casa.png");
            Image imgApto = cargarImagenRobusta("Img/ImagenesAlojamientos/apartamento.png", "/co/edu/uniquindio/proyectofinalhotelfx/Img/ImagenesAlojamientos/apartamento.png");

            registrarAlojamientos(controladorPrincipal, ciudad, imgHotel, imgCasa, imgApto);
        }
    }

    private void registrarAlojamientos(ControladorPrincipal controlador, Ciudad ciudad,
                                       Image imgHotel, Image imgCasa, Image imgApto) {
        controlador.getPlataforma().getServiciosAlojamiento().registrarAlojamiento(
                "Hotel " + ciudad.name(), ciudad, "Hotel confortable en " + ciudad.name(),
                350000 + (int) (Math.random() * 200000),
                imgHotel,
                List.of(ServiciosIncluidos.WIFI, ServiciosIncluidos.DESAYUNO, ServiciosIncluidos.PISCINA),
                20, 10, true, TipoAlojamiento.HOTEL);

        controlador.getPlataforma().getServiciosAlojamiento().registrarAlojamiento(
                "Casa " + ciudad.name(), ciudad, "Casa acogedora en " + ciudad.name(),
                200000 + (int) (Math.random() * 100000),
                imgCasa,
                List.of(ServiciosIncluidos.WIFI, ServiciosIncluidos.COCINA, ServiciosIncluidos.ESTACIONAMIENTO),
                6, 3, true, TipoAlojamiento.CASA);

        controlador.getPlataforma().getServiciosAlojamiento().registrarAlojamiento(
                "Apto " + ciudad.name(), ciudad, "Apartamento en " + ciudad.name(),
                150000 + (int) (Math.random() * 80000),
                imgApto,
                List.of(ServiciosIncluidos.WIFI, ServiciosIncluidos.TV, ServiciosIncluidos.COCINA),
                2, 1, true, TipoAlojamiento.APARTAMENTO);
    }

    private Image cargarImagenRobusta(String rutaExterna, String rutaInterna) {
        try {
            // Intentar desde archivo del sistema (ej. Img/hotel.png)
            File archivo = new File(rutaExterna);
            if (archivo.exists()) {
                return new Image(archivo.toURI().toString());
            }

            // Si no está, buscar en el classpath (recursos empaquetados)
            InputStream inputStream = getClass().getResourceAsStream(rutaInterna);
            if (inputStream != null) {
                return new Image(inputStream);
            }

            throw new IOException("No se encontró la imagen ni en " + rutaExterna + " ni en " + rutaInterna);
        } catch (Exception e) {
            System.err.println("Error crítico al cargar imagen: " + e.getMessage());
            return new Image("https://via.placeholder.com/80x60.png?text=Sin+Imagen");
        }
    }
}