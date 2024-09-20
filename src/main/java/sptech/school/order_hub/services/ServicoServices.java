package sptech.school.order_hub.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import sptech.school.order_hub.entitiy.EmpresaTemCategoria;
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


    public Servico createServico(Servico servicoParaCadastrar) {
        Integer empresId = servicoParaCadastrar.getEmpresaTemCategoria().getEmpresa().getIdEmpresa();
        boolean empresaExiste = empresaRepository.existsById(empresId);

        if (!empresaExiste) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Empresa não encontrada.");
        }
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

    public List<Servico> findByCategoria(EmpresaTemCategoria empresaTemCategoria) {

        List<Servico> servicosEncontrados = servicoRepository.findByEmpresaTemCategoria(empresaTemCategoria);

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
}
