package sptech.school.order_hub.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import sptech.school.order_hub.controller.agendamento.response.ReceitaMensalResponseDTO;
import sptech.school.order_hub.entitiy.Agendamento;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface AgendamentoRepository extends JpaRepository<Agendamento, Integer> {

    @Query("""
            SELECT a FROM Agendamento a
                WHERE a.agenda.idAgenda = :idAgenda
                    AND a.dataHora BETWEEN :startOfDay AND :endOfDay
                    AND (a.statusAgendamento = 'AGENDADO' OR a.statusAgendamento = 'PENDENTE')
            """)
    List<Agendamento> BuscarAgendamento(Integer idAgenda, LocalDateTime startOfDay, LocalDateTime endOfDay);

    @Query("SELECT a FROM Agendamento a WHERE a.agenda.idAgenda = :idAgenda AND a.dataHora BETWEEN :startOfDay AND :endOfDay")
    List<Agendamento> BuscarAgendamentoCompleto(Integer idAgenda, LocalDateTime startOfDay, LocalDateTime endOfDay);

    Optional<Agendamento> findByIdAgendamento(Integer idAgendamento);

    @Query(value = """
        WITH MesAtual AS (
            SELECT SUM(valor_servico) AS totalReceita
            FROM agendamento
                     JOIN servico ON agendamento.fk_servico = servico.id_servico
            WHERE fk_empresa = ?1
              AND status_agendamento = 'REALIZADO'
              AND MONTH(data_hora) = ?2
        ),
             MesAnterior AS (
                 SELECT SUM(valor_servico) AS totalValorAnterior
                 FROM agendamento
                          JOIN servico ON agendamento.fk_servico = servico.id_servico
                 WHERE fk_empresa = 1
                   AND status_agendamento = 'REALIZADO'
                   AND MONTH(data_hora) = (?2) - 1
             )
        SELECT
            a.totalReceita,
            CASE
                WHEN b.totalValorAnterior = 0 THEN NULL
                ELSE ((a.totalReceita - b.totalValorAnterior) / b.totalValorAnterior) * 100
                END AS comparativoReceita
        FROM MesAtual a, MesAnterior b;
        """, nativeQuery = true)
    List<Object[]> ReceitaMensal(Integer idEmpresa, Integer mesAtual);


}
