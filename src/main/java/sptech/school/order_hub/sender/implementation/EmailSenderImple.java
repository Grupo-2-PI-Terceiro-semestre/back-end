package sptech.school.order_hub.sender.implementation;

import sptech.school.order_hub.sender.MessageSender;

public class EmailSenderImple implements MessageSender {


    @Override
    public void sendNotification(String destinatario, String acao) {

        System.out.println("Email enviado para: " + destinatario + " com a mensagem: " + acao);
    }
}
