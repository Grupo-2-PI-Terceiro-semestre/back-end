package sptech.school.order_hub.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import sptech.school.order_hub.entitiy.Cliente;
import sptech.school.order_hub.entitiy.Empresa;

import java.util.List;

public interface ClienteRepository extends JpaRepository<Cliente, Integer> {

    Page<Cliente> findAllByEmpresaOrderByIdPessoaAsc(Empresa empresa, PageRequest pagina);

}
