package sptech.school.order_hub.controller.cliente.request;

import sptech.school.order_hub.enuns.StatusAtividade;

public record CriarClienteRequestDTO(
        Integer idPessoa,
        String nomePessoa,
        String emailPessoa,
        String numeroTelefone,
        StatusAtividade statusAtividade
) {

}
