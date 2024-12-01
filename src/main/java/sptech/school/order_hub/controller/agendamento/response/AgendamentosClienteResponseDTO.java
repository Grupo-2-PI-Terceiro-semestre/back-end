package sptech.school.order_hub.controller.agendamento.response;

import lombok.Builder;

import java.time.LocalDate;
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
    public static AgendamentosClienteResponseDTO from(Integer idAgendamento, String nomeServico, String nomeEmpresa, LocalDateTime dataHora, String status, String atendente) {
        return AgendamentosClienteResponseDTO.builder()
            .idAgendamento(idAgendamento)
            .nomeServico(nomeServico)
            .nomeEmpresa(nomeEmpresa)
            .dataHora(dataHora)
            .status(status)
            .atendente(atendente)
            .build();
    }
}
