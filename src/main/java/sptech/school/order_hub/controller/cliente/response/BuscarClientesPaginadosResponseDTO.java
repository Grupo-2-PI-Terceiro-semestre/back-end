package sptech.school.order_hub.controller.cliente.response;

import lombok.Builder;
import sptech.school.order_hub.entitiy.Cliente;
import sptech.school.order_hub.entitiy.Paginacao;

import java.util.stream.Collectors;

import java.util.List;

@Builder
public record BuscarClientesPaginadosResponseDTO(
        List<ClienteDto> itens,
        Long totalItens,
        Boolean ultimaPagina
) {

    public static BuscarClientesPaginadosResponseDTO fromEntity(Paginacao<Cliente> paginacao) {
        List<ClienteDto> clienteDtos = paginacao.itens().stream()
                .map(cliente -> new ClienteDto(
                        cliente.getIdPessoa(),
                        cliente.getNomePessoa(),
                        cliente.getEmailPessoa(),
                        cliente.getNumeroTelefone()
                ))
                .collect(Collectors.toList());

        return BuscarClientesPaginadosResponseDTO.builder()
                .itens(clienteDtos)
                .totalItens(paginacao.tamanhoTotalItens())
                .ultimaPagina(paginacao.ultimaPagina())
                .build();
    }

    private record ClienteDto(
            Integer idCliente,
            String nomePessoa,
            String email,
            String telefone
    ) {

    }

}
