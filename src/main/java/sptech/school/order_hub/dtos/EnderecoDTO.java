package sptech.school.order_hub.dtos;

import sptech.school.order_hub.entitiy.Endereco;

public record EnderecoDTO(
        int idEndereco,
        String logradouro,
        String bairro,
        String cidade,
        String estado,
        String cep,
        String numero,
        String complemento
) {

    public Endereco toEntity() {
        return Endereco.builder()
                .idEndereco(idEndereco)
                .logradouro(logradouro)
                .bairro(bairro)
                .cidade(cidade)
                .estado(estado)
                .cep(cep)
                .numero(numero)
                .complemento(complemento)
                .build();
    }
}
