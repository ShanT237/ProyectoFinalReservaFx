package co.edu.uniquindio.proyectofinalhotelfx.Servicios;


import co.edu.uniquindio.proyectofinalhotelfx.Modelo.Entidades.Administrador;
import co.edu.uniquindio.proyectofinalhotelfx.Modelo.Entidades.Alojamiento;
import co.edu.uniquindio.proyectofinalhotelfx.Modelo.Entidades.Cliente;
import co.edu.uniquindio.proyectofinalhotelfx.Modelo.Entidades.Oferta;
import co.edu.uniquindio.proyectofinalhotelfx.Repo.AdmRepository;
import co.edu.uniquindio.proyectofinalhotelfx.Repo.AlojamientoRepository;
import co.edu.uniquindio.proyectofinalhotelfx.Repo.ClienteRepository;
import co.edu.uniquindio.proyectofinalhotelfx.Repo.ReservaRepository;
import co.edu.uniquindio.proyectofinalhotelfx.Singleton.SesionAdm;
import lombok.Builder;

import java.util.List;

@Builder
public class ServicioAdm {

    private final ServicioAlojamiento servicioAlojamiento;
    private final ServicioReserva servicioReserva;
    private final ServicioCliente servicioCliente;
    private final AdmRepository admRepository;

    SesionAdm sesionAdm = SesionAdm.instancia();

    public String loginAdm(String correo, String password){
        List lista = admRepository.obtenerAdms();
        admExiste(lista, correo, password);

        return admExiste(lista, correo, password);
    }

    public String admExiste(List<Administrador> lista, String correo, String password) {
        for (Administrador admin : lista) {
            if (admin.getCorreo().equals(correo)) {
                if (admin.getPassword().equals(password)) {
                    crearSesion(admin);
                    return "¡Bienvenido, administrador!";
                } else {
                    return "La contraseña es incorrecta.";
                }
            }
        }

        return "No existe un administrador con ese correo.";
    }

    public void crearSesion(Administrador administrador){
        sesionAdm.iniciarSesion(administrador);
        System.out.println("Sesion Iniciada con " + administrador.getNombre());
    }

    /*
    Metodos gestionar usuario
     */

    public void bloquearCuentaCliente(String idUsuario) throws Exception {
       servicioCliente.bloquearUsuario(idUsuario);
    }
    public void verActividadesDeCliente(String idUsuario){
        servicioReserva.obtenerReservasPorCliente(idUsuario);

    }

    /*
    GestionarAlojamientos
     */

    public void registrarAlojamiento(Alojamiento alojamiento){

    }
    public void actualizarAlojamiento(String idAlojamiento, Alojamiento nuevoAlojamiento){

    }
    public void eliminarAlojamiento(String idAlojamiento){

    }

    public void agregarOfertaEspecial(String idAlojamiento, Oferta oferta){

    }
    public void actualizarOfertaEspecial(String idAlojamiento, Oferta nuevaOferta){

    }
    public void eliminarOfertaEspecial(String idAlojamiento, String idOferta){

    }
    /*
    Gestionar Reservas
     */


}
