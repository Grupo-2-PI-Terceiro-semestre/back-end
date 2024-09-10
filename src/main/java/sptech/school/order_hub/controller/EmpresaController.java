package sptech.school.order_hub.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sptech.school.order_hub.entitiy.Empresa;
import sptech.school.order_hub.entitiy.Servico;
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
    public ResponseEntity<Empresa> findById(@PathVariable Integer idEmpresa) {
        Optional<Empresa> empresaId = this.repository.findById(idEmpresa);

        if (empresaId.isPresent()) {
            Empresa empresa = empresaId.get();
            return ResponseEntity.status(200).body(empresa);
        }

        return ResponseEntity.status(404).build();
    }

    @DeleteMapping("/{idEmpresa}")
    public ResponseEntity<Void> deleteById(@PathVariable Integer idEmpresa) {
        if (this.repository.existsById(idEmpresa)) {
            repository.deleteById(idEmpresa);
            return ResponseEntity.status(204).build();
        }

        return ResponseEntity.status(404).build();
    }

    @PutMapping("/{idEmpresa}")
    public ResponseEntity<Empresa> updateById(@PathVariable Integer idEmpresa, @RequestBody Empresa empresaParaAtualizar) {

        Optional<Empresa> empresaBanco = repository.findById(idEmpresa);

        empresaParaAtualizar.setIdEmpresa(null);
        if (empresaBanco.isPresent()) {
            empresaBanco.get().setIdEmpresa(idEmpresa);
            empresaBanco.get().setNomeEmpresa(empresaParaAtualizar.getNomeEmpresa());
            empresaBanco.get().setEmailEmpresa(empresaParaAtualizar.getEmailEmpresa());
            empresaBanco.get().setCnpj(empresaParaAtualizar.getCnpj());
            empresaBanco.get().setTelefone(empresaParaAtualizar.getTelefone());
            empresaBanco.get().setFkAssinante(empresaParaAtualizar.getFkAssinante());
            empresaBanco.get().setImagem(empresaParaAtualizar.getImagem());

            return ResponseEntity.status(200).body(repository.save(empresaBanco.get()));
        }

        return ResponseEntity.status(404).build();
    }
}
