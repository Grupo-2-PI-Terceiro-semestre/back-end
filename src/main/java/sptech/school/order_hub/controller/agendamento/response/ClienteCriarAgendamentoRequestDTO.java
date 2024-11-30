package sptech.school.order_hub.controller.agendamento.response;

import sptech.school.order_hub.enuns.StatusAgendamento;

import java.time.LocalDateTime;

public record ClienteCriarAgendamentoRequestDTO(
        Integer idCliente,
        Integer idServico,
        Integer idProfissional,
        LocalDateTime dataAgendamento,
        StatusAgendamento statusAgendamento
) {

}
