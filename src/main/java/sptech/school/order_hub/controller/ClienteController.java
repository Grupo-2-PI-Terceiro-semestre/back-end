package sptech.school.order_hub.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import sptech.school.order_hub.service.ClienteService;

@RestController
@RequestMapping("/clientes")
public class ClienteController {


    private final ClienteService service;

    public ClienteController(ClienteService clienteService) {
        this.service = clienteService;
    }

    @GetMapping("/api/cliente-random")
    public ResponseEntity<String> buscarUsuarios(){
        return ResponseEntity.status(200).body(service.getRandomUser());
    }
}
