package sptech.school.order_hub.controller.empresa.response;

import lombok.Builder;
import sptech.school.order_hub.entitiy.Empresa;

@Builder
public record BuscarEmpresaResponseDTO(
        String nomeEmpresa,
        String emailEmpresa,
        String telefone,
        String cnpj,
        String nomeCategoria,
        String urlLogo
) {

    public static BuscarEmpresaResponseDTO from(Empresa empresa) {
        return BuscarEmpresaResponseDTO.builder()
                .nomeEmpresa(empresa.getNomeEmpresa())
                .emailEmpresa(empresa.getEmailEmpresa())
                .telefone(empresa.getTelefone())
                .cnpj(empresa.getCnpj())
                .nomeCategoria(empresa.getCategoria() != null ? empresa.getCategoria().getNome() : null)
                .urlLogo(empresa.getUrlLogo())
                .build();
    }
}
