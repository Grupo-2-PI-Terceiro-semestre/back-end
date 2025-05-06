package sptech.school.order_hub.dtos;


import lombok.Builder;
import sptech.school.order_hub.config_exception.exceptions.SemConteudoException;
import sptech.school.order_hub.entitiy.Notificacao;

@Builder
public record NotificacaoDTO(
        Integer idNotificacao,
        String mensagemAgendamento,
        String mensagemCancelamento
) {
    public static Notificacao toEntity(NotificacaoDTO notificacao) {

        if (notificacao.idNotificacao() == null) {
            return Notificacao.builder()
                    .mensagemCancelamento(notificacao.mensagemCancelamento())
                    .mensagemAgendamento(notificacao.mensagemAgendamento())
                    .build();
        }

        return Notificacao.builder()
                .idNotificacao(notificacao.idNotificacao())
                .mensagemCancelamento(notificacao.mensagemCancelamento())
                .mensagemAgendamento(notificacao.mensagemAgendamento())
                .build();
    }

    public static NotificacaoDTO fromEntity(Notificacao notificacao) {

        if(notificacao == null) {
            throw new SemConteudoException("Notificação não encontrada.");
        }

        return new NotificacaoDTO(
                notificacao.getIdNotificacao() ,
                notificacao.getMensagemAgendamento(),
                notificacao.getMensagemCancelamento()
        );
    }
}
