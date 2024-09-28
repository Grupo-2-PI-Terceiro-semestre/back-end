package sptech.school.order_hub.controller.servico.request;

public record BuscarServicoRequestDTO(
        String nomeServico
) {

    public static BuscarServicoRequestDTO from(String nomeServico) {

        if (nomeServico == null || nomeServico.isBlank()) {
            throw new IllegalArgumentException("Nome do serviço não pode ser nulo ou vazio");
        }

        return new BuscarServicoRequestDTO(nomeServico);
    }
}
