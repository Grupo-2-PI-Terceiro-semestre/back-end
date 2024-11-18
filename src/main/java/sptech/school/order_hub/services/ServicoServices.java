package sptech.school.order_hub.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import sptech.school.order_hub.controller.cliente.request.BuscarClienteRequestDto;
import sptech.school.order_hub.controller.servico.request.BuscarServicoPaginadoDTO;
import sptech.school.order_hub.controller.servico.request.BuscarServicoRequestDTO;
import sptech.school.order_hub.controller.servico.response.BuscarServicosDTO;
import sptech.school.order_hub.entitiy.Cliente;
import sptech.school.order_hub.entitiy.Empresa;
import sptech.school.order_hub.entitiy.Paginacao;
import sptech.school.order_hub.entitiy.Servico;
import sptech.school.order_hub.repository.EmpresaRepository;
import sptech.school.order_hub.repository.ServicoRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ServicoServices {


    @Autowired
    private EmpresaRepository empresaRepository;

    @Autowired
    private ServicoRepository servicoRepository;


    public Servico createServico(Servico servicoParaCadastrar, Integer empresId) {
        Empresa empresa = empresaRepository.findById(empresId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Empresa não encontrada."));

        empresa.addServico(servicoParaCadastrar);

        servicoParaCadastrar.setIdServico(null);

        return servicoRepository.save(servicoParaCadastrar);
    }

    public Servico findById(Integer idServico) {
        Optional<Servico> possivelServico = this.servicoRepository.findById(idServico);
        if (possivelServico.isPresent()) {
            return possivelServico.get();
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Serviço não encontrado.");
    }

    public List<Servico> findByCategoria(String nomeServico) {
        List<Servico> servicosEncontrados = servicoRepository.findByNomeServicoLike(nomeServico);

        if (servicosEncontrados.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "Nenhum serviço encontrado para esta categoria.");
        }
        return servicosEncontrados;

    }


    public Servico updateServico(int idServico, Servico servicoParaAtualizar) {
        Servico servicoBanco = servicoRepository.findById(idServico)
                .orElseThrow(() -> new RuntimeException("Serviço não encontrado"));

        servicoBanco.setNomeServico(servicoParaAtualizar.getNomeServico());
        servicoBanco.setValorServico(servicoParaAtualizar.getValorServico());
        servicoBanco.setDescricao(servicoParaAtualizar.getDescricao());

        return servicoRepository.save(servicoBanco);
    }

    public void deleteById(int idServico) {
        if (!this.servicoRepository.existsById(idServico)) {
            servicoRepository.deleteById(idServico);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Serviço não encontrado.");
        }
    }

    public List<BuscarServicosDTO> buscarServicosDaEmpresa(int idEmpresa) {

        Empresa empresa = empresaRepository.findById(idEmpresa)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Empresa não encontrada."));

        List<Servico> servicos = servicoRepository.findyServicoByEmpresaId(idEmpresa);

        if (servicos.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "Nenhum serviço encontrado para esta empresa.");
        }

        return servicos.stream()
                .map(BuscarServicosDTO::from)
                .toList();
    }


    public Paginacao<Servico> buscarServicosPaginado(final Integer idEmpresa, final BuscarServicoPaginadoDTO request) {

        final var pagina = PageRequest.of(request.pagina(), request.tamanho());

        final var empresa = empresaRepository.findById(idEmpresa).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Empresa não existe"));

        final var page = servicoRepository.findAllByEmpresaOrderByIdServicoAsc(empresa, pagina);

        return Paginacao.of(page.getContent(), page.getTotalElements(), page.isLast());
    }
}