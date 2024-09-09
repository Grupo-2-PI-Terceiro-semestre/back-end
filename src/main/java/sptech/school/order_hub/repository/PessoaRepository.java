package sptech.school.order_hub.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sptech.school.order_hub.entitiy.Pessoa;

public interface PessoaRepository extends JpaRepository<Pessoa, Integer> {
Boolean existsByEmail(String email);
}
