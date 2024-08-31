package sptech.school.order_hub.entitiy;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Servico {

    @Getter
    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idServico;

    private String nomeServico;
    private Double valorServico;
    private String descricao;
    private Integer fkCategoria;
    private Integer fkEmpresa;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Servico servico = (Servico) o;
        return Objects.equals(idServico, servico.idServico) && Objects.equals(nomeServico, servico.nomeServico) && Objects.equals(valorServico, servico.valorServico) && Objects.equals(descricao, servico.descricao) && Objects.equals(fkCategoria, servico.fkCategoria) && Objects.equals(fkEmpresa, servico.fkEmpresa);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(idServico);
    }
}