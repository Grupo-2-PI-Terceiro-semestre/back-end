package sptech.school.order_hub.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
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

}
