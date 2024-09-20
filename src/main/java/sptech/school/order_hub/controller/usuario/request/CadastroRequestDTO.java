package sptech.school.order_hub.controller.usuario.request;

import sptech.school.order_hub.enuns.TipoUsuario;

public record CadastroRequestDTO(
        String nomePessoa,
        String emailPessoa,
        String senha,
        TipoUsuario tiposDeUsuario,
        boolean representante,
        String firebaseUid
) {

}
