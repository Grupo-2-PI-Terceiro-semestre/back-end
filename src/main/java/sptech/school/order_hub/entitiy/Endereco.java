package sptech.school.order_hub.entitiy;

import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Getter
@Setter
@EqualsAndHashCode
@Builder
@Table(name = "ENDERECO")
public class Endereco {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private int idEndereco;
    private String logradouro;
    private String bairro;
    private String cidade;
    private String estado;
    private String cep;
    private String numero;
    private String complemento;
}
