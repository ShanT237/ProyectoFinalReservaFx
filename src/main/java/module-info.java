module co.edu.uniquindio.proyectofinalhotelfx {
    requires javafx.controls;
    requires javafx.fxml;
    requires static lombok;
    requires java.desktop;


    opens co.edu.uniquindio.proyectofinalhotelfx to javafx.fxml;
    exports co.edu.uniquindio.proyectofinalhotelfx;
    exports co.edu.uniquindio.proyectofinalhotelfx.Controladores to javafx.fxml;
    opens co.edu.uniquindio.proyectofinalhotelfx.Controladores to javafx.fxml;
}