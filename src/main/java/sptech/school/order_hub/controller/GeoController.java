package sptech.school.order_hub.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sptech.school.order_hub.service.GeoService;

@RestController
@RequestMapping("geolocalizacao")
public class GeoController {

    private final GeoService service;

    public GeoController(GeoService geoService) {
        this.service = geoService;
    }

    @GetMapping("/multipla")
    public ResponseEntity<String> buscarGeoLocalizacaoProxima(@RequestParam String cep){
        return ResponseEntity.status(200).body(service.getCoordinates(cep));
    }

}
