package sptech.school.order_hub.dtos;

import lombok.Builder;
import sptech.school.order_hub.entitiy.Cliente;

import java.time.LocalDate;

@Builder
public record ClienteDTO(
        Integer idPesoa,
        String nomePessoa,
        String emailPessoa,
        LocalDate dataNascimento,
        String genero,
        String numeroTelefone,
        String telefone
) {
    public static ClienteDTO from(Cliente cliente) {
        return ClienteDTO.builder()
                .idPesoa(cliente.getIdPessoa())
                .nomePessoa(cliente.getNomePessoa())
                .emailPessoa(cliente.getEmailPessoa())
                .dataNascimento(cliente.getDataNascimento())
                .genero(cliente.getGenero())
                .numeroTelefone(cliente.getNumeroTelefone())
                .build();
    }

    public static Cliente toEntity(ClienteDTO clienteDTO) {
        return Cliente.builder()
                .idPessoa(clienteDTO.idPesoa())
                .nomePessoa(clienteDTO.nomePessoa())
                .emailPessoa(clienteDTO.emailPessoa())
                .dataNascimento(clienteDTO.dataNascimento())
                .dataCriacao(LocalDate.now())
                .genero(clienteDTO.genero())
                .numeroTelefone(clienteDTO.numeroTelefone())
                .build();
    }


}
