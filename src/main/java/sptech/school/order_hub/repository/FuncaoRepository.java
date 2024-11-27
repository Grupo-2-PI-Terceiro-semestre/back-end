package sptech.school.order_hub.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sptech.school.order_hub.entitiy.Funcao;

public interface FuncaoRepository extends JpaRepository<Funcao, Integer> {

    boolean existsByNomeFuncao(String nomeFuncao);
}
