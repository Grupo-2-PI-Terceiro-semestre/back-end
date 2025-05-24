package sptech.school.order_hub.entitiy;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import sptech.school.order_hub.enuns.StatusAtividade;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
@SuperBuilder
public abstract class Pessoa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pessoa")
    private Integer idPessoa;

    @NotNull
    @NotBlank
    @Column(name = "nome_pessoa")
    private String nomePessoa;

    @NotNull
    @NotBlank
    @Email
    @Column(name = "email_pessoa")
    private String emailPessoa;

//    private LocalDate dataNascimento;
//
//    private String genero;

    @Column(name = "data_criacao")
    private LocalDate dataCriacao;

    @Column(name = "numero_telefone")
    private String numeroTelefone;

    @ManyToOne
    @JoinColumn(name = "fk_empresa")
    @JsonBackReference
    private Empresa empresa;

    @Enumerated(EnumType.STRING)
    @Column(name = "status_atividade")
    private StatusAtividade statusAtividade;

    public Pessoa(String email) {
        this.emailPessoa = email;
    }

    public String getStatusAtividade() {
        return statusAtividade.toString();
    }
}
