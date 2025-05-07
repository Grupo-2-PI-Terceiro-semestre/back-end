package sptech.school.order_hub.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sptech.school.order_hub.config_exception.exceptions.NotificationException;

@Service
public class NotificationService {

    private static final Logger logger = LoggerFactory.getLogger(NotificationService.class);

    private final SimpMessagingTemplate messagingTemplate;

    @Autowired
    public NotificationService(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    /**
     * Envia notificação para um tópico específico de uma empresa
     * @param empresaId ID da empresa destinatária (não pode ser nulo ou vazio)
     * @param message Objeto de mensagem a ser enviado (pode ser DTO, String, etc.)
     * @throws IllegalArgumentException se empresaId for inválido
     * @throws NotificationException se ocorrer erro no envio
     */
    public void sendNotificationToEmpresa(String empresaId, Object message) {
        validateEmpresaId(empresaId);

        try {
            String destination = "/topic/" + empresaId.trim() + "/notifications";
            logger.debug("Enviando notificação para {}: {}", destination, message);

            messagingTemplate.convertAndSend(destination, message);
            logger.info("Notificação enviada com sucesso para empresa {}", empresaId);

        } catch (Exception e) {
            String errorMsg = "Falha ao enviar notificação para empresa " + empresaId;
            logger.error(errorMsg, e);
            throw new NotificationException(errorMsg, e);
        }
    }

    /**
     * Valida o ID da empresa
     * @param empresaId ID a ser validado
     * @throws IllegalArgumentException se o ID for inválido
     */
    private void validateEmpresaId(String empresaId) {
        if (empresaId == null || empresaId.trim().isEmpty()) {
            String errorMsg = "ID da empresa não pode ser nulo ou vazio";
            logger.error(errorMsg);
            throw new IllegalArgumentException(errorMsg);
        }
    }
}