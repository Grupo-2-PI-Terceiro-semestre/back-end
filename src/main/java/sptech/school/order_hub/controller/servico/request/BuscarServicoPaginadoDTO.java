    package sptech.school.order_hub.controller.servico.request;

    public record BuscarServicoPaginadoDTO(
            Integer pagina,
            Integer tamanho
    ) {

        public static BuscarServicoPaginadoDTO from(Integer pagina, Integer tamanho) {

            if (pagina == null) {
                pagina = 0;
            }

            if (tamanho == null) {
                tamanho = 8;
            }

            return new BuscarServicoPaginadoDTO(pagina,tamanho);
        }
    }

