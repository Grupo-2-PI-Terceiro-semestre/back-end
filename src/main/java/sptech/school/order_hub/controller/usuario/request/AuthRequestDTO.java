package sptech.school.order_hub.controller.usuario.request;

public record AuthRequestDTO(
        String emailPessoa, String senha, String firebaseUid
) {
}
