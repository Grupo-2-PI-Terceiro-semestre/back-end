package sptech.school.order_hub.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import sptech.school.order_hub.config_exception.exceptions.RecursoNaoEncontradoException;
import sptech.school.order_hub.controller.categoria.response.CategoriasResponseDTO;
import sptech.school.order_hub.entitiy.Categoria;
import sptech.school.order_hub.repository.CategoriaRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoriaServices {

    @Autowired
    private CategoriaRepository repository;

    public List<CategoriasResponseDTO> findAll() {

        List<Categoria> categorias = repository.findAll();

        if (categorias.isEmpty()) {
            throw new RecursoNaoEncontradoException("Nenhuma categoria encontrada.");
        }

        return categorias.stream()
                .map(CategoriasResponseDTO::fromEntity)
                .collect(Collectors.toList());
    }


    public Categoria findByNome(String nome) {
        return repository.findByNome(nome).orElseThrow(() -> new RecursoNaoEncontradoException("A categoria que esta tentando cadastrar não existe."));
    }

    public Categoria save(Categoria categoria) {
        return repository.save(categoria);
    }
}
