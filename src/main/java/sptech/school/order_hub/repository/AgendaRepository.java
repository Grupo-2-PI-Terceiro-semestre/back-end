package sptech.school.order_hub.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sptech.school.order_hub.entitiy.Agenda;

public interface AgendaRepository extends JpaRepository<Agenda, Integer> {
}
