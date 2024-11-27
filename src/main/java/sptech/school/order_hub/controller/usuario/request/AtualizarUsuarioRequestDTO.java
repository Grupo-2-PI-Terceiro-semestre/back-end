package sptech.school.order_hub.controller.usuario.request;

import sptech.school.order_hub.entitiy.Funcao;
import sptech.school.order_hub.enuns.TipoUsuario;

public record AtualizarUsuarioRequestDTO(
        Integer idPessoa,
        String nomePessoa,
        String numeroTelefone,
        String emailPessoa,
        Integer idFuncao
) {
}
