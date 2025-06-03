package sptech.school.order_hub.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import sptech.school.order_hub.controller.categoria.response.CategoriasResponseDTO;
import sptech.school.order_hub.entitiy.Categoria;
import sptech.school.order_hub.repository.CategoriaRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CategoriaServicesTest {

    @Mock
    private CategoriaRepository repository;

    @InjectMocks
    private CategoriaServices categoriaServices;

    private Categoria categoria;

    @BeforeEach
    void setUp() {
        categoria = new Categoria();
        categoria.setIdCategoria(1);
        categoria.setNome("Categoria Teste");
    }

    // Testando o método findAll()
    @Test
    @DisplayName("Testa a busca de todas as categorias quando existem categorias cadastradas")
    void testFindAll_CategoriasExistem() {
        // Arrange
        when(repository.findAll()).thenReturn(List.of(categoria));  // Mock do retorno do findAll()

        // Act
        List<CategoriasResponseDTO> result = categoriaServices.findAll();

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Categoria Teste", result.get(0).nomeCategoria());
        verify(repository, times(1)).findAll();  // Verifica se o método findAll foi chamado
    }

    // Testando o método findByNome()
    @Test
    @DisplayName("Testa a busca de categoria pelo nome, esperando encontrar a categoria correspondente")
    void testFindByNome_CategoriaEncontrada() {
        // Arrange
        when(repository.findByNome("Categoria Teste")).thenReturn(Optional.of(categoria));  // Mock do retorno do findByNome()

        // Act
        Categoria result = categoriaServices.findByNome("Categoria Teste");

        // Assert
        assertNotNull(result);
        assertEquals("Categoria Teste", result.getNome());
        verify(repository, times(1)).findByNome("Categoria Teste");  // Verifica se o método findByNome foi chamado
    }

    // Testando o método save()
    @Test
    @DisplayName("Testa o salvamento de uma categoria, verificando se a categoria é salva corretamente")
    void testSave_CategoriaSalva() {
        // Arrange
        when(repository.save(categoria)).thenReturn(categoria);  // Mock do save

        // Act
        Categoria result = categoriaServices.save(categoria);

        // Assert
        assertNotNull(result);
        assertEquals("Categoria Teste", result.getNome());
        verify(repository, times(1)).save(categoria);  // Verifica se o método save foi chamado
    }
}
