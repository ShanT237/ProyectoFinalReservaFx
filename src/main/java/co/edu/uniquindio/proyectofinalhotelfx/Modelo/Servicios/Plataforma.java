package co.edu.uniquindio.proyectofinalhotelfx.Modelo.Servicios;

public class Plataforma {
    private ServicioAdm servicioAdm;
    private ServicioUsuario servicioUsuario;
    private ServicioReserva servicioReserva;
    private ServiviosAlojamiento serviviosAlojamiento;

    public Plataforma(){
        servicioAdm = new ServicioAdm();
        servicioUsuario = new ServicioUsuario();
        servicioReserva = new ServicioReserva();
        serviviosAlojamiento = new ServiviosAlojamiento();
    }


    public void loginAdm(){

    }

}
