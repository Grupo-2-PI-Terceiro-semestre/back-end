package sptech.school.order_hub.services;

import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.server.ResponseStatusException;
import sptech.school.order_hub.config.security.config.TokenServices;
import sptech.school.order_hub.controller.agendamento.request.BuscarAgendamentoRequestDTO;
import sptech.school.order_hub.controller.usuario.request.CadastroUsuarioRequestDTO;
import sptech.school.order_hub.controller.usuario.response.AuthResponseDTO;
import sptech.school.order_hub.dtos.UsuarioDTO;
import sptech.school.order_hub.entitiy.Endereco;
import sptech.school.order_hub.entitiy.Usuario;
import sptech.school.order_hub.repository.UsuarioRepository;
import sptech.school.order_hub.services.UsuarioServices;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class UsuarioServicesTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private TokenServices tokenServices;

    @InjectMocks
    private UsuarioServices usuarioServices;

    private Usuario usuario;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Initialize mock user
        usuario = new Usuario();
        usuario.setIdPessoa(1);
        usuario.setNomePessoa("Test User");
        usuario.setEmailPessoa("testuser@example.com");
        usuario.setSenha("password123");
    }

    @Test
    @DisplayName("Deve autenticar o usuário com sucesso quando as credenciais forem válidas")
    void testAutenticar_withValidCredentials() throws Exception {
        // Arrange
        String email = "testuser@example.com";
        String password = "password123";
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(email, password);
        Authentication authentication = mock(Authentication.class);
        when(authenticationManager.authenticate(authToken)).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(usuario);
        String token = "mockedToken";
        when(tokenServices.generateToken(usuario)).thenReturn(token);

        // Act
        ResponseEntity<AuthResponseDTO> response = usuarioServices.autenticar(usuario);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }



    @Test
    @DisplayName("Deve atualizar o usuário com sucesso quando o usuário existir")
    void testUpdate_whenUserExists() {
        // Arrange
        int userId = 1;
        Usuario updatedUser = new Usuario();
        updatedUser.setNomePessoa("Updated Name");

        when(usuarioRepository.findById(userId)).thenReturn(java.util.Optional.of(usuario));
        when(usuarioRepository.save(usuario)).thenReturn(updatedUser);

        // Act
        Usuario result = usuarioServices.update(userId, updatedUser);

        // Assert
        assertEquals("Updated Name", result.getNomePessoa());
    }

    @Test
    @DisplayName("Deve lançar exceção ao tentar atualizar usuário quando o usuário não for encontrado")
    void testUpdate_whenUserNotFound() {
        // Arrange
        int userId = 1;
        Usuario updatedUser = new Usuario();
        updatedUser.setNomePessoa("Updated Name");

        when(usuarioRepository.findById(userId)).thenReturn(java.util.Optional.empty());

        // Act & Assert
        assertThrows(ResponseStatusException.class, () -> usuarioServices.update(userId, updatedUser));
    }

    @Test
    @DisplayName("Deve lançar exceção ao tentar excluir usuário quando o usuário não for encontrado")
    void testDeleteById_whenUserNotFound() {
        // Arrange
        int userId = 1;
        when(usuarioRepository.findById(userId)).thenReturn(java.util.Optional.empty());

        // Act & Assert
        assertThrows(ResponseStatusException.class, () -> usuarioServices.deleteById(userId));
    }

    @Test
    @DisplayName("Deve lançar exceção ao tentar adicionar um endereço quando o usuário não for encontrado")
    void testCreateEndereco_whenUserNotFound() {
        // Arrange
        Endereco endereco = new Endereco();
        int userId = 1;
        when(usuarioRepository.findById(userId)).thenReturn(java.util.Optional.empty());

        // Act & Assert
        assertThrows(ResponseStatusException.class, () -> usuarioServices.createEndereco(endereco, userId));
    }
}
