package sptech.school.order_hub.services;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.web.server.ResponseStatusException;
import sptech.school.order_hub.config.security.config.TokenServices;
import sptech.school.order_hub.controller.agendamento.request.AtualizarAgendamentoRequestDTO;
import sptech.school.order_hub.controller.agendamento.request.BuscarAgendamentoRequestDTO;
import sptech.school.order_hub.controller.cliente.request.AtualizarPerfilClienteEmpresaRequestDTO;
import sptech.school.order_hub.controller.usuario.request.AtualizarUsuarioRequestDTO;
import sptech.school.order_hub.controller.usuario.request.BuscarUsuarioPaginadoDTO;
import sptech.school.order_hub.controller.usuario.request.CadastroUsuarioRequestDTO;
import sptech.school.order_hub.controller.usuario.response.AuthResponseDTO;
import sptech.school.order_hub.controller.usuario.response.BuscarColaboradoresResponseDTO;
import sptech.school.order_hub.controller.usuario.response.CadastroUsuarioResponseDTO;
import sptech.school.order_hub.controller.usuario.response.PerfilAtualizadoDTO;
import sptech.school.order_hub.dtos.AgendamentoDTO;
import sptech.school.order_hub.dtos.ClienteDTO;
import sptech.school.order_hub.dtos.UsuarioDTO;
import sptech.school.order_hub.dtos.UsuarioFuncaoDTO;
import sptech.school.order_hub.entitiy.*;
import sptech.school.order_hub.enuns.StatusAtividade;
import sptech.school.order_hub.exception.UserCreationException;
import sptech.school.order_hub.repository.*;

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
    private final CategoriaRepository categoriaRepository;
    private final AgendaRepository agendaRepository;
    private final FuncaoRepository funcaoRepository;
    private final FuncaoServices funcaoServices;

    public UsuarioServices(UsuarioRepository repository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, TokenServices tokenServices, EmpresaRepository empresaRepository, EmpresaServices empresaServices, AgendamentoServices agendamentoServices, CategoriaRepository categoriaRepository, AgendaRepository agendaRepository, FuncaoRepository funcaoRepository, FuncaoServices funcaoServices) {

        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.tokenServices = tokenServices;
        this.empresaRepository = empresaRepository;
        this.empresaServices = empresaServices;
        this.agendamentoServices = agendamentoServices;
        this.categoriaRepository = categoriaRepository;
        this.agendaRepository = agendaRepository;
        this.funcaoRepository = funcaoRepository;
        this.funcaoServices = funcaoServices;
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
            usuario.setStatusAtividade(StatusAtividade.ATIVO);
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

            Agenda agenda = agendaRepository.save(new Agenda());

            agenda.setUsuario(usuario);

            usuario.setAgenda(agenda);

            repository.save(usuario);
            return CadastroUsuarioResponseDTO.toEntity(usuario);
        } catch (Exception e) {
            throw new UserCreationException("Erro ao cadastrar o usuário: " + e.getMessage(), e);
        }
    }

    public List<BuscarColaboradoresResponseDTO> findAllByColaboradores(Integer idEmpresa, BuscarAgendamentoRequestDTO request, Boolean dadosCoompletos) {

        Empresa empresa = empresaServices.buscarEmpresaEFuncionarios(idEmpresa);

        List<Usuario> usuarios = repository.buscarColaborador(empresa.getIdEmpresa());

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
                        colaborador.funcao() == null ? "" : colaborador.funcao(),
                        agendamento.cliente().nomePessoa(),
                        agendamento.cliente().numeroTelefone(),
                        agendamento.servico().nomeServico(),
                        agendamento.servico().descricao(),
                        agendamento.statusAgendamento(),
                        agendamento.horaAgendamento().toString()
                ));
            }
        }
        writer.flush();
    }

    public PerfilAtualizadoDTO atualizarEmpresaCliente(AtualizarPerfilClienteEmpresaRequestDTO request) {
        try {
            // Verificar se o usuário existe
            Usuario usuario = repository.findById(request.usuario().idPessoa())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado."));

            // Verificar se a categoria existe
            Categoria categoria = categoriaRepository.findById(request.empresa().idCategoria())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Categoria não encontrada."));

            // Atualizar dados do usuário
            if (request.usuario().nome() != null) {
                usuario.setNomePessoa(request.usuario().nome());
            }
            if (request.usuario().cpf() != null) {
                usuario.setCpf(request.usuario().cpf());
            }


            // Verificar se a empresa existe

            if (request.empresa().idEmpresa() == null) {

                Empresa novaEmpresa = new Empresa();
                novaEmpresa.setNomeEmpresa(request.empresa().nomeEmpresa());
                novaEmpresa.setCnpj(request.empresa().cnpj());
                novaEmpresa.setTelefone(request.empresa().telefone());
                novaEmpresa.setCategoria(categoria);
                novaEmpresa.addUsuario(usuario);

                Empresa empresaSalva = empresaRepository.save(novaEmpresa);

                usuario.setEmpresa(empresaSalva);

                Usuario usuarioAtualizado = repository.save(usuario);

                return PerfilAtualizadoDTO.toPerfil(usuarioAtualizado, empresaSalva);
            }
            Usuario usuarioAtualizado = repository.save(usuario);

            Optional<Empresa> empresaOpt = empresaRepository.findById(request.empresa().idEmpresa());

            final var empresa = getEmpresa(request, empresaOpt, categoria);

            return PerfilAtualizadoDTO.toPerfil(usuarioAtualizado, empresaRepository.save(empresa));

        } catch (TransactionSystemException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Erro ao atualizar perfil: " + e.getMessage());
        }
    }

    private static Empresa getEmpresa(AtualizarPerfilClienteEmpresaRequestDTO request, Optional<Empresa> empresaOpt, Categoria categoria) {
        Empresa empresa = empresaOpt.get();

        if (request.empresa().nomeEmpresa() != null) {
            empresa.setNomeEmpresa(request.empresa().nomeEmpresa());
        }
        if (request.empresa().cnpj() != null) {
            empresa.setCnpj(request.empresa().cnpj());
        }
        if (request.empresa().telefone() != null) {
            empresa.setTelefone(request.empresa().telefone());
        }
        if (request.empresa().idCategoria() != null) {
            empresa.setCategoria(categoria);
        }
        return empresa;
    }


    public Paginacao<Usuario> buscarUsuariosPaginado(final Integer idEmpresa, final BuscarUsuarioPaginadoDTO request) {

        final var pagina = PageRequest.of(request.pagina(), request.tamanho());

        final var empresa = empresaRepository.findById(idEmpresa).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Empresa não existe"));

//        final var page = repository.findAllByEmpresaOrderByIdPessoaAsc(empresa, pagina);
        final var page = repository.findAllByEmpresaAndStatusAtividadeOrderByIdPessoaAsc(empresa, StatusAtividade.ATIVO, pagina);

        return Paginacao.of(page.getContent(), page.getTotalElements(), page.isLast());
    }

    public UsuarioFuncaoDTO criarUsuario(Usuario usuario, Integer idEmpresa) {

        final var empresa = buscarEmpresa(idEmpresa);
        final var funcao = buscarFuncao(usuario.getFuncao().getIdFuncao());

        usuario.setEmpresa(empresa);
        usuario.setFuncao(funcao);

        Agenda agenda = agendaRepository.save(new Agenda());

        agenda.setUsuario(usuario);

        usuario.setAgenda(agenda);

        usuario.setStatusAtividade(StatusAtividade.ATIVO);

        Usuario usuarioCriado = repository.save(usuario);

        empresa.addUsuario(usuarioCriado);

        empresaRepository.save(empresa);

        return UsuarioFuncaoDTO.from(usuarioCriado);
    }

    public UsuarioFuncaoDTO atualizarUsuario(AtualizarUsuarioRequestDTO requestDTO) {

        final var usuario = repository.findByIdPessoa(requestDTO.idPessoa())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Usuário não encontrado"));

        final var funcao = funcaoServices.findById(requestDTO.idFuncao());
        final var nome = requestDTO.nomePessoa();
        final var telefone = requestDTO.numeroTelefone();
        final var email = requestDTO.emailPessoa();

        usuario.setNomePessoa(nome);
        usuario.setNumeroTelefone(telefone);
        usuario.setEmailPessoa(email);
        usuario.setFuncao(funcao);

        final var usuarioAtualizado = repository.save(usuario);

        return UsuarioFuncaoDTO.from(usuarioAtualizado);
    }

    private Empresa buscarEmpresa(Integer idEmpresa) {
        return empresaRepository.findById(idEmpresa)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Empresa não encontrada."));
    }

    private Funcao buscarFuncao(Integer idFuncao) {
        return funcaoRepository.findById(idFuncao)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Função não encontrada."));
    }

    public UsuarioFuncaoDTO updateStatusUsuario(final Integer idPessoa) {

        Usuario usuario = repository.findById(idPessoa)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Cliente não encontrado"));

        usuario.setStatusAtividade(StatusAtividade.INATIVO);

        Usuario usuarioInativo = repository.save(usuario);

        return UsuarioFuncaoDTO.from(usuarioInativo);
    }
}
