package sptech.school.order_hub.controller.servico.response;

import lombok.Builder;
import sptech.school.order_hub.entitiy.Paginacao;
import sptech.school.order_hub.entitiy.Servico;

import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Builder
public record BuscarServicosPaginadosResponseDTO(
        List<ServicoDTO> itens,
        Long totalItens,
        Boolean ultimaPagina
) {
    public static BuscarServicosPaginadosResponseDTO fromEntity(Paginacao<Servico> paginacao) {
        List<ServicoDTO> servicoDTOS = paginacao.itens().stream()
                .map(servico -> new ServicoDTO(
                        servico.getIdServico(),
                        servico.getNomeServico(),
                        servico.getValorServico(),
                        servico.getDescricao(),
                        servico.getCorReferenciaHex(),
                        servico.getDuracao()
                ))
                .collect(Collectors.toList());

        return BuscarServicosPaginadosResponseDTO.builder()
                .itens(servicoDTOS)
                .totalItens(paginacao.tamanhoTotalItens())
                .ultimaPagina(paginacao.ultimaPagina())
                .build();
    }

    private record ServicoDTO(
            Integer idServico,
            String nomeServico,
            Double valorServico,
            String descricao,
            String corReferenciaHex,
            LocalTime duracao
    ) {

    }
}