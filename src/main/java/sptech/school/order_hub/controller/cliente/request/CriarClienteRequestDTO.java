package sptech.school.order_hub.controller.cliente.request;

import lombok.Builder;
import sptech.school.order_hub.entitiy.Cliente;

@Builder
public record CriarClienteRequestDTO(
        Integer idPessoa,
        String nomePessoa,
        String emailPessoa,
        String numeroTelefone,
        String senha
) {

    public static Cliente toEntity(CriarClienteRequestDTO requestDTO) {
        return Cliente.builder()
                .nomePessoa(requestDTO.nomePessoa())
                .emailPessoa(requestDTO.emailPessoa())
                .numeroTelefone(requestDTO.numeroTelefone())
                .senha(requestDTO.senha())
                .build();
    }
}
