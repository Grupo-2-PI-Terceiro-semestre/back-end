package sptech.school.order_hub.controller.agendamento;


import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sptech.school.order_hub.controller.agendamento.request.BuscarAgendamentoRequestDTO;
import sptech.school.order_hub.controller.agendamento.response.BuscarAgendamentoResponseDTO;
import sptech.school.order_hub.dtos.AgendamentoDTO;
import sptech.school.order_hub.services.AgendamentoServices;
import sptech.school.order_hub.services.UsuarioServices;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("api/v1/agendamentos")
public class AgendamentoController {


    private final AgendamentoServices agendamentoServices;

    public AgendamentoController(AgendamentoServices agendamentoServices) {
        this.agendamentoServices = agendamentoServices;
    }


}
