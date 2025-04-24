package sptech.school.order_hub.services;

import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import sptech.school.order_hub.entitiy.Endereco;
import sptech.school.order_hub.entitiy.GeocodingResponse;

import java.util.Optional;

@Service
public class GeocodingService {

    private final WebClient webClient;

    public GeocodingService(WebClient.Builder builder) {
        this.webClient = builder
                .baseUrl("https://nominatim.openstreetmap.org")
                .defaultHeader(HttpHeaders.USER_AGENT, "OrderHub/1.0") // Importante para Nominatim
                .build();
    }

    public Optional<GeocodingResponse> getCoordinatesFromAddress(Endereco endereco) {
        String fullAddress = String.format("%s, %s, %s, %s, %s, %s",
                endereco.getLogradouro(),
                endereco.getNumero(),
                endereco.getBairro(),
                endereco.getCidade(),
                endereco.getEstado(),
                endereco.getCep()
        );

        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/search")
                        .queryParam("q", fullAddress)
                        .queryParam("format", "json")
                        .queryParam("limit", "1")
                        .build())
                .retrieve()
                .bodyToFlux(GeocodingResponse.class)
                .next() // Pega o primeiro resultado
                .blockOptional();
    }
}
