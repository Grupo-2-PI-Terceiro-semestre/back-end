package sptech.school.order_hub.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.ses.SesClient;
import software.amazon.awssdk.services.ses.model.*;

@Service
public class EmailServices {

    private final SesClient sesClient;

    @Value("${aws.ses.source-email}")
    private String sourceEmail;

    public EmailServices(SesClient sesClient) {
        this.sesClient = sesClient;
    }

    public void enviarEmail(String para, String assunto, String texto) {
        Destination destination = Destination.builder()
                .toAddresses(para)
                .build();

        Content subject = Content.builder()
                .data(assunto)
                .build();

        Content bodyText = Content.builder()
                .data(texto)
                .build();

        Body body = Body.builder()
                .text(bodyText)
                .build();

        Message message = Message.builder()
                .subject(subject)
                .body(body)
                .build();

        SendEmailRequest request = SendEmailRequest.builder()
                .destination(destination)
                .message(message)
                .source(sourceEmail)
                .build();

        try {
            sesClient.sendEmail(request);
            System.out.println("Email enviado com sucesso!");
        } catch (SesException e) {
            System.err.println("Erro ao enviar email: " + e.awsErrorDetails().errorMessage());
        }
    }
}
