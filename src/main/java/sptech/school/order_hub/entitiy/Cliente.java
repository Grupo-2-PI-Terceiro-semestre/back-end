package sptech.school.order_hub.entitiy;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@SuperBuilder
@EqualsAndHashCode(callSuper = false)
@Table(name = "CLIENTE")
public class Cliente extends Pessoa {

    private String senha;

    private LocalDate dataNascimento;

    private String genero;

    public Cliente() {
        super();
    }
}
