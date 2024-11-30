package sptech.school.order_hub.controller.cliente.request;

public record AtualizarClienteRequestDTO (
        Integer idPessoa,
        String nomePessoa,
        String numeroTelefone,
        String emailPessoa
){
}
