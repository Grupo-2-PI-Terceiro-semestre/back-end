package sptech.school.order_hub.controller.cliente.response;

import lombok.Builder;
import sptech.school.order_hub.entitiy.Cliente;
import sptech.school.order_hub.entitiy.Paginacao;
import java.util.stream.Collectors;

import java.util.List;

@Builder
public record BuscarClientesResponseDTO(
        List<ClienteDto> itens,
        Long totalItens,
        Boolean ultimaPagina
) {

    public static BuscarClientesResponseDTO fromEntity(Paginacao<Cliente> paginacao) {
        List<ClienteDto> clienteDtos = paginacao.getItens().stream()
                .map(cliente -> new ClienteDto(
                        cliente.getIdPessoa(),
                        cliente.getNomePessoa(),
                        cliente.getEmailPessoa(),
                        cliente.getNumeroTelefone()
                ))
                .collect(Collectors.toList());

        return BuscarClientesResponseDTO.builder()
                .itens(clienteDtos)
                .totalItens(paginacao.getTamanhoTotalItens())
                .ultimaPagina(paginacao.isUltimaPagina())
                .build();
    }

    public record ClienteDto(Integer idCliente,
                             String nomePessoa,
                             String email,
                             String telefone) {

    }

}
