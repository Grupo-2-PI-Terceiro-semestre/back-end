package sptech.school.order_hub.controller.agenda;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sptech.school.order_hub.controller.agenda.request.BuscarHorariosIndisponiveisRequest;
import sptech.school.order_hub.services.AgendaServices;

import java.sql.Time;
import java.util.List;

@Tag(name = "Agenda", description = "Controller de agendas")
@RestController
@RequestMapping("api/v1/agendas")
public class AgendaController {

    private final AgendaServices agendaServices;

    public AgendaController(AgendaServices agendaServices) {
        this.agendaServices = agendaServices;
    }

    @GetMapping("/horarios-indisponiveis/empresa/{idEmpresa}")
    public ResponseEntity<List<Time>> buscarHorariosIndisponiveis(@PathVariable Integer idEmpresa, @RequestBody BuscarHorariosIndisponiveisRequest request) {
        var output = agendaServices.buscarHorariosIndisponiveis(idEmpresa, request);
        return ResponseEntity.ok(output);
    }
}
