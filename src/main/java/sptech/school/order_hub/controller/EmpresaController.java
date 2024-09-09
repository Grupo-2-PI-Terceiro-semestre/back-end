package sptech.school.order_hub.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sptech.school.order_hub.entitiy.Empresa;
import sptech.school.order_hub.repository.EmpresaRepository;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/empresas")
public class EmpresaController {

    @Autowired
    private EmpresaRepository repository;

    @PostMapping
    public ResponseEntity<Empresa> create(@RequestBody Empresa empresaParaCadastrar) {
        empresaParaCadastrar.setIdEmpresa(null);

        Empresa empresaSalva =this.repository.save(empresaParaCadastrar);

        return ResponseEntity.status(201).body(empresaSalva);
    }

    @GetMapping()
    public ResponseEntity<List<Empresa>> listar() {
        List<Empresa> todasEmpresas = this.repository.findAll();

        if (todasEmpresas.isEmpty()) {
            return ResponseEntity.status(204).build();
        }

        return ResponseEntity.status(200).body(todasEmpresas);
    }

    @GetMapping("/{idEmpresa}")
    public ResponseEntity<Empresa> findById(@PathVariable Integer id) {
        Optional<Empresa> empresaId = this.repository.findById(id);

        if (empresaId.isPresent()) {
            Empresa empresa = empresaId.get();
            return ResponseEntity.status(200).body(empresa);
        }

        return ResponseEntity.status(404).build();
    }

    @DeleteMapping("/{idEmpresa}")
    public ResponseEntity<Void> deleteById(@PathVariable Integer id) {
        if (this.repository.existsById(id)) {
            repository.deleteById(id);
            return ResponseEntity.status(204).build();
        }

        return ResponseEntity.status(404).build();
    }

    @PutMapping("/{idEmpresa}")
    public ResponseEntity<Empresa> updateById(@PathVariable Integer id, @RequestBody Empresa empresaParaAtualizar) {
        if (repository.existsById(id)) {
            empresaParaAtualizar.setIdEmpresa(id);
            Empresa empresaAtualizada = repository.save(empresaParaAtualizar);

            return ResponseEntity.status(200).body(empresaAtualizada);
        }

        return ResponseEntity.status(400).build();
    }
}
