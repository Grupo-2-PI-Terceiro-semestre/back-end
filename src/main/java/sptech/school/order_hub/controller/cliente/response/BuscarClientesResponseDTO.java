package sptech.school.order_hub.controller.cliente.response;

import lombok.Builder;
import sptech.school.order_hub.entitiy.Cliente;

@Builder
public record BuscarClientesResponseDTO(
        Integer idCliente,
        String nomePessoa
) {

    public static BuscarClientesResponseDTO fromEntity(Cliente cliente) {
        return BuscarClientesResponseDTO.builder()
                .idCliente(cliente.getIdPessoa())
                .nomePessoa(cliente.getNomePessoa())
                .build();
    }
}
