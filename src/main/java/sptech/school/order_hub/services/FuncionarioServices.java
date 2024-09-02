package sptech.school.order_hub.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import sptech.school.order_hub.entitiy.Funcionario;
import sptech.school.order_hub.repository.FuncionarioRepository;

import java.util.List;
import java.util.Optional;

@Service
public class FuncionarioServices {

    @Autowired
    private FuncionarioRepository repository;

    public ResponseEntity<Funcionario> create(Funcionario funcionario) {
        funcionario.setIdFuncionario(null);
        return ResponseEntity.status(201).body(this.repository.save(funcionario));
    }

    public ResponseEntity<List<Funcionario>> findAll() {
        List<Funcionario> funcionarios = repository.findAll();

        if (funcionarios.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(funcionarios);
    }

    public ResponseEntity<Funcionario> findById(Integer idFuncionario) {
        Optional<Funcionario> funcionario = repository.findById(idFuncionario);
        return funcionario.map(value -> ResponseEntity.status(HttpStatus.OK).body(value)).orElseGet(() -> ResponseEntity.status(HttpStatus.NO_CONTENT).build());
    }

    public ResponseEntity<List<Funcionario>> findByFkEmpresa(Integer idEmpresa) {

        List<Funcionario> funcionarios = repository.findByFkEmpresa(idEmpresa);

        if (funcionarios.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }

        return ResponseEntity.status(HttpStatus.OK).body(funcionarios);
    }

    public ResponseEntity<Funcionario> update(Integer id, Funcionario funcionarioAtualizar) {

        Optional<Funcionario> funcionario = repository.findById(id);

        if (funcionario.isPresent()) {
            funcionario.get().setIdFuncionario(id);
            funcionario.get().setNomeFuncionario(funcionarioAtualizar.getNomeFuncionario());
            funcionario.get().setCargo(funcionarioAtualizar.getCargo());
            funcionario.get().setFkEmpresa(funcionarioAtualizar.getFkEmpresa());
            funcionario.get().setFkUsuario(funcionarioAtualizar.getFkUsuario());
            funcionario.get().setFkGestor(funcionarioAtualizar.getFkGestor());
            return ResponseEntity.status(HttpStatus.OK).body(repository.save(funcionario.get()));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    public ResponseEntity<Void> delete(Integer id) {
        Optional<Funcionario> funcionario = repository.findById(id);
        if (funcionario.isPresent()) {
            repository.deleteById(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}
