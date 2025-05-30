package sptech.school.order_hub.controller.empresa;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sptech.school.order_hub.controller.empresa.request.BuscarEmpresaRequestDTO;
import sptech.school.order_hub.controller.empresa.request.CadastroEmpresaRequestDTO;
import sptech.school.order_hub.controller.empresa.response.*;
import sptech.school.order_hub.dtos.EnderecoDTO;
import sptech.school.order_hub.dtos.NotificacaoDTO;
import sptech.school.order_hub.entitiy.Empresa;
import sptech.school.order_hub.entitiy.Imagem;
import sptech.school.order_hub.services.EmpresaServices;
import sptech.school.order_hub.services.ImagensServices;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Tag(name = "Empresa", description = "Controller de empresas")
@RestController
@RequestMapping("/api/v1/empresas")
public class EmpresaController {

    @Autowired
    private EmpresaServices empresaService;
    @Autowired
    private ImagensServices imagensServices;

    @Operation(summary = "Cadastrar uma empresa", description = "Cadastra uma empresa")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Empresa cadastrada com sucesso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CadastroEmpresaRequestDTO.class),
                            examples = @ExampleObject(value = "{\"id\": 1, \"nome\": \"Empresa 1\", \"cnpj\": \"12345678901234\", \"telefone\": \"1234567890\", \"email\": \"empresa1@example.com\"}")
                    )
            ),
            @ApiResponse(responseCode = "400", description = "Erro ao criar empresa"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    @PostMapping
    public ResponseEntity<CadastroEmpresaResponseDTO> create(@Valid @RequestBody CadastroEmpresaRequestDTO empresaDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(empresaService.create(empresaDTO.toEntity(), empresaDTO.idPessoa()));
    }

    @Operation(summary = "Listar empresa pelo nome", description = "Lista todas as empresas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Empresas listadas com sucesso",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = "[{\"id\": 1, \"nome\": \"Empresa 1\", \"cnpj\": \"12345678901234\", \"telefone\": \"1234567890\", \"email\": \"empresa1@example.com\"}]")
                    )
            ),
            @ApiResponse(responseCode = "400", description = "Erro ao listar empresas"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    @GetMapping("/buscar")
    public ResponseEntity<List<BuscarEmpresaServicoResponseDTO>> listarEmpresaPeloNome(@Valid @RequestParam BuscarEmpresaRequestDTO termo) {
        return ResponseEntity.status(HttpStatus.OK).body(empresaService.listarEmpresaPeloNome(termo));
    }

    @GetMapping("/buscar/categoria/{categoria}")
    public ResponseEntity<List<BuscarEmpresaServicoResponseDTO>> buscarEmpresasPorCategoria(@PathVariable String categoria) {
        return ResponseEntity.status(HttpStatus.OK).body(empresaService.buscarEmpresasPorCategoria(categoria));
    }

    @GetMapping("/perfil/{idEmpresa}")
    public ResponseEntity<BuscarPerfilEmpresaResponseDTO> buscarPerfilEmpresa(@Valid @PathVariable Integer idEmpresa) {
        return ResponseEntity.status(HttpStatus.OK).body(empresaService.buscarPerfilEmpresa(idEmpresa));
    }

    @GetMapping("/geolocalizacao")
    public ResponseEntity<List<BuscarPerfilEmpresaResponseDTO>> buscarGeolocalizacao(@RequestParam String cidade, @RequestParam String bairro) {
        return ResponseEntity.status(HttpStatus.OK).body(empresaService.buscarGeolocalizacao(cidade, bairro));
    }

    @Operation(summary = "buscar por id", description = "Busca uma empresa pelo id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Empresa encontrada com sucesso",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = "[{\"id\": 1, \"nome\": \"Empresa 1\", \"cnpj\": \"12345678901234\", \"telefone\": \"1234567890\", \"email\": \"empresa1@example.com\"}]")
                    )
            ),
            @ApiResponse(responseCode = "400", description = "Erro ao buscar empresa"),
            @ApiResponse(responseCode = "403", description = "Acesso negado"),
            @ApiResponse(responseCode = "404", description = "Empresa não encontrada")
    })
    @GetMapping("/{idEmpresa}")
    public ResponseEntity<BuscarEmpresaResponseDTO> findById(@PathVariable Integer idEmpresa) {
        return ResponseEntity.status(HttpStatus.OK).body(empresaService.findById(idEmpresa));
    }

    @GetMapping("/{idEmpresa}/notificacao")
    public ResponseEntity<NotificacaoDTO> findNotificacaoById(@PathVariable Integer idEmpresa) {
        return ResponseEntity.status(HttpStatus.OK).body(empresaService.findNotificacaoById(idEmpresa));
    }

    @PutMapping("/{idEmpresa}/notificacao")
    public ResponseEntity<NotificacaoDTO> createNotificacao(@PathVariable Integer idEmpresa, @RequestBody NotificacaoDTO notificacao) {
        var request = NotificacaoDTO.toEntity(notificacao);
        var response = empresaService.createNotificacao(idEmpresa, request);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }


    @GetMapping("/{idEmpresa}/endereco")
    public ResponseEntity<EnderecoDTO> findEnderecoById(@PathVariable Integer idEmpresa) {
        return ResponseEntity.status(HttpStatus.OK).body(empresaService.findEnderecoById(idEmpresa));
    }

    @PostMapping("/endereco/{idEmpresa}")
    public ResponseEntity<EnderecoDTO> updateEnderecoById(@PathVariable Integer idEmpresa, @RequestBody EnderecoDTO endereco) {
        return ResponseEntity.status(HttpStatus.OK).body(empresaService.updateEnderecoById(idEmpresa, endereco));
    }

    @PostMapping("/imagem/upload/{idEmpresa}")
    public ResponseEntity<String> uploadImagem(@RequestParam MultipartFile file, @PathVariable Integer idEmpresa) throws IOException {
        String urlImagem = imagensServices.uploadLogoEmpresa(file, idEmpresa);
        return ResponseEntity.ok(urlImagem);
    }


    @PostMapping("/imagem/uploadImagem/{idEmpresa}")
    public ResponseEntity<Map<String, Object>> uploadImagensEmpresa(@RequestParam MultipartFile file, @PathVariable Integer idEmpresa) throws IOException {
        Imagem imagem = imagensServices.uploadImagensEmpresa(file, idEmpresa); // Recupera a imagem salva

        // Monta a resposta com os dados da imagem
        Map<String, Object> response = new HashMap<>();
        response.put("idImagem", imagem.getIdImagem());
        response.put("urlImagem", imagem.getUrlImagem());


        return ResponseEntity.ok(response); // Retorna os dados como JSON
    }

    @GetMapping("/imagens/{idEmpresa}")
    public ResponseEntity<List<BuscarImagensDTO>> findImagensByEmpresaId(@PathVariable Integer idEmpresa) {
        return ResponseEntity.ok(imagensServices.findImagensByEmpresaId(idEmpresa));
    }

    @Operation(summary = "Deletar empresa", description = "Deleta uma empresa")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Empresa deletada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro ao deletar empresa"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    @DeleteMapping("/{idEmpresa}")
    public ResponseEntity<Void> deleteById(@PathVariable Integer idEmpresa) {
        empresaService.deleteById(idEmpresa);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Operation(summary = "Atualizar empresa", description = "Atualiza uma empresa")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Empresa atualizada com sucesso",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = "{\"id\": 1, \"nome\": \"Empresa 1\", \"cnpj\": \"12345678901234\", \"telefone\": \"1234567890\", \"email\": \"empresa1@example.com\"}")
                    )
            ),
            @ApiResponse(responseCode = "400", description = "Erro ao atualizar empresa"),
            @ApiResponse(responseCode = "403", description = "Acesso negado"),
            @ApiResponse(responseCode = "404", description = "Empresa não encontrada")
    })
    @PutMapping("/{idEmpresa}")
    public ResponseEntity<Empresa> updateById(@PathVariable Integer idEmpresa, @RequestBody Empresa empresaParaAtualizar) {
        Empresa empresaAtualizada = empresaService.updateById(idEmpresa, empresaParaAtualizar);
        return ResponseEntity.status(HttpStatus.OK).body(empresaAtualizada);
    }

    @DeleteMapping("imagem/{idImagem}")
    public ResponseEntity<Void> deleteImagemById(@PathVariable Integer idImagem) {
        imagensServices.deleteImagemById(idImagem);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
