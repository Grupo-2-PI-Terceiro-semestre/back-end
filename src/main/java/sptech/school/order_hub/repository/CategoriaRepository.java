// CategoriaRepository.java
package sptech.school.order_hub.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sptech.school.order_hub.entitiy.Categoria; // Altere esta linha

public interface CategoriaRepository extends JpaRepository<Categoria, Integer> {
}
