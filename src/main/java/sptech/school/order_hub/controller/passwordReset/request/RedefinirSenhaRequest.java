package sptech.school.order_hub.controller.passwordReset.request;
public record RedefinirSenhaRequest(
        String token,
        String novaSenha
) {
}
