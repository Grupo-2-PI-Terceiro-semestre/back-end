package sptech.school.order_hub.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class GeoService {

    @Value("opencage.api.key")
    private String apiKey;

    @Value("opencage.api.key")
    private static final String API_URL = "https://api.opencagedata.com/geocode/v1/json";
    private final RestTemplate restTemplate;

    public GeoService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String getCoordinates(String cep) {
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(API_URL)
                .queryParam("q", cep, ", Brasil")
                .queryParam("key", apiKey)
                .queryParam("limit", 1)
                .queryParam("language", "pt");

        String response = restTemplate.getForObject(uriBuilder.toUriString(), String.class);
        return response;
    }
}
