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

    @Query(value = """
SELECT c.nome_pessoa as Cliente,
       s.nome_servico as Servico,
       CONVERT(VARCHAR, agendamento.data_hora, 23) AS Dia, -- Exibe apenas a data no formato 'YYYY-MM-DD'
        CONVERT(VARCHAR, agendamento.data_hora, 8) AS Hora,
        u.nome_pessoa as Atendente
FROM agendamento
JOIN cliente as c
on agendamento.fk_cliente = c.id_pessoa
JOIN agenda as a on agendamento.fk_agenda = a.id_agenda
JOIN usuarios as u on a.fk_usuario = u.id_pessoa
JOIN servico as s on agendamento.fk_servico = s.id_servico
WHERE CAST(data_hora AS DATE) = CAST(GETDATE() AS DATE)
AND agendamento.status_agendamento = 'AGENDADO';
    """, nativeQuery = true)
    List<Object[]> findNextAgendamentoByEmpresa(Integer idEmpresa);


    @Query(value = """
SELECT SUM(servico.valor_servico) AS aReceber
FROM agendamento
JOIN servico ON agendamento.fk_servico = servico.id_servico
WHERE fk_empresa = ?1
AND status_agendamento = 'AGENDADO';
""", nativeQuery = true)
    Double buscarValorAReceber(Integer idEmpresa);

    @Query(value ="""
SELECT
    u.nome_pessoa AS Atendente,
    SUM(s.valor_servico) AS Receita
FROM agendamento AS a
JOIN agenda AS ag ON a.fk_agenda = ag.id_agenda
JOIN servico AS s ON a.fk_servico = s.id_servico
JOIN usuarios AS u ON ag.fk_usuario = u.id_pessoa
WHERE s.fk_empresa = ?1\s
  AND MONTH(data_hora) = MONTH(GETDATE())
  AND YEAR(data_hora) = YEAR(GETDATE())
GROUP BY u.nome_pessoa;
    """, nativeQuery = true)
    List<Object[]> ReceitaPorFuncionario(Integer idEmpresa);

    @Query(value = """
WITH MesAtual AS (
    SELECT COUNT(id_pessoa) AS totalClientes
    FROM cliente
    WHERE fk_empresa = ?1
      AND MONTH(data_criacao) = MONTH(GETDATE())
      AND YEAR(data_criacao) = YEAR(GETDATE())
),
MesAnterior AS (
    SELECT COUNT(id_pessoa) AS totalClientesAnterior
    FROM cliente
    WHERE fk_empresa = ?1
      AND data_criacao >= DATEADD(MONTH, -1, CAST(GETDATE() AS DATE)) -- Primeiro dia do mês anterior
      AND data_criacao < DATEADD(DAY, 1, EOMONTH(GETDATE(), -1)) -- Último dia do mês anterior
)
SELECT
    a.totalClientes AS totalClientes,
    b.totalClientesAnterior AS ClientesMesAnterior,
    CASE
        WHEN b.totalClientesAnterior IS NULL OR b.totalClientesAnterior = 0 THEN 0.0
        ELSE ((a.totalClientes - b.totalClientesAnterior) / CAST(b.totalClientesAnterior AS FLOAT)) * 100
    END AS comparativoClientes
FROM MesAtual a
LEFT JOIN MesAnterior b ON 1=1;
    """, nativeQuery = true)
    List<Object[]> ClientesMensal(Integer idEmpresa);
    
}
