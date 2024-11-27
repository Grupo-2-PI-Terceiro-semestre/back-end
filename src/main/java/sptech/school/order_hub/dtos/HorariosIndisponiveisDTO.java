package sptech.school.order_hub.dtos;

import lombok.Builder;

import java.sql.Time;

@Builder
public record HorariosIndisponiveisDTO(
         Time duracao,
         Time horaInicio,
         Time horaFinal
) {
    public static HorariosIndisponiveisDTO from(Time duracao, Time horaInicio, Time horaFinal) {
        return HorariosIndisponiveisDTO.builder()
                .duracao(duracao)
                .horaInicio(horaInicio)
                .horaFinal(horaFinal)
                .build();
    }
}
