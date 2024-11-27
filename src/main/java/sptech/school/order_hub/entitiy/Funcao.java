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
    private Integer idFuncao;

    @UniqueElements
    private String nomeFuncao;
}
