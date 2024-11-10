package sptech.school.order_hub.controller.agenda;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Agenda", description = "Controller de agendas")
@RestController
@RequestMapping("/agendas")
public class AgendaController {
}
