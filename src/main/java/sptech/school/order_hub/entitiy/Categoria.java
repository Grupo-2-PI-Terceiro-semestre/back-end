package sptech.school.order_hub.entitiy;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Getter
@Setter
@EqualsAndHashCode
@Table(name = "CATEGORIA")
public class Categoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idCategoria;

    private String nome;

    @OneToMany(mappedBy = "categoriaServico")
    private Set<EmpresaTemCategoria> empresaTemCategorias;
}
