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
public class Cliente {

    @Getter
    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String nomeCliente;
    private LocalDate dataNascimento;
    private String cpf;
    private Integer fkUsuario;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cliente cliente = (Cliente) o;
        return Objects.equals(id, cliente.id) && Objects.equals(nomeCliente, cliente.nomeCliente) && Objects.equals(dataNascimento, cliente.dataNascimento) && Objects.equals(cpf, cliente.cpf) && Objects.equals(fkUsuario, cliente.fkUsuario);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
