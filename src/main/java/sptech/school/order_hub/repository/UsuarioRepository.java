package sptech.school.order_hub.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.userdetails.UserDetails;
import sptech.school.order_hub.entitiy.*;
import sptech.school.order_hub.enuns.StatusAtividade;

import java.util.List;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    UserDetails findByEmailPessoa(String email);

    boolean existsByEmailPessoa(String email);

    boolean existsByEmailPessoaAndCpf(String email, String cpf);


    @Query(value = """
            select u.* from
                           empresa join usuarios as u on empresa.id_empresa = u.fk_empresa where u.status_atividade = 'ATIVO' and empresa.id_empresa = ?1 AND u.representante = 0
            """, nativeQuery = true)
    List<Usuario> buscarColaborador(Integer idEmpresa);

    UserDetails findByFirebaseUid(String uid);

//    Page<Usuario> findAllByEmpresaOrderByIdPessoaAsc(Empresa empresa, PageRequest pagina);

    Optional<Usuario> findByIdPessoa(Integer idPessoa);

    @Query("""
            SELECT u FROM Usuario u
            WHERE u.empresa = :empresa
            AND u.statusAtividade = :statusAtividade
            AND u.representante = false
            ORDER BY u.idPessoa ASC
            """)
    Page<Usuario> findAllByEmpresaAndStatusAtividadeAndRepresentanteOrderByIdPessoaAsc(Empresa empresa, StatusAtividade statusAtividade, PageRequest pagina);

    List<Usuario> findAllByEmpresa(Empresa empresa);
}
