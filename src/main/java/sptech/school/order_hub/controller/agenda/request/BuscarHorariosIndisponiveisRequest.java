package sptech.school.order_hub.controller.agenda.request;

import lombok.Builder;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;

@Builder
public record BuscarHorariosIndisponiveisRequest(
        Integer idEmpresa,
        Integer id_agenda,
        LocalDate data
) {
    public static BuscarHorariosIndisponiveisRequest from(Integer idEmpresa, Integer id_agenda, LocalDate data) {
        return BuscarHorariosIndisponiveisRequest.builder()
                .idEmpresa(idEmpresa)
                .id_agenda(id_agenda)
                .data(data)
                .build();
    }
}
