package sptech.school.order_hub.services;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import sptech.school.order_hub.controller.empresa.request.BuscarEmpresaRequestDTO;
import sptech.school.order_hub.controller.empresa.response.BuscarEmpresaResponseDTO;
import sptech.school.order_hub.controller.empresa.response.BuscarEmpresaServicoResponseDTO;
import sptech.school.order_hub.controller.empresa.response.BuscarPerfilEmpresaResponseDTO;
import sptech.school.order_hub.controller.empresa.response.CadastroEmpresaResponseDTO;
import sptech.school.order_hub.dtos.EnderecoDTO;
import sptech.school.order_hub.dtos.NotificacaoDTO;
import sptech.school.order_hub.dtos.PerfilEmpresaDTO;
import sptech.school.order_hub.entitiy.*;
import sptech.school.order_hub.repository.EmpresaRepository;
import sptech.school.order_hub.repository.NotificacaoRepository;
import sptech.school.order_hub.repository.UsuarioRepository;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class EmpresaServices {

    @Autowired
    EmpresaRepository repository;

    @Autowired
    EnderecoService enderecoService;

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    NotificacaoRepository notificacaoRepository;


    @Autowired
    private EmpresaRepository empresaRepository;
    @Autowired
    private CategoriaServices categoriaServices;

    public Empresa updateById(Integer idEmpresa, Empresa empresaParaAtualizar) {

        Empresa empresaExistente = this.repository.findById(idEmpresa)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Empresa não encontrada."));

        empresaParaAtualizar.setIdEmpresa(idEmpresa);
        empresaExistente.setNomeEmpresa(empresaParaAtualizar.getNomeEmpresa());
        empresaExistente.setEmailEmpresa(empresaParaAtualizar.getEmailEmpresa());
        empresaExistente.setCnpj(empresaParaAtualizar.getCnpj());
        empresaExistente.setTelefone(empresaParaAtualizar.getTelefone());

        return this.repository.save(empresaExistente);
    }

    public void deleteById(Integer idEmpresa) {
    }

    public BuscarEmpresaResponseDTO findById(Integer idEmpresa) {
        Empresa empresa = repository.findById(idEmpresa)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Empresa não encontrada."));
        return BuscarEmpresaResponseDTO.from(empresa);
    }

    public Empresa buscarEmpresaEFuncionarios(Integer idEmpresa) {
        return repository.findById(idEmpresa)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Empresa não encontrada."));
    }


    public CadastroEmpresaResponseDTO create(Empresa empresa, Integer idPessoa) {

        Usuario usuario = usuarioRepository.findById(idPessoa)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado."));

        if (empresaRepository.existsByCnpj(empresa.getCnpj())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "CNPJ já cadastrado.");
        }

        Categoria categoria = categoriaServices.findByNome(empresa.getCategoria().getNome());

        Endereco endereco = enderecoService.create(empresa.getEndereco());

        Empresa empresaCriada = new Empresa();
        empresaCriada.setNomeEmpresa(empresa.getNomeEmpresa());
        empresaCriada.setEmailEmpresa(empresa.getEmailEmpresa());
        empresaCriada.setCnpj(empresa.getCnpj());
        empresaCriada.setTelefone(empresa.getTelefone());
        empresaCriada.setEndereco(endereco);
        empresaCriada.addUsuario(usuario);
        empresaCriada.setCategoria(categoria);
        usuario.setEmpresa(empresaCriada);
        empresaRepository.save(empresaCriada);
        usuarioRepository.save(usuario);


        categoria.addEmpresa(empresaCriada);

        categoriaServices.save(categoria);


        return CadastroEmpresaResponseDTO.from(empresaCriada);
    }

    public List<BuscarEmpresaServicoResponseDTO> listarEmpresaPeloNome(BuscarEmpresaRequestDTO input) {
        Set<Object[]> resultados = repository.findByNomeEmpresaOrServico(input.termo(), input.termo());

        return getBuscarEmpresaServicoResponseDTOS(resultados);
    }

    public List<BuscarEmpresaServicoResponseDTO> buscarEmpresasPorCategoria(String categoria) {
        Set<Object[]> resultados = repository.buscarEmpresasPorCategoria(categoria);

        return getBuscarEmpresaServicoResponseDTOS(resultados);
    }

    private List<BuscarEmpresaServicoResponseDTO> getBuscarEmpresaServicoResponseDTOS(Set<Object[]> resultados) {
        if (resultados.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Nenhuma empresa encontrada.");
        }

        return resultados.stream()
                .map(resultado -> {
                    Empresa empresa = (Empresa) resultado[0];
                    String categoriaNome = (String) resultado[1];
                    return BuscarEmpresaServicoResponseDTO.from(
                            empresa.getIdEmpresa(),
                            empresa.getNomeEmpresa(),
                            empresa.getEndereco(),
                            empresa.getUrlLogo(),
                            empresa.getServicos(),
                            categoriaNome
                    );
                })
                .distinct()
                .collect(Collectors.toList());
    }

    public EnderecoDTO findEnderecoById(Integer idEmpresa) {

        var empresa = empresaRepository.findById(idEmpresa)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Empresa não encontrada."));

        return EnderecoDTO.fromEntity(empresa.getEndereco());

    }

    public EnderecoDTO updateEnderecoById(Integer idEmpresa, EnderecoDTO endereco) {

        var empresa = empresaRepository.findById(idEmpresa)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Empresa não encontrada."));

        Endereco enderecoCriado = null;
        if (endereco.idEndereco() == 0) {
            enderecoCriado = enderecoService.create(endereco.toEntity());
            empresa.setEndereco(enderecoCriado);
            empresaRepository.save(empresa);
            return EnderecoDTO.fromEntity(empresa.getEndereco());
        }

        Endereco enderecoExistente = empresa.getEndereco();

        enderecoExistente.setIdEndereco(endereco.idEndereco());
        enderecoExistente.setCep(endereco.cep());
        enderecoExistente.setCidade(endereco.cidade());
        enderecoExistente.setComplemento(endereco.complemento());
        enderecoExistente.setEstado(endereco.uf());
        enderecoExistente.setBairro(endereco.bairro());
        enderecoExistente.setLogradouro(endereco.logradouro());
        enderecoExistente.setNumero(endereco.numero());

        empresa.setEndereco(enderecoExistente);

        empresaRepository.save(empresa);

        return EnderecoDTO.fromEntity(empresa.getEndereco());
    }

    public NotificacaoDTO findNotificacaoById(Integer idEmpresa) {

        Empresa empresa = empresaRepository.findById(idEmpresa)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Empresa não encontrada."));

        return NotificacaoDTO.fromEntity(empresa.getNotificacao());
    }


    @Transactional
    public NotificacaoDTO createNotificacao(Integer idEmpresa, Notificacao notificacao) {

        Empresa empresa = empresaRepository.findById(idEmpresa)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Empresa não encontrada."));

        if (notificacao.getIdNotificacao() != null) {
            Notificacao notificacaoExistente = empresa.getNotificacao();
            if (notificacaoExistente != null) {
                notificacaoExistente.setMensagemCancelamento(notificacao.getMensagemCancelamento());
                notificacaoExistente.setMensagemAgendamento(notificacao.getMensagemAgendamento());
                empresa.setNotificacao(notificacaoExistente);
            } else {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Notificação não encontrada para atualização.");
            }
        } else {
            Notificacao notificacaoCriada = notificacaoRepository.save(notificacao);
            empresa.setNotificacao(notificacaoCriada);
        }

        empresaRepository.save(empresa);

        return NotificacaoDTO.fromEntity(empresa.getNotificacao());
    }

    public BuscarPerfilEmpresaResponseDTO buscarPerfilEmpresa(Integer idEmpresa) {

        Empresa empresa = empresaRepository.findById(idEmpresa)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Empresa não encontrada."));

        var imagens = empresa.getImagens().stream()
                .map(BuscarPerfilEmpresaResponseDTO.ImagemSimplificadaDTO::from)
                .collect(Collectors.toList());

        var servicos = empresa.getServicos().stream()
                .map(BuscarPerfilEmpresaResponseDTO.ServicoSimplificadoDTO::from)
                .collect(Collectors.toList());

        var usuarios = empresa.getUsuarios().stream()
                .map(BuscarPerfilEmpresaResponseDTO.UsuarioSimplificadoDTO::from)
                .collect(Collectors.toList());

        return new BuscarPerfilEmpresaResponseDTO(
                empresa.getNomeEmpresa(),
                EnderecoDTO.fromEntity(empresa.getEndereco()),
                imagens,
                servicos,
                usuarios
        );

    }
}
