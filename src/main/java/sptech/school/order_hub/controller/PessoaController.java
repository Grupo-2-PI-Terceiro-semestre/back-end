package sptech.school.order_hub.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sptech.school.order_hub.entitiy.Pessoa;
import sptech.school.order_hub.repository.PessoaRepository;

@RestController
@RequestMapping("/api/v1/pessoas")
public class PessoaController {

    @Autowired
    private PessoaRepository pessoaRepository;


    @PostMapping
    public ResponseEntity<Pessoa> createPessoa(
            @RequestBody Pessoa pessoa
    ) {

        if (this.pessoaRepository.existsByEmail(pessoa.getEmail())) {
            return ResponseEntity.status(409).build();
        }

        pessoa.setIdPessoa(null);
        Pessoa pessoaCadastrada = this.pessoaRepository.save(pessoa);

        return ResponseEntity.status(201).body(pessoaCadastrada);
    }
}
