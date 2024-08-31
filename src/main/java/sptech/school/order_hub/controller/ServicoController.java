package sptech.school.order_hub.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sptech.school.order_hub.entitiy.Servico;
import sptech.school.order_hub.repository.ServicoRepository;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/servicos")
public class ServicoController {

    @Autowired
    private ServicoRepository repository;

    @PostMapping
    public ResponseEntity<Servico>create(
        @RequestBody Servico servicoParaCadastrar
    ){
    servicoParaCadastrar.setIdServico(null);
    Servico servicoSalvo = this.repository.save(servicoParaCadastrar);
    return ResponseEntity.status(201).body(servicoSalvo);
    }

    @PutMapping("/{idServico}")
    public ResponseEntity<Servico> updateByld(
    @PathVariable int idServico,
    @RequestBody Servico servicoParaAtualizar){
    return ResponseEntity.status(404).build();
    }

    @GetMapping("/{fkEmpresa}")
    public ResponseEntity<List<Servico>> findServicesEnterprise(@PathVariable Integer fkEmpresa) {
        List<Servico> servicosEncontrados = this.repository.findByFkEmpresa(fkEmpresa);
        if (servicosEncontrados.isEmpty()){
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(200).body(servicosEncontrados);
    }



    @GetMapping("/{fkCategoria}")
    public ResponseEntity<List<Servico>> fkCategoriafindAServicesProfessional(@PathVariable Integer fkCategoria) {
        List<Servico> servicosEncontrados = this.repository.findByFkCategoria(fkCategoria);
        if (servicosEncontrados.isEmpty()){
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(200).body(servicosEncontrados);
    }

    @GetMapping("/{idServico}")
    public ResponseEntity<Servico> findById(@PathVariable int idServico
    ){
        Optional<Servico> possivelServico =
                this.repository.findById(idServico);

        if (possivelServico.isPresent()){
            Servico servico = possivelServico.get();
            return ResponseEntity.status(200).body(servico);
        }
        return ResponseEntity.status(404).build();
    }


    @DeleteMapping("/{idServico}")
    public ResponseEntity<Void> deleteById(@PathVariable int idServico
    ){
        if (this.repository.existsById(idServico)){
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(404).build();
    }


    @GetMapping
    public ResponseEntity<List<Servico>> listar(){
        List<Servico> todosServicos = this.repository.findAll();

        if(todosServicos.isEmpty()){
            return ResponseEntity.status(204).build();
        }

        return ResponseEntity.status(200).body(todosServicos);

    }
}
