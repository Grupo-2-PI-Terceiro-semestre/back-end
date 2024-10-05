package sptech.school.order_hub.controller.agendamento.response;

import lombok.Builder;
import sptech.school.order_hub.entitiy.Cliente;

@Builder
public record BuscarAgendamentoResponseDTO(
        Integer idCliente,
        String nomeCliente,
        String telefone
) {

    public static BuscarAgendamentoResponseDTO fromEntity(Cliente cliente) {
        return BuscarAgendamentoResponseDTO.builder()
                .idCliente(cliente.getIdPessoa())
                .nomeCliente(cliente.getNomePessoa())
                .telefone(cliente.getNumeroTelefone())
                .build();
    }
}
