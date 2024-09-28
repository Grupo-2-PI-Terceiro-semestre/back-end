package sptech.school.order_hub.controller.categoria;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sptech.school.order_hub.controller.categoria.response.CategoriasResponseDTO;
import sptech.school.order_hub.services.CategoriaServices;

import java.util.List;

@RestController
@RequestMapping("api/v1/categorias")
public class CategoriaController {


    @Autowired
    private CategoriaServices services;

    @GetMapping
    public ResponseEntity<List<CategoriasResponseDTO>> findAll() {
        return ResponseEntity.ok(services.findAll());
    }
}
