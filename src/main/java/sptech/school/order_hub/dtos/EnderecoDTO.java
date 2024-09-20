package sptech.school.order_hub.dtos;

public record EnderecoDTO(
        int idEndereco,
        String logradouro,
        String bairro,
        String cidade,
        String estado,
        String cep,
        String numero,
        String complemento
) {}
