package sptech.school.order_hub.controller.funcao.request;

import sptech.school.order_hub.entitiy.Funcao;

public record FuncaoRequest(
        String nomeFuncao
) {

    public static Funcao toEntity(FuncaoRequest request) {
        return Funcao.builder()
                .nomeFuncao(request.nomeFuncao())
                .build();
    }
}
