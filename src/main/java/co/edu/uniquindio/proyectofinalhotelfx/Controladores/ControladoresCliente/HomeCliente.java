package co.edu.uniquindio.proyectofinalhotelfx.Controladores.ControladoresCliente;


import co.edu.uniquindio.proyectofinalhotelfx.Modelo.Entidades.Usuario;
import co.edu.uniquindio.proyectofinalhotelfx.Servicios.ServicioBilleteraVirtual;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class HomeCliente {

    private Usuario usuario;
    private ServicioBilleteraVirtual servicioBilleteraVirtual;

    @FXML
    private Label lblNombreUsuario;

    @FXML
    private Label lblSaldo;

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
        if (lblNombreUsuario != null) {
            lblNombreUsuario.setText(usuario.getNombre());
        }
        actualizarSaldo();
    }

    public void setServicioBilleteraVirtual(ServicioBilleteraVirtual servicio) {
        this.servicioBilleteraVirtual = servicio;
        actualizarSaldo();
    }

    private void actualizarSaldo() {
        if (usuario != null && servicioBilleteraVirtual != null) {
            double saldo = servicioBilleteraVirtual.consultarSaldo(usuario.getCedula());
            if (lblSaldo != null) {
                lblSaldo.setText("$" + String.format("%.2f", saldo));
            }
        }
    }
}
