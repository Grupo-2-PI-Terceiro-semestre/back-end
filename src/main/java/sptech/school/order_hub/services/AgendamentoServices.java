package sptech.school.order_hub.services;


import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import sptech.school.order_hub.controller.agendamento.request.BuscarAgendamentoRequestDTO;
import sptech.school.order_hub.controller.usuario.response.BuscarColaboradoresResponseDTO;
import sptech.school.order_hub.dtos.AgendamentoDTO;
import sptech.school.order_hub.entitiy.Agendamento;
import sptech.school.order_hub.repository.AgendamentoRepository;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
public class AgendamentoServices {


    private final AgendamentoRepository repository;

    public AgendamentoServices(AgendamentoRepository repository) {
        this.repository = repository;
    }


    public List<AgendamentoDTO> buscaAgendamento(BuscarAgendamentoRequestDTO request, Integer idAgenda) {

        LocalDateTime startOfDay = request.dataAgendamento().atStartOfDay();
        LocalDateTime endOfDay = request.dataAgendamento().atTime(LocalTime.MAX);

        List<Agendamento> agendamentos = repository.BuscarAgendamento(idAgenda, startOfDay, endOfDay);

        return agendamentos.stream()
                .map(AgendamentoDTO::from)
                .toList();
    }
}
