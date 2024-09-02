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
@Getter
@Setter
public class Empresa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idEmpresa;
    private String nomeEmpresa;
    private String emailEmpresa;
    private String cnpj;
    private String telefone;

    private Integer fkAssinante;

    @Lob
    private byte[] imagem;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Empresa empresa = (Empresa) o;
        return idEmpresa == empresa.idEmpresa && Objects.equals(nomeEmpresa, empresa.nomeEmpresa) && Objects.equals(emailEmpresa, empresa.emailEmpresa) && Objects.equals(cnpj, empresa.cnpj) && Objects.equals(telefone, empresa.telefone) && Objects.equals(fkAssinante, empresa.fkAssinante) && Objects.deepEquals(imagem, empresa.imagem);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(idEmpresa);
    }
}
