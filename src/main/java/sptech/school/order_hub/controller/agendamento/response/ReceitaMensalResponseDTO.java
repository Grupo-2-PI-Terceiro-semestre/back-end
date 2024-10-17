package sptech.school.order_hub.controller.agendamento.response;

import lombok.Builder;

@Builder
public record ReceitaMensalResponseDTO(
     Double totalReceita,
     Double comparativoReceita
) {

public static ReceitaMensalResponseDTO from(Double totalReceita, Double comparativoReceita) {
        return ReceitaMensalResponseDTO.builder()
                .totalReceita(totalReceita)
                .comparativoReceita(comparativoReceita)
                .build();
    }


}
