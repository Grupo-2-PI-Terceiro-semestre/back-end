package sptech.school.order_hub.controller.usuario.request;

import sptech.school.order_hub.entitiy.Funcao;
import sptech.school.order_hub.entitiy.Usuario;
import sptech.school.order_hub.enuns.TipoUsuario;

public record CadastroUsuarioRequestDTO(
        String nomePessoa,
        String emailPessoa,
        String senha,
        TipoUsuario tiposDeUsuario,
        boolean representante,
        String firebaseUid,
        String numeroTelefone,
        Funcao funcao
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
