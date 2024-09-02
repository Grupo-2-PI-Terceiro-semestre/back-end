package sptech.school.order_hub.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sptech.school.order_hub.entitiy.Funcionario;
import sptech.school.order_hub.services.FuncionarioServices;

import java.util.List;

@RestController
@RequestMapping("api/v1/funcionarios")
public class FuncionarioController {

    @Autowired
    private FuncionarioServices services;

    @PostMapping()
    public ResponseEntity<Funcionario> create(@RequestBody Funcionario funcionario) {
        return this.services.create(funcionario);
    }

    @GetMapping
    public ResponseEntity<List<Funcionario>> finfAll() {
        return this.services.findAll();
    }

    @GetMapping("/{idFuncionario}")
    public ResponseEntity<Funcionario> findById(@PathVariable Integer idFuncionario) {
        return this.services.findById(idFuncionario);
    }

    @GetMapping("/empresa/{idEmpresa}")
    public ResponseEntity<List<Funcionario>> findByEnterprises(@PathVariable Integer idEmpresa) {
        return this.services.findByFkEmpresa(idEmpresa);
    }

    @PutMapping("{id}")
    public ResponseEntity<Funcionario> update(@PathVariable Integer id, @RequestBody Funcionario funcionarioAtualizar) {
        return this.services.update(id, funcionarioAtualizar);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        return this.services.delete(id);
    }
}
