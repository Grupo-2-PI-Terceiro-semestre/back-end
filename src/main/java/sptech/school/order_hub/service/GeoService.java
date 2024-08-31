package sptech.school.order_hub.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class GeoService {

    @Value("${client.fid}")
    private String clientFid;

    @Value("${client.id}")
    private String clientId;

    @Value("${client.url.hash}")
    private String clientUrlHash;

    private static final String CLIENT_ENDPOINT = "https://jonathanaparecido80-034c9cc7727f92bf.api.findcep.com";
    private final RestTemplate restTemplate;

    public GeoService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String getCepInfo(String cep) {
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(CLIENT_ENDPOINT)
                .path("https://randomuser.me/api/");

        String finalUrl = uriBuilder.buildAndExpand(cep).toUriString();
        System.out.println("URL gerada: " + finalUrl);

        try {
            ResponseEntity<String> response = restTemplate.exchange(
                    uriBuilder.buildAndExpand(cep).toUriString(),
                    HttpMethod.GET,
                    String.class
            );
            return response.getBody();
        } catch (HttpClientErrorException e) {
            // Captura e exibe detalhes do erro
            HttpStatus statusCode = (HttpStatus) e.getStatusCode();
            String errorResponse = e.getResponseBodyAsString();
            System.err.println("Erro ao acessar a API: Status Code: " + statusCode);
            System.err.println("Resposta de Erro: " + errorResponse);
            throw e; // Re-lança a exceção após logging
        }
    }
}
