package sptech.school.order_hub.controller.cliente.response;

import lombok.Builder;
import sptech.school.order_hub.entitiy.Cliente;

@Builder
public record BuscarClienteResponseDTO(
        String nomePessoa,
        String numeroTelefone,
        String dataNascimento,
        String genero
) {

    public static BuscarClienteResponseDTO fromEntity(Cliente cliente) {
        String telefoneLimpo = cliente.getNumeroTelefone() == null ? "" : cliente.getNumeroTelefone().replaceAll("[^0-9]", "");

        String telefoneFormatado = "";
        if (telefoneLimpo.length() == 11) {
            String ddd = telefoneLimpo.substring(0, 2);
            String parte1 = telefoneLimpo.substring(2, 7);
            String parte2 = telefoneLimpo.substring(7);
            telefoneFormatado = String.format("(%s)%s-%s", ddd, parte1, parte2);
        } else {
            telefoneFormatado = telefoneLimpo;
        }

        return BuscarClienteResponseDTO.builder()
                .nomePessoa(cliente.getNomePessoa())
                .numeroTelefone(telefoneFormatado)
                .dataNascimento(cliente.getDataNascimento() == null ? "" : cliente.getDataNascimento().toString())
                .genero(cliente.getGenero() == null ? "" : cliente.getGenero())
                .build();
    }
}