package sptech.school.order_hub.controller.agendamento.response;

public record ProximosAgendamentosResponseDTO(
        String nomeCliente,
        String nomeServico,
        String dataAgendamento,
        String horaAgendamento,
        String atendente
) {
    public static ProximosAgendamentosResponseDTO from(String nomeCliente, String servico, String dataAgendamento, String horaAgendamento, String atendente) {
        return new ProximosAgendamentosResponseDTO(nomeCliente, servico, dataAgendamento, horaAgendamento, atendente);
    }


}
