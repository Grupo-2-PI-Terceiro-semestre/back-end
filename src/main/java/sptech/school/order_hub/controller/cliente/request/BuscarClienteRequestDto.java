package sptech.school.order_hub.controller.cliente.request;

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
}
