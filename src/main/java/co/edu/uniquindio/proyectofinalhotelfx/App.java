package co.edu.uniquindio.proyectofinalhotelfx;

import co.edu.uniquindio.proyectofinalhotelfx.Controladores.ControladorPrincipal;
import co.edu.uniquindio.proyectofinalhotelfx.Modelo.Entidades.Administrador;
import co.edu.uniquindio.proyectofinalhotelfx.Modelo.Entidades.Cliente;
import co.edu.uniquindio.proyectofinalhotelfx.Modelo.Enums.Ciudad;
import co.edu.uniquindio.proyectofinalhotelfx.Modelo.Enums.ServiciosIncluidos;
import co.edu.uniquindio.proyectofinalhotelfx.Modelo.Enums.TipoAlojamiento;
import co.edu.uniquindio.proyectofinalhotelfx.Singleton.SesionAdm;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class App extends Application {

    public void iniciarDatos() throws Exception {
        ControladorPrincipal controladorPrincipal = ControladorPrincipal.getInstancia();
        Image imagen = new Image(getClass().getResource("/co/edu/uniquindio/proyectofinalhotelfx/Imagenes/expediav2-703354-2927a7-654387.png").toExternalForm());
        Administrador adm = Administrador.builder()
                .nombre("Santiago")
                .cedula("12323")
                .telefono("3216549870")
                .correo("santiago.rodriguezt@uqvirtual.edu.co")
                .password("1234password")
                .build();
        SesionAdm sesionAdm = SesionAdm.instancia();
        sesionAdm.iniciarSesion(adm);


        controladorPrincipal.getPlataforma().registrarCliente("Juan", "1234567890", "3216549870", "yesuaesteban@gmail.com", "1234Password23", "1234Password23" );


        // Casa 1
        controladorPrincipal.getPlataforma().getServiciosAlojamiento().registrarAlojamiento(
                "Casa Campestre", Ciudad.CALI, "Casa grande en zona campestre", 200000, 8,
                imagen,
                List.of(ServiciosIncluidos.WIFI, ServiciosIncluidos.COCINA), 8, 4, true, TipoAlojamiento.CASA);

        // Hotel 1
        controladorPrincipal.getPlataforma().getServiciosAlojamiento().registrarAlojamiento(
                "Hotel Sol Caribe", Ciudad.CARTAGENA, "Hotel con piscina y desayuno incluido", 450000, 30,
                imagen,
                List.of(ServiciosIncluidos.WIFI, ServiciosIncluidos.TV, ServiciosIncluidos.PISCINA), 30, 10, false, TipoAlojamiento.HOTEL);

        // Apartamento 1
        controladorPrincipal.getPlataforma().getServiciosAlojamiento().registrarAlojamiento(
                "Apto Bogotá Centro", Ciudad.BOGOTA, "Apartamento moderno cerca al centro", 120000, 4,
                imagen,
                List.of(ServiciosIncluidos.WIFI, ServiciosIncluidos.SPA), 4, 2, true, TipoAlojamiento.APARTAMENTO);

        // Casa 2
        controladorPrincipal.getPlataforma().getServiciosAlojamiento().registrarAlojamiento(
                "Casa Familiar", Ciudad.MEDELLIN, "Casa acogedora para familias", 180000, 6,
                imagen,
                List.of(ServiciosIncluidos.WIFI, ServiciosIncluidos.SPA), 6, 3, false, TipoAlojamiento.CASA);

        // Hotel 2
        controladorPrincipal.getPlataforma().getServiciosAlojamiento().registrarAlojamiento(
                "Hotel Mar Azul", Ciudad.SANTAMARTA, "Hotel 5 estrellas con vista al mar", 600000, 40,
                imagen,
                List.of(ServiciosIncluidos.WIFI, ServiciosIncluidos.TV, ServiciosIncluidos.PISCINA, ServiciosIncluidos.RESTAURANTE), 40, 15, true, TipoAlojamiento.HOTEL);

        // Apartamento 2
        controladorPrincipal.getPlataforma().getServiciosAlojamiento().registrarAlojamiento(
                "Mini Estudio", Ciudad.MEDELLIN, "Pequeño estudio amoblado", 90000, 2,
                imagen,
                List.of(ServiciosIncluidos.WIFI), 2, 1, false, TipoAlojamiento.APARTAMENTO);

        // Casa 3
        controladorPrincipal.getPlataforma().getServiciosAlojamiento().registrarAlojamiento(
                "Casa de Campo", Ciudad.ARMENIA, "Casa de campo con parqueadero", 160000, 5,
                imagen,
                List.of(ServiciosIncluidos.COCINA, ServiciosIncluidos.GIMNASIO), 5, 2, true, TipoAlojamiento.CASA);

        // Hotel 3
        controladorPrincipal.getPlataforma().getServiciosAlojamiento().registrarAlojamiento(
                "Hotel Boutique Colonial", Ciudad.BOGOTA, "Hotel boutique con desayuno", 480000, 36,
                imagen,
                List.of(ServiciosIncluidos.WIFI, ServiciosIncluidos.TV, ServiciosIncluidos.PISCINA, ServiciosIncluidos.COCINA), 36, 12, true, TipoAlojamiento.HOTEL);

        // Apartamento 3
        controladorPrincipal.getPlataforma().getServiciosAlojamiento().registrarAlojamiento(
                "Apto Familiar Cartagena", Ciudad.CARTAGENA, "Apartamento con 3 habitaciones", 140000, 6,
                imagen,
                List.of(ServiciosIncluidos.WIFI, ServiciosIncluidos.COCINA, ServiciosIncluidos.TV), 6, 3, true, TipoAlojamiento.APARTAMENTO);

        // Casa 4
        controladorPrincipal.getPlataforma().getServiciosAlojamiento().registrarAlojamiento(
                "Villa Relax", Ciudad.SANTAMARTA, "Casa con piscina privada", 350000, 10, imagen,
                List.of(ServiciosIncluidos.WIFI, ServiciosIncluidos.PISCINA, ServiciosIncluidos.COCINA), 10, 5, false, TipoAlojamiento.CASA);

    }
    @Override
    public void start(Stage stage) throws Exception {
        iniciarDatos();
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("PantallaPrincipal.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 600);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

}