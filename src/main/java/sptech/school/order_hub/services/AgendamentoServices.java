package sptech.school.order_hub.services;


import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import sptech.school.order_hub.controller.agendamento.request.AtualizarAgendamentoDinamicoRequestDTO;
import sptech.school.order_hub.controller.agendamento.request.BuscarAgendamentoRequestDTO;
import sptech.school.order_hub.controller.agendamento.response.ReceitaMensalResponseDTO;
import sptech.school.order_hub.dtos.AgendamentoDTO;
import sptech.school.order_hub.entitiy.Agenda;
import sptech.school.order_hub.entitiy.Agendamento;
import sptech.school.order_hub.enuns.StatusAgendamento;
import sptech.school.order_hub.repository.*;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
public class AgendamentoServices {


    private final AgendamentoRepository repository;
    private final AgendaRepository agendaRepository;

    public AgendamentoServices(AgendamentoRepository repository, AgendaRepository agendaRepository) {
        this.repository = repository;
        this.agendaRepository = agendaRepository;
    }


    public List<AgendamentoDTO> buscaAgendamento(BuscarAgendamentoRequestDTO request, Integer idAgenda, Boolean dadosCompletos) {

        LocalDateTime startOfDay = request.dataAgendamento().atStartOfDay();
        LocalDateTime endOfDay = request.dataAgendamento().atTime(LocalTime.MAX);

        List<Agendamento> agendamentos = null;
        if(dadosCompletos){
            agendamentos = repository.BuscarAgendamentoCompleto(idAgenda, startOfDay, endOfDay);

        }else{
        agendamentos = repository.BuscarAgendamento(idAgenda, startOfDay, endOfDay);
        }

        return agendamentos.stream()
                .map(AgendamentoDTO::from)
                .toList();
    }


    public AgendamentoDTO atualizarAgendamentoParcial(AtualizarAgendamentoDinamicoRequestDTO requestDTO) {
        Agendamento agendamento = repository.findByIdAgendamento(requestDTO.idAgendamento())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Agendamento não encontrado"));

        Agenda agenda = agendaRepository.findById(requestDTO.idAgenda())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Agenda não encontrada"));

        Optional.ofNullable(requestDTO.horaAgendamento())
                .ifPresent(agendamento::setDataHora);

        agendamento.setAgenda(agenda);

        Agendamento agendamentoAtualizado = repository.save(agendamento);

        return AgendamentoDTO.from(agendamentoAtualizado);
    }

    public AgendamentoDTO cancelarAgendamento(Integer idAgendamento) {

        Agendamento agendamento = repository.findByIdAgendamento(idAgendamento)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Agendamento não encontrado"));

        agendamento.setStatusAgendamento(StatusAgendamento.CANCELADO);

        Agendamento agendamentoCancelado = repository.save(agendamento);

        return AgendamentoDTO.from(agendamentoCancelado);
    }

    public ReceitaMensalResponseDTO buscarReceitaMensal(Integer idEmpresa, Integer mes) {
        List<Object[]> result = repository.ReceitaMensal(idEmpresa, mes);

        if (result.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Receita não encontrada");
        }

        Object[] row = result.get(0);
        Double totalReceita = (Double) row[0];
        Double comparativoReceita = (Double) row[1];

        return new ReceitaMensalResponseDTO(totalReceita, comparativoReceita);
    }

}
