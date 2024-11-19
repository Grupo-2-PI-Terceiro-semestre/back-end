package sptech.school.order_hub.controller.usuario.request;

import sptech.school.order_hub.controller.servico.request.BuscarServicoPaginadoDTO;

public record BuscarUsuarioPaginadoDTO(
        Integer pagina,
        Integer tamanho
) {
    public BuscarUsuarioPaginadoDTO {
        if (pagina == null) {
            pagina = 0;
        }

        if (tamanho == null) {
            tamanho = 9;
        }
    }

    public static BuscarUsuarioPaginadoDTO from(Integer pagina, Integer tamanho) {
        return new BuscarUsuarioPaginadoDTO(pagina, tamanho);
    }
}
