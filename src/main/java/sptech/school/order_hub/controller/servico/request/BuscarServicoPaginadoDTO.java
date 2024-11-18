package sptech.school.order_hub.controller.servico.request;

import sptech.school.order_hub.controller.cliente.request.BuscarClienteRequestDto;

public record BuscarServicoPaginadoDTO(
        Integer pagina,
        Integer tamanho
) {

    public BuscarServicoPaginadoDTO {
        if (pagina == null) {
            pagina = 0;
        }

        if (tamanho == null) {
            tamanho = 9;
        }
    }

    public static BuscarServicoPaginadoDTO from(Integer pagina, Integer tamanho) {
        return new BuscarServicoPaginadoDTO(pagina, tamanho);
    }
}