package sptech.school.order_hub.controller.agendamento.response;

import lombok.Builder;

@Builder
public record ReceitaPorFuncionarioResponseDTO(
        String Funcionario,
        Double Receita
) {
    public static ReceitaPorFuncionarioResponseDTO from(String nomeFuncionario, Double totalReceita) {
        return ReceitaPorFuncionarioResponseDTO.builder()
            .Funcionario(nomeFuncionario)
            .Receita(totalReceita)
            .build();
    }
}






