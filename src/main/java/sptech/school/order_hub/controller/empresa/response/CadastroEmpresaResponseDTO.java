package sptech.school.order_hub.controller.empresa.response;

import lombok.Builder;
import sptech.school.order_hub.entitiy.Empresa;

@Builder
public record CadastroEmpresaResponseDTO(
        Integer idEmpresa,
        String nomeEmpresa,
        String emailEmpresa,
        String cnpj,
        String telefone
) {

    public static CadastroEmpresaResponseDTO from(Empresa empresa) {
        return CadastroEmpresaResponseDTO.builder()
                .idEmpresa(empresa.getIdEmpresa())
                .nomeEmpresa(empresa.getNomeEmpresa())
                .emailEmpresa(empresa.getEmailEmpresa())
                .cnpj(empresa.getCnpj())
                .telefone(empresa.getTelefone())
                .build();
    }
}
