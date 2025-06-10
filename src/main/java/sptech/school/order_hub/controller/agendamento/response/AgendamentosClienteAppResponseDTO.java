package sptech.school.order_hub.controller.agendamento.response;

import lombok.Builder;
import sptech.school.order_hub.dtos.ProficionalDTO;
import sptech.school.order_hub.entitiy.Agendamento;

import java.time.LocalDateTime;
import java.util.List;

@Builder
public record AgendamentosClienteAppResponseDTO(
        Integer idAgendamento,
        String nomeServico,
        String nomeEmpresa,
        LocalDateTime dataHora,
        String status,
        String atendente,
        List<ProficionalDTO> proficionaisDaEmpresa
) {
    public static AgendamentosClienteAppResponseDTO from(Agendamento agendamento, List<ProficionalDTO> proficionals) {
        return AgendamentosClienteAppResponseDTO.builder()
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
                .proficionaisDaEmpresa(proficionals)
                .build();
    }
}
