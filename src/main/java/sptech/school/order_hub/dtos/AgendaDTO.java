package sptech.school.order_hub.dtos;

import sptech.school.order_hub.entitiy.Agenda;

import java.util.List;
import java.util.stream.Collectors;

public record AgendaDTO(
        Integer idAgenda,
        List<AgendamentoDTO> agendamentos
) {

    public static AgendaDTO fromEntity(Agenda agenda) {
        List<AgendamentoDTO> agendamentoDTOs = agenda != null && agenda.getAgendamentos() != null
                ? agenda.getAgendamentos().stream()
                .map(AgendamentoDTO::from)
                .collect(Collectors.toList())
                : List.of();

        return new AgendaDTO(
                agenda != null ? agenda.getIdAgenda() : null,
                agendamentoDTOs
        );
    }
}
