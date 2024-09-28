package sptech.school.order_hub.entitiy;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Getter
@Setter
@EqualsAndHashCode
@Builder
@Table(name = "CATEGORIA")
public class Categoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idCategoria;

    private String nome;

    @OneToMany(mappedBy = "categoria", cascade = CascadeType.PERSIST)
    @JsonManagedReference
    private List<Empresa> empresas = new ArrayList<>();

    public void addEmpresa(Empresa empresa) {
        this.empresas.add(empresa);
    }

}
