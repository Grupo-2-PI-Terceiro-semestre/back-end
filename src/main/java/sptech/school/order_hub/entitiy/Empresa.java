package sptech.school.order_hub.entitiy;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.validator.constraints.br.CNPJ;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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
    private List<Usuario> usuarios = new ArrayList<>();

    @OneToMany(mappedBy = "empresa", cascade = CascadeType.PERSIST)
    @JsonManagedReference
    private List<Cliente> clientes = new ArrayList<>();

    @OneToMany(mappedBy = "empresa", cascade = CascadeType.PERSIST)
    private List<Servico> servicos;

    @JoinColumn(name = "fk_endereco")
    @OneToOne
    private Endereco endereco;

    @JoinColumn(name = "fk_notificacao")
    @OneToOne
    private Notificacao notificacao;

    @ManyToOne
    @JoinColumn(name = "fk_categoria")
    private Categoria categoria;

    private String urlImagem;

    public void addUsuario(Usuario usuario) {
        this.usuarios.add(usuario);
    }

    public void addServico(Servico servico) {
        this.servicos.add(servico);
    }

    public void addCliente(Cliente cliente) {
        this.clientes.add(cliente);
    }
}
