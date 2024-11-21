package sptech.school.order_hub.controller.agendamento.response;

import lombok.Builder;

@Builder
public record ClientesMensaisResponseDTO(
        Integer totalClientes,
        Double comparativoClientes
) {

    public static ClientesMensaisResponseDTO from(Integer totalClientes, Double comparativoClientes) {
        return ClientesMensaisResponseDTO.builder()
            .totalClientes(totalClientes)
            .comparativoClientes(comparativoClientes)
            .build();
    }

}
