package sptech.school.order_hub.services;


import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import sptech.school.order_hub.config_exception.exceptions.ParametrosInvalidosException;
import sptech.school.order_hub.config_exception.exceptions.RecursoNaoEncontradoException;
import sptech.school.order_hub.config_exception.exceptions.SemConteudoException;
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

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AgendamentoServices extends Subject {


    private final AgendamentoRepository repository;
    private final AgendaRepository agendaRepository;
    private final ServicoServices servicoServices;
    private final ClienteServices clienteServices;
    private final NotificationService notificationService;

    private final CopyOnWriteArrayList<SseEmitter> emitters = new CopyOnWriteArrayList<>();


    public List<AgendamentoDTO> buscaAgendamento(BuscarAgendamentoRequestDTO request, Integer idAgenda, Boolean dadosCompletos) {
        if (request.dataAgendamento() == null) {
            throw new ParametrosInvalidosException("A data não pode ser nula");
        }

        LocalDateTime startOfDay = request.dataAgendamento().atStartOfDay();
        LocalDateTime endOfDay = request.dataAgendamento().atTime(LocalTime.MAX);

        List<Agendamento> agendamentos;
        if (dadosCompletos) {
            agendamentos = repository.BuscarAgendamentoCompleto(idAgenda, startOfDay, endOfDay);
        } else {
            agendamentos = repository.BuscarAgendamento(idAgenda, startOfDay, endOfDay);
        }

        return agendamentos.stream()
                .map(AgendamentoDTO::from)
                .toList();
    }


    public List<AgendamentosClienteResponseDTO> buscarAgendamentosCliente(Integer idCliente) {

        try {
            return repository.buscarAgendamentosDeUmCliente(idCliente).stream()
                    .map(result -> new AgendamentosClienteResponseDTO(
                            (Integer) result[0],
                            (String) result[1],
                            (String) result[2],
                            ((java.sql.Timestamp) result[3]).toLocalDateTime(),
                            (String) result[4],
                            (String) result[5]
                    )).collect(Collectors.toList());
        } catch (RuntimeException e) {
            throw new SemConteudoException("");
        }

    }

    public List<ProximosAgendamentosResponseDTO> buscarAgendamentos(Integer idEmpresa) {
        try {
            List<Object[]> resultados = repository.findNextAgendamentoByEmpresa(idEmpresa);

            System.out.println("Resultados findNextAgendamentoByEmpresa: " + resultados);

            return resultados.stream()
                    .map(result -> new ProximosAgendamentosResponseDTO(
                            result[0] != null ? result[0].toString() : "",
                            result[1] != null ? result[1].toString() : "",
                            result[2] != null ? result[2].toString() : "",
                            result[3] != null ? result[3].toString() : "",
                            result[4] != null ? result[4].toString() : ""
                    ))
                    .collect(Collectors.toList());

        } catch (RuntimeException e) {
            e.printStackTrace();
            throw new SemConteudoException("Erro ao buscar próximos agendamentos.");
        }
    }



    public Agendamento findById(Integer idAgendamento) {
        Optional<Agendamento> agendamento = repository.findById(idAgendamento);
        if (agendamento.isPresent()) {
            return agendamento.get();
        }
        throw new SemConteudoException("Agenda não encontrada");
    }


    public AgendamentoDTO atualizarAgendamentoParcial(AtualizarAgendamentoParcialRequestDTO requestDTO) {
        Agendamento agendamento = repository.findByIdAgendamento(requestDTO.idAgendamento())
                .orElseThrow(() -> new SemConteudoException("Agenda não encontrada"));

        Agenda agenda = agendaRepository.findById(requestDTO.idAgenda())
                .orElseThrow(() -> new SemConteudoException("Agenda não encontrada"));

        Optional.ofNullable(requestDTO.horaAgendamento())
                .ifPresent(agendamento::setDataHora);

        agendamento.setAgenda(agenda);

        Agendamento agendamentoAtualizado = repository.save(agendamento);

        //tigerEvent(agendamentoAtualizado);

        return AgendamentoDTO.from(agendamentoAtualizado);
    }


    public AgendamentoDTO clienteCriarAgendamento(ClienteCriarAgendamentoRequestDTO requestDTO) {

        var idAgenda = agendaRepository.findIdAgendaByUsuarioId(requestDTO.idProfissional());

        final var agendamento = new Agendamento();
        final var agenda = agendaRepository.findById(idAgenda)
                .orElseThrow(() -> new SemConteudoException("Agenda não encontrada"));

        final var servico = servicoServices.findById(requestDTO.idServico());
        final var cliente = clienteServices.findById(requestDTO.idCliente());

        agendamento.setServico(servico);
        agendamento.setCliente(cliente);
        agendamento.setAgenda(agenda);
        agendamento.setDataHora(requestDTO.dataAgendamento());
        agendamento.setStatusAgendamento(requestDTO.statusAgendamento());

        final var agendamentoCriado = repository.save(agendamento);

        final var empresa = agendaRepository.findIdEnterpriseByAgenda(requestDTO.idProfissional());

        notificationService.sendNotificationToEmpresa(empresa, gerarMensagemNotificacao(agendamentoCriado));

        return AgendamentoDTO.from(agendamentoCriado);
    }

    private String gerarMensagemNotificacao(Agendamento agendamento) {
        String status = agendamento.getStatusAgendamento();
        String nomeCliente = agendamento.getCliente().getNomePessoa();
        String nomeServico = agendamento.getServico().getNomeServico();
        String nomeProfissional = agendamento.getAgenda().getUsuario().getNomePessoa();
        String dataHoraFormatada = agendamento.getDataHora()
                .format(DateTimeFormatter.ofPattern("dd/MM/yyyy 'às' HH:mm"));

        switch (status) {
            case "PENDENTE":
                return String.format(
                        "<b style=\\\"font-size:12px;\\\">Novo agendamento pendente</b><br>%s agendou um serviço de %s com o profissional %s para o dia %s.<br><b>Clique para verificar.</b>",
                        nomeCliente, nomeServico, nomeProfissional, dataHoraFormatada
                );
            case "CANCELADO":
                return String.format(
                        "<b style=\\\"font-size:12px;\\\">Agendamento cancelado</b><br>%s cancelou o serviço de %s com o profissional %s do dia %s.<br><b>Clique para verificar.</b>",
                        nomeCliente, nomeServico, nomeProfissional, dataHoraFormatada
                );
            default:
                return "Você tem uma atualização na agenda. Atualize para mais detalhes.";
        }
    }


    public AgendamentoDTO atualizarAgendamento(AtualizarAgendamentoRequestDTO requestDTO) {

        final var agendamento = repository.findByIdAgendamento(requestDTO.idAgendamento())
                .orElseThrow(() -> new SemConteudoException("Agendamento não encontrado"));

        final var agenda = agendaRepository.findById(requestDTO.idAgenda())
                .orElseThrow(() -> new SemConteudoException("Agenda não encontrada"));

        final var servico = servicoServices.findById(requestDTO.idServico());
        final var cliente = clienteServices.findById(requestDTO.idCliente());

        Optional.ofNullable(requestDTO.dataAgendamento())
                .ifPresent(agendamento::setDataHora);


        agendamento.setCliente(cliente);
        agendamento.setServico(servico);
        agendamento.setAgenda(agenda);

        final var agendamentoAtualizado = repository.save(agendamento);

        //tigerEvent(agendamentoAtualizado);

        return AgendamentoDTO.from(agendamentoAtualizado);
    }

    public AgendamentoDTO updateStatusAgendamento(final Integer idAgendamento, final StatusAgendamento status) {

        Agendamento agendamento = repository.findByIdAgendamento(idAgendamento)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Agendamento não encontrado"));

        agendamento.setStatusAgendamento(status);

        Agendamento agendamentoCancelado = repository.save(agendamento);

        final var empresa = agendaRepository.findIdEnterpriseByAgenda(agendamento.getAgenda().getUsuario().getEmpresa().getIdEmpresa());

        if (agendamentoCancelado.getStatusAgendamento().equals("CANCELADO")) {
            notificationService.sendNotificationToEmpresa(empresa, gerarMensagemNotificacao(agendamentoCancelado));
        }

        return AgendamentoDTO.from(agendamentoCancelado);
    }

    public ReceitaEComparativoResponseDTO buscarReceitaMensal(Integer idEmpresa, Integer mes) {
        List<Object[]> result = repository.ReceitaMensal(idEmpresa, mes);

        if (result.isEmpty()) {
            throw new SemConteudoException("");
        }

        Object[] row = result.get(0);
        Double totalReceita = (Double) row[0];
        Double comparativoReceita = (Double) row[1];

        return new ReceitaEComparativoResponseDTO(totalReceita, comparativoReceita);
    }

    public ServicoMensalResponseDTO buscarServicoMensal(Integer idEmpresa, Integer mes) {


        try {
            List<Object[]> result = repository.ServicoMensal(idEmpresa, mes);

            Integer totalServicos = 0;
            Double comparativoServicos = 0.0;

            if (!result.isEmpty()) {
                Object[] row = result.get(0);
                totalServicos = row[0] != null ? ((Number) row[0]).intValue() : 0;
                comparativoServicos = row[1] != null ? ((Number) row[1]).doubleValue() : 0.0;
            }

            return new ServicoMensalResponseDTO(totalServicos, comparativoServicos);
        } catch (RuntimeException e) {
            throw new SemConteudoException("");
        }


    }

    public TicketMedioResponseDTO buscarTicketMedio(Integer idEmpresa) {
        Double ticketMedio = repository.TicketMedio(idEmpresa);

        return TicketMedioResponseDTO.from(ticketMedio);
    }

    public AgendamentoDTO criarAgendamento(CriarAgendamentoRequestDTO requestDTO) {
        Agendamento agendamento = new Agendamento();
        Agenda agenda = agendaRepository.findById(requestDTO.idAgenda())
                .orElseThrow(() -> new RecursoNaoEncontradoException("Agenda não encontrada"));

        Servico servico = servicoServices.findById(requestDTO.idServico());
        Cliente cliente = clienteServices.findById(requestDTO.idCliente());

        agendamento.setServico(servico);
        agendamento.setCliente(cliente);
        agendamento.setAgenda(agenda);
        agendamento.setDataHora(requestDTO.dataAgendamento());
        agendamento.setStatusAgendamento(requestDTO.statusAgendamento());

        notificarObservers(cliente, "agendamento");

        Agendamento agendamentoCriado = repository.save(agendamento);

        //tigerEvent(agendamentoCriado);

        return AgendamentoDTO.from(agendamentoCriado);
    }

    public void tigerEvent(Agendamento agendamento) {
        boolean refrash = agendamento != null &&
                agendamento.getDataHora().toLocalDate().isEqual(LocalDateTime.now().toLocalDate());

        for (SseEmitter emitter : getEmitters()) {
            try {
                emitter.send(SseEmitter.event()
                        .name("refrash")
                        .data(refrash));
            } catch (IOException e) {
                emitter.complete();
                getEmitters().remove(emitter);
            }
        }
    }


    public void addEmitter(SseEmitter emitter) {
        emitters.add(emitter);

        emitter.onTimeout(() -> {
            System.out.println("Timeout do emissor");
            emitters.remove(emitter);
        });

        emitter.onCompletion(() -> {
            System.out.println("Emissor completado");
            emitters.remove(emitter);
        });
    }

    public List<SseEmitter> getEmitters() {
        return emitters;
    }

    private void notificarObservers(Cliente destinatario, String acao) {

        addObserver(new NotificacaoObserver(new EmailSenderImple(), destinatario.getEmailPessoa()));
        addObserver(new NotificacaoObserver(new SmsSenderImple(), destinatario.getNumeroTelefone()));

        notifyObservers(acao);
    }

    public Double buscarValorAReceber(Integer idEmpresa) {
        return repository.buscarValorAReceber(idEmpresa);
    }

    public List<ReceitaPorFuncionarioResponseDTO> buscarReceitaPorFuncionario(Integer idEmpresa) {

        try {
            return repository.ReceitaPorFuncionario(idEmpresa).stream()
                    .map(result -> new ReceitaPorFuncionarioResponseDTO(
                            (String) result[0],
                            (Double) result[1]
                    )).collect(Collectors.toList());
        } catch (RuntimeException e) {
            throw new SemConteudoException("");
        }
    }

    public ClientesMensaisResponseDTO buscarClientesMensais(Integer idEmpresa) {

        try {
            Object[] row = repository.ClientesMensal(idEmpresa).get(0);
            Integer totalClientes = ((Number) row[0]).intValue();
            Double comparativoClientes = ((Number) row[1]).doubleValue();

            return new ClientesMensaisResponseDTO(totalClientes, comparativoClientes);
        } catch (RuntimeException e) {
            throw new SemConteudoException("");
        }

    }

    public List<ReceitaPorMesDTO> buscarReceitaPorMes(Integer idEmpresa) {

        try {
            return repository.ReceitaPorMes(idEmpresa).stream()
                    .map(result -> {
                        String anoMes = result[0] instanceof String
                                ? (String) result[0]
                                : result[0].toString();
                        Double receita = (Double) result[1];
                        return new ReceitaPorMesDTO(anoMes, receita);
                    }).collect(Collectors.toList());
        } catch (RuntimeException e) {
            throw new SemConteudoException("");
        }


    }

    public List<ServicoDiaSemanaResponseDTO> buscarServicoDiaSemana(Integer idEmpresa) {
        try {
            List<Object[]> resultados = repository.ServicoPorDiaDaSemana(idEmpresa);
            return resultados.stream()
                    .map(result -> new ServicoDiaSemanaResponseDTO(
                            (Integer) result[0],
                            ((Number) result[1]).intValue()
                    )).collect(Collectors.toList());
        } catch (RuntimeException e) {
            e.printStackTrace();
            throw new SemConteudoException("Erro ao buscar serviços por dia da semana.");
        }
    }


    public List<ReceitaPorServicoResponseDTO> buscarReceitaPorServico(Integer idEmpresa) {


        try {
            return repository.ReceitaPorServico(idEmpresa).stream()
                    .map(result -> new ReceitaPorServicoResponseDTO(
                            (String) result[0],
                            (Double) result[1]
                    )).collect(Collectors.toList());
        } catch (RuntimeException e) {
            throw new SemConteudoException("");
        }
    }


    public AgendamentoDTO cancelaAgendamento(Integer idAgendamento) {
        Agendamento agendamento = repository.findByIdAgendamento(idAgendamento)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Agendamento não encontrado"));

        agendamento.setStatusAgendamento(StatusAgendamento.CANCELADO);

        Agendamento agendamentoCancelado = repository.save(agendamento);

        //tigerEvent(agendamentoCancelado);

        return AgendamentoDTO.from(agendamentoCancelado);
    }
}
