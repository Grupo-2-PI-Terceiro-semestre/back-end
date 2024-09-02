package sptech.school.order_hub.entitiy;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Funcionario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idFuncionario;
    private String nomeFuncionario;
    private String cargo;

    private Integer fkEmpresa;
    private Integer fkGestor;
    private Integer fkUsuario;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Funcionario that = (Funcionario) o;
        return Objects.equals(idFuncionario, that.idFuncionario) && Objects.equals(nomeFuncionario, that.nomeFuncionario) && Objects.equals(cargo, that.cargo) && Objects.equals(fkEmpresa, that.fkEmpresa) && Objects.equals(fkGestor, that.fkGestor) && Objects.equals(fkUsuario, that.fkUsuario);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(idFuncionario);
    }
}
