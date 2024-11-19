package sptech.school.order_hub.controller.cliente.request;

import jakarta.persistence.criteria.CriteriaBuilder;

public record BuscarClienteRequestDto(
        Integer pagina, Integer tamanho
) {
    public BuscarClienteRequestDto {
        if (pagina == null) {
            pagina = 0;
        }

        if (tamanho == null) {
            tamanho = 9;
        }
    }

    public static BuscarClienteRequestDto from(Integer pagina, Integer tamanho) {
        return new BuscarClienteRequestDto(pagina, tamanho);
    }
}



