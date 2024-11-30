package sptech.school.order_hub.dtos;

import lombok.Builder;
import sptech.school.order_hub.entitiy.Cliente;
import sptech.school.order_hub.enuns.StatusAtividade;

import java.time.LocalDate;

@Builder
public record ClienteDTO(
        Integer idPessoa,
        String nomePessoa,
        String emailPessoa,
        String numeroTelefone
) {
    public static ClienteDTO from(Cliente cliente) {
        return ClienteDTO.builder()
                .idPessoa(cliente.getIdPessoa())
                .nomePessoa(cliente.getNomePessoa())
                .emailPessoa(cliente.getEmailPessoa())
                .numeroTelefone(cliente.getNumeroTelefone())
                .build();
    }

    public static Cliente toEntity(ClienteDTO clienteDTO) {
        return Cliente.builder()
                .nomePessoa(clienteDTO.nomePessoa())
                .emailPessoa(clienteDTO.emailPessoa())
                .dataCriacao(LocalDate.now())
                .numeroTelefone(clienteDTO.numeroTelefone())
                .build();
    }


}
