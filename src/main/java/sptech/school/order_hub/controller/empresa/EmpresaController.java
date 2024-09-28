package sptech.school.order_hub.controller.empresa;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sptech.school.order_hub.controller.empresa.request.BuscarEmpresaRequestDTO;
import sptech.school.order_hub.controller.empresa.request.CadastroEmpresaRequestDTO;
import sptech.school.order_hub.controller.empresa.response.BuscarEmpresaResponseDTO;
import sptech.school.order_hub.controller.empresa.response.CadastroEmpresaResponseDTO;
import sptech.school.order_hub.dtos.EmpresaDTO;
import sptech.school.order_hub.entitiy.Empresa;
import sptech.school.order_hub.services.EmpresaServices;

import java.util.List;

@RestController
@RequestMapping("/api/v1/empresas")
public class EmpresaController {

    @Autowired
    private EmpresaServices empresaService;

    @PostMapping
    public ResponseEntity<CadastroEmpresaResponseDTO> create(@Valid @RequestBody CadastroEmpresaRequestDTO empresaDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(empresaService.create(empresaDTO.toEntity(), empresaDTO.idPessoa()));
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<BuscarEmpresaResponseDTO>> listarEmpresaPeloNome(@Valid @RequestParam BuscarEmpresaRequestDTO termo) {
        return ResponseEntity.status(HttpStatus.OK).body(empresaService.listarEmpresaPeloNome(termo));
    }

    @GetMapping("/{idEmpresa}")
    public ResponseEntity<Empresa> findById(@PathVariable Integer idEmpresa) {
        Empresa empresa = empresaService.findById(idEmpresa);
        return ResponseEntity.status(HttpStatus.OK).body(empresa);
    }

    @DeleteMapping("/{idEmpresa}")
    public ResponseEntity<Void> deleteById(@PathVariable Integer idEmpresa) {
        empresaService.deleteById(idEmpresa);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

    }

    @PutMapping("/{idEmpresa}")
    public ResponseEntity<Empresa> updateById(@PathVariable Integer idEmpresa, @RequestBody Empresa empresaParaAtualizar) {
        Empresa empresaAtualizada = empresaService.updateById(idEmpresa, empresaParaAtualizar);
        return ResponseEntity.status(HttpStatus.OK).body(empresaAtualizada);

    }
}
