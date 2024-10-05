package sptech.school.order_hub.controller.usuario.response;

import sptech.school.order_hub.entitiy.Empresa;
import sptech.school.order_hub.enuns.TipoUsuario;

public record AuthResponseDTO(
        Integer id,
        String nome,
        TipoUsuario tipoUsuario,
        Integer idEmpresa
) {
}
