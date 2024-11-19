package sptech.school.order_hub.controller.cliente.request;

import lombok.Builder;
import sptech.school.order_hub.controller.cliente.response.PerfilAtualizadoDTO;
import sptech.school.order_hub.entitiy.Cliente;
import sptech.school.order_hub.entitiy.Empresa;

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
            String telefone
    ) {

    }
}


