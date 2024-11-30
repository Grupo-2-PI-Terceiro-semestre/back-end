package sptech.school.order_hub.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import sptech.school.order_hub.entitiy.Cliente;
import sptech.school.order_hub.entitiy.Empresa;
import sptech.school.order_hub.entitiy.Usuario;
import sptech.school.order_hub.enuns.StatusAtividade;

import java.util.List;
import java.util.Optional;

public interface ClienteRepository extends JpaRepository<Cliente, Integer> {

    Page<Cliente> findAllByEmpresaAndStatusAtividadeOrderByIdPessoaAsc(Empresa empresa, StatusAtividade statusAtividade, PageRequest pagina);

//    Page<Cliente> findAllByEmpresaOrderByIdPessoaAsc(Empresa empresa, PageRequest pagina);

    List<Cliente> findAllByEmpresa(Empresa empresa);

    Optional<Cliente> findByIdPessoa(Integer idPessoa);

    Optional<Cliente> findByEmailPessoa(String email);

    Optional<Cliente> findByEmailPessoaAndSenha(String email, String senha);
}
