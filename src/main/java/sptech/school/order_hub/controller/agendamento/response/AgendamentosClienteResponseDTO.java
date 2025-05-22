package sptech.school.order_hub.controller.agendamento.response;

import lombok.Builder;
import sptech.school.order_hub.entitiy.Agendamento;

import java.time.LocalDateTime;

@Builder
public record AgendamentosClienteResponseDTO(
        Integer idAgendamento,
        String nomeServico,
        String nomeEmpresa,
        LocalDateTime dataHora,
        String status,
        String atendente
) {
    public static AgendamentosClienteResponseDTO from(Agendamento agendamento) {
        return AgendamentosClienteResponseDTO.builder()
                .idAgendamento(agendamento
                        .getIdAgendamento()
                )
                .nomeServico(
                        agendamento.getServico()
                                .getNomeServico()
                )
                .nomeEmpresa(
                        agendamento.getAgenda()
                                .getUsuario()
                                .getEmpresa()
                                .getNomeEmpresa()
                )
                .dataHora(agendamento.getDataHora())
                .status(agendamento
                        .getStatusAgendamento()
                )
                .atendente(
                        agendamento.getAgenda()
                                .getUsuario()
                                .getNomePessoa()
                )
                .build();
    }
}
