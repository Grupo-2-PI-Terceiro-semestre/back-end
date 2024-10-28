package sptech.school.order_hub.observer;

import sptech.school.order_hub.sender.MessageSender;

public class NotificacaoObserver implements Observer {


    private final MessageSender messageSender;
    private final String destinatario;

    public NotificacaoObserver(MessageSender messageSender, String destinatario) {
        this.messageSender = messageSender;
        this.destinatario = destinatario;
    }

    @Override
    public void update(String acao) {
        messageSender.sendNotification(destinatario, acao);
    }
}
