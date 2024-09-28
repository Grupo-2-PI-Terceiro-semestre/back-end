package sptech.school.order_hub.entitiy;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.validator.constraints.br.CPF;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import sptech.school.order_hub.enuns.TipoUsuario;

import java.util.Collection;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Getter
@Setter
@SuperBuilder
@Table(name = "USUARIOS")
public class Usuario extends Pessoa implements UserDetails {

    private String senha;

    @ManyToOne()
    @JoinColumn(name = "endereco_id")
    private Endereco endereco;

    @OneToOne
    @JoinColumn(name = "agenda_id")
    private Agenda agenda;

    private TipoUsuario tiposDeUsuario;

    private Boolean representante;

    private String cpf;

    private String funcao;

    private String firebaseUid;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (this.tiposDeUsuario == TipoUsuario.ADMIN) {
            return List.of(new SimpleGrantedAuthority("ROLE_ADMIN"), new SimpleGrantedAuthority("ROLE_USER"));
        } else {
            return List.of(new SimpleGrantedAuthority("ROLE_USER"));
        }
    }

    @Override
    public String getPassword() {
        if (this.senha == null || this.senha.isEmpty()) {
            return this.firebaseUid;
        }
        return this.senha;
    }

    @Override
    public String getUsername() {
        return this.getEmailPessoa();
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }
}

