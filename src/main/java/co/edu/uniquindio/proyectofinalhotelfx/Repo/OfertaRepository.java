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

