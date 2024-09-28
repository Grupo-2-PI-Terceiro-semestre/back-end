package sptech.school.order_hub.controller.empresa.response;

import sptech.school.order_hub.entitiy.Endereco;
import sptech.school.order_hub.entitiy.Servico;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public record BuscarEmpresaResponseDTO(
        Integer idEmpresa,
        String nomeEmpresa,
        String bairro,
        String cidade,
        UUID idImagem,
        List<String> servicos
) {


    public static BuscarEmpresaResponseDTO from(Integer idEmpresa, String nomeEmpresa, Endereco endereco, UUID idImagem, List<Servico> servicos) {


        List<String> nomesServicos = servicos.stream()
                .map(Servico::getNomeServico)
                .collect(Collectors.toList());

        return new BuscarEmpresaResponseDTO(idEmpresa, nomeEmpresa, endereco.getBairro(), endereco.getCidade(), idImagem, nomesServicos);
    }
}
