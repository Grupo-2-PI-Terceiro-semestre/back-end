package sptech.school.order_hub.services.queue;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.awspring.cloud.sqs.annotation.SqsListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class SqsListenerService {



    private final SimpMessagingTemplate messagingTemplate;

    public SqsListenerService(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @SqsListener(value = "${aws.sqs.queue.name}")
    public void consumer(String messageJson) throws JsonProcessingException {
        try {
            ObjectMapper mapper = new ObjectMapper();
            MessageEmpresa message = mapper.readValue(messageJson, MessageEmpresa.class);

            String destino = "/topic/" + message.empresaId() + "/notifications";
            messagingTemplate.convertAndSend(destino, message.mensagem());

        } catch (Exception e) {
            System.err.println("Erro ao processar mensagem: " + e.getMessage());
            throw e;
        }
    }
}

