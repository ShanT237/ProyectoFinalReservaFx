module co.edu.uniquindio.proyectofinalhotelfx {
    requires javafx.controls;
    requires javafx.fxml;
    requires static lombok;
    requires java.desktop;
    requires org.simplejavamail.core;
    requires org.simplejavamail;
    requires jakarta.xml.bind;
    requires java.xml;

    // Abre paquetes a javafx.fxml
    opens co.edu.uniquindio.proyectofinalhotelfx.Controladores to javafx.fxml;
    opens co.edu.uniquindio.proyectofinalhotelfx.Controladores.ControladoresCliente to javafx.fxml; // <--- FALTA ESTA LÍNEA

    // Exporta paquetes
    exports co.edu.uniquindio.proyectofinalhotelfx;
    exports co.edu.uniquindio.proyectofinalhotelfx.Controladores;
    exports co.edu.uniquindio.proyectofinalhotelfx.Controladores.ControladoresCliente;
}

