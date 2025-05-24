package sptech.school.order_hub.entitiy;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.validator.constraints.UniqueElements;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Getter
@Setter
@Table(name = "FUNCAO")
@Builder
public class Funcao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_funcao")
    private Integer idFuncao;

    @UniqueElements
    @Column(name = "nome_funcao")
    private String nomeFuncao;
}
