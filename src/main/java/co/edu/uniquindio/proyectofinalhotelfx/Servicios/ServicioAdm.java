package co.edu.uniquindio.proyectofinalhotelfx.Servicios;


import co.edu.uniquindio.proyectofinalhotelfx.Modelo.Entidades.Administrador;
import co.edu.uniquindio.proyectofinalhotelfx.Modelo.Entidades.Alojamiento;
import co.edu.uniquindio.proyectofinalhotelfx.Modelo.Entidades.Reserva;
import co.edu.uniquindio.proyectofinalhotelfx.Modelo.Enums.*;
import co.edu.uniquindio.proyectofinalhotelfx.Notificacion.Notificacion;
import co.edu.uniquindio.proyectofinalhotelfx.Persistencia.Ruta;
import co.edu.uniquindio.proyectofinalhotelfx.Persistencia.Persistencia;
import co.edu.uniquindio.proyectofinalhotelfx.vo.TipoAlojamientoGanancia;
import lombok.Builder;

import java.io.IOException;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Builder
public class ServicioAdm {

    private final ServicioAlojamiento servicioAlojamiento;
    private final ServicioReserva servicioReserva;
    private final ServicioCliente servicioCliente;
    private final ServicioOferta servicioOferta;
    private final Administrador adm = leerDatos();

    public Administrador loginAdm(String correo, String password){
        if (adm != null && adm.getCorreo().equals(correo) && adm.getPassword().equals(password)){
            return adm;
        }else{
            return null;
        }

    }

    public void cambiarContrasena(String correo) throws Exception {
        if (correo == null || correo.isEmpty()) {
            throw new Exception("El correo no puede estar vacío");
        }

        if (adm == null) {
            throw new Exception("No se encontró un administrador con el correo proporcionado.");
        }


        int codigo = crearCodigo();


        adm.setCodigoActivacion(codigo);


        try {
            Notificacion.enviarNotificacion(
                    correo,
                    "Su código es: " + codigo,
                    "Código para cambiar la contraseña ADM"
            );
        } catch (Exception e) {
            throw new Exception("Error al enviar el código de verificación: " + e.getMessage());
        }
    }

    public int crearCodigo() {
        SecureRandom random = new SecureRandom();
        return 1000 + random.nextInt(9000); // Entre 1000 y 9999
    }
    /*
    Metodos gestionar usuario
     */

    public void bloquearCuentaCliente(String idUsuario) throws Exception {
        servicioCliente.bloquearUsuario(idUsuario);
    }
    public void verActividadesDeCliente(String idUsuario){
        try {
            // Opción 1: Solo mostrar en consola (como parece ser la intención actual)
            servicioReserva.obtenerReservasPorCliente(idUsuario);


        } catch (Exception e) {
            System.err.println("Error al ver actividades del cliente " + idUsuario + ": " + e.getMessage());
        }
    }
    // O si prefieres que retorne la lista de reservas:
    public List<Reserva> verActividadesDeClienteConRetorno(String idUsuario) throws Exception {
        return servicioReserva.obtenerReservasPorCliente(idUsuario);
    }

    /*
    GestionarAlojamientos
     */

    public void registrarAlojamiento(String nombre, Ciudad ciudad, String descripcion, double precioPorNocheBase, String imagen, List<ServiciosIncluidos> serviciosIncluidos, int capacidadPersonas, int numeroHabitaciones, boolean admiteMascotas, TipoAlojamiento tipoAlojamiento){
        verificarDatosAlojamiento(nombre, ciudad, descripcion, precioPorNocheBase, imagen, serviciosIncluidos,
                capacidadPersonas, numeroHabitaciones, admiteMascotas,
                tipoAlojamiento);
        servicioAlojamiento.registrarAlojamiento(nombre, ciudad, descripcion, precioPorNocheBase,imagen, serviciosIncluidos,
                capacidadPersonas, numeroHabitaciones, admiteMascotas,
                tipoAlojamiento);


    }

    public void verificarDatosAlojamiento(String nombre, Ciudad ciudad, String descripcion, double precioPorNocheBase, String imagen, List<ServiciosIncluidos> serviciosIncluidos, int capacidadPersonas, int numeroHabitaciones, boolean admiteMascotas, TipoAlojamiento tipoAlojamiento){

        if (nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío.");
        }
        if (ciudad == null) {
            throw new IllegalArgumentException("La ciudad no puede ser nula.");
        }
        if (descripcion == null || descripcion.isBlank()) {
            throw new IllegalArgumentException("La descripción no puede estar vacía.");
        }
        if (precioPorNocheBase <= 0) {
            throw new IllegalArgumentException("El precio debe ser mayor a 0.");
        }
        if (serviciosIncluidos == null) {
            throw new IllegalArgumentException("La lista de servicios no puede ser nula.");
        }
        if (tipoAlojamiento == null) {
            throw new IllegalArgumentException("El tipo de alojamiento no puede ser nulo.");
        }
    }
    public void actualizarAlojamiento(String idAlojamiento, String nombre, Ciudad ciudad, String descripcion, double precioPorNocheBase, String imagen, List<ServiciosIncluidos> serviciosIncluidos, int capacidadPersonas, int numeroHabitaciones, boolean admiteMascotas, TipoAlojamiento tipoAlojamiento){
        verificarDatosAlojamiento(nombre, ciudad, descripcion, precioPorNocheBase, imagen, serviciosIncluidos,
                capacidadPersonas, numeroHabitaciones, admiteMascotas,
                tipoAlojamiento);

        servicioAlojamiento.actualizarAlojamiento( idAlojamiento, nombre,  ciudad,  descripcion,  precioPorNocheBase, imagen, serviciosIncluidos,  capacidadPersonas,  numeroHabitaciones, admiteMascotas, tipoAlojamiento);

    }
    public void eliminarAlojamiento(String idAlojamiento){
        if (idAlojamiento == null){
            throw new IllegalArgumentException("El id del alojamiento no puede ser nulo.");
        }

        servicioAlojamiento.eliminarAlojamiento(idAlojamiento);

    }

