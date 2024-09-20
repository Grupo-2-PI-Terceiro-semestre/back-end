package sptech.school.order_hub.entitiy;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Entity
@Table(name = "EMPRESA_TEM_CATEGORIA")
public class EmpresaTemCategoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "empresa_id")
    private Empresa empresa;

    @ManyToOne
    @JoinColumn(name = "categoria_id")
    private Categoria categoriaServico;

    @OneToMany(mappedBy = "empresaTemCategoria")
    private Set<Servico> servicos;
}
