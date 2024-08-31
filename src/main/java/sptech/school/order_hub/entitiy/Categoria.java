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
public class Categoria {

    @Getter
    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idCategoria;
    private String nomeCategoria;

    @ManyToMany
    @JoinTable(
            name = "categoria_na_empresa",
            joinColumns = @JoinColumn(name = "fkCategoria"),
            inverseJoinColumns = @JoinColumn(name = "fkEmpresa")
    )
    private Set<Empresa> empresas = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Categoria categoria = (Categoria) o;
        return Objects.equals(idCategoria, categoria.idCategoria) && Objects.equals(nomeCategoria, categoria.nomeCategoria) && Objects.equals(empresas, categoria.empresas);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(idCategoria);
    }
}