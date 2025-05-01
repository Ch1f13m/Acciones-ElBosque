package co.edu.unbosque.accioneselbosqueapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

/**
 * Servicio que permite enviar correos electrónicos simples utilizando
 * la configuración definida en la aplicación (como Mailtrap o Gmail).
 */
@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    /**
     * Envía un correo electrónico a una dirección específica.
     *
     * @param para   Dirección de correo del destinatario
     * @param asunto Asunto del correo
     * @param cuerpo Cuerpo o contenido del mensaje
     */
    public void enviarCorreo(String para, String asunto, String cuerpo) {
        SimpleMailMessage mensaje = new SimpleMailMessage();
        mensaje.setTo(para);         // destinatario
        mensaje.setSubject(asunto);  // asunto del mensaje
        mensaje.setText(cuerpo);     // cuerpo del mensaje
        mailSender.send(mensaje);    // envío del correo
    }
}
