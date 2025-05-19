package sptech.school.order_hub.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import sptech.school.order_hub.entitiy.AcaoNotificacao;

import java.util.List;

public interface AcaoNotificacaoRepository extends JpaRepository<AcaoNotificacao, Long> {

    @Query("SELECT a.id FROM AcaoNotificacao a WHERE a.enviada = true AND a.empresa.idEmpresa = ?1 AND a.lida = false")
    List<Long> buscarListaNoficacaoNaoEnviadaPorEmpresaId(String idEmpresa);

    @Query("SELECT a FROM AcaoNotificacao a WHERE a.id = ?1 AND a.enviada = true AND a.lida = false")
    AcaoNotificacao BuscarNotificaoNaoNotificacaoLida(Long idAcaoNotificacao);

}
