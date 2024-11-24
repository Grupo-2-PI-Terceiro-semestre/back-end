package sptech.school.order_hub.dtos;

import lombok.Builder;
import sptech.school.order_hub.entitiy.Funcao;
import sptech.school.order_hub.entitiy.Usuario;

@Builder
public record UsuarioDTO(
        int idUsuario,
        String nomePessoa,
        String numeroTelefone,
        String cpf,
//        String dataNascimento,
//        String sexo,
//        String idEndereco,
        Funcao funcao
) {
    public static UsuarioDTO from(Usuario usuario) {
        return UsuarioDTO.builder()
                .idUsuario(usuario.getIdPessoa())
                .nomePessoa(usuario.getNomePessoa())
                .numeroTelefone(usuario.getNumeroTelefone())
                .cpf(usuario.getCpf())
//                .dataNascimento(usuario.getD)
//                .sexo(usuario.getS)
//                .idEndereco(usuario.getEndereco())
                .build();
    }

    public static Usuario toEntity(UsuarioDTO usuarioDTO) {
        return Usuario.builder()
                .nomePessoa(usuarioDTO.nomePessoa())
                .numeroTelefone(usuarioDTO.numeroTelefone())
                .cpf(usuarioDTO.cpf())
                .funcao(usuarioDTO.funcao())
                .build();
    }
}
