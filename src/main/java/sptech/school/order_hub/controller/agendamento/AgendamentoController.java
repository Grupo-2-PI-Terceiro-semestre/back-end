package sptech.school.order_hub.controller.agendamento;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import sptech.school.order_hub.controller.agendamento.request.AtualizarAgendamentoParcialRequestDTO;
import sptech.school.order_hub.controller.agendamento.request.AtualizarAgendamentoRequestDTO;
import sptech.school.order_hub.controller.agendamento.response.*;
import sptech.school.order_hub.dtos.AgendamentoDTO;
import sptech.school.order_hub.dtos.UsuarioDTO;
import sptech.school.order_hub.enuns.StatusAgendamento;
import sptech.school.order_hub.services.AgendamentoServices;
import sptech.school.order_hub.services.NotificationService;

import java.time.LocalDateTime;
import java.util.List;

@Tag(name = "Agendamento", description = "Controller de agendamentos")
@RestController
@RequestMapping("api/v1/agendamentos")
public class AgendamentoController {


    private final AgendamentoServices agendamentoServices;
    private final NotificationService notificationService;


    public AgendamentoController(AgendamentoServices agendamentoServices, NotificationService notificationService) {
        this.agendamentoServices = agendamentoServices;
        this.notificationService = notificationService;
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

    @PostMapping("/cliente")
    public ResponseEntity<AgendamentoDTO> clienteCriarAgendamento(@RequestBody ClienteCriarAgendamentoRequestDTO requestDTO) {
        AgendamentoDTO agendamentoCriado = agendamentoServices.clienteCriarAgendamento(requestDTO);
        return ResponseEntity.status(HttpStatus.OK).body(agendamentoCriado);
    }

    @PostMapping("/app/cliente")
    public ResponseEntity<Void> clienteCriarAgendamentoApp(@RequestBody ClienteCriarAgendamentoRequestDTO requestDTO) {
        agendamentoServices.clienteCriarAgendamento(requestDTO);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/cliente/{idCliente}")
    public ResponseEntity<List<AgendamentosClienteResponseDTO>> buscarAgendamentosCliente(@PathVariable Integer idCliente) {
        return ResponseEntity.status(HttpStatus.OK).body(agendamentoServices.buscarAgendamentosCliente(idCliente));
    }

    @GetMapping("/app/cliente/{idCliente}")
    public ResponseEntity<List<AgendamentosClienteAppResponseDTO>> buscarAgendamentosAppCliente(@PathVariable Integer idCliente) {

        // Mock de proficionais da empresa
        UsuarioDTO usuario1 = new UsuarioDTO(
                1,
                "Maria Oliveira",
                "11999999999",
                "123.456.789-00",
                "1990-05-10",
                "FEMININO",
                "END123"
        );

        UsuarioDTO usuario2 = new UsuarioDTO(
                2,
                "João Pereira",
                "11888888888",
                "987.654.321-00",
                "1985-12-20",
                "MASCULINO",
                "END456"
        );

        List<UsuarioDTO> proficionais = List.of(usuario1, usuario2);

        // Mock do agendamento
        AgendamentosClienteAppResponseDTO agendamentoMock = AgendamentosClienteAppResponseDTO.builder()
                .idAgendamento(101)
                .nomeServico("Corte de Cabelo")
                .nomeEmpresa("Barbearia do João")
                .dataHora(LocalDateTime.of(2025, 6, 8, 14, 30))
                .status("CONFIRMADO")
                .atendente("Carlos Silva")
                .proficionaisDaEmpresa(proficionais)
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(List.of(agendamentoMock));
    }


    @PutMapping("/notificacao/{idEmpresa}")
    public ResponseEntity<Void> buscarNotificacao(@PathVariable Integer idEmpresa) {
        notificationService.buscarMensagensNaoLidas(idEmpresa.toString());
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PutMapping("/notificacao/lida/{idEmpresa}")
    public ResponseEntity<Void> marcarComoLida(@PathVariable String idEmpresa) {
        notificationService.marcarComoLida(idEmpresa);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping(value = "/sse", produces = "text/event-stream")
    public SseEmitter streamEvents() {
        SseEmitter emitter = new SseEmitter(Long.MAX_VALUE); // Configura o timeout do SSE
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
    public ResponseEntity<AgendamentoDTO> updateStatusAgendamento(@PathVariable Integer idAgendamento, @RequestParam String status) {
        return ResponseEntity.status(HttpStatus.OK).body(agendamentoServices.updateStatusAgendamento(idAgendamento, StatusAgendamento.fromString(status)));
    }

    @Operation(summary = "Buscar Receita Mensal", description = "Busca a receita mensal de uma empresa")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Receita mensal encontrada",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ReceitaEComparativoResponseDTO.class),
                            examples = @ExampleObject(value = "{\"receitaMensal\": 1000.0}")
                    )
            ),
            @ApiResponse(responseCode = "400", description = "Erro ao buscar receita mensal"),
            @ApiResponse(responseCode = "403", description = "Acesso negado"),
            @ApiResponse(responseCode = "404", description = "Empresa não encontrada"),
    })
    @GetMapping("/empresa/{idEmpresa}")
    public ResponseEntity<ReceitaEComparativoResponseDTO> buscarReceitaMensal(@PathVariable Integer idEmpresa, @RequestParam Integer mes) {
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

    @GetMapping("/{idEmpresa}")
    public ResponseEntity<List<ProximosAgendamentosResponseDTO>> buscarAgendamentos(@PathVariable Integer idEmpresa) {
        return ResponseEntity.status(HttpStatus.OK).body(agendamentoServices.buscarAgendamentos(idEmpresa));
    }

    @GetMapping("/aReceber/{idEmpresa}")
    public ResponseEntity<Double> buscarValorAReceber(@PathVariable Integer idEmpresa) {
        if (idEmpresa == null || idEmpresa <= 0) {
            throw new IllegalArgumentException("ID da empresa inválido ou não fornecido.");
        }
        Double valorAReceber = agendamentoServices.buscarValorAReceber(idEmpresa);
        return ResponseEntity.status(HttpStatus.OK).body(valorAReceber);
    }

    @GetMapping("/receitaPorFuncionario/{idEmpresa}")
    public ResponseEntity<List<ReceitaPorFuncionarioResponseDTO>> buscarReceitaPorFuncionario(@PathVariable Integer idEmpresa) {
        return ResponseEntity.status(HttpStatus.OK).body(agendamentoServices.buscarReceitaPorFuncionario(idEmpresa));
    }

    @GetMapping("/novosClientes/{idEmpresa}")
    public ResponseEntity<ClientesMensaisResponseDTO> buscarClientesMensais(@PathVariable Integer idEmpresa) {
        return ResponseEntity.status(HttpStatus.OK).body(agendamentoServices.buscarClientesMensais(idEmpresa));
    }

    @GetMapping("/receitaPorMes/{idEmpresa}")
    public ResponseEntity<List<ReceitaPorMesDTO>> buscarReceitaPorMes(@PathVariable Integer idEmpresa) {
        return ResponseEntity.status(HttpStatus.OK).body(agendamentoServices.buscarReceitaPorMes(idEmpresa));
    }

    @GetMapping("/servicoDiaSemana/{idEmpresa}")
    public ResponseEntity<List<ServicoDiaSemanaResponseDTO>> buscarServicoDiaSemana(@PathVariable Integer idEmpresa) {
        return ResponseEntity.status(HttpStatus.OK).body(agendamentoServices.buscarServicoDiaSemana(idEmpresa));
    }


    @GetMapping("/receitaPorServico/{idEmpresa}")
    public ResponseEntity<List<ReceitaPorServicoResponseDTO>> buscarReceitaPorServico(@PathVariable Integer idEmpresa) {
        return ResponseEntity.status(HttpStatus.OK).body(agendamentoServices.buscarReceitaPorServico(idEmpresa));
    }

    @PutMapping("/cancelaAgendamento/{idAgendamento}")
    public ResponseEntity<AgendamentoDTO> cancelaAgendamento(@PathVariable Integer idAgendamento) {
        return ResponseEntity.status(HttpStatus.OK).body(agendamentoServices.cancelaAgendamento(idAgendamento));
    }
}
