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
import sptech.school.order_hub.entitiy.*;
import sptech.school.order_hub.repository.EmpresaRepository;
import sptech.school.order_hub.repository.NotificacaoRepository;
import sptech.school.order_hub.repository.UsuarioRepository;

import java.util.ArrayList;
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
    private GeocodingService geocodingService;


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
                            empresa.getEndereco() != null ? empresa.getEndereco() : new Endereco(),
                            empresa.getUrlLogo() != null ? empresa.getUrlLogo() : "",
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

    public EnderecoDTO updateEnderecoById(Integer idEmpresa, EnderecoDTO enderecoDTO) {

        var empresa = empresaRepository.findById(idEmpresa)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Empresa não encontrada."));

        Endereco enderecoCriado;

        if (enderecoDTO.idEndereco() == 0) {
            enderecoCriado = enderecoService.create(enderecoDTO.toEntity());

            // Buscar coordenadas
            geocodingService.getCoordinatesFromAddress(enderecoCriado)
                    .ifPresent(geo -> {
                        enderecoCriado.setLatitude(String.valueOf(geo.getLat()));
                        enderecoCriado.setLongitude(String.valueOf(geo.getLon()));
                    });

            empresa.setEndereco(enderecoCriado);
            empresaRepository.save(empresa);
            return EnderecoDTO.fromEntity(empresa.getEndereco());
        }

        Endereco enderecoExistente = empresa.getEndereco();

        enderecoExistente.setCep(enderecoDTO.cep());
        enderecoExistente.setCidade(enderecoDTO.cidade());
        enderecoExistente.setComplemento(enderecoDTO.complemento());
        enderecoExistente.setEstado(enderecoDTO.uf());
        enderecoExistente.setBairro(enderecoDTO.bairro());
        enderecoExistente.setLogradouro(enderecoDTO.logradouro());
        enderecoExistente.setNumero(enderecoDTO.numero());

        geocodingService.getCoordinatesFromAddress(enderecoExistente)
                .ifPresent(geo -> {
                    enderecoExistente.setLatitude(String.valueOf(geo.getLat()));
                    enderecoExistente.setLongitude(String.valueOf(geo.getLon()));
                });

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
                EnderecoDTO.fromEntity(empresa.getEndereco() != null ? empresa.getEndereco() : new Endereco()),
                imagens,
                servicos,
                usuarios
        );

    }


    public List<BuscarPerfilEmpresaResponseDTO> buscarGeolocalizacao(String cidade, String bairro) {

        var empresas = repository.buscarPorGeolocalizacao(cidade, bairro); // Passando as variáveis corretamente

        if (empresas.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Nenhuma empresa encontrada.");
        }

        List<BuscarPerfilEmpresaResponseDTO> responseList = new ArrayList<>();

        for (Empresa empresa : empresas) {

            if(empresa.getEndereco().getLatitude() == null || empresa.getEndereco().getLongitude() == null) {
                geocodingService.getCoordinatesFromAddress(empresa.getEndereco())
                        .ifPresent(geo -> {
                            empresa.getEndereco().setLatitude(String.valueOf(geo.getLat()));
                            empresa.getEndereco().setLongitude(String.valueOf(geo.getLon()));
                        });
            }

            var imagens = empresa.getImagens().stream()
                    .map(BuscarPerfilEmpresaResponseDTO.ImagemSimplificadaDTO::from)
                    .collect(Collectors.toList());

            var servicos = empresa.getServicos().stream()
                    .map(BuscarPerfilEmpresaResponseDTO.ServicoSimplificadoDTO::from)
                    .collect(Collectors.toList());

            var usuarios = empresa.getUsuarios().stream()
                    .map(BuscarPerfilEmpresaResponseDTO.UsuarioSimplificadoDTO::from)
                    .collect(Collectors.toList());

            responseList.add(new BuscarPerfilEmpresaResponseDTO(
                    empresa.getNomeEmpresa(),
                    EnderecoDTO.fromEntity(empresa.getEndereco() != null ? empresa.getEndereco() : new Endereco()),
                    imagens,
                    servicos,
                    usuarios
            ));
        }

        return responseList;
    }

}
