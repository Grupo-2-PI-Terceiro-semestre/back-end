package sptech.school.order_hub.controller.agendamento.response;

import java.time.LocalDateTime;

public record CriarAgendamentoRequestDTO(
        Integer idCliente,
        Integer idServico,
        Integer idAgenda,
        LocalDateTime dataAgendamento
) {

}
