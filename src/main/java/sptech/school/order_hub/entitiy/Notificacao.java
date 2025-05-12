package sptech.school.order_hub.entitiy;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Builder
@Table(name = "NOTIFICACAO")
public class Notificacao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_notificacao")
    private Integer idNotificacao;

    @Column(name = "mensagem_cancelamento")
    private String mensagemCancelamento;

    @Column(name = "mensagem_agendamento")
    private String mensagemAgendamento;
}
