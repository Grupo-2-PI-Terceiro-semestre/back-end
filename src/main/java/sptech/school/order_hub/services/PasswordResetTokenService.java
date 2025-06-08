package sptech.school.order_hub.services;

import lombok.Setter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import sptech.school.order_hub.entitiy.Cliente;
import sptech.school.order_hub.entitiy.PasswordResetToken;
import sptech.school.order_hub.repository.ClienteRepository;
import sptech.school.order_hub.repository.PasswordResetTokenRepository;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class PasswordResetTokenService {

    private final PasswordResetTokenRepository tokenRepository;
    private final ClienteRepository clienteRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailServices emailServices;

    public PasswordResetTokenService(
            PasswordResetTokenRepository tokenRepository,
            ClienteRepository clienteRepository,
            PasswordEncoder passwordEncoder,
            EmailServices emailServices // <- recebido aqui
    ) {
        this.tokenRepository = tokenRepository;
        this.clienteRepository = clienteRepository;
        this.passwordEncoder = passwordEncoder;
        this.emailServices = emailServices;
    }

    public Optional<PasswordResetToken> gerarTokenParaEmail(String emailCliente) {
        return clienteRepository.findByEmailPessoa(emailCliente)
                .map(cliente -> {
                    PasswordResetToken token = new PasswordResetToken(cliente);
                    tokenRepository.save(token);

                    emailServices.enviarEmail(
                            cliente.getEmailPessoa(),
                            "Recuperação de Senha - OrderHub",
                            "Use este token para redefinir sua senha: " + token.getToken()
                    );

                    return token;
                });
    }


    public Boolean validarToken(String tokenInformado) {
        return tokenRepository.findByToken(tokenInformado)
                .filter(token -> token.getDataExpiracao().isAfter(LocalDateTime.now()))
                .isPresent();
    }

    private boolean tokenExpirado(PasswordResetToken token) {
        return token.getDataExpiracao().isBefore(LocalDateTime.now());
    }

    public boolean redefinirSenha(String tokenInformado, String novaSenha) {
        Optional<PasswordResetToken> tokenOpt = tokenRepository.findByToken(tokenInformado);

        if (tokenOpt.isEmpty() || tokenExpirado(tokenOpt.get())) {
            return false;
        }

        Cliente cliente = tokenOpt.get().getCliente();
        cliente.setSenha(passwordEncoder.encode(novaSenha));

        clienteRepository.save(cliente);
        tokenRepository.delete(tokenOpt.get());

        return true;
    }
}
