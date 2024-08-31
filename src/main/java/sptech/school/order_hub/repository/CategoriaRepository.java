package sptech.school.order_hub.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sptech.school.order_hub.entitiy.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria, Integer> {
}
