package sptech.school.order_hub.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import sptech.school.order_hub.entitiy.*;
import sptech.school.order_hub.enuns.StatusAtividade;

import java.util.List;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    UserDetails findByEmailPessoa(String email);

    boolean existsByEmailPessoa(String email);

    boolean existsByEmailPessoaAndCpf(String email, String cpf);

    List<Usuario> findAllByEmpresaAndStatusAtividadeContaining(Empresa empresa, StatusAtividade statusAtividade);

    UserDetails findByFirebaseUid(String uid);

//    Page<Usuario> findAllByEmpresaOrderByIdPessoaAsc(Empresa empresa, PageRequest pagina);

    Optional<Usuario> findByIdPessoa(Integer idPessoa);

    Page<Usuario> findAllByEmpresaAndStatusAtividadeOrderByIdPessoaAsc(Empresa empresa, StatusAtividade statusAtividade, PageRequest pagina);
}
