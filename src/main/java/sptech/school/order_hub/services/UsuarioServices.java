package sptech.school.order_hub.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import sptech.school.order_hub.config.secutity.config.TokenServices;
import sptech.school.order_hub.controller.usuario.request.AuthRequestDTO;
import sptech.school.order_hub.controller.usuario.request.CadastroRequestDTO;
import sptech.school.order_hub.controller.usuario.response.AuthResponseDTO;
import sptech.school.order_hub.controller.usuario.response.CadastroResponseDTO;
import sptech.school.order_hub.entitiy.Empresa;
import sptech.school.order_hub.entitiy.Endereco;
import sptech.school.order_hub.entitiy.Usuario;
import sptech.school.order_hub.exception.UserCreationException;
import sptech.school.order_hub.repository.EmpresaRepository;
import sptech.school.order_hub.repository.UsuarioRepository;

import javax.naming.AuthenticationException;
import java.util.List;
import java.util.Optional;

@Service
public class UsuarioServices {

    @Autowired
    private UsuarioRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    TokenServices tokenServices;

    @Autowired
    private EmpresaRepository empresaRepository;

    @Autowired
    private EmpresaServices empresaServices;


    public ResponseEntity<AuthResponseDTO> autenticar(Usuario usuario) throws AuthenticationException {

        UsernamePasswordAuthenticationToken authToken;

        if (usuario.getFirebaseUid() == null) {
            authToken = new UsernamePasswordAuthenticationToken(usuario.getEmailPessoa(), usuario.getSenha());
        } else {
            authToken = new UsernamePasswordAuthenticationToken(usuario.getEmailPessoa(), usuario.getFirebaseUid());
        }

        Authentication authentication = authenticationManager.authenticate(authToken);

        String token = tokenServices.generateToken((Usuario) authentication.getPrincipal());

        Usuario usuarioAutenticado = (Usuario) authentication.getPrincipal();
        AuthResponseDTO authResponseDTO = new AuthResponseDTO(usuarioAutenticado.getIdPessoa(), usuarioAutenticado.getNomePessoa(), usuarioAutenticado.getTiposDeUsuario(), usuarioAutenticado.getEmpresa());

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + token);

        return new ResponseEntity<>(authResponseDTO, headers, HttpStatus.OK);
    }

    public Usuario create(CadastroRequestDTO usuarioDTO) {

        Usuario usuario = new Usuario();

        if (repository.existsByEmailPessoa(usuarioDTO.emailPessoa())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Usuário já cadastrado.");
        }
        try {
            if (usuarioDTO.senha() != null) {
                usuario.setSenha(passwordEncoder.encode(usuarioDTO.senha()));
            }
            usuario.setNomePessoa(usuarioDTO.nomePessoa());
            usuario.setFirebaseUid(usuarioDTO.firebaseUid());
            usuario.setEmailPessoa(usuarioDTO.emailPessoa());
            usuario.setTiposDeUsuario(usuarioDTO.tiposDeUsuario());
            usuario.setRepresentante(usuarioDTO.representante());
            repository.save(usuario);
            return usuario;
        } catch (Exception e) {
            throw new UserCreationException("Erro ao cadastrar o usuário: " + e.getMessage(), e);
        }
    }


    public CadastroResponseDTO create(Usuario usuario) {

        if (repository.existsByEmailPessoa(usuario.getEmailPessoa())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Usuário já cadastrado.");
        }
        try {
            if (usuario.getPassword() != null) {
                usuario.setSenha(passwordEncoder.encode(usuario.getPassword()));
            } else {
                usuario.setFirebaseUid(passwordEncoder.encode(usuario.getFirebaseUid()));
            }
            repository.save(usuario);
            return CadastroResponseDTO.toEntity(usuario);
        } catch (Exception e) {
            throw new UserCreationException("Erro ao cadastrar o usuário: " + e.getMessage(), e);
        }
    }

    public List<Usuario> findAllByEmpresa(Integer idEmpresa) {

        Empresa empresa = empresaServices.findById(idEmpresa);

        List<Usuario> usuarios = repository.findAllByEmpresa(empresa);

        if (usuarios.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT);
        }

        return usuarios;
    }

    public Usuario findById(Integer idUsuario) {
        Optional<Usuario> usuario = repository.findById(idUsuario);
        if (usuario.isPresent()) {
            return usuario.get();
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }

    public Usuario update(Integer id, Usuario usuarioAtualizar) {

        Optional<Usuario> usuario = repository.findById(id);

        if (usuario.isPresent()) {
            usuario.get().setIdPessoa(id);
            usuario.get().setNomePessoa(usuarioAtualizar.getNomePessoa());
            usuario.get().setTiposDeUsuario(usuarioAtualizar.getTiposDeUsuario());
            usuario.get().setEmpresa(usuarioAtualizar.getEmpresa());
            usuario.get().setEndereco(usuarioAtualizar.getEndereco());
            return usuario.get();
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }

    public Void deleteById(Integer id) {
        Optional<Usuario> usuario = repository.findById(id);
        if (usuario.isPresent()) {
            repository.deleteById(id);
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }

    public Usuario createEndereco(Endereco endereco, Integer idUsuario) {
        Optional<Usuario> usuario = repository.findById(idUsuario);
        if (usuario.isPresent()) {
            usuario.get().setEndereco(endereco);
            return usuario.get();
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }

    public Usuario createWithEmpresa(Usuario usuario, Integer idEmpresa) {
        if (repository.existsByEmailPessoaAndCpf(usuario.getEmailPessoa(), usuario.getCpf())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Usuário já cadastrado.");
        }
        Empresa empresa = empresaServices.findById(idEmpresa);

        usuario.setIdPessoa(null);
        usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));

        empresa.addUsuario(usuario);

        usuario.setEmpresa(empresa);

        repository.save(usuario);

        empresaRepository.save(empresa);

        return usuario;
    }


}
