package sptech.school.order_hub.controller.agendamento.request;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record AtualizarAgendamentoDinamicoRequestDTO(
        Integer idAgendamento,
        LocalDateTime horaAgendamento,
        Integer idAgenda
) {
    public static AtualizarAgendamentoDinamicoRequestDTO from(Integer idAgendamento, LocalDateTime horaAgendamento, Integer idAgenda, Integer idCliente, Integer idServico, Integer idUsuario) {
        return AtualizarAgendamentoDinamicoRequestDTO.builder()
                .idAgendamento(idAgendamento)
                .horaAgendamento(horaAgendamento)
                .idAgenda(idAgenda)
                .build();
    }
}
