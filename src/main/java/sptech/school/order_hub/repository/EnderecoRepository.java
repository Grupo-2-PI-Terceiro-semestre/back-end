package sptech.school.order_hub.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sptech.school.order_hub.entitiy.Endereco;

public interface EnderecoRepository extends JpaRepository<Endereco, Integer> {
}
