package sptech.school.order_hub.services;

import jakarta.servlet.http.HttpServletResponse;
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
import sptech.school.order_hub.controller.agendamento.request.BuscarAgendamentoRequestDTO;
import sptech.school.order_hub.controller.usuario.request.CadastroUsuarioRequestDTO;
import sptech.school.order_hub.controller.usuario.response.AuthResponseDTO;
import sptech.school.order_hub.controller.usuario.response.BuscarColaboradoresResponseDTO;
import sptech.school.order_hub.controller.usuario.response.CadastroUsuarioResponseDTO;
import sptech.school.order_hub.dtos.AgendamentoDTO;
import sptech.school.order_hub.entitiy.Empresa;
import sptech.school.order_hub.entitiy.Endereco;
import sptech.school.order_hub.entitiy.Usuario;
import sptech.school.order_hub.exception.UserCreationException;
import sptech.school.order_hub.repository.EmpresaRepository;
import sptech.school.order_hub.repository.UsuarioRepository;

import javax.naming.AuthenticationException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UsuarioServices {


    private final UsuarioRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final TokenServices tokenServices;
    private final EmpresaRepository empresaRepository;
    private final EmpresaServices empresaServices;
    private final AgendamentoServices agendamentoServices;

    public UsuarioServices(UsuarioRepository repository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, TokenServices tokenServices, EmpresaRepository empresaRepository, EmpresaServices empresaServices, AgendamentoServices agendamentoServices) {

        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.tokenServices = tokenServices;
        this.empresaRepository = empresaRepository;
        this.empresaServices = empresaServices;
        this.agendamentoServices = agendamentoServices;
    }


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
        AuthResponseDTO authResponseDTO = AuthResponseDTO.from(usuarioAutenticado);

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + token);

        return new ResponseEntity<>(authResponseDTO, headers, HttpStatus.OK);
    }

    public Usuario create(CadastroUsuarioRequestDTO usuarioDTO) {

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


    public CadastroUsuarioResponseDTO create(Usuario usuario) {

        if (repository.existsByEmailPessoa(usuario.getEmailPessoa())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Usuário já cadastrado.");
        }
        try {
            if (usuario.getFirebaseUid() == null) {
                usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
            } else {
                usuario.setFirebaseUid(passwordEncoder.encode(usuario.getFirebaseUid()));
            }
            repository.save(usuario);
            return CadastroUsuarioResponseDTO.toEntity(usuario);
        } catch (Exception e) {
            throw new UserCreationException("Erro ao cadastrar o usuário: " + e.getMessage(), e);
        }
    }

    public List<BuscarColaboradoresResponseDTO> findAllByColaboradores(Integer idEmpresa, BuscarAgendamentoRequestDTO request, Boolean dadosCoompletos) {

        Empresa empresa = empresaServices.buscarEmpresaEFuncionarios(idEmpresa);

        List<Usuario> usuarios = repository.findAllByEmpresa(empresa);

        if (usuarios.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT);
        }

        return usuarios.stream()
                .map(usuario -> {
                    List<AgendamentoDTO> agendamentos = agendamentoServices.buscaAgendamento(request, usuario.getAgenda().getIdAgenda(), dadosCoompletos);

                    return BuscarColaboradoresResponseDTO.from(usuario, agendamentos);
                })
                .collect(Collectors.toList());
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
        Empresa empresa = empresaServices.buscarEmpresaEFuncionarios(idEmpresa);

        usuario.setIdPessoa(null);
        usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));

        empresa.addUsuario(usuario);

        usuario.setEmpresa(empresa);

        repository.save(usuario);

        empresaRepository.save(empresa);

        return usuario;
    }

    public void exportarAgendamentos(BuscarAgendamentoRequestDTO request, Integer idEmpresa, HttpServletResponse response) throws IOException {
        List<BuscarColaboradoresResponseDTO> colaboradores = findAllByColaboradores(idEmpresa, request, true);

        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; filename=\"agendamentos.csv\"");

        PrintWriter writer = response.getWriter();

        writer.printf("%S,%S,%S,%S,%S,%S,%S,%S,%S\n", "id", "nome funcionário", "função", "nome cliente", "telefone cliente", "nome serviço", "descrição serviço", "status do agendamento", "data hora serviço");

        for (BuscarColaboradoresResponseDTO colaborador : colaboradores) {
            for (AgendamentoDTO agendamento : colaborador.agendamentoDTOS()) {
                writer.println(String.format("%d,%s,%s,%s,%s,%s,%s,%s,%s",
                        colaborador.idFuncionario(),
                        colaborador.nomeFuncionario(),
                        colaborador.funcao(),
                        agendamento.cliente().nomePessoa(),
                        agendamento.cliente().telefone(),
                        agendamento.servico().nomeServico(),
                        agendamento.servico().descricao(),
                        agendamento.statusAgendamento(),
                        agendamento.horaAgendamento().toString()
                ));
            }
        }
        writer.flush();
    }
}
