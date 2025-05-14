package sptech.school.order_hub.services.queue;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.sqs.SqsAsyncClient;
import software.amazon.awssdk.services.sqs.model.SendMessageRequest;

@Service
public class SqsProducerService {

    private final SqsAsyncClient sqsClient;

    @Value("${aws.sqs.queue.name}")
    String QUEUE_URL;

    public SqsProducerService(SqsAsyncClient sqsClient) {
        this.sqsClient = sqsClient;
    }

    public void send(Object mensagem) {
        try {
            sqsClient.sendMessage(SendMessageRequest.builder()
                    .queueUrl(QUEUE_URL)
                    .messageBody(new ObjectMapper().writeValueAsString(mensagem))
                    .build());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

