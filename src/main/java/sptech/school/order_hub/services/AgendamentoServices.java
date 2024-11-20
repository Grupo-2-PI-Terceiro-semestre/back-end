package sptech.school.order_hub.services;


import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import sptech.school.order_hub.controller.agendamento.request.AtualizarAgendamentoParcialRequestDTO;
import sptech.school.order_hub.controller.agendamento.request.AtualizarAgendamentoRequestDTO;
import sptech.school.order_hub.controller.agendamento.request.BuscarAgendamentoRequestDTO;
import sptech.school.order_hub.controller.agendamento.response.*;
import sptech.school.order_hub.dtos.AgendamentoDTO;
import sptech.school.order_hub.entitiy.Agenda;
import sptech.school.order_hub.entitiy.Agendamento;
import sptech.school.order_hub.entitiy.Cliente;
import sptech.school.order_hub.entitiy.Servico;
import sptech.school.order_hub.enuns.StatusAgendamento;
import sptech.school.order_hub.observer.NotificacaoObserver;
import sptech.school.order_hub.observer.Subject;
import sptech.school.order_hub.repository.AgendaRepository;
import sptech.school.order_hub.repository.AgendamentoRepository;
import sptech.school.order_hub.sender.implementation.EmailSenderImple;
import sptech.school.order_hub.sender.implementation.SmsSenderImple;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

@Service
public class AgendamentoServices extends Subject {


    private final AgendamentoRepository repository;
    private final AgendaRepository agendaRepository;
    private final ServicoServices servicoServices;
    private final ClienteServices clienteServices;

    private final CopyOnWriteArrayList<SseEmitter> emitters = new CopyOnWriteArrayList<>();


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

    public List<ProximosAgendamentosResponseDTO> buscarAgendamentos(Integer idEmpresa) {
        return repository.findNextAgendamentoByEmpresa(idEmpresa).stream()
                .map(result -> new ProximosAgendamentosResponseDTO(
                        (String) result[0],
                        (String) result[1],
                        (String) result[2],
                        (String) result[3],
                        (String) result[4]
                )).collect(Collectors.toList());
    }


    public Agendamento findById(Integer idAgendamento) {
        Optional<Agendamento> agendamento = repository.findById(idAgendamento);
        if (agendamento.isPresent()) {
            return agendamento.get();
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Agendamento não encontrado.");
    }


    public AgendamentoDTO atualizarAgendamentoParcial(AtualizarAgendamentoParcialRequestDTO requestDTO) {
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

        tigerEvent(agendamentoAtualizado);

        return AgendamentoDTO.from(agendamentoAtualizado);
    }

    public AgendamentoDTO atualizarAgendamento(AtualizarAgendamentoRequestDTO requestDTO) {

        final var agendamento = repository.findByIdAgendamento(requestDTO.idAgendamento())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Agendamento não encontrado"));

        final var agenda = agendaRepository.findById(requestDTO.idAgenda())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Agenda não encontrada"));

        final var servico = servicoServices.findById(requestDTO.idServico());
        final var cliente = clienteServices.findById(requestDTO.idCliente());

        Optional.ofNullable(requestDTO.dataAgendamento())
                .ifPresent(agendamento::setDataHora);


        agendamento.setCliente(cliente);
        agendamento.setServico(servico);
        agendamento.setAgenda(agenda);

        final var agendamentoAtualizado = repository.save(agendamento);

        tigerEvent(agendamentoAtualizado);

        return AgendamentoDTO.from(agendamentoAtualizado);
    }

    public AgendamentoDTO cancelarAgendamento(final Integer idAgendamento, final StatusAgendamento status) {

        Agendamento agendamento = repository.findByIdAgendamento(idAgendamento)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Agendamento não encontrado"));

        agendamento.setStatusAgendamento(status);

        Agendamento agendamentoCancelado = repository.save(agendamento);

        tigerEvent(agendamentoCancelado);

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

    public ServicoMensalResponseDTO buscarServicoMensal(Integer idEmpresa, Integer mes) {
        List<Object[]> result = repository.ServicoMensal(idEmpresa, mes);

        Integer totalServicos = 0;
        Double comparativoServicos = 0.0;

        if (!result.isEmpty()) {
            Object[] row = result.get(0);
            totalServicos = row[0] != null ? ((Number) row[0]).intValue() : 0;
            comparativoServicos = row[1] != null ? ((Number) row[1]).doubleValue() : 0.0;
        }

        return new ServicoMensalResponseDTO(totalServicos, comparativoServicos);
    }

    public TicketMedioResponseDTO buscarTicketMedio(Integer idEmpresa) {
        Double ticketMedio = repository.TicketMedio(idEmpresa);

        return TicketMedioResponseDTO.from(ticketMedio);
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

        tigerEvent(agendamentoCriado);

        return AgendamentoDTO.from(agendamentoCriado);
    }

    private void tigerEvent(Agendamento agendamento) {

        boolean refrash;
        if (agendamento == null) {
            refrash = false;
        } else {
            refrash = agendamento.getDataHora().toLocalDate().isEqual(LocalDateTime.now().toLocalDate());
        }


        for (SseEmitter emitter : emitters) {
            try {
                emitter.send(SseEmitter.event()
                        .name("refrash")
                        .data(refrash));
            } catch (Exception e) {
                emitter.complete();
                emitters.remove(emitter);
            }
        }
    }

    public void addEmitter(SseEmitter emitter) {
        emitters.add(emitter);
        emitter.onCompletion(() -> emitters.remove(emitter));
        emitter.onTimeout(() -> emitters.remove(emitter));
    }

    private void notificarObservers(Cliente destinatario, String acao) {

        addObserver(new NotificacaoObserver(new EmailSenderImple(), destinatario.getEmailPessoa()));
        addObserver(new NotificacaoObserver(new SmsSenderImple(), destinatario.getNumeroTelefone()));

        notifyObservers(acao);
    }

    public Double buscarValorAReceber(Integer idEmpresa) {
        return repository.buscarValorAReceber(idEmpresa);
    }

}
