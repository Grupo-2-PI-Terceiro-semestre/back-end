package sptech.school.order_hub.entitiy;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.validator.constraints.br.CNPJ;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Setter
@EqualsAndHashCode
@Table(name = "EMPRESA")
@Builder
public class Empresa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idEmpresa;

    @NotBlank
    @NotNull
    private String nomeEmpresa;

    private String emailEmpresa;

    @CNPJ
    private String cnpj;

    @NotNull
    private String telefone;

    @OneToMany(mappedBy = "empresa", cascade = CascadeType.PERSIST)
    @JsonManagedReference
    private List<Usuario> usuarios = new ArrayList<>();;

    @OneToMany(mappedBy = "empresa", cascade = CascadeType.PERSIST)
    @JsonManagedReference
    private List<Cliente> clientes = new ArrayList<>();;

    @OneToMany(mappedBy = "empresa", cascade = CascadeType.PERSIST)
    private List<EmpresaTemCategoria> empresaTemCategorias = new ArrayList<>();;

    @OneToOne
    private Endereco endereco;

    @Lob
    private byte[] imagem;

    public void addUsuario(Usuario usuario) {
        this.usuarios.add(usuario);
    }

    public void addCliente(Cliente cliente) {
        this.clientes.add(cliente);
    }
}
