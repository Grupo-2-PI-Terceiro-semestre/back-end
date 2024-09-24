package sptech.school.order_hub.controller.usuario.request;

import jakarta.validation.constraints.NotBlank;
import sptech.school.order_hub.entitiy.Usuario;

public record AuthRequestDTO(
        String emailPessoa,
        String senha,
        String firebaseUid
) {

        public Usuario toEntity() {
                return Usuario.builder()
                        .emailPessoa(emailPessoa)
                        .senha(senha)
                        .firebaseUid(firebaseUid)
                        .build();
        }
}
