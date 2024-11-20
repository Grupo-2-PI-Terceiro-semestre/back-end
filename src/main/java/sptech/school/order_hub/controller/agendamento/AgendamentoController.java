package sptech.school.order_hub.controller.agendamento;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;
import sptech.school.order_hub.controller.agendamento.request.AtualizarAgendamentoParcialRequestDTO;
import sptech.school.order_hub.controller.agendamento.request.AtualizarAgendamentoRequestDTO;
import sptech.school.order_hub.controller.agendamento.response.*;
import sptech.school.order_hub.dtos.AgendamentoDTO;
import sptech.school.order_hub.enuns.StatusAgendamento;
import sptech.school.order_hub.services.AgendamentoServices;

import java.io.IOException;
import java.util.List;

@Tag(name = "Agendamento", description = "Controller de agendamentos")
@RestController
@RequestMapping("api/v1/agendamentos")
public class AgendamentoController {


    private final AgendamentoServices agendamentoServices;


    public AgendamentoController(AgendamentoServices agendamentoServices) {
        this.agendamentoServices = agendamentoServices;
    }

    @Operation(summary = "Criar um Agendamento", description = "Cria um agendamento")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Agendamento criado com sucesso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = AgendamentoDTO.class),
                            examples = @ExampleObject(value = "{\"id\": 1, \"dataHora\": \"2023-10-10T10:00:00\", \"statusAgendamento\": \"CONFIRMADO\"}")
                    )
            ),
            @ApiResponse(responseCode = "400", description = "Erro ao criar agendamento",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = String.class),
                            examples = @ExampleObject(value = "{\"message\": \"Erro ao criar agendamento\"}")
                    )
            ),
            @ApiResponse(responseCode = "403", description = "Acesso negado"),
            @ApiResponse(responseCode = "500", description = "Erro interno")
    })

    @PostMapping
    public ResponseEntity<AgendamentoDTO> criarAgendamento(@RequestBody CriarAgendamentoRequestDTO requestDTO) {
        AgendamentoDTO agendamentoCriado = agendamentoServices.criarAgendamento(requestDTO);
        return ResponseEntity.status(HttpStatus.OK).body(agendamentoCriado);
    }

    @GetMapping(value = "/sse", produces = "text/event-stream")
    public SseEmitter streamEvents() {
        SseEmitter emitter = new SseEmitter(Long.MAX_VALUE);
        agendamentoServices.addEmitter(emitter);
        return emitter;
    }



    @Operation(summary = "Atualizar um Agendamento", description = "Atualiza um agendamento")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Agendamento atualizado com sucesso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = AgendamentoDTO.class),
                            examples = @ExampleObject(value = "{\"id\": 1, \"dataHora\": \"2023-10-10T10:00:00\", \"statusAgendamento\": \"CONFIRMADO\"}")
                    )
            ),
            @ApiResponse(responseCode = "400", description = "Erro ao atualizar agendamento"),
            @ApiResponse(responseCode = "403", description = "Acesso negado"),
            @ApiResponse(responseCode = "404", description = "Agendamento não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno")

    })

    @PutMapping()
    public ResponseEntity<AgendamentoDTO> atualizarAgendamento(@RequestBody AtualizarAgendamentoRequestDTO requestDTO) {
        return ResponseEntity.status(HttpStatus.OK).body(agendamentoServices.atualizarAgendamento(requestDTO));
    }

    @PutMapping("/parcial")
    public ResponseEntity<AgendamentoDTO> atualizarAgendamentoParcial(@RequestBody AtualizarAgendamentoParcialRequestDTO requestDTO) {
        return ResponseEntity.status(HttpStatus.OK).body(agendamentoServices.atualizarAgendamentoParcial(requestDTO));
    }

    @Operation(summary = "Cancelar um Agendamento", description = "Cancela um agendamento")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Agendamento cancelado com sucesso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = AgendamentoDTO.class),
                            examples = @ExampleObject(value = "{\"id\": 1, \"dataHora\": \"2023-10-10T10:00:00\", \"statusAgendamento\": \"CANCELADO\"}")
                    )
            ),
            @ApiResponse(responseCode = "400", description = "Erro ao cancelar agendamento"),
            @ApiResponse(responseCode = "403", description = "Acesso negado"),
            @ApiResponse(responseCode = "404", description = "Agendamento não encontrado"),
    })
    @PutMapping("/{idAgendamento}")
    public ResponseEntity<AgendamentoDTO> cancelarAgendamento(@PathVariable Integer idAgendamento, @RequestParam String status) {
        return ResponseEntity.status(HttpStatus.OK).body(agendamentoServices.cancelarAgendamento(idAgendamento, StatusAgendamento.fromString(status)));
    }

    @Operation(summary = "Buscar Receita Mensal", description = "Busca a receita mensal de uma empresa")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Receita mensal encontrada",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ReceitaMensalResponseDTO.class),
                            examples = @ExampleObject(value = "{\"receitaMensal\": 1000.0}")
                    )
            ),
            @ApiResponse(responseCode = "400", description = "Erro ao buscar receita mensal"),
            @ApiResponse(responseCode = "403", description = "Acesso negado"),
            @ApiResponse(responseCode = "404", description = "Empresa não encontrada"),
    })
    @GetMapping("/empresa/{idEmpresa}")
    public ResponseEntity<ReceitaMensalResponseDTO> buscarReceitaMensal(@PathVariable Integer idEmpresa, @RequestParam Integer mes) {
        return ResponseEntity.status(HttpStatus.OK).body(agendamentoServices.buscarReceitaMensal(idEmpresa, mes));
    }

    @GetMapping("/servicos/{idEmpresa}")
    public ResponseEntity<ServicoMensalResponseDTO> buscarServicoMensal(@PathVariable Integer idEmpresa, @RequestParam Integer mes) {
        return ResponseEntity.status(HttpStatus.OK).body(agendamentoServices.buscarServicoMensal(idEmpresa, mes));
    }


    @GetMapping("/ticket/{idEmpresa}")
    public ResponseEntity<TicketMedioResponseDTO> buscarTicketMedio(@PathVariable Integer idEmpresa) {
        return ResponseEntity.status(HttpStatus.OK).body(agendamentoServices.buscarTicketMedio(idEmpresa));
    }

    @GetMapping("/agendamentos/{idEmpresa}")
    public ResponseEntity<List<ProximosAgendamentosResponseDTO>> buscarAgendamentos(@PathVariable Integer idEmpresa) {
        return ResponseEntity.status(HttpStatus.OK).body(agendamentoServices.buscarAgendamentos(idEmpresa));
    }

}
