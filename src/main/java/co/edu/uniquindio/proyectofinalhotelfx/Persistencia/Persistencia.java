package co.edu.uniquindio.proyectofinalhotelfx.Persistencia;

import java.io.*;

public class Persistencia {
    public static void serializarObjeto(String ruta, Object objeto) throws IOException {
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ruta));
        oos.writeObject(objeto);
        oos.close();
    }


    /**
     * Metodo que permite deserializar un objeto de un archivo en la ruta especificada
     * @return Objeto deserializado
     * @throws Exception
     */
    public static Object deserializarObjeto(String ruta) throws Exception{


        if(!new File(ruta).exists()){
            return null;
        }


        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(ruta));
        Object objeto = ois.readObject();
        ois.close();


        return objeto;
    }

}
