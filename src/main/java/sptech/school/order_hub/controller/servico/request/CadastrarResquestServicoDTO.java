package sptech.school.order_hub.controller.servico.request;

import java.time.LocalTime;

public record CadastrarResquestServicoDTO(
        String nomeServico,
        Double valorServico,
        LocalTime duracao,
        String corReferenciaHex,
        String descricao
) {
}
