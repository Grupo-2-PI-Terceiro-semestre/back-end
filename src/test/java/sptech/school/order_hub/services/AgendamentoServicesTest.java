package sptech.school.order_hub.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.server.ResponseStatusException;
import sptech.school.order_hub.controller.agendamento.response.ClienteCriarAgendamentoRequestDTO;
import sptech.school.order_hub.entitiy.Agendamento;
import sptech.school.order_hub.enuns.StatusAgendamento;
import sptech.school.order_hub.repository.AgendaRepository;
import sptech.school.order_hub.repository.AgendamentoRepository;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AgendamentoServicesTest {

    @InjectMocks
    private AgendamentoServices agendamentoServices;

    @Mock
    private AgendamentoRepository repository;

    @Mock
    private AgendaRepository agendaRepository;

    @Mock
    private ServicoServices servicoServices;

    @Mock
    private ClienteServices clienteServices;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    @DisplayName("Testa a busca de agendamento por ID quando o agendamento é encontrado")
    void testFindByIdFound() {
        // Arrange
        Integer idAgendamento = 1;
        Agendamento mockAgendamento = new Agendamento();
        when(repository.findById(idAgendamento)).thenReturn(Optional.of(mockAgendamento));

        // Act
        Agendamento result = agendamentoServices.findById(idAgendamento);

        // Assert
        assertNotNull(result);
        assertEquals(mockAgendamento, result);
        verify(repository, times(1)).findById(idAgendamento);
    }

    @Test
    @DisplayName("Testa a busca de agendamento por ID quando o agendamento não é encontrado, esperando exceção ResponseStatusException")
    void testFindByIdNotFound() {
        // Arrange
        Integer idAgendamento = 1;
        when(repository.findById(idAgendamento)).thenReturn(Optional.empty());

        // Act & Assert
        ResponseStatusException exception = assertThrows(ResponseStatusException.class,
                () -> agendamentoServices.findById(idAgendamento));


        verify(repository, times(1)).findById(idAgendamento);
    }

    @Test
    @DisplayName("Testa a criação de agendamento para o cliente quando a agenda não é encontrada, esperando exceção ResponseStatusException")
    void testClienteCriarAgendamento_AgendaNaoEncontrada() {
        // Arrange
        ClienteCriarAgendamentoRequestDTO requestDTO = new ClienteCriarAgendamentoRequestDTO(1, 1, 1, LocalDateTime.now(), StatusAgendamento.AGENDADO);

        // Mock para retornar id de agenda válido
        when(agendaRepository.findIdAgendaByUsuarioId(requestDTO.idProfissional())).thenReturn(1);
        when(agendaRepository.findById(1)).thenReturn(Optional.empty()); // Agenda não encontrada

        // Act & Assert
        assertThrows(ResponseStatusException.class, () -> {
            agendamentoServices.clienteCriarAgendamento(requestDTO);
        });
    }

    @Test
    @DisplayName("Testa a busca de agendamento por ID, retornando o agendamento existente")
    void findById_agendamentoExistente_retornaAgendamento() {
        // Mockando os dados
        Integer idAgendamento = 1;
        Agendamento agendamentoMock = new Agendamento();
        agendamentoMock.setIdAgendamento(idAgendamento);

        when(repository.findById(idAgendamento)).thenReturn(Optional.of(agendamentoMock));

        // Teste
        Agendamento resultado = agendamentoServices.findById(idAgendamento);

        // Validações
        assertNotNull(resultado);
        assertEquals(idAgendamento, resultado.getIdAgendamento());
        verify(repository, times(1)).findById(idAgendamento);
    }

    @Test
    @DisplayName("Testa a busca de agendamento por ID, esperando exceção quando o agendamento não existe")
    void findById_agendamentoNaoExistente_lancaExcecao() {
        // Mockando os dados
        Integer idAgendamento = 1;

        when(repository.findById(idAgendamento)).thenReturn(Optional.empty());

        // Teste e validação
        assertThrows(ResponseStatusException.class, () -> agendamentoServices.findById(idAgendamento));
        verify(repository, times(1)).findById(idAgendamento);
    }

}
