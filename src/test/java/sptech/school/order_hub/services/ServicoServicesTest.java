package sptech.school.order_hub.services;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import sptech.school.order_hub.dtos.ServicoAddDTO;
import sptech.school.order_hub.entitiy.Servico;
import sptech.school.order_hub.repository.ServicoRepository;
import sptech.school.order_hub.repository.EmpresaRepository;
import sptech.school.order_hub.entitiy.Empresa;
import sptech.school.order_hub.enuns.StatusAtividade;

import java.util.Optional;

class ServicoServicesTest {

    @Mock
    private EmpresaRepository empresaRepository;

    @Mock
    private ServicoRepository servicoRepository;

    @InjectMocks
    private ServicoServices servicoServices;

    private Empresa empresa;
    private Servico servico;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Setup mock data
        empresa = new Empresa();
        empresa.setIdEmpresa(1);

        servico = new Servico();
        servico.setIdServico(1);
        servico.setNomeServico("Corte de cabelo");
        servico.setValorServico(50.0);
        servico.setDescricao("Corte de cabelo básico");
        servico.setStatusAtividade(StatusAtividade.ATIVO);
    }

    @Test
    @DisplayName("Deve criar um serviço com sucesso quando o serviço for válido e a empresa existir")
    void testCreateServico() {
        // Arrange
        when(empresaRepository.findById(1)).thenReturn(Optional.of(empresa));
        when(servicoRepository.save(any(Servico.class))).thenReturn(servico);

        // Act
        ServicoAddDTO servicoAddDTO = servicoServices.createServico(servico, 1);

        // Assert
        assertNotNull(servicoAddDTO);
        assertEquals("Corte de cabelo", servicoAddDTO.nomeServico());
        verify(empresaRepository).save(empresa);
        verify(servicoRepository).save(servico);
    }

    @Test
    @DisplayName("Deve retornar o serviço com sucesso quando encontrado pelo ID")
    void testFindByIdServico_Success() {
        // Arrange
        when(servicoRepository.findById(1)).thenReturn(Optional.of(servico));

        // Act
        Servico foundServico = servicoServices.findById(1);

        // Assert
        assertNotNull(foundServico);
        assertEquals("Corte de cabelo", foundServico.getNomeServico());
    }

    @Test
    @DisplayName("Deve lançar exceção com status 404 quando o serviço não for encontrado pelo ID")
    void testFindByIdServico_NotFound() {
        // Arrange
        when(servicoRepository.findById(1)).thenReturn(Optional.empty());

        // Act & Assert
        ResponseStatusException thrown = assertThrows(ResponseStatusException.class, () -> {
            servicoServices.findById(1);
        });

        assertEquals(HttpStatus.NOT_FOUND, thrown.getStatusCode());
        assertEquals("Serviço não encontrado.", thrown.getReason());
    }

    @Test
    @DisplayName("Deve lançar exceção com status 404 ao tentar excluir um serviço que não existe")
    void testDeleteById_NotFound() {
        // Arrange
        when(servicoRepository.existsById(1)).thenReturn(false);

        // Act & Assert
        ResponseStatusException thrown = assertThrows(ResponseStatusException.class, () -> {
            servicoServices.deleteById(1);
        });

        assertEquals(HttpStatus.NOT_FOUND, thrown.getStatusCode());
        assertEquals("Serviço não encontrado.", thrown.getReason());
    }
}
