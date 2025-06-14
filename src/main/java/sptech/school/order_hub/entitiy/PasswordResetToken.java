package sptech.school.order_hub.entitiy;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.SuperBuilder;
import sptech.school.order_hub.entitiy.Cliente;

import java.time.LocalDateTime;
import java.util.Random;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@SuperBuilder
public class PasswordResetToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idToken;

    @NotNull
    @NotBlank
    private String token;

    @ManyToOne
    @JoinColumn(name = "fk_cliente")
    private Cliente cliente;

    private LocalDateTime dataExpiracao;

    public PasswordResetToken(Cliente cliente) {
        this.token = generateSimpleToken(4);
        this.dataExpiracao = LocalDateTime.now().plusMinutes(30);
        this.cliente = cliente;
    }

    private String generateSimpleToken(int length) {
        String chars = "0123456789";
        StringBuilder sb = new StringBuilder();
        Random rnd = new Random();
        for (int i = 0; i < length; i++) {
            sb.append(chars.charAt(rnd.nextInt(chars.length())));
        }
        return sb.toString();
    }
}
