package br.edu.ifpb.pdist.front.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class FrontendController {

     @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/frontend")
    public String getFrontendData() {
        // Fazer uma chamada à API do projeto "backend"
        String backendData = restTemplate.getForObject("http://localhost:8081/backend", String.class);
        return "Frontend Data: " + backendData;
    }
}