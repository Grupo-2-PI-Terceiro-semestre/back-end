package sptech.school.order_hub.controller.agenda;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sptech.school.order_hub.controller.agenda.request.BuscarHorariosIndisponiveisRequest;
import sptech.school.order_hub.services.AgendaServices;

import java.sql.Time;
import java.time.LocalDate;
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
    public ResponseEntity<List<Time>> buscarHorariosIndisponiveis(@PathVariable Integer idEmpresa,
                                                                  @RequestParam Integer idProfissional,
                                                                  @RequestParam LocalDate data) {
        var request = BuscarHorariosIndisponiveisRequest.from(idProfissional, data);
        var output = agendaServices.buscarHorariosIndisponiveis(idEmpresa, request);
        return ResponseEntity.ok(output);
    }
}
