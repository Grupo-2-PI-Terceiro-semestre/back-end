package sptech.school.order_hub.services;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import sptech.school.order_hub.entitiy.Funcao;
import sptech.school.order_hub.entitiy.Servico;
import sptech.school.order_hub.repository.FuncaoRepository;

import java.net.HttpRetryException;
import java.util.List;
import java.util.Optional;

@Service
public class FuncaoServices {

    private final FuncaoRepository funcaoRepository;

    public FuncaoServices(FuncaoRepository funcaoRepository) {
        this.funcaoRepository = funcaoRepository;
    }

    public Funcao create(Funcao funcao){

        if(funcaoRepository.existsByNomeFuncao(funcao.getNomeFuncao())){
            throw new RuntimeException("Função já cadastrada");
        }

        return funcaoRepository.save(funcao);
    }

    public List<Funcao> buscarTodasFuncoes() {

        List<Funcao> funcoes = funcaoRepository.findAll();

        if(funcoes.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "Nenhuma função cadastrada");
        }

        return funcoes;

    }

    public Funcao findById(Integer idFuncao) {
        Optional<Funcao> possivelFuncao = this.funcaoRepository.findById(idFuncao);
        if (possivelFuncao.isPresent()) {
            return possivelFuncao.get();
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Função não encontrada.");
    }
}
