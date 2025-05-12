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
    @Column(name = "id_empresa")
    private Integer idEmpresa;

    @NotBlank
    @NotNull
    @Column(name = "nome_empresa")
    private String nomeEmpresa;

    @Column(name = "email_empresa")
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
    private List<Servico> servicos = new ArrayList<>();

    @OneToMany(mappedBy = "empresa", cascade = CascadeType.PERSIST)
    private List<Imagem> imagens = new ArrayList<>();

    @JoinColumn(name = "fk_endereco")
    @OneToOne
    private Endereco endereco;

    @JoinColumn(name = "fk_notificacao")
    @OneToOne
    private Notificacao notificacao;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "fk_categoria")
    private Categoria categoria;

    @Column(name = "url_logo")
    private String urlLogo;

    public void addUsuario(Usuario usuario) {
        this.usuarios.add(usuario);
    }

    public void addImagem(Imagem imagem) {
        this.imagens.add(imagem);
    }

    public void addServico(Servico servico) {
        this.servicos.add(servico);
    }

    public void addCliente(Cliente cliente) {
        this.clientes.add(cliente);
    }
}
