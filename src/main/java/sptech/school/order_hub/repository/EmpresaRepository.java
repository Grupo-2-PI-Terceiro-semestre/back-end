package sptech.school.order_hub.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import sptech.school.order_hub.entitiy.Empresa;

import java.util.List;
import java.util.Set;

public interface EmpresaRepository extends JpaRepository<Empresa, Integer> {

    boolean existsByCnpj(String cnpj);

    @Query("SELECT e FROM Empresa e JOIN e.servicos s WHERE e.nomeEmpresa LIKE %:termo% OR s.nomeServico LIKE %:termo%")
    List<Empresa> buscarPorNomeOuServico(@Param("termo") String termo);

    @Query("""
                SELECT e, c.nome
                FROM Empresa e
                LEFT JOIN e.servicos s
                JOIN e.categoria c
                WHERE lower(e.nomeEmpresa) LIKE lower(CONCAT(COALESCE(:nomeEmpresa, ''), '%'))
                OR lower(s.nomeServico) LIKE lower(CONCAT(COALESCE(:nomeServico, ''), '%'))
            """)
    Set<Object[]> findByNomeEmpresaOrServico(@Param("nomeEmpresa") String nomeEmpresa, @Param("nomeServico") String nomeServico);

    @Query("""
                SELECT e, c.nome
                FROM Empresa e
                LEFT JOIN e.servicos s
                JOIN e.categoria c
                WHERE c.nome = :categoria
            """)
    Set<Object[]> buscarEmpresasPorCategoria(String categoria);

    @Query("""
                SELECT e
                FROM Empresa e
                JOIN e.endereco endereco
                WHERE endereco.bairro = ?2 OR endereco.cidade = ?1
            """)
    List<Empresa> buscarPorGeolocalizacao(String cidade, String bairro);


}
