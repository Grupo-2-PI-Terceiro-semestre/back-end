package sptech.school.order_hub.controller.servico.request;

import java.time.LocalTime;

public record AtualizarServicoRequestDTO(
        Integer idServico,
        String nomeServico,
        Double valorServico,
        LocalTime duracao,
        String descricao,
        String corReferenciaHex
) {
}
