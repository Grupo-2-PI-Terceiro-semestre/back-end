package sptech.school.order_hub.controller.cliente.request;

public record CriarClienteRequestDTO(
        Integer idPessoa,
        String nomePessoa,
        String emailPessoa,
        String numeroTelefone
) {

}
