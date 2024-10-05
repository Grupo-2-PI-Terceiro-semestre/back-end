package sptech.school.order_hub.controller.agendamento.request;

import java.time.LocalDate;

public record BuscarAgendamentoRequestDTO(
        LocalDate dataAgendamento
) {
    public BuscarAgendamentoRequestDTO {
        if (dataAgendamento == null) {
            // Caso a data seja nula, você pode definir um valor padrão ou lançar uma exceção
            throw new IllegalArgumentException("A data não pode ser nula");
        }
    }

    public static BuscarAgendamentoRequestDTO of(LocalDate dataAgendamento) {
        return new BuscarAgendamentoRequestDTO(dataAgendamento);
    }
}
