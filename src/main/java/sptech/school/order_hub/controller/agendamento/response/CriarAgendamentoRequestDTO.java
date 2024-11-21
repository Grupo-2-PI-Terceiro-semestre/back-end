package sptech.school.order_hub.controller.agendamento.response;

import sptech.school.order_hub.enuns.StatusAgendamento;

import java.time.LocalDateTime;

public record CriarAgendamentoRequestDTO(
        Integer idCliente,
        Integer idServico,
        Integer idAgenda,
        LocalDateTime dataAgendamento,
        StatusAgendamento statusAgendamento
) {

}
