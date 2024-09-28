package sptech.school.order_hub.controller.servico;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sptech.school.order_hub.entitiy.Categoria;
import sptech.school.order_hub.entitiy.Servico;
import sptech.school.order_hub.repository.ServicoRepository;
import sptech.school.order_hub.services.ServicoServices;

import java.util.List;

@RestController
@RequestMapping("api/v1/servicos")
public class ServicoController {

    @Autowired
    private ServicoRepository repository;

    @Autowired
    private ServicoServices servicoService;


    @PutMapping("/{idServico}")
    public ResponseEntity<Servico> updateById(
            @PathVariable int idServico,
            @RequestBody Servico servicoParaAtualizar) {
        Servico servicoAtualizado = servicoService.updateServico(idServico, servicoParaAtualizar);
        return ResponseEntity.status(HttpStatus.OK).body(servicoAtualizado);
    }

    @PostMapping("/{idEmpresa}")
    public ResponseEntity<Servico> create(@Valid @RequestBody Servico servicoParaCadastrar, @PathVariable int idEmpresa) {
        Servico servicoCriado = servicoService.createServico(servicoParaCadastrar, idEmpresa);
        return ResponseEntity.status(HttpStatus.CREATED).body(servicoCriado);
    }


    @GetMapping("/categoria")
    public ResponseEntity<List<Servico>> findServicesByCategoria(@Valid @RequestBody Categoria categoria) {
        List<Servico> servicosEncontrados = servicoService.findByCategoria(categoria.getNome());
        return ResponseEntity.status(HttpStatus.OK).body(servicosEncontrados);
    }

    @GetMapping("/{idServico}")
    public ResponseEntity<Servico> findById(@Valid @PathVariable int idServico) {
        return ResponseEntity.status(HttpStatus.OK).body(servicoService.findById(idServico));
    }


    @DeleteMapping("/{idServico}")
    public ResponseEntity<Void> deleteById(@Valid @PathVariable int idServico
    ) {
        servicoService.deleteById(idServico);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }


    @GetMapping
    public ResponseEntity<List<Servico>> listar() {
        List<Servico> todosServicos = this.repository.findAll();

        if (todosServicos.isEmpty()) {
            return ResponseEntity.status(204).build();
        }

        return ResponseEntity.status(200).body(todosServicos);

    }
}
