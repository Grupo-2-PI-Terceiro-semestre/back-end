package sptech.school.order_hub.services;

import org.springframework.stereotype.Service;
import sptech.school.order_hub.controller.agenda.request.BuscarHorariosIndisponiveisRequest;
import sptech.school.order_hub.dtos.HorariosIndisponiveisDTO;
import sptech.school.order_hub.repository.AgendaRepository;

import java.sql.Time;
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

        // Criando a agenda diária com intervalos de 15 minutos a partir das 06:00
        List<Time> agendaDiaria = new ArrayList<>();
        for (int i = 0; i < 64; i++) {
            agendaDiaria.add(Time.valueOf(LocalTime.of(6, 0).plusMinutes(i * 15)));
        }

        // Remover os horários indisponíveis da agenda
        for (HorariosIndisponiveisDTO horario : horariosIndisponiveis) {
            int indexInicial = agendaDiaria.indexOf(horario.horaInicio());
            int indexFinal = agendaDiaria.indexOf(horario.horaFinal());
            if (indexInicial != -1 && indexFinal != -1 && indexFinal >= indexInicial) {
                // Remover intervalos entre o horário inicial e final (inclusive)
                for (int i = indexInicial; i <= indexFinal; i++) {
                    agendaDiaria.set(i, null);  // Marca como indisponível
                }
            }
        }

        int tempoServicoMinutos = 45; // Duração do serviço em minutos (por exemplo, 45 minutos)
        int intervalosNecessarios = tempoServicoMinutos / 15; // Quantos intervalos de 15 minutos são necessários

        List<Time> disponiveis = new ArrayList<>();

        // Verifica a disponibilidade dos horários consecutivos
        for (int i = 0; i <= agendaDiaria.size() - intervalosNecessarios; i++) {
            boolean disponivel = true;
            // Verifica se todos os intervalos necessários estão disponíveis
            for (int j = 0; j < intervalosNecessarios; j++) {
                if (agendaDiaria.get(i + j) == null) {  // Se encontrar um horário indisponível, marca como falso
                    disponivel = false;
                    break;
                }
            }
            // Se todos os intervalos estiverem disponíveis, adicione o primeiro horário da sequência
            if (disponivel) {
                disponiveis.add(agendaDiaria.get(i));
            }
        }

        return disponiveis;
    }
}
