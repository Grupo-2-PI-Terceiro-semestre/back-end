package sptech.school.order_hub.controller.empresa.response;

import lombok.Builder;
import sptech.school.order_hub.entitiy.Empresa;

@Builder
public record BuscarEmpresaResponseDTO(
        String nomeEmpresa,
        String emailEmpresa,
        String telefone,
        String urlLogo
) {

    public static BuscarEmpresaResponseDTO from(Empresa empresa) {
        return BuscarEmpresaResponseDTO.builder()
                .nomeEmpresa(empresa.getNomeEmpresa())
                .emailEmpresa(empresa.getEmailEmpresa())
                .telefone(empresa.getTelefone())
                .urlLogo(empresa.getUrlImagem())
                .build();
    }

}
