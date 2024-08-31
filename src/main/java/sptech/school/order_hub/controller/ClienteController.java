package sptech.school.order_hub.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sptech.school.order_hub.services.ClienteServices;

@RestController
@RequestMapping("/api/v1/clientes")
public class ClienteController {


    private final ClienteServices service;

    public ClienteController(ClienteServices clienteServices) {
        this.service = clienteServices;
    }

    @GetMapping("/api/random")
    public ResponseEntity<String> buscarUsuarios() {
        return ResponseEntity.status(200).body(service.getRandomUser());
    }
}
