package co.edu.uniquindio.proyectofinalhotelfx.Controlador;

import co.edu.uniquindio.proyectofinalhotelfx.Modelo.Entidades.Administrador;
import javafx.fxml.FXML;

public class Login {
    @FXML
    private void iniciarSesion() {
        // ... (validación previa)
        if (usuario instanceof Administrador) {
            cargarVentana("/fxml/HomeAdmin.fxml", "Admin Dashboard");
        } else {
            cargarVentana("/fxml/HomeCliente.fxml", "Bienvenido");
        }
    }
}
