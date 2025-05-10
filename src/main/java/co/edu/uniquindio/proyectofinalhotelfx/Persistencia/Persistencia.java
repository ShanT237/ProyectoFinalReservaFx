package co.edu.uniquindio.proyectofinalhotelfx.Persistencia;

import java.io.*;

public class Persistencia {

    public static void serializarObjeto(String ruta, Object objeto) throws IOException {
        File archivo = new File(ruta);
        File carpeta = archivo.getParentFile();

        if (carpeta != null && !carpeta.exists()) {
            carpeta.mkdirs();
        }

        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(archivo));
        oos.writeObject(objeto);
        oos.close();
    }

    /**
     * Método que permite deserializar un objeto de un archivo en la ruta especificada
     * @return Objeto deserializado
     * @throws Exception
     */
    public static Object deserializarObjeto(String ruta) throws Exception {
        File archivo = new File(ruta);

        if (!archivo.exists()) {
            return null;
        }

        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(archivo));
        Object objeto = ois.readObject();
        ois.close();

        return objeto;
    }
}