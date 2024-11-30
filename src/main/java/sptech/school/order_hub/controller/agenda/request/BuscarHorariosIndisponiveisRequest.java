package sptech.school.order_hub.controller.agenda.request;

import lombok.Builder;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;

@Builder
public record BuscarHorariosIndisponiveisRequest(
        Integer idProfissional,
        Integer idServico,
        LocalDate data
) {
    public static BuscarHorariosIndisponiveisRequest from(Integer idProfissional, LocalDate data, Integer idServico) {
        return BuscarHorariosIndisponiveisRequest.builder()
                .idServico(idServico)
                .idProfissional(idProfissional)
                .data(data)
                .build();
    }
}
