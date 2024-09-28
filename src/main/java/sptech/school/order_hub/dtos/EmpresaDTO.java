package sptech.school.order_hub.dtos;

public record EmpresaDTO(
        Integer idEmpresa,
        String nomeEmpresa,
        String emailEmpresa,
        String cnpj,
        String telefone,
        Integer idPessoa,
        EnderecoDTO endereco
) {}

