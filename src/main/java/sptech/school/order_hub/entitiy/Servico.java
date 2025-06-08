package sptech.school.order_hub.entitiy;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.*;
import sptech.school.order_hub.enuns.StatusAgendamento;
import sptech.school.order_hub.enuns.StatusAtividade;

import java.sql.Time;
import java.time.LocalTime;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Getter
@Setter
@EqualsAndHashCode
@Builder
@Table(name = "servico")
public class Servico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_servico")
    private Integer idServico;

    @Column(name = "duracao")
    private LocalTime duracao;

    @ManyToOne
    @JoinColumn(name = "fk_empresa")
    private Empresa empresa;

    @Column(name = "nome_servico")
    private String nomeServico;

    @Column(name = "valor_servico")
    private Double valorServico;

    @Column(name = "descricao")
    private String descricao;

    @Column(name = "cor_referencia_hex")
    private String corReferenciaHex;

    @Enumerated(EnumType.STRING)
    @Column(name = "status_atividade")
    private StatusAtividade statusAtividade;
}

