package sptech.school.order_hub.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import sptech.school.order_hub.controller.agendamento.response.ReceitaMensalResponseDTO;
import sptech.school.order_hub.entitiy.Agendamento;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface AgendamentoRepository extends JpaRepository<Agendamento, Integer> {

    @Query("SELECT a FROM Agendamento a WHERE a.agenda.idAgenda = :idAgenda AND a.dataHora BETWEEN :startOfDay AND :endOfDay AND a.statusAgendamento = 'AGENDADO'")
    List<Agendamento> BuscarAgendamento(Integer idAgenda, LocalDateTime startOfDay, LocalDateTime endOfDay);

    @Query("SELECT a FROM Agendamento a WHERE a.agenda.idAgenda = :idAgenda AND a.dataHora BETWEEN :startOfDay AND :endOfDay")
    List<Agendamento> BuscarAgendamentoCompleto(Integer idAgenda, LocalDateTime startOfDay, LocalDateTime endOfDay);

    Optional<Agendamento> findByIdAgendamento(Integer idAgendamento);

    @Query(value = """

            WITH MesAtual AS (
            SELECT COALESCE(SUM(valor_servico), 0) AS totalReceita
            FROM agendamento
                     JOIN servico ON agendamento.fk_servico = servico.id_servico
            WHERE fk_empresa = ?1
              AND status_agendamento = 'REALIZADO'
              AND MONTH(data_hora) = ?2
        ),
        MesAnterior AS (
            SELECT COALESCE(SUM(valor_servico), 0) AS totalValorAnterior
            FROM agendamento
                     JOIN servico ON agendamento.fk_servico = servico.id_servico
            WHERE fk_empresa = ?1
              AND status_agendamento = 'REALIZADO'
              AND MONTH(data_hora) = (?2) - 1
        )
        SELECT
            a.totalReceita,
            CASE
                WHEN b.totalValorAnterior = 0 THEN 0
                ELSE ((a.totalReceita - b.totalValorAnterior) / b.totalValorAnterior) * 100
            END AS comparativoReceita
        FROM MesAtual a, MesAnterior b;
        """, nativeQuery = true)
    List<Object[]> ReceitaMensal(Integer idEmpresa, Integer mesAtual);

    @Query(value = """
        WITH MesAtual AS (
            SELECT COUNT(id_servico) AS totalServicos
            FROM agendamento
                     JOIN servico ON agendamento.fk_servico = servico.id_servico
            WHERE fk_empresa = ?1
              AND status_agendamento = 'REALIZADO'
              AND MONTH(data_hora) = ?2
        ),
             MesAnterior AS (
                 SELECT COUNT(id_servico) AS totalServicosAnterior
                 FROM agendamento
                          JOIN servico ON agendamento.fk_servico = servico.id_servico
                 WHERE fk_empresa = 1
                   AND status_agendamento = 'REALIZADO'
                   AND MONTH(data_hora) = (?2) - 1
             )
            SELECT
            a.totalServicos,
            CASE
                WHEN b.totalServicosAnterior IS NULL OR b.totalServicosAnterior = 0 THEN 0
                ELSE ((a.totalServicos - b.totalServicosAnterior) / b.totalServicosAnterior) * 100
            END AS comparativoServicos
            FROM MesAtual a
            LEFT JOIN MesAnterior b ON 1=1;
        """, nativeQuery = true)
    List<Object[]> ServicoMensal(Integer idEmpresa, Integer mesAtual);

  @Query(value = """
SELECT AVG(s.valor_servico)
FROM servico AS s
WHERE fk_empresa = ?1;
        """, nativeQuery = true)
    Double TicketMedio(Integer idEmpresa);

  @Query(value ="""
SELECT YEAR(a.data_hora) as ano, MONTH(a.data_hora) as mes, sum(s.valor_servico) as totalReceita
from agendamento as a join servico as s on a.fk_servico = s.id_servico
where a.fk_agenda = ?1
group by YEAR(a.data_hora), MONTH(a.data_hora);""", nativeQuery = true)
  List<Object[]> ReceitaPorMes(Integer idEmpresa);

    @Query(value ="""
SELECT DATEPART(WEEKDAY, a.data_hora) AS dia_semana, COUNT(a.id_agendamento) AS total_agendamentos
  FROM agendamento AS a where MONTH(data_hora) = ?1 AND YEAR(data_hora) = ?2 AND a.fk_agenda = ?3
  GROUP BY DATEPART(WEEKDAY, a.data_hora)
  ORDER BY dia_semana;
  """, nativeQuery = true)
  List<Object[]> ServicoPorDiaDaSemana(Integer idEmpresa);

    @Query(value ="""
select s.nome_servico as nomeServicos, sum(s.valor_servico) as totalReceita
from agendamento as a join servico as s on a.fk_servico = s.id_servico
where a.fk_agenda = ?1
group by s.nome_servico;
    """, nativeQuery = true)
    List<Object[]> ReceitaPorServico(Integer idEmpresa);

    @Query(value ="""
    SELECT
        c.nome_pessoa AS Nome,
        s.nome_servico AS Servico,
        CONVERT(DATE, a.data_hora) AS Dia, -- Exibe apenas a data
        CONVERT(TIME, a.data_hora) AS Hora, -- Exibe apenas a hora
        u.nome_pessoa AS Atendente
    FROM agendamento AS a
    JOIN servico AS s ON a.fk_servico = s.id_servico
    JOIN cliente AS c ON a.fk_cliente = c.id_pessoa
    JOIN agenda ON a.fk_agenda = agenda.id_agenda
    JOIN usuarios AS u ON agenda.fk_usuario = u.id_pessoa
    WHERE c.fk_empresa = ?1 AND a.status_agendamento = 'AGENDADO'
    AND a.data_hora >= GETDATE()D
    ORDER BY a.data_hora;
    """, nativeQuery = true)
    List<Object[]> findNextAgendamentoByEmpresa(Integer idEmpresa);

    @Query(value ="""
SELECT SUM(servico.valor_servico) AS aReceber
FROM agendamento
JOIN servico ON agendamento.fk_servico = servico.id_servico
WHERE fk_empresa = ?1
AND status_agendamento = 'AGENDADO';
    """, nativeQuery = true)
    Double ReceitaAReceber(Integer idEmpresa);

    @Query(value ="""
    SELECT
        u.nome_pessoa AS Atendente,
        SUM(s.valor_servico) AS Receita
    FROM
        agendamento AS a
    JOIN
        servico AS s
        ON a.fk_servico = s.id_servico
    JOIN
        agenda
        ON a.fk_agenda = agenda.id_agenda
    JOIN
        usuarios AS u
        ON agenda.fk_usuario = u.id_pessoa
    WHERE
        u.fk_empresa = ?1
        AND a.status_agendamento = 'REALIZADO'
    GROUP BY
        u.nome_pessoa
    ORDER BY
        Receita DESC;
    """, nativeQuery = true)
    List<Object[]> ReceitaPorFuncionario(Integer idEmpresa);

}
