package sptech.school.order_hub.service;

import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class ClienteService {

    private static final String RANDOM_USER_API_ENDPOINT = "https://randomuser.me/api/";
    private final RestTemplate restTemplate;

    public ClienteService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String getRandomUser() {
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(RANDOM_USER_API_ENDPOINT);

        String finalUrl = uriBuilder.toUriString();
        System.out.println("URL gerada: " + finalUrl);

        try {
            ResponseEntity<String> response = restTemplate.exchange(
                    finalUrl,
                    HttpMethod.GET,
                    null,
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
