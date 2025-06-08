package sptech.school.order_hub.entitiy;


import jakarta.persistence.*;
import lombok.*;
import sptech.school.order_hub.dtos.MessageEmpresa;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Builder
@Table(name = "ACAO_NOTIFICACAO")
public class AcaoNotificacao {

    @Id
    @GeneratedValue
    private Long id;

    private String mensagem;

    private boolean lida = false;

    private boolean enviada = false;

    @ManyToOne
    @JoinColumn(name = "fk_empresa")
    private Empresa empresa;

    private LocalDateTime dataCriacao;
    private LocalDateTime dataEnvio;


    public static AcaoNotificacao fromEntity(MessageEmpresa message) {
        return AcaoNotificacao.builder()
                .mensagem(message.mensagem())
                .lida(false)
                .enviada(false)
                .empresa(message.empresa())
                .dataCriacao(LocalDateTime.now())
                .dataEnvio(null)
                .build();
    }
}
