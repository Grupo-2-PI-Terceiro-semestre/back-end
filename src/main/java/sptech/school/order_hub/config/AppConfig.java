package sptech.school.order_hub.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import software.amazon.awssdk.services.s3.S3Client;
import sptech.school.order_hub.repository.EmpresaRepository;
import sptech.school.order_hub.repository.ImagensRepository;
import sptech.school.order_hub.services.ImagensServices;

@Configuration
public class AppConfig {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public ImagensServices imagensServices(
            S3Client s3Client,
            String urlPadrao,
            EmpresaRepository empresaRepository,
            ImagensRepository imagensRepository
    ) {
        return new ImagensServices(
                s3Client,
                urlPadrao,
                empresaRepository,
                imagensRepository
        );
    }
}

