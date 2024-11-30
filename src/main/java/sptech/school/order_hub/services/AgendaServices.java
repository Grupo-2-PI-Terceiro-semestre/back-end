package sptech.school.order_hub.services;

import org.springframework.stereotype.Service;
import sptech.school.order_hub.controller.agenda.request.BuscarHorariosIndisponiveisRequest;
import sptech.school.order_hub.dtos.HorariosIndisponiveisDTO;
import sptech.school.order_hub.repository.AgendaRepository;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class AgendaServices {

    private final AgendaRepository agendaRepository;
    private final ServicoServices servicoServices;

    public AgendaServices(AgendaRepository agendaRepository, ServicoServices servicoServices) {
        this.agendaRepository = agendaRepository;
        this.servicoServices = servicoServices;
    }

    public List<Time> buscarHorariosIndisponiveis(
            Integer idEmpresa,
            BuscarHorariosIndisponiveisRequest request) {

        final var idAgenda = agendaRepository.findIdAgendaByUsuarioId(request.idProfissional());

        List<Object[]> resultados = agendaRepository.buscarHorariosIndisponiveis(idEmpresa, idAgenda, request.data());

        List<HorariosIndisponiveisDTO> horariosIndisponiveis = new ArrayList<>();

        for (Object[] row : resultados) {
            Time duracao = (Time) row[0];
            Time horaInicio = (Time) row[1];
            Time horaFinal = (Time) row[2];
            horariosIndisponiveis.add(new HorariosIndisponiveisDTO(duracao, horaInicio, horaFinal));
        }

        var servico = servicoServices.findById(request.idServico());
        LocalTime duracao = servico.getDuracao();
        int tempoServicoMinutos = (duracao.getHour() * 60 + duracao.getMinute());

        LocalTime horarioFinal = LocalTime.of(22, 0).minusMinutes(tempoServicoMinutos);

        List<Time> agendaDiaria = new ArrayList<>();
        LocalTime horarioInicial;

        if (request.data().equals(LocalDate.now())) {
            LocalTime agora = LocalTime.now();
            if (agora.isBefore(LocalTime.of(6, 0))) {
                horarioInicial = LocalTime.of(6, 0);
            } else {
                horarioInicial = agora.plusMinutes(15 - (agora.getMinute() % 15));
            }

            if (horarioInicial.isAfter(horarioFinal)) {
                return agendaDiaria;
            }
        } else {
            horarioInicial = LocalTime.of(6, 0);
        }

        for (LocalTime horario = horarioInicial;
             !horario.isAfter(horarioFinal);
             horario = horario.plusMinutes(15)) {
            agendaDiaria.add(Time.valueOf(horario));
        }

        for (HorariosIndisponiveisDTO horario : horariosIndisponiveis) {
            int indexInicial = agendaDiaria.indexOf(horario.horaInicio());
            int indexFinal = agendaDiaria.indexOf(horario.horaFinal());
            if (indexInicial != -1 && indexFinal != -1 && indexFinal >= indexInicial) {
                for (int i = indexInicial; i <= indexFinal; i++) {
                    agendaDiaria.set(i, null);
                }
            }
        }

        int intervalosNecessarios = tempoServicoMinutos / 15;

        List<Time> disponiveis = new ArrayList<>();

        // Verificar intervalos disponíveis para o serviço
        for (int i = 0; i <= agendaDiaria.size() - intervalosNecessarios; i++) {
            boolean disponivel = true;
            for (int j = 0; j < intervalosNecessarios; j++) {
                if (agendaDiaria.get(i + j) == null) {
                    disponivel = false;
                    break;
                }
            }
            if (disponivel) {
                disponiveis.add(agendaDiaria.get(i));
            }
        }

        return disponiveis;
    }



}
