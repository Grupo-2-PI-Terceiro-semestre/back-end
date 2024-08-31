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

@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Agenda {

    @Getter
    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idAgenda;
    private Integer fkCliente;
    private Integer fkFuncionario;
    private LocalDate dataAgendamento;
    private Integer fkServico;
    private Integer fkEmpresa;
    private Integer fkCategoria;
    private Integer fkStatus;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Agenda agenda = (Agenda) o;
        return Objects.equals(idAgenda, agenda.idAgenda) && Objects.equals(fkCliente, agenda.fkCliente) && Objects.equals(fkFuncionario, agenda.fkFuncionario) && Objects.equals(dataAgendamento, agenda.dataAgendamento) && Objects.equals(fkServico, agenda.fkServico) && Objects.equals(fkEmpresa, agenda.fkEmpresa) && Objects.equals(fkCategoria, agenda.fkCategoria) && Objects.equals(fkStatus, agenda.fkStatus);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(idAgenda);
    }
}
