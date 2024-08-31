package sptech.school.order_hub.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class ClienteServices {

    private static final String RANDOM_USER_API_ENDPOINT = "https://randomuser.me/api/";
    private final RestTemplate restTemplate;

    public ClienteServices(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String getRandomUser() {
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(RANDOM_USER_API_ENDPOINT);

        String finalUrl = uriBuilder.toUriString();
        try {
            ResponseEntity<String> response = restTemplate.exchange(
                    finalUrl,
                    HttpMethod.GET,
                    null,
                    String.class
            );
            return response.getBody();
        } catch (HttpClientErrorException e) {
            throw e;
        }
    }
}
