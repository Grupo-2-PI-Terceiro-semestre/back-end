package sptech.school.order_hub.controller.usuario.request;

import jakarta.validation.constraints.NotBlank;
import sptech.school.order_hub.entitiy.Usuario;
import sptech.school.order_hub.enuns.TipoUsuario;

public record CadastroRequestDTO(
        String nomePessoa,

        String emailPessoa,
        String senha,
        TipoUsuario tiposDeUsuario,
        boolean representante,
        String firebaseUid
) {
    public Usuario toEntity() {
        return Usuario.builder()
                .nomePessoa(nomePessoa)
                .emailPessoa(emailPessoa)
                .senha(senha)
                .tiposDeUsuario(tiposDeUsuario)
                .representante(representante)
                .firebaseUid(firebaseUid)
                .build();
    }

}
