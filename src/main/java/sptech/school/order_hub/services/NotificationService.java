package sptech.school.order_hub.services;

import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import sptech.school.order_hub.config_exception.exceptions.NotificationException;
import sptech.school.order_hub.entitiy.AcaoNotificacao;
import sptech.school.order_hub.entitiy.Empresa;
import sptech.school.order_hub.repository.AcaoNotificacaoRepository;
import sptech.school.order_hub.dtos.MessageEmpresa;

import java.util.List;

@Service
public class NotificationService {

    private static final Logger logger = LoggerFactory.getLogger(NotificationService.class);

    private final AcaoNotificacaoRepository repository;

    public NotificationService(AcaoNotificacaoRepository repository) {
        this.repository = repository;
    }

    /**
     * Envia uma notificação para a fila SQS com base no ID da empresa
     *
     * @param empresa ID da empresa
     * @param message mensagem a ser enviada (pode ser string ou JSON serializado)
     */
    public void sendNotificationToEmpresa(Empresa empresa, String message) {
        validateEmpresaId(empresa.getIdEmpresa().toString());

        try {
            var menssagem = new MessageEmpresa(empresa, message);

            var entity = AcaoNotificacao.fromEntity(menssagem);

            repository.save(entity);

            logger.info("Mensagem enviada com sucesso para a fila. Empresa: {}", empresa.getIdEmpresa());

        } catch (Exception e) {
            String errorMsg = "Falha ao enviar mensagem para a fila da empresa " + empresa.getIdEmpresa();
            logger.error(errorMsg, e);
            throw new NotificationException(errorMsg, e);
        }
    }

    @Transactional
    public void buscarMensagensNaoLidas(String empresaId) {

        List<Long> notificacoesNaoLidas = repository.buscarListaNoficacaoNaoEnviadaPorEmpresaId(empresaId);

        for (Long id : notificacoesNaoLidas) {
            AcaoNotificacao acao = repository.BuscarNotificaoNaoNotificacaoLida(id);
            acao.setEnviada(false);
            repository.save(acao);
        }
    }

    private void validateEmpresaId(String empresaId) {
        if (empresaId == null || empresaId.trim().isEmpty()) {
            String errorMsg = "ID da empresa não pode ser nulo ou vazio";
            logger.error(errorMsg);
            throw new IllegalArgumentException(errorMsg);
        }
    }

    public void marcarComoLida(String empresaId) {

        List<Long> notificacoesNaoLidas = repository.buscarListaNoficacaoNaoEnviadaPorEmpresaId(empresaId);

        for (Long id : notificacoesNaoLidas) {
            AcaoNotificacao acao = repository.findById(id)
                    .orElseThrow(() -> new NotificationException("Ação de notificação não encontrada com o ID: " + id));
            acao.setLida(true);
            acao.setEnviada(true);
            repository.save(acao);
        }
    }
}
