package sptech.school.order_hub.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sptech.school.order_hub.services.ClienteServices;

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
    public ResponseEntity<String> findByQuantityUserOrder(@PathVariable Integer results) {
        return service.findByQuantityUserOrder(results);
    }
}
