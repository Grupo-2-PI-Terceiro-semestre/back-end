package sptech.school.order_hub.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;
import sptech.school.order_hub.controller.cliente.request.AtualizarClienteRequestDTO;
import sptech.school.order_hub.dtos.ClienteDTO;
import sptech.school.order_hub.entitiy.Cliente;
import sptech.school.order_hub.enuns.StatusAtividade;
import sptech.school.order_hub.repository.ClienteRepository;
import sptech.school.order_hub.repository.EmpresaRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class ClienteServicesTest {

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private ClienteRepository clienteRepository;

    @Mock
    private EmpresaRepository empresaRepository;

    private ClienteServices clienteServices;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        clienteServices = new ClienteServices(passwordEncoder, restTemplate, clienteRepository, empresaRepository);
    }

    @Test
    @DisplayName("Testa o login do cliente com senha inválida, esperando erro 401 com mensagem 'Senha inválida.'")
    void testLoginCliente_Fail_InvalidPassword() {
        // Arrange
        Cliente cliente = new Cliente();
        cliente.setEmailPessoa("test@example.com");
        cliente.setSenha("encryptedPassword");

        when(clienteRepository.findByEmailPessoa("test@example.com")).thenReturn(Optional.of(cliente));
        when(passwordEncoder.matches("wrongPassword", "encryptedPassword")).thenReturn(false);

        // Act & Assert
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            clienteServices.loginCliente(cliente);
        });

        assertEquals(HttpStatus.UNAUTHORIZED, exception.getStatusCode());
        assertEquals("Senha inválida.", exception.getReason());
    }

    @Test
    @DisplayName("Testa a busca de cliente pelo ID, esperando erro 404 com mensagem 'Cliente não encontrado.' quando o cliente não existe")
    void testBuscarClientes_ClienteNotFound() {
        // Arrange
        when(clienteRepository.findByIdPessoa(1)).thenReturn(Optional.empty());

        // Act & Assert
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            clienteServices.findById(1);
        });

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
        assertEquals("Cliente não encontrado.", exception.getReason());
    }

    @Test
    @DisplayName("Testa a atualização de dados do cliente, verificando se as novas informações são retornadas corretamente")
    void testAtualizarCliente() {
        // Arrange
        Cliente cliente = new Cliente();
        cliente.setIdPessoa(1);
        cliente.setNomePessoa("Old Name");
        cliente.setEmailPessoa("old@example.com");

        AtualizarClienteRequestDTO requestDTO = new AtualizarClienteRequestDTO(1, "New Name", "123456789", "new@example.com");

        when(clienteRepository.findByIdPessoa(1)).thenReturn(Optional.of(cliente));
        when(clienteRepository.save(cliente)).thenReturn(cliente);

        // Act
        ClienteDTO clienteDTO = clienteServices.atualizarCliente(requestDTO);

        // Assert
        assertNotNull(clienteDTO);
        assertEquals("New Name", clienteDTO.nomePessoa());
        assertEquals("123456789", clienteDTO.numeroTelefone());
        assertEquals("new@example.com", clienteDTO.emailPessoa());
    }

    @Test
    @DisplayName("Testa a atualização do status de atividade do cliente, verificando se o status é alterado corretamente")
    void testUpdateStatusCliente() {
        // Arrange
        Cliente cliente = new Cliente();
        cliente.setIdPessoa(1);
        cliente.setStatusAtividade(StatusAtividade.ATIVO);

        when(clienteRepository.findById(1)).thenReturn(Optional.of(cliente));
        when(clienteRepository.save(cliente)).thenReturn(cliente);

        // Act
        ClienteDTO clienteDTO = clienteServices.updateStatusCliente(1);

        // Assert
        assertNotNull(clienteDTO);
    }

    // Additional tests can be written for other methods like buscarClientesPaginado, criarCliente, etc.
}
