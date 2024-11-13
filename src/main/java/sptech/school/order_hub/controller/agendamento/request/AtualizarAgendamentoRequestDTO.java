package sptech.school.order_hub.controller.agendamento.request;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record AtualizarAgendamentoRequestDTO(
        Integer idAgendamento,
        Integer idCliente,
        Integer idServico,
        Integer idAgenda,
        LocalDateTime dataAgendamento
) {

}
