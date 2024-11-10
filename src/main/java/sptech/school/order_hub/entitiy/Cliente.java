package sptech.school.order_hub.entitiy;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@Setter
@SuperBuilder
@EqualsAndHashCode(callSuper = false)
@Table(name = "CLIENTE")
public class Cliente extends Pessoa {

    public Cliente() {
        super();
    }
}
