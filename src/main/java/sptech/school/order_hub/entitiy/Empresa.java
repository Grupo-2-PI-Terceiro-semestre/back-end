package sptech.school.order_hub.entitiy;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Empresa {
    @Getter
    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idEmpresa;
    @Getter
    private String nomeEmpresa;
    private String email;
    private String cnpj;
    private String telefone;

    @ManyToMany
    @JoinTable(
            name = "categoria_na_empresa",
            joinColumns = @JoinColumn(name = "fkEmpresa"),
            inverseJoinColumns = @JoinColumn(name = "fkCategoria")
    )
    private Set<Categoria> categorias = new HashSet<>();

    @Lob
    private byte[] imagem;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Empresa empresa = (Empresa) o;
        return idEmpresa == empresa.idEmpresa && Objects.equals(nomeEmpresa, empresa.nomeEmpresa) && Objects.equals(email, empresa.email) && Objects.equals(cnpj, empresa.cnpj) && Objects.equals(telefone, empresa.telefone) && Objects.equals(categorias, empresa.categorias) && Objects.deepEquals(imagem, empresa.imagem);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(idEmpresa);
    }
}
