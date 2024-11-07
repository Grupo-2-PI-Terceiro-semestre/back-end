package sptech.school.order_hub.entitiy;


import java.util.List;

public class Paginacao <T>{

    private final List<T> itens;
    private final long tamanhoTotalItens;
    private final boolean ultimaPagina;

    public Paginacao(List<T> itens, long tamanhoTotalItens, boolean ultimaPagina) {
        this.itens = itens;
        this.tamanhoTotalItens = tamanhoTotalItens;
        this.ultimaPagina = ultimaPagina;
    }

    public List<T> getItens() {
        return itens;
    }

    public long getTamanhoTotalItens() {
        return tamanhoTotalItens;
    }

    public boolean isUltimaPagina() {
        return ultimaPagina;
    }
}
