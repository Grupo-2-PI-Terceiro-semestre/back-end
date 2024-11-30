package sptech.school.order_hub.controller.cliente.request;

import lombok.Builder;
import sptech.school.order_hub.enuns.StatusAtividade;

@Builder
public record CriarClienteRequestDTO(
        Integer idPessoa,
        String nomePessoa,
        String emailPessoa,
        String numeroTelefone,
        String senha,
        StatusAtividade statusAtividade
) {
}
