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

    public AgendaServices(AgendaRepository agendaRepository) {
        this.agendaRepository = agendaRepository;
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

        List<Time> agendaDiaria = new ArrayList<>();

        if (request.data().equals(LocalDate.now())) {
            for (int i = 0; i < 64; i++) {
                agendaDiaria.add(Time.valueOf(LocalTime.now().plusMinutes(i * 15)));
            }
        } else {
            for (int i = 0; i < 64; i++) {
                agendaDiaria.add(Time.valueOf(LocalTime.of(6, 0).plusMinutes(i * 15)));
            }
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

        int tempoServicoMinutos = 45;
        int intervalosNecessarios = tempoServicoMinutos / 15;

        List<Time> disponiveis = new ArrayList<>();

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
