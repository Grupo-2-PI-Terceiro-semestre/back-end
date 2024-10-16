package sptech.school.order_hub.dtos;

import lombok.Builder;
import sptech.school.order_hub.entitiy.Agendamento;
import sptech.school.order_hub.enuns.StatusAgendamento;

import java.time.LocalDateTime;

@Builder
public record AgendamentoDTO(
        Integer idAgendamento,
        LocalDateTime horaAgendamento,
        String statusAgendamento,
        ClienteDTO cliente,
        ServicoDTO servico
) {

    public static AgendamentoDTO from(Agendamento agendamento) {
        return AgendamentoDTO.builder()
                .idAgendamento(agendamento.getIdAgendamento())
                .horaAgendamento(agendamento.getDataHora())
                .statusAgendamento(agendamento.getStatusAgendamento())
                .cliente(ClienteDTO.from(agendamento.getCliente()))
                .servico(ServicoDTO.fromEntity(agendamento.getServico()))
                .build();
    }
}
