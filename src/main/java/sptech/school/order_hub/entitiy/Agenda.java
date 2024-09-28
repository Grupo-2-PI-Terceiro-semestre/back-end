package sptech.school.order_hub.entitiy;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Getter
@Setter
@EqualsAndHashCode
@Table(name = "AGENDA")
public class Agenda {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idAgenda;

    @OneToOne
    private Usuario usuario;

    @OneToMany(mappedBy = "agenda")
    private List<Agendamento> agendamentos;
}
