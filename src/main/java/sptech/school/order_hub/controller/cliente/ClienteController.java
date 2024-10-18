package sptech.school.order_hub.controller.cliente;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sptech.school.order_hub.controller.cliente.response.BuscarClientesResponseDTO;
import sptech.school.order_hub.entitiy.Cliente;
import sptech.school.order_hub.services.ClienteServices;

import java.util.List;

@RestController
@RequestMapping("/api/v1/clientes")
public class ClienteController {


    private final ClienteServices service;

    public ClienteController(ClienteServices clienteServices) {
        this.service = clienteServices;
    }

    @GetMapping("api/externo/random")
    public ResponseEntity<String> findByRandomUserApi() {
        return service.findByUserRandomApi();
    }

    @GetMapping("api/externo/random/results/{results}")
    public ResponseEntity<String> findByQuantityUser(@PathVariable Integer results) {
        return service.findByQuantityUser(results);
    }

    @GetMapping("api/externo/random/gender")
    public ResponseEntity<String> findByGanderUserRandomApi(@RequestParam String gender) {
        return service.findByGanderUserRandomApi(gender);
    }

    @GetMapping("api/externo/random/nat")
    public ResponseEntity<String> findByNatUser(@RequestParam String nat) {
        return service.findByNatUser(nat);
    }

    @GetMapping("api/externo/random/order/{results}")
    public ResponseEntity<Cliente[]> findByQuantityUserOrder(@PathVariable Integer results, @RequestParam String nat) {
        return service.findByQuantityUserOrder(results, nat);
    }

    @PostMapping("api/externo/pesquisa-binaria")
    public ResponseEntity<Integer> resultPesquisaBinaria(@RequestBody Cliente cliente) {
        return service.pesquisaBinaria(cliente);
    }

    @GetMapping("empresa/{idEmpresa}")
    public ResponseEntity<List<BuscarClientesResponseDTO>> buscarClientes(@PathVariable Integer idEmpresa) {
        return ResponseEntity.status(HttpStatus.OK).body(service.buscarClientes(idEmpresa));
    }
}
