package sptech.school.order_hub.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import sptech.school.order_hub.entitiy.Empresa;
import sptech.school.order_hub.entitiy.Usuario;

import java.util.List;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    UserDetails findByEmailPessoa(String email);

    boolean existsByEmailPessoa(String email);

    boolean existsByEmailPessoaAndCpf(String email, String cpf);

    List<Usuario> findAllByEmpresa(Empresa empresa);

    UserDetails findByFirebaseUid(String uid);
}
