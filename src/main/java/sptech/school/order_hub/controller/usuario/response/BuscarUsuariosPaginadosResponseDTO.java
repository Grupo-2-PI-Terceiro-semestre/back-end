package sptech.school.order_hub.controller.usuario.response;

import lombok.Builder;
import sptech.school.order_hub.controller.cliente.response.BuscarClientesPaginadosResponseDTO;
import sptech.school.order_hub.controller.servico.response.BuscarServicosPaginadosResponseDTO;
import sptech.school.order_hub.dtos.UsuarioDTO;
import sptech.school.order_hub.entitiy.Funcao;
import sptech.school.order_hub.entitiy.Paginacao;
import sptech.school.order_hub.entitiy.Usuario;

import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Builder
public record BuscarUsuariosPaginadosResponseDTO(
        List<UsuarioDTO> itens,
        Long totalItens,
        Boolean ultimaPagina
) {
    public static BuscarUsuariosPaginadosResponseDTO fromEntity(Paginacao<Usuario> paginacao) {
        List<UsuarioDTO> usuarioDTOS = paginacao.itens().stream()
                .map(usuario -> new UsuarioDTO(
                        usuario.getIdPessoa(),
                        usuario.getNomePessoa(),
                        usuario.getNumeroTelefone(),
                        usuario.getEmailPessoa(),
                        usuario.getFuncao() == null ? new Funcao(0, "S/F") : usuario.getFuncao()
                ))
                .collect(Collectors.toList());

        return BuscarUsuariosPaginadosResponseDTO.builder()
                .itens(usuarioDTOS)
                .totalItens(paginacao.tamanhoTotalItens())
                .ultimaPagina(paginacao.ultimaPagina())
                .build();
    }

    private record UsuarioDTO(
            Integer idPessoa,
            String nomePessoa,
            String numeroTelefone,
            String emailPessoa,
            Funcao funcao
    ) {

    }
}
