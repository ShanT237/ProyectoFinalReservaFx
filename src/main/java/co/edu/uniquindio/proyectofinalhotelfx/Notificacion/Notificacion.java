package co.edu.uniquindio.proyectofinalhotelfx.Notificacion;

import java.time.LocalDateTime;
import java.util.UUID;

public class Notificacion {

    private UUID id;
    String destinatario;
    String asunto;
    String cuerpo;
    LocalDateTime fechaEnvio;
    boolean leido;
}
