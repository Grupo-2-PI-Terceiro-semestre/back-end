package sptech.school.order_hub.controller.agendamento.response;

import lombok.Builder;

@Builder
public record TicketMedioResponseDTO(
        Double ticketMedio
        )
{
    public static TicketMedioResponseDTO from(Double ticketMedio) {
        return TicketMedioResponseDTO.builder()
            .ticketMedio(ticketMedio)
            .build();
    }
}

