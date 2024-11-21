package sptech.school.order_hub.services;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import sptech.school.order_hub.controller.empresa.request.BuscarEmpresaRequestDTO;
import sptech.school.order_hub.controller.empresa.response.BuscarEmpresaResponseDTO;
import sptech.school.order_hub.controller.empresa.response.BuscarEmpresaServicoResponseDTO;
import sptech.school.order_hub.controller.empresa.response.CadastroEmpresaResponseDTO;
import sptech.school.order_hub.dtos.EnderecoDTO;
import sptech.school.order_hub.dtos.NotificacaoDTO;
import sptech.school.order_hub.entitiy.*;
import sptech.school.order_hub.repository.EmpresaRepository;
import sptech.school.order_hub.repository.NotificacaoRepository;
import sptech.school.order_hub.repository.UsuarioRepository;

import java.util.List;
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

        List<Empresa> empresas = repository.findByNomeEmpresaOrServico(input.termo(), input.termo());

        if (empresas.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Nenhuma empresa encontrada.");
        }

        return empresas.stream()
                .map(empresa -> BuscarEmpresaServicoResponseDTO.from(
                        empresa.getIdEmpresa(),
                        empresa.getNomeEmpresa(),
                        empresa.getEndereco(),
                        empresa.getUrlImagem(),
                        empresa.getServicos())
                )
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

        enderecoService.create(endereco.toEntity());

        empresa.setEndereco(endereco.toEntity());

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

}
