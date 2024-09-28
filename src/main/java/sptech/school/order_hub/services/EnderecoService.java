package sptech.school.order_hub.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sptech.school.order_hub.dtos.EnderecoDTO;
import sptech.school.order_hub.entitiy.Endereco;
import sptech.school.order_hub.repository.EnderecoRepository;

@Service
public class EnderecoService {

    @Autowired
    private EnderecoRepository repository;

    public Endereco create(Endereco endereco) {

        if (endereco == null) {
            throw new IllegalArgumentException("Endereco não pode ser nulo");
        }
        return repository.save(endereco);
    }
}
