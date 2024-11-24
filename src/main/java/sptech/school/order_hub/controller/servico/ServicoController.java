package sptech.school.order_hub.controller.servico;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sptech.school.order_hub.controller.servico.request.BuscarServicoPaginadoDTO;
import sptech.school.order_hub.controller.servico.response.BuscarServicosDTO;
import sptech.school.order_hub.controller.servico.response.BuscarServicosPaginadosResponseDTO;
import sptech.school.order_hub.dtos.ServicoDTO;
import sptech.school.order_hub.entitiy.Categoria;
import sptech.school.order_hub.entitiy.Servico;
import sptech.school.order_hub.repository.ServicoRepository;
import sptech.school.order_hub.services.ServicoServices;

import java.util.List;

@Tag(name = "Servico", description = "Controller de serviços")
@RestController
@RequestMapping("api/v1/servicos")
public class ServicoController {

    @Autowired
    private ServicoRepository repository;

    @Autowired
    private ServicoServices servicoService;


    @Operation(summary = "Atualiza um serviço", description = "Atualiza um serviço")
    @PutMapping("/{idServico}")
    public ResponseEntity<Servico> updateById(
            @PathVariable int idServico,
            @RequestBody Servico servicoParaAtualizar) {
        Servico servicoAtualizado = servicoService.updateServico(idServico, servicoParaAtualizar);
        return ResponseEntity.status(HttpStatus.OK).body(servicoAtualizado);
    }

    @Operation(summary = "Cadastrar um serviço", description = "Cadastra um serviço")
    @PostMapping("/{idEmpresa}")
    public ResponseEntity<ServicoDTO> create(@RequestBody ServicoDTO servicoDTO, @PathVariable int idEmpresa) {

        ServicoDTO servicoCriado = servicoService.createServico(ServicoDTO.toEntity(servicoDTO), idEmpresa);

        return ResponseEntity.status(HttpStatus.CREATED).body(servicoCriado);
    }

    @Operation(summary = "Buscar serviços de uma empresa", description = "Busca os serviços de uma empresa")
    @GetMapping("/empresa/{idEmpresa}")
    public ResponseEntity<List<BuscarServicosDTO>> findServicesByEmpresa(@PathVariable int idEmpresa) {

        List<BuscarServicosDTO> servicosEncontrados = servicoService.buscarServicosDaEmpresa(idEmpresa);
        return ResponseEntity.status(HttpStatus.OK).body(servicosEncontrados);
    }

    @PostMapping("empresa/paginado/{idEmpresa}")
    public ResponseEntity<BuscarServicosPaginadosResponseDTO> buscarServicosPaginados(@PathVariable Integer idEmpresa,
                                                                                      @RequestBody BuscarServicoPaginadoDTO request) {
//        var request = BuscarServicoPaginadoDTO.from(pagina,tamanho);
        var output = servicoService.buscarServicosPaginado(idEmpresa, request);
        var responseDto = BuscarServicosPaginadosResponseDTO.fromEntity(output);
        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }

    @Operation(summary = "Buscar serviços por categoria", description = "Busca os serviços por categoria")
    @GetMapping("/categoria")
    public ResponseEntity<List<Servico>> findServicesByCategoria(@Valid @RequestBody Categoria categoria) {
        List<Servico> servicosEncontrados = servicoService.findByCategoria(categoria.getNome());
        return ResponseEntity.status(HttpStatus.OK).body(servicosEncontrados);
    }

    @Operation(summary = "Buscar serviço por id", description = "Busca um serviço por id")
    @GetMapping("/{idServico}")
    public ResponseEntity<Servico> findById(@Valid @PathVariable int idServico) {
        return ResponseEntity.status(HttpStatus.OK).body(servicoService.findById(idServico));
    }


    @Operation(summary = "Deletar um serviço", description = "Deleta um serviço")
    @DeleteMapping("/{idServico}")
    public ResponseEntity<Void> deleteById(@Valid @PathVariable int idServico
    ) {
        servicoService.deleteById(idServico);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Operation(summary = "Listar todos os serviços", description = "Lista todos os serviços")
    @GetMapping
    public ResponseEntity<List<Servico>> listar() {
        List<Servico> todosServicos = this.repository.findAll();

        if (todosServicos.isEmpty()) {
            return ResponseEntity.status(204).build();
        }

        return ResponseEntity.status(200).body(todosServicos);

    }
}