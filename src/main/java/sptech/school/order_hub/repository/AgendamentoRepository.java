package sptech.school.order_hub.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import sptech.school.order_hub.controller.agendamento.response.AgendamentosClienteResponseDTO;
import sptech.school.order_hub.entitiy.Agendamento;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface AgendamentoRepository extends JpaRepository<Agendamento, Integer> {

    @Query("""
            SELECT a FROM Agendamento a
                WHERE a.agenda.idAgenda = :idAgenda
                    AND a.dataHora BETWEEN :startOfDay AND :endOfDay
                    AND a.statusAgendamento <> 'CANCELADO'
            """)
    List<Agendamento> BuscarAgendamento(Integer idAgenda, LocalDateTime startOfDay, LocalDateTime endOfDay);

    @Query("SELECT a FROM Agendamento a WHERE a.agenda.idAgenda = :idAgenda AND a.dataHora BETWEEN :startOfDay AND :endOfDay")
    List<Agendamento> BuscarAgendamentoCompleto(Integer idAgenda, LocalDateTime startOfDay, LocalDateTime endOfDay);

    @Query("""
                SELECT a FROM Agendamento a
                JOIN a.servico s
                JOIN s.empresa e
                JOIN a.agenda ag
                JOIN ag.usuario u
                WHERE a.cliente.idPessoa = ?1
                ORDER BY a.dataHora DESC
            """)
    List<Agendamento> buscarAgendamentosDeUmCliente(Integer idCliente);


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
                     WHERE fk_empresa = ?1
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
SELECT
    DATE_FORMAT(a.data_hora, '%Y-%m') AS ano_mes,
    SUM(s.valor_servico) AS totalReceita
FROM
    agendamento AS a
JOIN
    servico AS s ON a.fk_servico = s.id_servico
WHERE
    s.fk_empresa = ?1
    AND a.status_agendamento = 'REALIZADO'
GROUP BY
    DATE_FORMAT(a.data_hora, '%Y-%m');""", nativeQuery = true)
  List<Object[]> ReceitaPorMes(Integer idEmpresa);

    @Query(value ="""
SELECT
  DAYOFWEEK(a.data_hora) AS dia_semana,
  COUNT(a.id_agendamento) AS total_agendamentos
FROM agendamento AS a
JOIN servico AS s ON a.fk_servico = s.id_servico
WHERE
  MONTH(a.data_hora) = MONTH(NOW())
  AND YEAR(a.data_hora) = YEAR(NOW())
  AND s.fk_empresa = ?1
  AND a.status_agendamento = 'REALIZADO'
GROUP BY DAYOFWEEK(a.data_hora)
ORDER BY dia_semana;
  """, nativeQuery = true)
  List<Object[]> ServicoPorDiaDaSemana(Integer idEmpresa);

    @Query(value ="""
select s.nome_servico as nomeServicos, sum(s.valor_servico) as totalReceita
from agendamento as a join servico as s on a.fk_servico = s.id_servico
where
    MONTH(a.data_hora) = MONTH(NOW())
    AND YEAR(a.data_hora) = YEAR(NOW())
    AND s.fk_empresa = ?1
    AND a.status_agendamento = 'REALIZADO'
group by s.nome_servico
order by totalReceita desc;
    """, nativeQuery = true)
    List<Object[]> ReceitaPorServico(Integer idEmpresa);

    @Query(value = """
SELECT
    c.nome_pessoa AS Cliente,
    s.nome_servico AS Servico,
    DATE(agendamento.data_hora) AS Dia,    
    TIME(agendamento.data_hora) AS Hora,    
    u.nome_pessoa AS Atendente
FROM agendamento
JOIN cliente AS c ON agendamento.fk_cliente = c.id_pessoa
JOIN agenda AS a ON agendamento.fk_agenda = a.id_agenda
JOIN usuarios AS u ON a.fk_usuario = u.id_pessoa
JOIN servico AS s ON agendamento.fk_servico = s.id_servico
WHERE DATE(agendamento.data_hora) = DATE(NOW())
  AND agendamento.status_agendamento = 'AGENDADO'
  AND s.fk_empresa = ?1;
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
  AND MONTH(data_hora) = MONTH(NOW())
  AND YEAR(data_hora) = YEAR(NOW())
  AND a.status_agendamento = 'REALIZADO'
GROUP BY u.nome_pessoa
ORDER BY Receita DESC;
    """, nativeQuery = true)
    List<Object[]> ReceitaPorFuncionario(Integer idEmpresa);

    @Query(value = """
WITH MesAtual AS (
    SELECT COUNT(id_pessoa) AS totalClientes
    FROM cliente
    WHERE fk_empresa = ?1
      AND cliente.status_atividade = 'ATIVO'
      AND MONTH(data_criacao) = MONTH(NOW())
      AND YEAR(data_criacao) = YEAR(NOW())
),
MesAnterior AS (
    SELECT COUNT(id_pessoa) AS totalClientesAnterior
    FROM cliente
    WHERE fk_empresa = ?1
      AND data_criacao >= DATE_FORMAT(DATE_SUB(NOW(), INTERVAL 1 MONTH), '%Y-%m-01') -- Primeiro dia do mês anterior
      AND data_criacao < DATE_FORMAT(NOW(), '%Y-%m-01') -- Primeiro dia do mês atual
      AND cliente.status_atividade = 'ATIVO'
)
SELECT
    a.totalClientes AS totalClientes,
    b.totalClientesAnterior AS ClientesMesAnterior,
    CASE
        WHEN b.totalClientesAnterior IS NULL OR b.totalClientesAnterior = 0 THEN 0.0
        ELSE ((a.totalClientes - b.totalClientesAnterior) / b.totalClientesAnterior) * 100
    END AS comparativoClientes
FROM MesAtual a
LEFT JOIN MesAnterior b ON 1=1;
    """, nativeQuery = true)

    List<Object[]> ClientesMensal(Integer idEmpresa);
}
