package sptech.school.order_hub.dtos;

import lombok.Builder;
import sptech.school.order_hub.entitiy.Cliente;

@Builder
public record ClienteDTO(
        int idCliente,
        String nomePessoa,
        String telefone
) {
    public static ClienteDTO from(Cliente cliente) {
        return ClienteDTO.builder()
                .idCliente(cliente.getIdPessoa())
                .nomePessoa(cliente.getNomePessoa())
                .telefone(cliente.getNumeroTelefone())
                .build();
    }
}
