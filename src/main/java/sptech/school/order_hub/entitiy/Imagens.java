package sptech.school.order_hub.entitiy;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Imagens {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idImagem;

    private String urlImagem;

    @ManyToOne
    @JoinColumn(name = "fk_empresa")
    private Empresa empresa;

}
