package sptech.school.order_hub.services;


import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import sptech.school.order_hub.controller.agendamento.request.AtualizarAgendamentoDinamicoRequestDTO;
import sptech.school.order_hub.controller.agendamento.request.BuscarAgendamentoRequestDTO;
import sptech.school.order_hub.controller.agendamento.response.CriarAgendamentoRequestDTO;
import sptech.school.order_hub.controller.agendamento.response.ReceitaMensalResponseDTO;
import sptech.school.order_hub.dtos.AgendamentoDTO;
import sptech.school.order_hub.entitiy.Agenda;
import sptech.school.order_hub.entitiy.Agendamento;
import sptech.school.order_hub.entitiy.Cliente;
import sptech.school.order_hub.entitiy.Servico;
import sptech.school.order_hub.enuns.StatusAgendamento;
import sptech.school.order_hub.observer.NotificacaoObserver;
import sptech.school.order_hub.observer.Subject;
import sptech.school.order_hub.repository.*;
import sptech.school.order_hub.sender.implementation.EmailSenderImple;
import sptech.school.order_hub.sender.implementation.SmsSenderImple;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
public class AgendamentoServices extends Subject {


    private final AgendamentoRepository repository;
    private final AgendaRepository agendaRepository;
    private final ServicoServices servicoServices;
    private final ClienteServices clienteServices;

    public AgendamentoServices(AgendamentoRepository repository, AgendaRepository agendaRepository, ServicoServices servicoServices, ClienteServices clienteServices) {
        this.repository = repository;
        this.agendaRepository = agendaRepository;
        this.servicoServices = servicoServices;
        this.clienteServices = clienteServices;
    }


    public List<AgendamentoDTO> buscaAgendamento(BuscarAgendamentoRequestDTO request, Integer idAgenda, Boolean dadosCompletos) {

        LocalDateTime startOfDay = request.dataAgendamento().atStartOfDay();
        LocalDateTime endOfDay = request.dataAgendamento().atTime(LocalTime.MAX);

        List<Agendamento> agendamentos = null;
        if (dadosCompletos) {
            agendamentos = repository.BuscarAgendamentoCompleto(idAgenda, startOfDay, endOfDay);

        } else {
            agendamentos = repository.BuscarAgendamento(idAgenda, startOfDay, endOfDay);
        }

        return agendamentos.stream()
                .map(AgendamentoDTO::from)
                .toList();
    }


    public Agendamento findById(Integer idAgendamento) {
        Optional<Agendamento> agendamento = repository.findById(idAgendamento);
        if (agendamento.isPresent()) {
            return agendamento.get();
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Agendamento não encontrado.");
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

    public AgendamentoDTO cancelarAgendamento(final Integer idAgendamento, final StatusAgendamento status) {

        Agendamento agendamento = repository.findByIdAgendamento(idAgendamento)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Agendamento não encontrado"));

        agendamento.setStatusAgendamento(status);

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

    public AgendamentoDTO criarAgendamento(CriarAgendamentoRequestDTO requestDTO) {
        Agendamento agendamento = new Agendamento();
        Agenda agenda = agendaRepository.findById(requestDTO.idAgenda())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Agenda não encontrada"));

        Servico servico = servicoServices.findById(requestDTO.idServico());
        Cliente cliente = clienteServices.findById(requestDTO.idCliente());

        agendamento.setServico(servico);
        agendamento.setCliente(cliente);
        agendamento.setAgenda(agenda);
        agendamento.setDataHora(requestDTO.dataAgendamento());
        agendamento.setStatusAgendamento(StatusAgendamento.AGENDADO);

        notificarObservers(cliente, "agendamento");

        Agendamento agendamentoCriado = repository.save(agendamento);

        return AgendamentoDTO.from(agendamentoCriado);
    }

    private void notificarObservers(Cliente destinatario, String acao) {

        addObserver(new NotificacaoObserver(new EmailSenderImple(), destinatario.getEmailPessoa()));
        addObserver(new NotificacaoObserver(new SmsSenderImple(), destinatario.getNumeroTelefone()));

        notifyObservers(acao);
    }
}
