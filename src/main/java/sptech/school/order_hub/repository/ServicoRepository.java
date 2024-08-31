package sptech.school.order_hub.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sptech.school.order_hub.entitiy.Servico;

import java.util.List;

public interface ServicoRepository extends JpaRepository<Servico, Integer> {

    List<Servico> findByFkEmpresa(Integer fkEmpresa);

    List<Servico> findByFkCategoria(Integer fkCategoria);
}
