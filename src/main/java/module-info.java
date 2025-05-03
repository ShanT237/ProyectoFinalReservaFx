module co.edu.uniquindio.proyectofinalhotelfx {
    requires javafx.controls;
    requires javafx.fxml;
    requires static lombok;
    requires org.simplejavamail;
    requires org.simplejavamail.core;
    requires java.xml;


    opens co.edu.uniquindio.proyectofinalhotelfx to javafx.fxml;
    exports co.edu.uniquindio.proyectofinalhotelfx;
}