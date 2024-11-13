package sptech.school.order_hub.controller.agendamento.request;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record AtualizarAgendamentoParcialRequestDTO(
        Integer idAgendamento,
        LocalDateTime horaAgendamento,
        Integer idAgenda
) {
    public static AtualizarAgendamentoParcialRequestDTO from(Integer idAgendamento, LocalDateTime horaAgendamento, Integer idAgenda, Integer idCliente, Integer idServico, Integer idUsuario) {
        return AtualizarAgendamentoParcialRequestDTO.builder()
                .idAgendamento(idAgendamento)
                .horaAgendamento(horaAgendamento)
                .idAgenda(idAgenda)
                .build();
    }
}
