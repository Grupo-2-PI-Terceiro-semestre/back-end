package sptech.school.order_hub.controller.agendamento.response;

import lombok.Builder;

@Builder
public record ReceitaPorServicoResponseDTO(
        String servico,
        Double receita
) {

    public static ReceitaPorServicoResponseDTO from(String servico, Double receita) {
        return ReceitaPorServicoResponseDTO.builder()
            .servico(servico)
            .receita(receita)
            .build();
    }

}
