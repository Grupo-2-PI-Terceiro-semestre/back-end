package sptech.school.order_hub.controller.servico.request;

import sptech.school.order_hub.enuns.StatusAtividade;

import java.time.LocalTime;

public record CadastrarResquestServicoDTO(
        String nomeServico,
        Double valorServico,
        LocalTime duracao,
        String corReferenciaHex,
        String descricao,
        StatusAtividade statusAtividade
) {
}
