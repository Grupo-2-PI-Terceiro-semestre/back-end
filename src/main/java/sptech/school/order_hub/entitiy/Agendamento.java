package sptech.school.order_hub.entitiy;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
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
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "idAgendamento")
public class Agendamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idAgendamento;

    private LocalDateTime dataHora;

    @JoinColumn(name = "fk_agenda")
    @ManyToOne(cascade = CascadeType.PERSIST)  // Evitando operações desnecessárias em cascata
    private Agenda agenda;

    @JoinColumn(name = "fk_cliente")
    @ManyToOne
    private Cliente cliente;

    @JoinColumn(name = "fk_servico")
    @ManyToOne
    private Servico servico;
}
