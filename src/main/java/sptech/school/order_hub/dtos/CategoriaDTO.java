package sptech.school.order_hub.dtos;

import lombok.Builder;
import sptech.school.order_hub.entitiy.Categoria;
import sptech.school.order_hub.entitiy.Empresa;

import java.util.HashSet;
import java.util.List;

@Builder
public record CategoriaDTO(
        String nome
) {

    public Categoria toEntity() {

        return Categoria.builder()
                .nome(nome)
                .build();
    }
}
