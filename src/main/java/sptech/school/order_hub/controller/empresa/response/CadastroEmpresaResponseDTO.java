package sptech.school.order_hub.controller.empresa.response;

import lombok.Builder;
import sptech.school.order_hub.dtos.EnderecoDTO;
import sptech.school.order_hub.dtos.UsuarioDTO;
import sptech.school.order_hub.entitiy.Empresa;

import java.util.List;

@Builder
public record CadastroEmpresaResponseDTO(
        int idEmpresa,
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
