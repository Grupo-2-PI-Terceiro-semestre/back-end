package sptech.school.order_hub.sender;

public interface MessageSender {

    void sendNotification(String destinatario, String assunto, String corpo);
}

