package co.edu.uniquindio.proyectofinalhotelfx.Repo;

import co.edu.uniquindio.proyectofinalhotelfx.Modelo.Entidades.Administrador;

import java.util.List;

public class AdmRepository {

    private List<Administrador> administradors;

    public List<Administrador> obtenerAdms() {
        return administradors;
    }

    public Administrador buscarAdm(String password, String correo) {
        for (Administrador adm : administradors) {
            if (adm.getPassword().equals(password) || adm.getCorreo().equals(correo)) {
                return adm;
            }
        }
        return null;

    }
}
