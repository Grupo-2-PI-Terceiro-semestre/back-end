package sptech.school.order_hub.services.queue;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import lombok.extern.slf4j.XSlf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.sqs.SqsAsyncClient;
import software.amazon.awssdk.services.sqs.model.SendMessageRequest;

@Service
@Slf4j
public class SqsProducerService {

    private final SqsAsyncClient sqsClient;

    @Value("${aws.sqs.queue.name}")
    String QUEUE_URL;

    public SqsProducerService(SqsAsyncClient sqsClient) {
        this.sqsClient = sqsClient;
    }

    public void send(Object mensagem) {
        try {

            String json = new ObjectMapper().writeValueAsString(mensagem);

            sqsClient.sendMessage(SendMessageRequest.builder()
                    .queueUrl(QUEUE_URL)
                    .messageBody(json)
                    .build()
            ).whenComplete((response, exception) -> {
                if (exception != null) {
                    log.error("Erro ao enviar mensagem para SQS", exception);
                } else {
                    log.info("###################################################################");
                    log.info("Mensagem enviada com sucesso. MessageId: {}", response.messageId());
                    log.info("###################################################################");
                }
            });

        } catch (Exception e) {
            log.error("Erro ao serializar a mensagem", e);
        }
    }

}

