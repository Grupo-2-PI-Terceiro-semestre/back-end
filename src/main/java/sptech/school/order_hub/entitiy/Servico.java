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
@Table(name = "SERVICO")
public class Servico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idServico;

    @NotBlank
    @Size(min = 3, max = 100)
    private String nomeServico;

    @NotNull
    @Positive
    private Double valorServico;

    @NotBlank
    @Size(max = 255)
    private String descricao;

    private String corReferenciaHex;

    private LocalTime duracao;

    @ManyToOne
    @JoinColumn(name = "fk_empresa")
    private Empresa empresa;

    @Enumerated(EnumType.STRING)
    private StatusAtividade statusAtividade;
}
