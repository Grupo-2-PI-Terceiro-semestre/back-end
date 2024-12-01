package sptech.school.order_hub.controller.empresa.response;

import sptech.school.order_hub.entitiy.Endereco;
import sptech.school.order_hub.entitiy.Servico;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public record BuscarEmpresaServicoResponseDTO(
        Integer idEmpresa,
        String nomeEmpresa,
        String bairro,
        String cidade,
        String uf,
        String urlLogo,
        List<String> servicos,
        String categoria
) {

    public static BuscarEmpresaServicoResponseDTO from(
            Integer idEmpresa,
            String nomeEmpresa,
            Endereco endereco,
            String urlLogo,
            List<Servico> servicos,
            String categoria
    ) {
        List<String> nomesServicos = servicos.stream()
                .map(Servico::getNomeServico)
                .collect(Collectors.toList());

        return new BuscarEmpresaServicoResponseDTO(
                idEmpresa,
                nomeEmpresa,
                endereco.getBairro(),
                endereco.getCidade(),
                endereco.getEstado(),
                urlLogo,
                nomesServicos,
                categoria
        );
    }
}
