package sptech.school.order_hub.services;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import sptech.school.order_hub.entitiy.Empresa;
import sptech.school.order_hub.entitiy.Imagens;
import sptech.school.order_hub.repository.EmpresaRepository;
import sptech.school.order_hub.repository.ImagensRepository;

import java.io.IOException;
import java.util.Objects;
import java.util.UUID;

@Service
public class ImagensServices {

    private final S3Client s3Client;
    private final String urlPadrao;
    private final EmpresaRepository empresaRepository;
    private final ImagensRepository imagensRepository;

    @Value("${aws.bucket}")
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

    public String uploadImagem(MultipartFile file, Integer idEmpresa) throws IOException {
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

        String urlImagem = urlPadrao + "/" + enderecoImagem;

        Imagens imagem = Imagens.builder()
                .urlImagem(urlImagem)
                .empresa(empresa)
                .build();
        imagensRepository.save(imagem);

        empresa.addImagem(imagem);

        empresaRepository.save(empresa);

        return urlImagem;
    }
}
