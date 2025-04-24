package sptech.school.order_hub.dtos;

import sptech.school.order_hub.entitiy.Endereco;

public record EnderecoDTO(
        int idEndereco,
        String logradouro,
        String cidade,
        String bairro,
        String uf,
        String cep,
        String numero,
        String complemento,
        String lat,
        String lng
) {

    public Endereco toEntity() {
        return Endereco.builder()
                .idEndereco(idEndereco)
                .logradouro(logradouro)
                .cidade(cidade)
                .bairro(bairro)
                .estado(uf)
                .cep(cep)
                .numero(numero)
                .complemento(complemento)
                .latitude(lat)
                .longitude(lng)
                .build();
    }

    public static EnderecoDTO fromEntity(Endereco endereco) {
        return new EnderecoDTO(
                endereco.getIdEndereco(),
                endereco.getLogradouro(),
                endereco.getCidade(),
                endereco.getBairro(),
                endereco.getEstado(),
                endereco.getCep(),
                endereco.getNumero(),
                endereco.getComplemento(),
                endereco.getLatitude(),
                endereco.getLongitude()
        );
    }
}
