package sptech.school.order_hub.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sptech.school.order_hub.entitiy.Notificacao;

public interface NotificacaoRepository extends JpaRepository<Notificacao, Integer> {
}
