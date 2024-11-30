package sptech.school.order_hub.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import sptech.school.order_hub.entitiy.Cliente;
import sptech.school.order_hub.entitiy.Empresa;
import sptech.school.order_hub.entitiy.Servico;
import sptech.school.order_hub.enuns.StatusAtividade;

import java.util.List;
import java.util.Optional;

public interface ServicoRepository extends JpaRepository<Servico, Integer> {

    List<Servico> findByNomeServicoLike(String nomeServico);

    @Query("SELECT s FROM Servico s WHERE s.empresa.idEmpresa = ?1")
    List<Servico> findyServicoByEmpresaId(int idEmpresa);

//    Page<Servico> findAllByEmpresaOrderByIdServicoAsc(Empresa empresa, PageRequest pagina);

    Page<Servico> findAllByEmpresaAndStatusAtividadeOrderByIdServicoAsc(Empresa empresa, StatusAtividade statusAtividade, PageRequest pagina);

    Optional<Servico> findByIdServico(Integer idServico);
}