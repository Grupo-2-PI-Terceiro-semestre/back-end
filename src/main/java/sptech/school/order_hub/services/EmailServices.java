package sptech.school.order_hub.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailServices {

    @Autowired
    private JavaMailSender mailSender;

    public void enviarEmail(String para, String assunto, String texto, String from) {
        SimpleMailMessage mensagem = new SimpleMailMessage();
        mensagem.setTo(para);
        mensagem.setSubject(assunto);
        mensagem.setText(texto);
        mensagem.setFrom(from);
        mailSender.send(mensagem);
    }

}
