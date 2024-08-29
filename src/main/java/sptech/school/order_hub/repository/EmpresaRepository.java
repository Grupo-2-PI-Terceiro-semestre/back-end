package sptech.school.order_hub.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sptech.school.order_hub.entitiy.Empresa;

public interface EmpresaRepository extends JpaRepository<Empresa,Integer> {
}
