package sptech.school.order_hub.controller.empresa.response;

import lombok.Builder;
import sptech.school.order_hub.entitiy.Imagem;

@Builder
public record BuscarImagensDTO(
        Integer idImagem,
        String urlImagem
) {
    public static BuscarImagensDTO from(Imagem imagem){
        return BuscarImagensDTO.builder()
                .idImagem(imagem.getIdImagem())
                .urlImagem(imagem.getUrlImagem())
                .build();
    }
}
