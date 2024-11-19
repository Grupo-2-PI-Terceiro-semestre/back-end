package sptech.school.order_hub.controller.cliente;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sptech.school.order_hub.controller.agendamento.response.CriarAgendamentoRequestDTO;
import sptech.school.order_hub.controller.cliente.request.BuscarClienteRequestDto;
import sptech.school.order_hub.controller.cliente.request.CriarClienteRequestDTO;
import sptech.school.order_hub.controller.cliente.response.BuscarClientesPaginadosResponseDTO;
import sptech.school.order_hub.controller.cliente.response.BuscarClientesResponseDTO;
import sptech.school.order_hub.dtos.AgendamentoDTO;
import sptech.school.order_hub.dtos.ClienteDTO;
import sptech.school.order_hub.entitiy.Cliente;
import sptech.school.order_hub.services.ClienteServices;

import java.util.List;

@Tag(name = "Cliente", description = "Controller de clientes")
@RestController
@RequestMapping("/api/v1/clientes")
public class ClienteController {


    private final ClienteServices service;

    public ClienteController(ClienteServices clienteServices) {
        this.service = clienteServices;
    }

    @Operation(summary = "Buscar todos os clientes", description = "Busca todos os clientes")
    @ApiResponse(responseCode = "200", description = "Clientes encontrados", content = @Content(mediaType = "application/json",
            examples = {
                    @ExampleObject(name = "Exemplo 1", value = "[{\"id\": 1, \"nome\": \"Cliente 1\", \"cpf\": \"12345678901\", \"telefone\": \"1234567890\"}]"),
                    @ExampleObject(name = "Exemplo 2", value = "[{\"id\": 2, \"nome\": \"Cliente 2\", \"cpf\": \"98765432109\", \"telefone\": \"0987654321\"}]")
            }))
    @ApiResponse(responseCode = "204", description = "Nenhum cliente encontrado")
    @GetMapping("api/externo/random")
    public ResponseEntity<String> findByRandomUserApi() {
        return service.findByUserRandomApi();
    }

    @Operation(summary = "Buscar a quantidade de usuários", description = "Busca a quantidade de usuários")
    @ApiResponse(responseCode = "200", description = "Total de usuarios encontrados", content = @Content(mediaType = "application/json",
            examples = @ExampleObject(value = "{\"total\": 5}")))
    @GetMapping("api/externo/random/results/{results}")
    public ResponseEntity<String> findByQuantityUser(@PathVariable Integer results) {
        return service.findByQuantityUser(results);
    }

    @Operation(summary = "Busca usuarios por gênero", description = "Busca usuarios por gênero")
    @ApiResponse(responseCode = "200", description = "Usuários encontrados", content = @Content(mediaType = "application/json",
            examples = @ExampleObject(value = "[{\"id\": 1, \"nome\": \"Cliente 1\", \"cpf\": \"12345678901\", \"telefone\": \"1234567890\"}], \"gender\": \"male\"", name = "Exemplo 1")))
    @GetMapping("api/externo/random/gender")
    public ResponseEntity<String> findByGanderUserRandomApi(@RequestParam String gender) {
        return service.findByGanderUserRandomApi(gender);
    }

    @Operation(summary = "Busca usuarios por nacionalidade", description = "Busca usuarios por nacionalidade")
    @ApiResponse(responseCode = "200", description = "Usuários encontrados", content = @Content(mediaType = "application/json",
            examples = @ExampleObject(value = "[{\"id\": 1, \"nome\": \"Cliente 1\", \"cpf\": \"12345678901\", \"telefone\": \"1234567890\"}], \"nat\": \"BR\"", name = "Exemplo 1")))
    @ApiResponse(responseCode = "204", description = "Nenhum usuário encontrado")
    @ApiResponse(responseCode = "400", description = "Nacionalidade inválida")
    @ApiResponse(responseCode = "500", description = "Erro interno")
    @GetMapping("api/externo/random/nat")
    public ResponseEntity<String> findByNatUser(@RequestParam String nat) {
        return service.findByNatUser(nat);
    }


    @GetMapping("api/externo/random/order/{results}")
    public ResponseEntity<Cliente[]> findByQuantityUserOrder(@PathVariable Integer results, @RequestParam String nat) {
        return service.findByQuantityUserOrder(results, nat);
    }

    @PostMapping("api/externo/pesquisa-binaria")
    public ResponseEntity<Integer> resultPesquisaBinaria(@RequestBody ClienteDTO cliente) {
        return service.pesquisaBinaria(ClienteDTO.toEntity(cliente));
    }

    @Operation(summary = "Buscar todos os clientes de uma empresa", description = "Busca todos os clientes de uma empresa")
    @ApiResponse(responseCode = "200", description = "Clientes encontrados", content = @Content(mediaType = "application/json",
            examples = {
                    @ExampleObject(name = "Exemplo 1", value = "[{\"id\": 1, \"nome\": \"Cliente 1\", \"telefone\": \"1234567890\", \"email\": \"cliente1@example.com\"}]"),
                    @ExampleObject(name = "Exemplo 1", value = "[{\"id\": 2, \"nome\": \"Cliente 2\", \"telefone\": \"1234567890\", \"email\": \"cliente2@example.com\"}]"),
            }))

//    @PostMapping("empresa/{idEmpresa}")
//    public ResponseEntity<BuscarClientesPaginadosResponseDTO> buscarClientes(@PathVariable Integer idEmpresa,
//                                                                             @RequestParam Integer pagina,
//                                                                             @RequestParam Integer tamanho,
//                                                                             ) {
//
//        var request = BuscarClienteRequestDto.from(tamanho, pagina);
//        var output = service.buscarClientesPaginado(idEmpresa, request);
//        var responseDto = BuscarClientesPaginadosResponseDTO.fromEntity(output);
//        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
//    }


    @PostMapping("empresa/paginado/{idEmpresa}")
    public ResponseEntity<BuscarClientesPaginadosResponseDTO> buscarClientes(@PathVariable Integer idEmpresa,
                                                                             @RequestBody BuscarClienteRequestDto request
    ) {
        var output = service.buscarClientesPaginado(idEmpresa, request);
        var responseDto = BuscarClientesPaginadosResponseDTO.fromEntity(output);
        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }


    @GetMapping("empresa/{idEmpresa}")
    public ResponseEntity<List<BuscarClientesResponseDTO>> buscarClientes(@PathVariable final Integer idEmpresa) {
        final var output = service.buscarClientes(idEmpresa);
        return ResponseEntity.status(HttpStatus.OK).body(output);
    }

    @PostMapping
    public ResponseEntity<ClienteDTO> criarCliente(@RequestBody CriarClienteRequestDTO requestDTO) {
        return ResponseEntity.status(HttpStatus.OK).body(service.criarCliente(requestDTO));
    }
}