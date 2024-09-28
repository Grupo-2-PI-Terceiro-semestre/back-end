package sptech.school.order_hub.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import sptech.school.order_hub.entitiy.Empresa;

import java.util.List;

public interface EmpresaRepository extends JpaRepository<Empresa, Integer> {

    boolean existsByCnpj(String cnpj);

    @Query("SELECT e FROM Empresa e JOIN e.servicos s WHERE e.nomeEmpresa LIKE %:termo% OR s.nomeServico LIKE %:termo%")
    List<Empresa> buscarPorNomeOuServico(@Param("termo") String termo);

    @Query("""
                SELECT e
                FROM Empresa e
                LEFT JOIN e.servicos s
                WHERE lower(e.nomeEmpresa) LIKE lower(CONCAT(COALESCE(:nomeEmpresa, ''), '%'))
                OR lower(s.nomeServico) LIKE lower(CONCAT(COALESCE(:nomeServico, ''), '%'))
            """)
    List<Empresa> findByNomeEmpresaOrServico(@Param("nomeEmpresa") String nomeEmpresa, @Param("nomeServico") String nomeServico);
}
