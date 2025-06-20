package sptech.school.order_hub.services;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import sptech.school.order_hub.config_exception.exceptions.SemConteudoException;
import sptech.school.order_hub.controller.empresa.response.BuscarImagensDTO;
import sptech.school.order_hub.entitiy.Empresa;
import sptech.school.order_hub.entitiy.Imagem;
import sptech.school.order_hub.repository.EmpresaRepository;
import sptech.school.order_hub.repository.ImagensRepository;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
public class ImagensServices {

    private final S3Client s3Client;
    private final String urlPadrao;
    private final EmpresaRepository empresaRepository;
    private final ImagensRepository imagensRepository;

    @Value("${aws.bucket.bucket}")
    private String bucket;

    public ImagensServices(
            S3Client s3Client,
            String urlPadrao,
            EmpresaRepository empresaRepository,
            ImagensRepository imagensRepository) {
        this.s3Client = s3Client;
        this.urlPadrao = urlPadrao;
        this.empresaRepository = empresaRepository;
        this.imagensRepository = imagensRepository;
    }

    public String uploadLogoEmpresa(MultipartFile file, Integer idEmpresa) throws IOException {
        Empresa empresa = buscarEmpresa(idEmpresa);

        Imagem imagem = uploadImagem(file, idEmpresa);

        empresa.setUrlLogo(imagem.getUrlImagem());

        empresaRepository.save(empresa);

        return imagem.getUrlImagem();
    }

    public Imagem uploadImagensEmpresa(MultipartFile file, Integer idEmpresa) throws IOException {
        Imagem imagem = uploadImagem(file, idEmpresa); // Método que faz o upload da imagem

        Empresa empresa = buscarEmpresa(idEmpresa); // Recupera a empresa associada
        empresa.addImagem(imagem); // Adiciona a imagem à empresa

        imagensRepository.save(imagem); // Salva a imagem no repositório

        return imagem; // Retorna o objeto completo da imagem
    }

    public List<BuscarImagensDTO> findImagensByEmpresaId(Integer idEmpresa) {

        List<Imagem> imagens = imagensRepository.buscarImagensDaEmpresa(idEmpresa);

        if (imagens.isEmpty()) {
            throw new SemConteudoException("Nenhuma imagem encontrada");
        }

        return imagens.stream()
                .map(BuscarImagensDTO::from)
                .toList();
    }

    private Imagem uploadImagem(MultipartFile file, Integer idEmpresa) throws IOException {
        Empresa empresa = empresaRepository.findById(idEmpresa)
                .orElseThrow(() -> new IllegalArgumentException("Empresa não encontrada"));

        UUID idImagem = UUID.randomUUID();
        String nomeArquivo = Objects.requireNonNull(file.getOriginalFilename()).replaceAll("\\s", "");
        String enderecoImagem = idImagem + "-" + nomeArquivo;

        s3Client.putObject(PutObjectRequest.builder()
                        .bucket(bucket)
                        .key(enderecoImagem)
                        .contentType(file.getContentType())
                        .build(),
                software.amazon.awssdk.core.sync.RequestBody.fromBytes(file.getBytes()));

        String region = "us-east-1";
        String urlImagem = String.format("https://%s.s3.%s.amazonaws.com/%s", bucket, region, enderecoImagem);

        return Imagem.builder()
                .urlImagem(urlImagem)
                .empresa(empresa)
                .build();
    }


    private Empresa buscarEmpresa(Integer idEmpresa) {
        return empresaRepository.findById(idEmpresa)
                .orElseThrow(() -> new IllegalArgumentException("Empresa não encontrada"));
    }

    public void deleteImagemById(Integer idImagem) {
        imagensRepository.deleteById(idImagem);
    }
}
