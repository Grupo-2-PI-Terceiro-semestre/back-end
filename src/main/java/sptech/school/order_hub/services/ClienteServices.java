package sptech.school.order_hub.services;

import com.fasterxml.jackson.databind.util.JSONPObject;
import org.apache.tomcat.util.json.JSONFilter;
import org.apache.tomcat.util.json.JSONParser;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import sptech.school.order_hub.entitiy.Cliente;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

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

    public ResponseEntity<Cliente[]> findByQuantityUserOrder(Integer results, String nat) {
        ResponseEntity<String> response = getRandomUser(results, null, nat);

        Cliente[] clientes = new Cliente[results];
        try {
            JSONObject json = new JSONObject(response.getBody());

            JSONArray resultsArray = json.getJSONArray("results");

            for (int i = 0; i < resultsArray.length(); i++) {

                Cliente cliente = new Cliente();

                JSONObject user = resultsArray.getJSONObject(i);

                JSONObject nameObject = user.getJSONObject("name");
                JSONObject dataObject = user.getJSONObject("dob");
                JSONObject documentObject = user.getJSONObject("id");

                String dateString = dataObject.optString("date", "default date");
                String firstName = nameObject.optString("first", "default name");
                String document = documentObject.optString("value", "default value");
                OffsetDateTime dateOfBirth = OffsetDateTime.parse(dateString, DateTimeFormatter.ISO_OFFSET_DATE_TIME);

                cliente.setId((int) (Math.random() * 10000));
                cliente.setNomeCliente(firstName);
                cliente.setDataNascimento(dateOfBirth.toLocalDate());
                cliente.setCpf(document);

                clientes[i] = cliente;
            }

        } catch (Exception e) {
            System.err.println("Error processing JSON: " + e.getMessage());
        }

        return ResponseEntity.status(200).body(clientes);
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
