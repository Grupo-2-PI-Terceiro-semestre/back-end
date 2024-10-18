package sptech.school.order_hub.services;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;
import sptech.school.order_hub.controller.cliente.response.BuscarClientesResponseDTO;
import sptech.school.order_hub.entitiy.Cliente;
import sptech.school.order_hub.entitiy.Empresa;
import sptech.school.order_hub.repository.ClienteRepository;
import sptech.school.order_hub.repository.EmpresaRepository;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
public class ClienteServices {
    private Cliente[] clientes;

    private static final String RANDOM_USER_API_ENDPOINT = "https://randomuser.me/api/";
    private final RestTemplate restTemplate;

    private final ClienteRepository clienteRepository;
    private final EmpresaRepository empresaRepository;

    public ClienteServices(RestTemplate restTemplate, ClienteRepository clienteRepository, EmpresaRepository empresaRepository) {
        this.restTemplate = restTemplate;
        this.clienteRepository = clienteRepository;
        this.empresaRepository = empresaRepository;
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


    public List<BuscarClientesResponseDTO> buscarClientes(Integer idEmpresa) {

        Optional<Empresa> empresa = empresaRepository.findById(idEmpresa);

        if (empresa.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Empresa não existe");
        }
        return clienteRepository.findAllByEmpresa(empresa.get()).stream()
                .map(BuscarClientesResponseDTO::fromEntity)
                .toList();
    }

    public ResponseEntity<Cliente[]> findByQuantityUserOrder(Integer results, String nat) {
        ResponseEntity<String> response = getRandomUser(results, null, nat);

        this.clientes = new Cliente[results];
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

                cliente.setIdPessoa((int) (Math.random() * 10000));
                cliente.setNomePessoa(firstName);
                cliente.setDataNascimento(dateOfBirth.toLocalDate());
                cliente.setEmailPessoa(document);

                clientes[i] = cliente;
            }

        } catch (Exception e) {
            System.err.println("Error processing JSON: " + e.getMessage());
        }

        selectionSortOtimizado(clientes);

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

    private static Period calcularPeriodo(LocalDate dataNascimento, LocalDate dataAtual) {
        if (dataNascimento != null && dataAtual != null) {
            return Period.between(dataNascimento, dataAtual);
        } else {
            return Period.ZERO;
        }
    }

    private static int compararIdades(Cliente cliente1, Cliente cliente2) {
        Period idadeCliente1 = calcularPeriodo(cliente1.getDataNascimento(), LocalDate.now());
        Period idadeCliente2 = calcularPeriodo(cliente2.getDataNascimento(), LocalDate.now());


        if (idadeCliente1.getYears() != idadeCliente2.getYears()) {
            return Integer.compare(idadeCliente1.getYears(), idadeCliente2.getYears());
        } else if (idadeCliente1.getMonths() != idadeCliente2.getMonths()) {
            return Integer.compare(idadeCliente1.getMonths(), idadeCliente2.getMonths());
        } else {
            return Integer.compare(idadeCliente1.getDays(), idadeCliente2.getDays());
        }
    }

    private static void selectionSortOtimizado(Cliente[] clientes) {
        for (int i = 0; i < clientes.length - 1; i++) {
            int indiceMenor = i;
            for (int j = i + 1; j < clientes.length; j++) {
                if (compararIdades(clientes[j], clientes[indiceMenor]) < 0) {
                    indiceMenor = j;
                }
            }
            Cliente aux = clientes[i];
            clientes[i] = clientes[indiceMenor];
            clientes[indiceMenor] = aux;
        }
    }

    private static int pesquisaBinaria(Cliente cliente, Cliente[] vetorDeCliente) {
        int inicio = 0;
        int fim = vetorDeCliente.length - 1;
        while (inicio <= fim) {
            int meio = (inicio + fim) / 2;
            int comparacao = compararIdades(cliente, vetorDeCliente[meio]);
            if (comparacao == 0) {
                return meio;
            } else if (comparacao > 0) {
                inicio = meio + 1;
            } else {
                fim = meio - 1;
            }
        }
        return -1;
    }

    public ResponseEntity<Integer> pesquisaBinaria(Cliente cliente) {
        if (pesquisaBinaria(cliente, clientes) == -1) {
            return ResponseEntity.status(404).body(-1);
        }
        return ResponseEntity.status(200).body(pesquisaBinaria(cliente, clientes));
    }
}
