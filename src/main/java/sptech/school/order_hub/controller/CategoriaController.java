package sptech.school.order_hub.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sptech.school.order_hub.enuns.CategoriaEnum;
import sptech.school.order_hub.repository.CategoriaRepository;

import java.util.List;

@RestController
@RequestMapping("/categorias")
public class CategoriaController {


    @Autowired
    private CategoriaRepository repository;

    @GetMapping
    public ResponseEntity<List<CategoriaEnum>> findAll() {
        return null;
    }
}
