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

    }
}
