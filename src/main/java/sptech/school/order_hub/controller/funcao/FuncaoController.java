package sptech.school.order_hub.controller.funcao;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sptech.school.order_hub.controller.funcao.request.FuncaoRequest;
import sptech.school.order_hub.controller.funcao.response.FuncaoResponse;
import sptech.school.order_hub.services.FuncaoServices;

import java.util.List;

@RestController
@RequestMapping("/api/v1/funcoes")
public class FuncaoController {

    private final FuncaoServices funcaoServices;

    public FuncaoController(FuncaoServices funcaoServices) {
        this.funcaoServices = funcaoServices;
    }

    @PostMapping()
    public ResponseEntity<FuncaoResponse> create(@RequestBody FuncaoRequest request) {

        var funcao = funcaoServices.create(FuncaoRequest.toEntity(request));

        return ResponseEntity.ok(FuncaoResponse.from(funcao));
    }


    @GetMapping
    public ResponseEntity<List<FuncaoResponse>> findAll() {

        var funcao = funcaoServices.buscarTodasFuncoes();

        return ResponseEntity
                .ok(funcao
                .stream()
                .map(FuncaoResponse::from)
                .toList());
    }
}
