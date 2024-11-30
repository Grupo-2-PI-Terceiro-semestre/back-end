package sptech.school.order_hub.controller.cliente.request;

import lombok.Builder;
import sptech.school.order_hub.entitiy.Cliente;

@Builder
public record LoginClienteRequestDTO(
        String email,
        String senha
) {
    public static Cliente toEntity(LoginClienteRequestDTO requestDTO) {
        return Cliente.builder()
                .emailPessoa(requestDTO.email())
                .senha(requestDTO.senha())
                .build();
    }
}
