package sptech.school.order_hub.controller.cliente.response;

import sptech.school.order_hub.controller.empresa.response.BuscarEmpresaResponseDTO;
import sptech.school.order_hub.controller.usuario.response.AuthResponseDTO;
import sptech.school.order_hub.entitiy.Empresa;
import sptech.school.order_hub.entitiy.Usuario;

public record PerfilAtualizadoDTO(
        AuthResponseDTO usuario,
        BuscarEmpresaResponseDTO empresa
) {

    public static PerfilAtualizadoDTO toPerfil(Usuario usuario, Empresa empresa) {
        return new PerfilAtualizadoDTO(
                AuthResponseDTO.from(usuario),
                BuscarEmpresaResponseDTO.from(empresa)
        );
    }
}
