package sptech.school.order_hub.controller.usuario.response;

import lombok.Builder;
import sptech.school.order_hub.entitiy.Empresa;
import sptech.school.order_hub.entitiy.Usuario;
import sptech.school.order_hub.enuns.TipoUsuario;

@Builder
public record AuthResponseDTO(
        Integer id,
        String nome,
        TipoUsuario tipoUsuario,
        Integer idEmpresa
) {

    public static AuthResponseDTO from(Usuario usuario) {
        return AuthResponseDTO.builder()
                .id(usuario.getIdPessoa())
                .nome(usuario.getNomePessoa())
                .tipoUsuario(usuario.getTiposDeUsuario())
                .idEmpresa(usuario.getEmpresa() != null ? usuario.getEmpresa().getIdEmpresa() : null)
                .build();
    }

}
