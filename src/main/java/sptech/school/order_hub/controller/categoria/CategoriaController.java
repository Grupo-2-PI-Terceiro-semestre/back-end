package sptech.school.order_hub.controller.categoria;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sptech.school.order_hub.controller.categoria.response.CategoriasResponseDTO;
import sptech.school.order_hub.services.CategoriaServices;

import java.util.List;

@Tag(name = "Categoria", description = "Controller de categorias")
@RestController
@RequestMapping("api/v1/categorias")
public class CategoriaController {


    @Autowired
    private CategoriaServices services;

    @Operation(summary = "Listar todas as categorias", description = "Lista todas as categorias")
    @ApiResponse(responseCode = "200", description = "Categorias listadas com sucesso")
    @GetMapping
    public ResponseEntity<List<CategoriasResponseDTO>> findAll() {
        return ResponseEntity.ok(services.findAll());
    }
}
