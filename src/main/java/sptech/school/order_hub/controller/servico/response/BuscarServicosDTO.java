package sptech.school.order_hub.controller.servico.response;

import lombok.Builder;
import sptech.school.order_hub.entitiy.Servico;

@Builder
public record BuscarServicosDTO(
        Integer idServico,
        String nomeServico
) {

    public static BuscarServicosDTO from(Servico servico) {
        return BuscarServicosDTO.builder()
                .idServico(servico.getIdServico())
                .nomeServico(servico.getNomeServico())
                .build();
    }
}
