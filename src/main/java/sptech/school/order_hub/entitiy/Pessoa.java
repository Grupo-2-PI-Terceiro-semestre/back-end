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
    private Integer idPessoa;

    @NotNull
    @NotBlank
    private String nomePessoa;

    @NotNull
    @NotBlank
    @Email
    private String emailPessoa;

//    private LocalDate dataNascimento;
//
//    private String genero;

    private LocalDate dataCriacao;

    private String numeroTelefone;

    @ManyToOne
    @JoinColumn(name = "fk_empresa")
    @JsonBackReference
    private Empresa empresa;

    @Enumerated(EnumType.STRING)
    private StatusAtividade statusAtividade;

    public Pessoa(String email) {
        this.emailPessoa = email;
    }

    public String getStatusAtividade() {
        return statusAtividade.toString();
    }
}
