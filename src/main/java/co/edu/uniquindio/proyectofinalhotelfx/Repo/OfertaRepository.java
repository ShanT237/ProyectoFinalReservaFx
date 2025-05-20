package co.edu.uniquindio.proyectofinalhotelfx.Repo;


import co.edu.uniquindio.proyectofinalhotelfx.Modelo.Entidades.Alojamiento;
import co.edu.uniquindio.proyectofinalhotelfx.Modelo.Entidades.Oferta;
import co.edu.uniquindio.proyectofinalhotelfx.Persistencia.Ruta;
import co.edu.uniquindio.proyectofinalhotelfx.Persistencia.Persistencia;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class OfertaRepository {
    private final List<Oferta> ofertas;
    public OfertaRepository() {
        this.ofertas = leerDatos();
    }



    public List<Oferta> obtenerTodos() {
        return new ArrayList<>(ofertas);
    }
    /**
     * Guarda una nueva oferta en el repositorio
     * @param oferta La oferta a guardar
     */
    public void guardar(Oferta oferta) {
        ofertas.add(oferta);
        guardarDatos();
    }

    public void eliminarOferta(String idOferta) {
        for (Oferta oferta : ofertas) {
            if (oferta.getId().equals(idOferta)) {
                ofertas.remove(oferta);
                guardarDatos();
                return;
            }
        }
    }

    public void actualizarOferta(Oferta oferta) throws Exception {
        for (int i = 0; i < ofertas.size(); i++) {
            if (ofertas.get(i).getId().equals(oferta.getId())) {
                ofertas.set(i, oferta);
                guardarDatos();
                return;
            }
        }
        throw new Exception("No existe una oferta con el ID: " + oferta.getId());
    }

    public Oferta buscarPorId(String idOferta) {
        for (Oferta oferta : ofertas) {
            if (oferta.getId().equals(idOferta)) {
                return oferta;
            }
        }
        return null;
    }

    private void guardarDatos() {
        try {
            Persistencia.serializarObjeto(Ruta.RUTA_OFERTA, ofertas);
        } catch (IOException e) {
            System.err.println("Error guardando ofertas: " + e.getMessage());
        }
    }
    private List<Oferta> leerDatos() {
        try {
            Object datos = Persistencia.deserializarObjeto(Ruta.RUTA_OFERTA);
            if (datos != null) {
                return (List<Oferta>) datos;
            }
        } catch (Exception e) {
            System.err.println("Error cargando ofertas: " + e.getMessage());
        }
        return new ArrayList<>();
    }
}
