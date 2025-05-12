package sptech.school.order_hub.entitiy;

import com.fasterxml.jackson.annotation.*;
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
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id_agenda")
public class Agenda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idAgenda;

    @OneToOne
    @JsonIgnoreProperties("agenda")  // Ignorando a propriedade agenda na serialização do usuário
    @JoinColumn(name = "fk_usuario")
    private Usuario usuario;

    @OneToMany(mappedBy = "agenda")
    private List<Agendamento> agendamentos;
}
