package sptech.school.order_hub.controller.agendamento.response;

import lombok.Builder;

@Builder
public record ServicoMensalResponseDTO(
        Integer totalServicos,
        Double comparativoServicos)
{
    public static ServicoMensalResponseDTO from(Integer totalServicos, Double comparativoServicos) {
        return ServicoMensalResponseDTO.builder()
            .totalServicos(totalServicos)
            .comparativoServicos(comparativoServicos)
            .build();
    }
}
