package sptech.school.order_hub.controller.usuario.response;

import lombok.Builder;
import sptech.school.order_hub.entitiy.Usuario;

@Builder
public record CadastroResponseDTO(
        String nomePessoa,
        String emailPessoa,
        String tiposDeUsuario,
        boolean representante,
        String firebaseUid
) {

    public static CadastroResponseDTO toEntity(Usuario usuario) {
        return CadastroResponseDTO.builder()
                .nomePessoa(usuario.getNomePessoa())
                .emailPessoa(usuario.getEmailPessoa())
                .tiposDeUsuario(usuario.getTiposDeUsuario().name())
                .representante(usuario.getRepresentante())
                .build();
    }

}
