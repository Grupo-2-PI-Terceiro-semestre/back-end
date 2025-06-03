package sptech.school.order_hub.services;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import sptech.school.order_hub.controller.empresa.response.BuscarImagensDTO;
import sptech.school.order_hub.entitiy.Empresa;
import sptech.school.order_hub.entitiy.Imagem;
import sptech.school.order_hub.repository.EmpresaRepository;
import sptech.school.order_hub.repository.ImagensRepository;
import software.amazon.awssdk.services.s3.S3Client;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

class ImagensServicesTest {

    @Mock
    private S3Client s3Client;

    @Mock
    private EmpresaRepository empresaRepository;

    @Mock
    private ImagensRepository imagensRepository;

    @InjectMocks
    private ImagensServices imagensServices;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Deve fazer o upload da logo da empresa com sucesso e retornar a URL da imagem")
    void testUploadLogoEmpresa() throws IOException {
        // Arrange
        MultipartFile file = mock(MultipartFile.class);
        when(file.getOriginalFilename()).thenReturn("logo.png");
        when(file.getBytes()).thenReturn(new byte[0]);
        Empresa empresa = new Empresa();
        empresa.setIdEmpresa(1);
        when(empresaRepository.findById(1)).thenReturn(Optional.of(empresa));

        String urlPadrao = "https://s3.amazonaws.com/";
        imagensServices = new ImagensServices(s3Client, urlPadrao, empresaRepository, imagensRepository);

        // Act
        String result = imagensServices.uploadLogoEmpresa(file, 1);

        // Assert
        assertNotNull(result);
        verify(s3Client, times(1)).putObject(any(PutObjectRequest.class), any(RequestBody.class));
        assertTrue(result.contains("logo.png"));
    }

    @Test
    @DisplayName("Deve fazer o upload de imagem da empresa com sucesso e retornar a imagem com URL")
    void testUploadImagensEmpresa() throws IOException {
        // Arrange
        MultipartFile file = mock(MultipartFile.class);
        when(file.getOriginalFilename()).thenReturn("imagem.png");
        when(file.getBytes()).thenReturn(new byte[0]);
        Empresa empresa = new Empresa();
        empresa.setIdEmpresa(1);
        when(empresaRepository.findById(1)).thenReturn(Optional.of(empresa));

        String urlPadrao = "https://s3.amazonaws.com/";
        imagensServices = new ImagensServices(s3Client, urlPadrao, empresaRepository, imagensRepository);

        // Act
        Imagem imagem = imagensServices.uploadImagensEmpresa(file, 1);

        // Assert
        assertNotNull(imagem);
        verify(s3Client, times(1)).putObject(any(PutObjectRequest.class), any(RequestBody.class));
        assertTrue(imagem.getUrlImagem().contains("imagem.png"));
    }

    @Test
    @DisplayName("Deve retornar uma lista de imagens associadas à empresa com sucesso")
    void testFindImagensByEmpresaId() {
        // Arrange
        Imagem imagem = new Imagem();
        imagem.setUrlImagem("https://s3.amazonaws.com/imagem.png");
        when(imagensRepository.buscarImagensDaEmpresa(1)).thenReturn(List.of(imagem));

        // Act
        List<BuscarImagensDTO> imagensDTOs = imagensServices.findImagensByEmpresaId(1);

        // Assert
        assertEquals(1, imagensDTOs.size());
        assertEquals("https://s3.amazonaws.com/imagem.png", imagensDTOs.get(0).urlImagem());
    }

    @Test
    @DisplayName("Deve excluir uma imagem da empresa com sucesso quando fornecido o ID da imagem")
    void testDeleteImagemById() {
        // Arrange
        Integer imagemId = 1;

        // Act
        imagensServices.deleteImagemById(imagemId);

        // Assert
        verify(imagensRepository, times(1)).deleteById(imagemId);
    }
}
