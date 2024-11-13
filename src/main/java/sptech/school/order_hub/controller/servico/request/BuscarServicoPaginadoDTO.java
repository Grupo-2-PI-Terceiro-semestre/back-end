    package sptech.school.order_hub.controller.servico.request;

    public record BuscarServicoPaginadoDTO(
            String nomeServico,
            Integer pagina,
            Integer tamanho
    ) {

        public static BuscarServicoPaginadoDTO from(String nomeServico, Integer pagina, Integer tamanho) {

            if (pagina == null) {
                pagina = 0;
            }

            if (tamanho == null) {
                tamanho = 8;
            }

            if (nomeServico == null || nomeServico.isBlank()) {
                throw new IllegalArgumentException("Nome do serviço não pode ser nulo ou vazio");
            }

            return new BuscarServicoPaginadoDTO(nomeServico,pagina,tamanho);
        }
    }

