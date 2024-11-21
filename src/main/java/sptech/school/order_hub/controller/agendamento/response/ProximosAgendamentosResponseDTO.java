package sptech.school.order_hub.controller.agendamento.response;

public record ProximosAgendamentosResponseDTO(
        String Cliente,
        String Servico,
        String Dia,
        String Hora,
        String Atendente
) {
    public static ProximosAgendamentosResponseDTO from(String Cliente, String servico, String dataAgendamento, String horaAgendamento, String atendente) {
        return new ProximosAgendamentosResponseDTO(Cliente, servico, dataAgendamento, horaAgendamento, atendente);
    }


}
