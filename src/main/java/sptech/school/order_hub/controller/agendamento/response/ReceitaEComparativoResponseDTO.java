package sptech.school.order_hub.controller.agendamento.response;

import lombok.Builder;

@Builder
public record ReceitaEComparativoResponseDTO(
     Double totalReceita,
     Double comparativoReceita
) {

public static ReceitaEComparativoResponseDTO from(Double totalReceita, Double comparativoReceita) {
        return ReceitaEComparativoResponseDTO.builder()
                .totalReceita(totalReceita)
                .comparativoReceita(comparativoReceita)
                .build();
    }


}
