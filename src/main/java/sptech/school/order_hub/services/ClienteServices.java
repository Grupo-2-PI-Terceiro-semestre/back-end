package sptech.school.order_hub.services;

import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
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

    public ResponseEntity<String> findByUserRandomApi() {
        return getRandomUser(null, null, null);
    }

    public ResponseEntity<String> findByQuantityUser(Integer results) {
        return getRandomUser(results, null, null);
    }

    public ResponseEntity<String> findByGanderUserRandomApi(String gender) {
        return getRandomUser(null, gender, null);
    }

    public ResponseEntity<String> findByQuantityUserOrder(Integer results) {

        // TODO: IMPLEMENTAR LOGICA DE NEGOCIO PARA ORDENAR O RESULTADO

        return null;
    }

    public ResponseEntity<String> findByNatUser(String nat) {
        return getRandomUser(null, null, nat);
    }

    private ResponseEntity<String> getRandomUser(Integer results, String gender, String nat) {
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(RANDOM_USER_API_ENDPOINT);
        if (results != null) {
            if (results < 1 || results > 10) {
                return ResponseEntity
                        .status(HttpStatus.FORBIDDEN)
                        .body("Valor não pode passar de 10 e não pode ser menor que 1");
            } else {
                uriBuilder.queryParam("results", results);
            }
        }

        if (gender != null) {
            if (!gender.equals("male") && !gender.equals("feminine")) {
                return ResponseEntity
                        .status(HttpStatus.FORBIDDEN)
                        .body("Invalid gender. Please use 'male' or 'feminine'.");
            }
            uriBuilder.queryParam("gender", gender);
        }

        if (nat != null) {
            if (nat.length() != 2) {
                return ResponseEntity
                        .status(HttpStatus.FORBIDDEN)
                        .body("Valor invalido");
            }
            uriBuilder.queryParam("nat", nat);
        }

        String finalUrl = uriBuilder.toUriString();

        try {
            var response = restTemplate.exchange(
                    finalUrl,
                    HttpMethod.GET,
                    null,
                    String.class
            );
            return response;
        } catch (HttpClientErrorException e) {
            throw e;
        }
    }
}
