package sptech.school.order_hub.dtos;

public record UsuarioDTO(
        int idUsuario,
        String nomePessoa,
        String telefone,
        String cpf,
        String dataNascimento,
        String sexo,
        String idEndereco
) {
}
