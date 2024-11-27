package sptech.school.order_hub.controller.funcao.response;

import lombok.Builder;
import sptech.school.order_hub.entitiy.Funcao;

@Builder
public record FuncaoResponse(
        Integer idFuncao,
        String nomeFuncao
) {
    public static FuncaoResponse from(Funcao funcao) {
        return FuncaoResponse.builder()
                .idFuncao(funcao.getIdFuncao())
                .nomeFuncao(funcao.getNomeFuncao())
                .build();
    }
}
