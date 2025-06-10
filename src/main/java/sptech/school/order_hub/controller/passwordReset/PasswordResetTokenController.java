package sptech.school.order_hub.controller.passwordReset;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sptech.school.order_hub.controller.passwordReset.request.RecuperarSenhaRequest;
import sptech.school.order_hub.controller.passwordReset.request.RedefinirSenhaRequest;
import sptech.school.order_hub.controller.passwordReset.request.ValidarTokenRequest;
import sptech.school.order_hub.entitiy.PasswordResetToken;
import sptech.school.order_hub.services.PasswordResetTokenService;

@Tag(name = "Password Reset", description = "Endpoints for password reset functionality")
@RestController
@RequestMapping("/api/v1/password-reset")
public class PasswordResetTokenController {

    private final PasswordResetTokenService tokenService;

    public PasswordResetTokenController(PasswordResetTokenService tokenService) {
        this.tokenService = tokenService;
    }



    @PostMapping("/solicitar")
    public ResponseEntity<String> solicitarRecuperacao(@RequestBody RecuperarSenhaRequest request) {
        var tokenOpt = tokenService.gerarTokenParaEmail(request.email());

        if (tokenOpt.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        PasswordResetToken token = tokenOpt.get();
        return ResponseEntity.ok(token.getToken());
    }

    @PostMapping("/validar")
    public ResponseEntity<Boolean> validarToken(@RequestBody ValidarTokenRequest request) {
        return ResponseEntity.ok(tokenService.validarToken(request.token()));
    }

    @PutMapping("/redefinir")
    public ResponseEntity<Void> redefinirSenha(@RequestBody RedefinirSenhaRequest request) {
        boolean sucesso = tokenService.redefinirSenha(request.token(), request.novaSenha());
        if (!sucesso) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok().build();
    }
}
