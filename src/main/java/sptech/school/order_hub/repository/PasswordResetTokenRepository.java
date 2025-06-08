package sptech.school.order_hub.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sptech.school.order_hub.entitiy.PasswordResetToken;

import java.util.Optional;

public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Integer> {

    Optional<PasswordResetToken> findByToken(String token);

}
