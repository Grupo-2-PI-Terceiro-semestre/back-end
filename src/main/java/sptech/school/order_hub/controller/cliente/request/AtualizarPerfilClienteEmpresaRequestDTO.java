package sptech.school.order_hub.controller.cliente.request;

import lombok.Builder;

@Builder
public record AtualizarPerfilClienteEmpresaRequestDTO(
        ClienteDTO usuario,
        EmpresaDTO empresa
) {
    public record ClienteDTO(
            Integer idPessoa,
            String nome,
            String cpf
    ) {

    }

    public record EmpresaDTO(
            Integer idEmpresa,
            String nomeEmpresa,
            String cnpj,
            String telefone,
            Integer idCategoria
    ) {

    }
}


