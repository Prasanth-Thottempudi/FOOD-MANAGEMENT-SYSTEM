package com.food.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/api/map")
public class MapApiController {

    @Value("${geo.googleApiKey}")
    private String googleApiKey;

    private final RestTemplate rest = new RestTemplate();

    @GetMapping("/geocode")
    public ResponseEntity<String> geocode(@RequestParam String address) {
        String url = "https://maps.googleapis.com/maps/api/geocode/json?address=" + address + "&key=" + googleApiKey;
        String resp = rest.getForObject(url, String.class);
        return ResponseEntity.ok(resp);
    }
}
