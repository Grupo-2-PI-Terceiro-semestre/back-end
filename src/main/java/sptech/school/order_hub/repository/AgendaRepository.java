package sptech.school.order_hub.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import sptech.school.order_hub.entitiy.Agenda;

import java.time.LocalDate;
import java.util.List;

public interface AgendaRepository extends JpaRepository<Agenda, Integer> {

    @Query(value ="""
            SELECT
                s.duracao,
                CAST(a.data_hora AS TIME) AS horaInicio,
                CAST(DATEADD(MINUTE, DATEDIFF(MINUTE, '00:00:00', s.duracao), a.data_hora) AS TIME) AS horaFinal
            FROM agendamento AS a
            JOIN servico AS s ON a.fk_servico = s.id_servico
            JOIN agenda AS ag ON a.fk_agenda = ag.id_agenda
            JOIN usuarios AS u ON ag.fk_usuario = u.id_pessoa
            WHERE
                s.fk_empresa = ?1
                AND ag.id_agenda = ?2
                AND CAST(a.data_hora AS DATE) = ?3
                AND a.status_agendamento <> 'CANCELADO'
            ORDER BY a.data_hora ASC;
            """, nativeQuery = true)
    List<Object[]> buscarHorariosIndisponiveis(Integer idEmpresa, Integer idAgenda, LocalDate data);
}
