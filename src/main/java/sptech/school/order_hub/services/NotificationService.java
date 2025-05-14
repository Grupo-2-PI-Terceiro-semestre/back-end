package sptech.school.order_hub.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import sptech.school.order_hub.config_exception.exceptions.NotificationException;
import sptech.school.order_hub.services.queue.MessageEmpresa;
import sptech.school.order_hub.services.queue.SqsProducerService;

@Service
public class NotificationService {

    private static final Logger logger = LoggerFactory.getLogger(NotificationService.class);

    private final SqsProducerService sqs;

    public NotificationService(SqsProducerService sqs) {
        this.sqs = sqs;
    }

    /**
     * Envia uma notificação para a fila SQS com base no ID da empresa
     * @param empresaId ID da empresa
     * @param message mensagem a ser enviada (pode ser string ou JSON serializado)
     */
    public void sendNotificationToEmpresa(String empresaId, String message) {
        validateEmpresaId(empresaId);

        try {
            var fullMessage = new MessageEmpresa(empresaId, message);

            sqs.send(fullMessage);

            logger.info("Mensagem enviada com sucesso para a fila. Empresa: {}", empresaId);

        } catch (Exception e) {
            String errorMsg = "Falha ao enviar mensagem para a fila da empresa " + empresaId;
            logger.error(errorMsg, e);
            throw new NotificationException(errorMsg, e);
        }
    }

    private void validateEmpresaId(String empresaId) {
        if (empresaId == null || empresaId.trim().isEmpty()) {
            String errorMsg = "ID da empresa não pode ser nulo ou vazio";
            logger.error(errorMsg);
            throw new IllegalArgumentException(errorMsg);
        }
    }
}
