package sptech.school.order_hub.controller.categoria.response;

import sptech.school.order_hub.entitiy.Categoria;

public record CategoriasResponseDTO(
        Integer idCategoria,
        String nomeCategoria
) {
    public static CategoriasResponseDTO fromEntity(Categoria categoria) {
        return new CategoriasResponseDTO(
                categoria.getIdCategoria(),
                categoria.getNome()
        );
    }
}
