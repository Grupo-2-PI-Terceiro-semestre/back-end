package sptech.school.order_hub.entitiy;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Table(name = "AGENDAMENTO")
public class Agendamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idAgenda;
    private LocalDateTime dataHora;

    @ManyToOne(cascade = CascadeType.ALL)
    private Agenda agenda;

    @ManyToOne
    private Cliente cliente;

    @ManyToOne
    private Servico servico;
}
