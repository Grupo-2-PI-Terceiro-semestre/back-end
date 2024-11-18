package sptech.school.order_hub.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import sptech.school.order_hub.entitiy.Cliente;
import sptech.school.order_hub.entitiy.Empresa;
import sptech.school.order_hub.entitiy.Servico;

import java.util.List;

public interface ServicoRepository extends JpaRepository<Servico, Integer> {

    List<Servico> findByNomeServicoLike(String nomeServico);

    @Query("SELECT s FROM Servico s WHERE s.empresa.idEmpresa = ?1")
    List<Servico> findyServicoByEmpresaId(int idEmpresa);

    Page<Servico> findAllByEmpresaOrderByIdServicoAsc(Empresa empresa, PageRequest pagina);
}
