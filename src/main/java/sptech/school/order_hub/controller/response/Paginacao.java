package sptech.school.order_hub.controller.response;


import java.util.List;

public record Paginacao<T>(

        List<T> itens,
        long tamanhoTotalItens,
        boolean ultimaPagina) {

    public static <T> Paginacao<T> of(
            List<T> itens,
            long tamanhoTotalItens,
            boolean ultimaPagina) {
        return new Paginacao<>(itens, tamanhoTotalItens, ultimaPagina);
    }
}
