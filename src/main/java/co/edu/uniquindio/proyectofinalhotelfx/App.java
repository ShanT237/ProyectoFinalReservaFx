package co.edu.uniquindio.proyectofinalhotelfx;

import co.edu.uniquindio.proyectofinalhotelfx.Controladores.ControladorPrincipal;
import co.edu.uniquindio.proyectofinalhotelfx.Modelo.Entidades.Administrador;
import co.edu.uniquindio.proyectofinalhotelfx.Singleton.SesionUsuario;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

public class App extends Application {

    @Override
    public void start(Stage stage) throws Exception {

        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("PantallaPrincipal.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 600);

        stage.setTitle("BookYourStay");
        stage.setScene(scene);
        stage.show();
    }

}
