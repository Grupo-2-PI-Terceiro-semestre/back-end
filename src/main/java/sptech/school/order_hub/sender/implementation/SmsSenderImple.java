package sptech.school.order_hub.sender.implementation;

import org.springframework.stereotype.Component;
import sptech.school.order_hub.sender.MessageSender;

@Component
public class SmsSenderImple implements MessageSender {

    @Override
    public void sendNotification(String destinatario, String acao) {
        System.out.println("Notificação via SMS Envida Com Sucesso!");
    }
}
