package sptech.school.order_hub.controller.agendamento;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sptech.school.order_hub.controller.agendamento.request.AtualizarAgendamentoDinamicoRequestDTO;
import sptech.school.order_hub.controller.agendamento.response.CriarAgendamentoRequestDTO;
import sptech.school.order_hub.controller.agendamento.response.ReceitaMensalResponseDTO;
import sptech.school.order_hub.dtos.AgendamentoDTO;
import sptech.school.order_hub.services.AgendamentoServices;

@RestController
@RequestMapping("api/v1/agendamentos")
public class AgendamentoController {


    private final AgendamentoServices agendamentoServices;

    public AgendamentoController(AgendamentoServices agendamentoServices) {
        this.agendamentoServices = agendamentoServices;
    }

    @PostMapping
    public ResponseEntity<AgendamentoDTO> criarAgendamento(@RequestBody CriarAgendamentoRequestDTO requestDTO) {
        return ResponseEntity.status(HttpStatus.OK).body(agendamentoServices.criarAgendamento(requestDTO));
    }

    @PutMapping()
    public ResponseEntity<AgendamentoDTO> atualizarAgendamento(@RequestBody AtualizarAgendamentoDinamicoRequestDTO requestDTO) {
        return ResponseEntity.status(HttpStatus.OK).body(agendamentoServices.atualizarAgendamentoParcial(requestDTO));
    }

    @PutMapping("/{idAgendamento}")
    public ResponseEntity<AgendamentoDTO> cancelarAgendamento(@PathVariable Integer idAgendamento) {
        return ResponseEntity.status(HttpStatus.OK).body(agendamentoServices.cancelarAgendamento(idAgendamento));
    }

    @GetMapping("/empresa/{idEmpresa}")
    public ResponseEntity<ReceitaMensalResponseDTO> buscarReceitaMensal(@PathVariable Integer idEmpresa, @RequestParam Integer mes) {
        return ResponseEntity.status(HttpStatus.OK).body(agendamentoServices.buscarReceitaMensal(idEmpresa, mes));
    }


}
