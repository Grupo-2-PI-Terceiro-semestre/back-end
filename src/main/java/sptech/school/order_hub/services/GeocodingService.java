package sptech.school.order_hub.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import sptech.school.order_hub.entitiy.Endereco;
import sptech.school.order_hub.entitiy.GeocodingResponse;

import java.util.Optional;

@Service
public class GeocodingService {

    private final WebClient webClient;

    private static final String GOOGLE_API_KEY = "AIzaSyDz7PlwrKvJbxV8U7W5Pj4Af63BF-JSSe4"; // Sua chave de API do Google

    public GeocodingService(WebClient.Builder builder) {
        this.webClient = builder
                .baseUrl("https://maps.googleapis.com/maps/api/geocode")
                .defaultHeader(HttpHeaders.USER_AGENT, "SeuApp/1.0")
                .build();
    }

    public Optional<GeocodingResponse> getCoordinatesFromAddress(Endereco endereco) {
        try {
            String fullAddress = String.format("%s %s, %s, %s, %s, Brasil, %s",
                    endereco.getLogradouro(),
                    endereco.getNumero(),
                    endereco.getBairro(),
                    endereco.getCidade(),
                    endereco.getEstado(),
                    endereco.getCep().replaceAll("[^0-9]", ""));

            System.out.println("Endereço original: " + fullAddress);

            var url = UriComponentsBuilder.fromUriString("/json")
                    .queryParam("address", fullAddress)
                    .queryParam("key", GOOGLE_API_KEY)
                    .build()
                    .toUriString();

            return webClient.get()
                    .uri(url)
                    .retrieve()
                    .bodyToMono(String.class)
                    .doOnNext(response -> System.out.println("Resposta da API: " + response))
                    .map(json -> {
                        try {
                            var root = new ObjectMapper().readTree(json);

                            if (!"OK".equals(root.path("status").asText())) {
                                return null;
                            }

                            var location = root.path("results").get(0).path("geometry").path("location");
                            double lat = location.path("lat").asDouble();
                            double lng = location.path("lng").asDouble();

                            GeocodingResponse geo = new GeocodingResponse();
                            geo.setLat(lat);
                            geo.setLon(lng);

                            return geo;
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    })
                    .blockOptional();

        } catch (Exception e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }
}
