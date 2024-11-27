package sptech.school.order_hub.controller.agendamento.response;

import lombok.Builder;

@Builder
public record ReceitaPorMesDTO (
        String ano_mes,
        Double receita
) {

    public static ReceitaPorMesDTO from(String ano_mes, Double receita) {
        return ReceitaPorMesDTO.builder()
            .ano_mes(ano_mes)
            .receita(receita)
            .build();
    }
}
