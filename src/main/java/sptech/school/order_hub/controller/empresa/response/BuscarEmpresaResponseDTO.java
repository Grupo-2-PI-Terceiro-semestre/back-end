package sptech.school.order_hub.controller.empresa.response;

import lombok.Builder;
import sptech.school.order_hub.entitiy.Categoria;
import sptech.school.order_hub.entitiy.Empresa;

@Builder
public record BuscarEmpresaResponseDTO(
        String nomeEmpresa,
        String emailEmpresa,
        String telefone,
        String cnpj,
        CategoriaDTO categoria,
        String urlLogo
) {

    public static BuscarEmpresaResponseDTO from(Empresa empresa) {
        return BuscarEmpresaResponseDTO.builder()
                .nomeEmpresa(empresa.getNomeEmpresa())
                .emailEmpresa(empresa.getEmailEmpresa())
                .telefone(empresa.getTelefone())
                .cnpj(empresa.getCnpj())
                .categoria(empresa.getCategoria() != null ? CategoriaDTO.from(empresa.getCategoria()) : null)
                .urlLogo(empresa.getUrlLogo())
                .build();
    }

    @Builder
    public record CategoriaDTO(
            Integer idCategoria,
            String nome
    ) {
        public static CategoriaDTO from(Categoria categoria) {
            return new CategoriaDTO(
                    categoria.getIdCategoria(),
                    categoria.getNome()
            );
        }
    }
}


