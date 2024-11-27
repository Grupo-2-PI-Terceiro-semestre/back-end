//package sptech.school.order_hub.services;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.springframework.web.server.ResponseStatusException;
//import sptech.school.order_hub.controller.agendamento.request.AtualizarAgendamentoParcialRequestDTO;
//import sptech.school.order_hub.controller.agendamento.request.BuscarAgendamentoRequestDTO;
//import sptech.school.order_hub.dtos.AgendamentoDTO;
//import sptech.school.order_hub.entitiy.Agenda;
//import sptech.school.order_hub.entitiy.Agendamento;
//import sptech.school.order_hub.repository.AgendaRepository;
//import sptech.school.order_hub.repository.AgendamentoRepository;
//
//import java.time.LocalDate;
//import java.time.LocalDateTime;
//import java.util.List;
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//
//class AgendamentoServicesTest {
//
//    @InjectMocks
//    private AgendamentoServices agendamentoServices;
//
//    @Mock
//    private AgendamentoRepository agendamentoRepository;
//
//    @Mock
//    private AgendaRepository agendaRepository;
//
//    @Mock
//    private ServicoServices servicoServices;
//
//    @Mock
//    private ClienteServices clienteServices;
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//    }
//
//    @Test
//    void buscaAgendamento_retornaListaDeAgendamentos() {
//        // Mockando os dados
//        BuscarAgendamentoRequestDTO requestDTO = new BuscarAgendamentoRequestDTO(LocalDate.now());
//        Integer idAgenda = 1;
//        Boolean dadosCompletos = false;
//
//        Agendamento agendamentoMock = new Agendamento();
//        agendamentoMock.setIdAgendamento(1);
//        agendamentoMock.setDataHora(LocalDateTime.now());
//
//        when(agendamentoRepository.BuscarAgendamento(
//                eq(idAgenda),
//                any(LocalDateTime.class),
//                any(LocalDateTime.class)
//        )).thenReturn(List.of(agendamentoMock));
//
//        // Teste
//        List<AgendamentoDTO> resultado = agendamentoServices.buscaAgendamento(requestDTO, idAgenda, dadosCompletos);
//
//        // Validações
//        assertNotNull(resultado);
//        assertEquals(1, resultado.size());
//        assertEquals(agendamentoMock.getIdAgendamento(), resultado.get(0).idAgendamento());
//        verify(agendamentoRepository, times(1)).BuscarAgendamento(anyInt(), any(), any());
//    }
//
//    @Test
//    void findById_agendamentoExistente_retornaAgendamento() {
//        // Mockando os dados
//        Integer idAgendamento = 1;
//        Agendamento agendamentoMock = new Agendamento();
//        agendamentoMock.setIdAgendamento(idAgendamento);
//
//        when(agendamentoRepository.findById(idAgendamento)).thenReturn(Optional.of(agendamentoMock));
//
//        // Teste
//        Agendamento resultado = agendamentoServices.findById(idAgendamento);
//
//        // Validações
//        assertNotNull(resultado);
//        assertEquals(idAgendamento, resultado.getIdAgendamento());
//        verify(agendamentoRepository, times(1)).findById(idAgendamento);
//    }
//
//    @Test
//    void findById_agendamentoNaoExistente_lancaExcecao() {
//        // Mockando os dados
//        Integer idAgendamento = 1;
//
//        when(agendamentoRepository.findById(idAgendamento)).thenReturn(Optional.empty());
//
//        // Teste e validação
//        assertThrows(ResponseStatusException.class, () -> agendamentoServices.findById(idAgendamento));
//        verify(agendamentoRepository, times(1)).findById(idAgendamento);
//    }
//
//    @Test
//    void atualizarAgendamentoParcial_retornaAgendamentoAtualizado() {
//        // Mockando os dados
//        Integer idAgendamento = 1;
//        Integer idAgenda = 2;
//        LocalDateTime novaDataHora = LocalDateTime.now();
//
//        AtualizarAgendamentoParcialRequestDTO requestDTO = new AtualizarAgendamentoParcialRequestDTO(idAgendamento, novaDataHora, idAgenda);
//
//        Agendamento agendamentoMock = new Agendamento();
//        agendamentoMock.setIdAgendamento(idAgendamento);
//
//        Agenda agendaMock = new Agenda();
//        agendaMock.setIdAgenda(idAgenda);
//
//        when(agendamentoRepository.findByIdAgendamento(idAgendamento)).thenReturn(Optional.of(agendamentoMock));
//        when(agendaRepository.findById(idAgenda)).thenReturn(Optional.of(agendaMock));
//        when(agendamentoRepository.save(any())).thenReturn(agendamentoMock);
//
//        // Teste
//        AgendamentoDTO resultado = agendamentoServices.atualizarAgendamentoParcial(requestDTO);
//
//        // Validações
//        assertNotNull(resultado);
//        assertEquals(idAgendamento, resultado.idAgendamento());
//        verify(agendamentoRepository, times(1)).findByIdAgendamento(idAgendamento);
//        verify(agendaRepository, times(1)).findById(idAgenda);
//        verify(agendamentoRepository, times(1)).save(any());
//    }
//}
