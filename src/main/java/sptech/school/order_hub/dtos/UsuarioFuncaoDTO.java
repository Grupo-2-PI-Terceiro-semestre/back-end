package sptech.school.order_hub.dtos;

import lombok.Builder;
import sptech.school.order_hub.entitiy.Funcao;
import sptech.school.order_hub.entitiy.Usuario;

@Builder
public record UsuarioFuncaoDTO(
        int idUsuario,
        String nomePessoa,
        String numeroTelefone,
        String emailPessoa,
        Integer idFuncao
) {

    public static UsuarioFuncaoDTO from(Usuario usuario) {
        return UsuarioFuncaoDTO.builder()
                .idUsuario(usuario.getIdPessoa())
                .nomePessoa(usuario.getNomePessoa())
                .numeroTelefone(usuario.getNumeroTelefone())
                .emailPessoa(usuario.getEmailPessoa())
                .build();
    }

    public static Usuario toEntity(UsuarioFuncaoDTO usuarioFuncaoDTO) {
        return Usuario.builder()
                .nomePessoa(usuarioFuncaoDTO.nomePessoa())
                .numeroTelefone(usuarioFuncaoDTO.numeroTelefone())
                .emailPessoa(usuarioFuncaoDTO.emailPessoa())
                .funcao(new Funcao(usuarioFuncaoDTO.idFuncao(), ""))
                .build();
    }

}
