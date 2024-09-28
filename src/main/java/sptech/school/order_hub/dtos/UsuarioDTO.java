package sptech.school.order_hub.dtos;

public record UsuarioDTO(
        int idUsuario,
        String nome,
        String email,
        String senha,
        String telefone,
        String cpf,
        String dataNascimento,
        String sexo,
        String idEndereco
) {
}
