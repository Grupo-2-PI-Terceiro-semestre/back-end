package sptech.school.order_hub.controller.usuario.response;

import lombok.Builder;
import sptech.school.order_hub.entitiy.Usuario;

@Builder
public record CadastroUsuarioResponseDTO(
        String nomePessoa,
        String emailPessoa,
        String tiposDeUsuario,
        boolean representante,
        String firebaseUid
) {

    public static CadastroUsuarioResponseDTO toEntity(Usuario usuario) {
        return CadastroUsuarioResponseDTO.builder()
                .nomePessoa(usuario.getNomePessoa())
                .emailPessoa(usuario.getEmailPessoa())
                .tiposDeUsuario(usuario.getTiposDeUsuario().name())
                .representante(usuario.getRepresentante())
                .build();
    }

}
