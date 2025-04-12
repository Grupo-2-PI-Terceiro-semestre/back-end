package sptech.school.order_hub.controller.cliente.request;

public record AtualizarClienteCompletoRequestDTO(
        Integer idPessoa,
        String nomePessoa,
        String numeroTelefone,
        String dataNascimento,
        String genero
){
}