    private void guardarDatos(Administrador adm) {
        try {
            Persistencia.serializarObjeto(Ruta.RUTA_ADM, adm);
        } catch (IOException e) {
            System.err.println("Error guardando adm: " + e.getMessage());
        }
    }

    private Administrador leerDatos() {
        try {
            Object datos = Persistencia.deserializarObjeto(Ruta.RUTA_ADM);
            if (datos instanceof Administrador) {
                return (Administrador) datos;
            }
        } catch (Exception e) {
            System.err.println("Error cargando admin: " + e.getMessage());
        }
        return null;
    }

    public void registrarOferta(Ciudad ciudad, TipoAlojamiento tipoAlojamiento, String id, String nombre, String descripcion, LocalDateTime fechaInicio, LocalDateTime fechaFin, boolean esGlobal, OfertaTipo tipoOferta, int nochesMinimas, double porcentajeDescuento, String imagen) throws Exception {
        servicioOferta.agregarOfertaEspecial(ciudad, tipoAlojamiento, id, nombre, descripcion, fechaInicio, fechaFin, esGlobal, tipoOferta, nochesMinimas, porcentajeDescuento, imagen);
    }

    public void eliminarOfertaEspecial(String idOferta) {
        servicioOferta.eliminarOfertaEspecial(idOferta);
    }

    public void actualizarOfertaEspecial(String idOferta, String nombre, Ciudad ciudad, TipoAlojamiento tipoAlojamiento, String descripcion, LocalDateTime fechaInicio, LocalDateTime fechaFin, boolean esGlobal, OfertaTipo tipoOferta, int nochesMinimas, double porcentajeDescuento, String imagen) throws Exception {
        servicioOferta.actualizarOfertaEspecial(idOferta, nombre, ciudad, tipoAlojamiento, descripcion, fechaInicio, fechaFin, esGlobal, tipoOferta, nochesMinimas, porcentajeDescuento, imagen);
    }


    public double calcularOcupacionAlojamiento(String idAlojamiento) {
        int totalNoches = servicioReserva.obtenerTotalNochesReservadas(idAlojamiento);
        int posiblesNoches = servicioReserva.obtenerTotalNochesDisponibles(idAlojamiento);
        return posiblesNoches == 0 ? 0 : (totalNoches * 100.0 / posiblesNoches);
    }

    public double calcularGananciasTotales(String idAlojamiento) {
        return servicioReserva.obtenerGananciasPorAlojamiento(idAlojamiento);
    }



    public List<TipoAlojamientoGanancia> obtenerTiposAlojamientoMasRentables() {
        List<TipoAlojamientoGanancia> resultado = new ArrayList<>();

        for (TipoAlojamiento tipo : TipoAlojamiento.values()) {
            List<Alojamiento> alojamientos = servicioAlojamiento.obtenerAlojamientosPorTipo(tipo);
            double totalGanancias = 0;

            for (Alojamiento a : alojamientos) {
                totalGanancias += servicioReserva.obtenerGananciasPorAlojamiento(a.getId());
            }

            resultado.add(TipoAlojamientoGanancia.builder()
                    .tipo(tipo)
                    .gananciaTotal(totalGanancias)
                    .build()
            );
        }

        // Ordenar de mayor a menor ganancia
        for (int i = 0; i < resultado.size() - 1; i++) {
            for (int j = i + 1; j < resultado.size(); j++) {
                if (resultado.get(j).getGananciaTotal() > resultado.get(i).getGananciaTotal()) {
                    TipoAlojamientoGanancia temp = resultado.get(i);
                    resultado.set(i, resultado.get(j));
                    resultado.set(j, temp);
                }
            }
        }

        return resultado;
    }




    public void registrarHabitacion(String idhotel, int numero, int capacidad, double precioPorNoche, List<ServiciosIncluidos> serviciosIncluidos, TipoHabitacionHotel tipoHabitacionHotel, String imagen) throws Exception {
        servicioAlojamiento.registrarHabitacion(idhotel, numero, capacidad, precioPorNoche, serviciosIncluidos, tipoHabitacionHotel, imagen);
    }

    public void eliminarHabitacionHotel(String idHotel, int idHabitacion) throws Exception {
        servicioAlojamiento.eliminarHabitacion(idHotel, idHabitacion);
    }

    public List<TipoAlojamientoGanancia> calcularPorcentajeReservasPorTipo(){
        return servicioReserva.calcularPorcentajeReservasPorTipo();

    }
    public List<Alojamiento> obtenerAlojamientosMasPopulares(Ciudad ciudad) throws Exception {
        return servicioReserva.obtenerAlojamientosMasPopulares(ciudad);
    }

}