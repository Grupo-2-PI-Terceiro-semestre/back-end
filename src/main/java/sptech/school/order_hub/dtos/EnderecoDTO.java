package sptech.school.order_hub.dtos;

import sptech.school.order_hub.entitiy.Endereco;

public record EnderecoDTO(
        int idEndereco,
        String logradouro,
        String cidade,
        String uf,
        String cep,
        String numero,
        String complemento
) {

    public Endereco toEntity() {
        return Endereco.builder()
                .idEndereco(idEndereco)
                .logradouro(logradouro)
                .cidade(cidade)
                .cidade(uf)
                .cep(cep)
                .numero(numero)
                .complemento(complemento)
                .build();
    }

    public static EnderecoDTO fromEntity(Endereco endereco) {
        return new EnderecoDTO(
                endereco.getIdEndereco(),
                endereco.getLogradouro(),
                endereco.getCidade(),
                endereco.getEstado(),
                endereco.getCep(),
                endereco.getNumero(),
                endereco.getComplemento()
        );
    }
}
