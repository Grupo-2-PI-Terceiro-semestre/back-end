package sptech.school.order_hub.dtos;

import sptech.school.order_hub.entitiy.Usuario;

public record ProficionalDTO(
        Integer idUsuario,
        String nomePessoa
) {
    public static ProficionalDTO from(Usuario usuario) {
        return new ProficionalDTO(
                usuario.getIdPessoa(),
                usuario.getNomePessoa()
        );
    }
}