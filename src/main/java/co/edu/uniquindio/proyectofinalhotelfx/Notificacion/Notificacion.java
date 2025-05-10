package co.edu.uniquindio.proyectofinalhotelfx.Notificacion;

import java.time.LocalDateTime;
import java.util.UUID;

import org.simplejavamail.api.email.Email;
import org.simplejavamail.api.mailer.Mailer;
import org.simplejavamail.api.mailer.config.TransportStrategy;
import org.simplejavamail.email.EmailBuilder;
import org.simplejavamail.mailer.MailerBuilder;


public class Notificacion {


    public static void enviarNotificacion(String destinatario, String asunto, String mensaje) {


        Email email = EmailBuilder.startingBlank()
                .from("proyectofinalprogramacion053@gmail.com")
                .to(destinatario)
                .withSubject(asunto)
                .withPlainText(mensaje)
                .buildEmail();


        try (Mailer mailer = MailerBuilder
                .withSMTPServer("smtp.gmail.com", 587, "proyectofinalprogramacion053@gmail.com", "gohp ilel gmil kfpb")
                .withTransportStrategy(TransportStrategy.SMTP_TLS)
                .withDebugLogging(true)
                .buildMailer()) {


            mailer.sendMail(email);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}