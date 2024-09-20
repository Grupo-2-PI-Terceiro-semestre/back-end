package sptech.school.order_hub.entitiy;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@Table(name = "CLIENTE")
public class Cliente extends Pessoa {

    public Cliente(
    ) {
        super();
    }

}
