package sptech.school.order_hub.entitiy;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Imagem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_imagem")
    private Integer idImagem;

    @Column(name = "url_imagem")
    private String urlImagem;

    @ManyToOne
    @JoinColumn(name = "fk_empresa")
    private Empresa empresa;

}
