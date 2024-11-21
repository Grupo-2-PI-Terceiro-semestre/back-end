package sptech.school.order_hub.controller.agendamento.response;

import lombok.Builder;

@Builder
public record ReceitaPorFuncionarioResponseDTO(
        String Atendente,
        Double Receita
) {
    public static ReceitaPorFuncionarioResponseDTO from(String nomeFuncionario, Double totalReceita) {
        return ReceitaPorFuncionarioResponseDTO.builder()
            .Atendente(nomeFuncionario)
            .Receita(totalReceita)
            .build();
    }
}






