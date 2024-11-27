package sptech.school.order_hub.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import sptech.school.order_hub.entitiy.Imagem;

import java.util.List;

public interface ImagensRepository extends JpaRepository<Imagem, Integer> {


    @Query("SELECT i FROM Imagem i WHERE i.empresa.idEmpresa = ?1")
    List<Imagem> buscarImagensDaEmpresa(Integer idEmpresa);
}
