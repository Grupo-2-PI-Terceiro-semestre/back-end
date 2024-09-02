package sptech.school.order_hub.entitiy;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Objects;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Assinante {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idAssinante;
    private String nomeAssinate;
    private LocalDate dtNascimento;
    private String cpf;

    private Integer fkUsuario;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Assinante assinante = (Assinante) o;
        return Objects.equals(idAssinante, assinante.idAssinante) && Objects.equals(nomeAssinate, assinante.nomeAssinate) && Objects.equals(dtNascimento, assinante.dtNascimento) && Objects.equals(cpf, assinante.cpf) && Objects.equals(fkUsuario, assinante.fkUsuario);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(idAssinante);
    }
}
