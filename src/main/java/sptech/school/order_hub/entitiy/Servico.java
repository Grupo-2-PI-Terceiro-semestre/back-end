package sptech.school.order_hub.entitiy;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Getter
@Setter
@EqualsAndHashCode
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

    @ManyToOne
    @JoinColumn(name = "id_empresa")
    private Empresa empresa;
}
