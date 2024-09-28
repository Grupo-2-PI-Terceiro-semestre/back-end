package sptech.school.order_hub.enuns;

import lombok.Getter;

@Getter
public enum TipoUsuario {
    ADMIN("ADMIN"),
    USER("USER");

    private String role;

    TipoUsuario(String role) {
        this.role = role;
    }

}
