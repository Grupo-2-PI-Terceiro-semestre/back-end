package sptech.school.order_hub.controller.agendamento.response;

import lombok.Builder;

@Builder
public record ServicoDiaSemanaResponseDTO(
        Integer dia_semana,
        Integer totalServicos
) {

        public static ServicoDiaSemanaResponseDTO from(Integer dia_semana, Integer totalServicos) {
            return ServicoDiaSemanaResponseDTO.builder()
                .dia_semana(dia_semana)
                .totalServicos(totalServicos)
                .build();
        }
}
