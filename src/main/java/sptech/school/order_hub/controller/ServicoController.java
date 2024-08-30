package sptech.school.order_hub.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sptech.school.order_hub.entitiy.Servico;
import sptech.school.order_hub.repository.ServicoRepository;

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



}
